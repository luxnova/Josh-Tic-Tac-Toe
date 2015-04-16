package applicotest.android.com.android_tic_tac_toe;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringListener;
import com.facebook.rebound.SpringSystem;

import java.util.ArrayList;
import java.util.List;

import applicotest.android.com.android_tic_tac_toe.Utilities.Util;
import applicotest.android.com.android_tic_tac_toe.animation.SupportAnimator;
import applicotest.android.com.android_tic_tac_toe.animation.ViewAnimationUtils;
import applicotest.android.com.android_tic_tac_toe.widget.RevealFrameLayout;


/**
 * This adapter goes for the board. It animates the board and handles the UI/UX.
 *
 * Created by JoshuaWilliams on 4/15/15.
 *
 * @version 1.0
 */
public class BoardAdapter extends BaseAdapter implements View.OnClickListener {
    private final String LOG_TAG = "BoardAdapter";
    private final MainActivity mainActivity;
    List<CellData> cellDataList = new ArrayList<>();
    Context context;



    public BoardAdapter(Context context, MainActivity mainActivity)
    {
        this.mainActivity = mainActivity;
        this.context = context;
    }
    @Override
    public int getCount() {
        return 9;
    }

    @Override
    public CellData getItem(int i) {
        return cellDataList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder viewHolder;

        if (view == null) {
            LayoutInflater inflater;
            viewHolder = new ViewHolder();

            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.board_cell, viewGroup, false);

            viewHolder.cell = (ImageView)view.findViewById(R.id.cell);
            viewHolder.textView = (TextView)view.findViewById(R.id.textview);

            CellData cellData = new CellData(i, view, viewGroup);
            cellDataList.add(cellData);
            viewHolder.cell.setTag(cellData);

            view.setTag(viewHolder);
            viewHolder = (ViewHolder) view.getTag();

        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.cell.setOnClickListener(this);

        return view;
    }

    /**
     * Onclick listener for the view (the cell)
     * This method works by checking to see if the user clicked on a tile already used.
     * Then it updates the board. It then gets the list for the user and adds the cell and removes it from the view.
     * From here it is a circle until the end of he game is reached.
     *
     * @param view - the view at which the listener is attached.
     */

    @Override
    public void onClick(View view) {


        CellData cellData = (CellData) view.getTag();


        Log.i(LOG_TAG, "" + cellData.getPosition());

        int cell = cellData.getPosition();

        if(mainActivity.boardLogic.computerOwnsCells(cell)
                || mainActivity.boardLogic.userOwnsCells(cell)
                || BoardLogic.computerIsPlaying)
        {
            return;
        }

        updateBoard(0, cellData);

        mainActivity.boardLogic.getUSER_OWNED_CELLS().add(cell);
        mainActivity.boardLogic.removeFromBoard(cell);

        if(mainActivity.boardLogic.hasGameEnded())
        {
            int winner = mainActivity.boardLogic.checkWinner();
            mainActivity.boardLogic.showEndGameDialog(winner);
        }
        else
        {
            mainActivity.boardLogic.startComputerTurn();
        }

    }


    /**
     * This method updates the board by performing the animations and updatnig the cells.
     *
     * @param from - determines whether the method is called from the computer or from the computer or user.
     * @param cellData - data associated with the row or gridITem.
     */
    public void updateBoard(int from, CellData cellData) {


        int cell = cellData.getPosition();
        View view = cellData.getView();
        ViewGroup viewGroup = cellData.getViewGroup();

        Log.i(LOG_TAG, "Updating Board. " + cell);


        View boardCell =  getView(cell, view, viewGroup);
        TextView textView = (TextView) boardCell.findViewById(R.id.textview);
        textView.setTypeface(Util.getTypfaceText("Yellowr.ttf"));

        if(from == 0)
        {
            textView.setText("X");

        }
        else
        {
            textView.setText("O");
        }
        performAnimation(from, view);
        mainActivity.boardLogic.printCells();

    }


    /**
     * This method performs the animation.
     *
     * @param code - determines whether the method is called from the computer or from the computer or user.
     * @param view - view being animated.
     */


    private void performAnimation(final int code, View view)
    {


        view = view.findViewById(R.id.parent);


        int cx = (view.getLeft() + view.getRight()) / 2;
        int cy = (view.getTop() + view.getBottom()) / 2;

        float finalRadius = ViewAnimationUtils.hypo(view.getWidth(), view.getHeight());

        SupportAnimator animator = ViewAnimationUtils.createCircularReveal(view, cx, cy, 0, finalRadius);

        animator.setInterpolator(new AccelerateDecelerateInterpolator());

        final View finalView = view;
        animator.addListener(new SupportAnimator.AnimatorListener() {
            @Override
            public void onAnimationStart() {
                MainActivity.setPrompt("Your turn.");
                BoardLogic.computerIsPlaying = false;

                ImageView bg = (ImageView) finalView.findViewById(R.id.cell);

                if(code == 0) {
                    bg.setBackgroundColor(Color.parseColor("#FF020FFF"));
                }
                else
                {
                    bg.setBackgroundColor(Color.parseColor("#FFD50400"));
                }
            }

            @Override
            public void onAnimationEnd() {
            }

            @Override
            public void onAnimationCancel() {

            }

            @Override
            public void onAnimationRepeat() {

            }
        });
        animator.setDuration(900);
        animator.start();
    }


    /**
     * View holder class so that the views do always get recycled.
     */


    class ViewHolder {
        ImageView cell;
        TextView textView;
    }

}
