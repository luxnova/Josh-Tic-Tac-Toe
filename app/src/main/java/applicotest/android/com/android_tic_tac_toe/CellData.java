package applicotest.android.com.android_tic_tac_toe;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by JoshuaWilliams on 4/16/15.
 *
 * @version 1.0
 *
 * This class holds the row data for the adapter.
 */
public class CellData {
    View view;
    ViewGroup viewGroup;
    int position;

    public CellData(int position, View view, ViewGroup viewGroup)
    {
        this.position = position;
        this.view = view;
        this.viewGroup = viewGroup;
    }

    public CellData(){}

    public void setPosition(int position)
    {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public void setView(View view) {
        this.view = view;
    }

    public void setViewGroup(ViewGroup viewGroup) {
        this.viewGroup = viewGroup;
    }

    public View getView() {
        return view;
    }

    public ViewGroup getViewGroup() {
        return viewGroup;
    }
}
