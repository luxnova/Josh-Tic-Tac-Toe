package applicotest.android.com.android_tic_tac_toe;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by JoshuaWilliams on 4/15/15.
 *
 * @version 1.0
 */
public class BoardLogic {
    private final String LOG_TAG = "BoardLogic";

    public List<Integer> USER_OWNED_CELLS = new ArrayList<>();
    public List<Integer> COMPUTER_OWNED_CELLS = new ArrayList<>();
    public List<Integer> BOARD_CELLS = new ArrayList<>();
    private Context context;
    public static boolean computerIsPlaying;
    private MainActivity mainActivity;
    AlertDialog dialog;

    /**
     * Constructor for the Board Logic class. Initiates the data and needed variables.
     *
     * @param context - context of the activity using the board logic class.
     * @param mainActivity - The activity instance that is calling the board logic class.
     */
    public BoardLogic(Context context, MainActivity mainActivity)
    {
        for(int i = 0; i < 9; i++)
        {
            BOARD_CELLS.add(i);
        }
        Log.i(LOG_TAG, "Initiating Board");

        this.context = context;
        this.mainActivity = mainActivity;
    }


    /**
     * This method determines if the cell on the board is owned by the user.
     *
     * @param cell - the cell
     * @return
     */
    public boolean userOwnsCells(int cell) {
        return USER_OWNED_CELLS.contains(cell);
    }



    /**
     * This method determines if the cell on the board is owned by the computer.
     *
     * @param cell - the cell
     * @return
     */
    public boolean computerOwnsCells(int cell)
    {
        return COMPUTER_OWNED_CELLS.contains(cell);
    }


    /**
     * This method gets the list of owned cells for the user..
     *
     * @return - list off owner cells
     */
    public List<Integer> getUSER_OWNED_CELLS()
    {
        Log.i(LOG_TAG, "Getting User Owned Cells");
        return USER_OWNED_CELLS;
    }


    /**
     * Removes cell from the board.
     * @param cell
     */
    public void removeFromBoard(int cell) {
        Log.i(LOG_TAG, "Removing " + cell + " from board.");
        for(int i = 0; i < BOARD_CELLS.size(); i++)
        {
            if(cell == BOARD_CELLS.get(i))
            {
                BOARD_CELLS.remove(i);
            }
        }
    }

    /**
     * This method checks if any one of the game ending conditions have been met.
     * Condition 1: computerHasWon (has 3 consecutive cells)
     * Condition 2: userHasWon (has 3 consecutive cells)
     * Condiiton 3: does the board have any empty cells.
     *
     *
     *  * @return
     */
    public boolean hasGameEnded() {

        if (computerHasWon())
        {
            Log.i(LOG_TAG,"Computer has won.");
            return true;
        }

        if(userHasWon())
        {
            Log.i(LOG_TAG,"User has won.");
            return true;
        }

        if(BOARD_CELLS.size() == 0)
        {
            Log.i(LOG_TAG, "Game Has ended.");
            return true;
        }
        Log.i(LOG_TAG, "Game Has Not ended");
        return false;
    }

    /**
     * Shows the dialog for the for end of the game.
     *
     * @param winner - code to determine who was the winner or if it was a draw.
     */

    public void showEndGameDialog(int winner) {
        Log.i(LOG_TAG, "Showing End of Game Dialog");

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.alertdialog_view, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(view);

        TextView titleTextView = (TextView) view.findViewById(R.id.title_textView);
        TextView messageTextView = (TextView) view.findViewById(R.id.messageTextView);



        if(winner == 0) {
            titleTextView.setText(context.getString(R.string.You_win));
            messageTextView.setText(context.getString(R.string.GreatJob));
        }else if(winner == 1)
        {
            titleTextView.setText(context.getString(R.string.You_Lose));
            messageTextView.setText(context.getString(R.string.awe_than_sics));
        }
        else
        {
            titleTextView.setText(context.getString(R.string.tie));
            messageTextView.setText(context.getString(R.string.tiedmatch));
        }



        Button okButton = (Button) view.findViewById(R.id.okButton);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restartGame();
                dialog.dismiss();
            }
        });

        dialog = builder.create();
        dialog.show();
    }

    /**
     * Method restarts game and resets the variables.
     *
     */
    public void restartGame() {
        Log.i(LOG_TAG, "Restarting game.");

        USER_OWNED_CELLS = new ArrayList<>();
        COMPUTER_OWNED_CELLS = new ArrayList<>();
        BOARD_CELLS = new ArrayList<>();

        for(int i = 0; i < 9; i++)
        {
            BOARD_CELLS.add(i);
        }

        mainActivity.initiateActivity();
    }

    /**
     * Thsis method ckecks 2 condition  to see if the user or computer has won.
     * @return
     */
    public int checkWinner() {
        Log.i(LOG_TAG, "Checking Winner.");

        if(userHasWon())
        {
            return 0;
        }
        else if(computerHasWon())
        {
            return 1;
        }
        else
        {
            return 2;
        }
    }

    /**
     * These methods all determine if the user has won and if they did then how they win.
     *
     * @return whether the user has won or not.
     */
    private boolean computerHasWon() {
        return computerWonByCell0() || computerWonByCell1() || computerWonByCell2() || computerWonByCell3()
                || computerWonByCell4() || computerWonByCell5() || computerWonByCell6()
                || computerWonByCell7() || computerWonByCell8();

    }

    private boolean userHasWon() {
        return userWonByCell0() || userWonByCell1() || userWonByCell2() || userWonByCell3()
                || userWonByCell4() || userWonByCell5() || userWonByCell6() || userWonByCell7() || userWonByCell8();
    }

    private boolean userWonByCell0()
    {
        return userOwnsCells(0) && userOwnsCells(1) && userOwnsCells(2) ||
                userOwnsCells(0) && userOwnsCells(3) && userOwnsCells(6) ||
                userOwnsCells(0) && userOwnsCells(4) && userOwnsCells(8);

    }

    private boolean userWonByCell1()
    {
        return userOwnsCells(1) && userOwnsCells(0) && userOwnsCells(2) ||
                userOwnsCells(1) && userOwnsCells(4) && userOwnsCells(7);

    }

    private boolean userWonByCell2()
    {
        return userOwnsCells(2) && userOwnsCells(0) && userOwnsCells(1) ||
                userOwnsCells(2) && userOwnsCells(5) && userOwnsCells(8) ||
                userOwnsCells(2) && userOwnsCells(4) && userOwnsCells(6);

    }

    private boolean userWonByCell3()
    {
        return userOwnsCells(3) && userOwnsCells(0) && userOwnsCells(6) ||
                userOwnsCells(3) && userOwnsCells(4) && userOwnsCells(5);

    }

    private boolean userWonByCell4()
    {
        return userOwnsCells(4) && userOwnsCells(3) && userOwnsCells(5) ||
                userOwnsCells(4) && userOwnsCells(1) && userOwnsCells(7) ||
                userOwnsCells(4) && userOwnsCells(2) && userOwnsCells(6) ||
                userOwnsCells(4) && userOwnsCells(0) && userOwnsCells(8);

    }


    private boolean userWonByCell5()
    {
        return userOwnsCells(5) && userOwnsCells(2) && userOwnsCells(8) ||
                userOwnsCells(5) && userOwnsCells(3) && userOwnsCells(4);

    }

    private boolean userWonByCell6()
    {
        return userOwnsCells(6) && userOwnsCells(3) && userOwnsCells(0) ||
                userOwnsCells(6) && userOwnsCells(4) && userOwnsCells(2) ||
                userOwnsCells(6) && userOwnsCells(7) && userOwnsCells(8);

    }

    private boolean userWonByCell7()
    {
        return userOwnsCells(7) && userOwnsCells(1) && userOwnsCells(4) ||
                userOwnsCells(7) && userOwnsCells(6) && userOwnsCells(8);

    }

    private boolean userWonByCell8()
    {
        return userOwnsCells(8) && userOwnsCells(0) && userOwnsCells(4) ||
                userOwnsCells(8) && userOwnsCells(2) && userOwnsCells(5) ||
                userOwnsCells(8) && userOwnsCells(6) && userOwnsCells(7);

    }


    /**
     * This also tells if the computer has won and how the computer won.
     *
     * @return whether the computer has won or not.
     */

    private boolean computerWonByCell0()
    {
        return computerOwnsCells(0) && computerOwnsCells(1) && computerOwnsCells(2) ||
                computerOwnsCells(0) && computerOwnsCells(3) && computerOwnsCells(6) ||
                computerOwnsCells(0) && computerOwnsCells(4) && computerOwnsCells(8);

    }


    private boolean computerWonByCell1()
    {
        return computerOwnsCells(1) && computerOwnsCells(0) && computerOwnsCells(2) ||
                computerOwnsCells(1) && computerOwnsCells(4) && computerOwnsCells(7);

    }

    private boolean computerWonByCell2()
    {
        return computerOwnsCells(2) && computerOwnsCells(0) && computerOwnsCells(1) ||
                computerOwnsCells(2) && computerOwnsCells(5) && computerOwnsCells(8) ||
                computerOwnsCells(2) && computerOwnsCells(4) && computerOwnsCells(6);

    }

    private boolean computerWonByCell3()
    {
        return computerOwnsCells(3) && computerOwnsCells(0) && computerOwnsCells(6) ||
                computerOwnsCells(3) && computerOwnsCells(4) && computerOwnsCells(5);

    }

    private boolean computerWonByCell4()
    {
        return computerOwnsCells(4) && computerOwnsCells(3) && computerOwnsCells(5) ||
                computerOwnsCells(4) && computerOwnsCells(1) && computerOwnsCells(7) ||
                computerOwnsCells(4) && computerOwnsCells(2) && computerOwnsCells(6) ||
                computerOwnsCells(4) && computerOwnsCells(0) && computerOwnsCells(8);

    }

    private boolean computerWonByCell5()
    {
        return computerOwnsCells(5) && computerOwnsCells(2) && computerOwnsCells(8) ||
                computerOwnsCells(5) && computerOwnsCells(3) && computerOwnsCells(4);

    }

    private boolean computerWonByCell6()
    {
        return computerOwnsCells(6) && computerOwnsCells(3) && computerOwnsCells(0) ||
                computerOwnsCells(6) && computerOwnsCells(4) && computerOwnsCells(2) ||
                computerOwnsCells(6) && computerOwnsCells(7) && computerOwnsCells(8);

    }

    private boolean computerWonByCell7()
    {
        return computerOwnsCells(7) && computerOwnsCells(1) && computerOwnsCells(4) ||
                computerOwnsCells(7) && computerOwnsCells(6) && computerOwnsCells(8);

    }

    private boolean computerWonByCell8()
    {
        return computerOwnsCells(8) && computerOwnsCells(0) && computerOwnsCells(4) ||
                computerOwnsCells(8) && computerOwnsCells(2) && computerOwnsCells(5) ||
                computerOwnsCells(8) && computerOwnsCells(6) && computerOwnsCells(7);

    }

    /**
     * Simply starts the computer's turn.
     */
    public void startComputerTurn() {
        Log.i(LOG_TAG, context.getString(R.string.startcputurn));
        MainActivity.setPrompt(context.getString(R.string.computer_is_playing));

        computerIsPlaying = true;
        startDelay();
    }

    /**
     * This will make the computer make a random celection on an empy board/
     */
    public void makeRandomSelection() {
        Log.i(LOG_TAG, "Computer Making Random Selection.");

        int nextCell;
        if(BOARD_CELLS.size() - 1 > 0) {
            nextCell = randInt(0, BOARD_CELLS.size() - 1);
        }
        else
        {
            nextCell = 0;
        }

        Log.i(LOG_TAG, "make Random Selection - " + nextCell + " " + BOARD_CELLS.size());
        nextCell = BOARD_CELLS.get(nextCell);
        makeSelection(nextCell);
    }


    /**
     * This method makes the computer make a selection on a cell/ if the integer object that references this row the it starts over to repick.
     * It then removes the cell from the board and removes the board.
     * @param cell
     */
    private void makeSelection(int cell) {
        Log.i(LOG_TAG, "Computer Making Selection. " + cell);

        if(userOwnsCells(cell) || computerOwnsCells(cell))
        {
            makeRandomSelection();
            return;
        }

        removeFromBoard(cell);
        COMPUTER_OWNED_CELLS.add(cell);

         CellData cellData = mainActivity.boardAdapter.getItem(cell);
         mainActivity.boardAdapter.updateBoard(1, cellData);

        if(hasGameEnded())
        {
            int winner = checkWinner();
            showEndGameDialog(winner);
        }
    }

    /**
     * This method produces random number within a range.
     *
     * @param min - minimum range.
     * @param max - maximum range.
     * @return the random integer.
     */

    public static int randInt(int min, int max) {
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }


    /**
     * Prints all cells.
     */
    public void printCells() {
        for(int i = 0; i < COMPUTER_OWNED_CELLS.size(); i++)
        {
            Log.i(LOG_TAG, "COMPUTER CELLS - " + COMPUTER_OWNED_CELLS.get(i));
        }

        for(int i = 0; i < USER_OWNED_CELLS.size(); i++)
        {
            Log.i(LOG_TAG, "USER CELLS - " + USER_OWNED_CELLS.get(i));
        }

        for(int i = 0; i < BOARD_CELLS.size(); i++)
        {
            Log.i(LOG_TAG, "EMPTY CELLS - " + BOARD_CELLS.get(i));
        }

        System.out.println("**********************************************************************************");
    }


    /**
     * Timer that similates the computer 'thinking' to make a tirn.
     */
    int seconds = 0;

    //runs without a timer by reposting this handler at the end of the runnable
    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {

        @Override
        public void run() {
            seconds = seconds+1;

            if(seconds == 3)
            {
                seconds = 0;
                makeRandomSelection();
                timerHandler.removeCallbacks(timerRunnable);
                return;
            }
            Log.i("Timer", "" + seconds);

            timerHandler.postDelayed(this, 1000);
        }
    };

    private void startDelay() {
        timerHandler.postDelayed(timerRunnable, 0);
    }
}
