package applicotest.android.com.android_tic_tac_toe;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.rebound.BaseSpringSystem;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringListener;
import com.facebook.rebound.SpringSystem;
import com.facebook.rebound.SpringSystemListener;

import java.util.ArrayList;
import java.util.List;

import applicotest.android.com.android_tic_tac_toe.Utilities.Util;

/**
 * This activity allows users to play at tic tac toe game.
 *
 * @author JoshuaWilliams
 * @version 1.0
 */

public class MainActivity extends Activity {

    private final String LOG_TAG = "MainActivity";
    public BoardLogic boardLogic;
    public BoardAdapter boardAdapter;
    private static TextView promptTextView;
    private SpringSystem springSystem;
    private Spring spring;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initiateActivity();
    }

    /**
     * method to initiate the activity. It finds views and starts the game.
     */
    public void initiateActivity() {
        Util.context = this;
        GridView board = (GridView) findViewById(R.id.board);

        boardAdapter = new BoardAdapter(this, this);
        board.setAdapter(boardAdapter);

        promptTextView = (TextView) findViewById(R.id.title);
        promptTextView.setTypeface(Util.getTypfaceText("kommitt.ttf"));

        boardLogic = new BoardLogic(MainActivity.this, MainActivity.this);


        showStartDialog();
    }

    private void showStartDialog() {
        if(dialog != null && dialog.isShowing())
        {
            return;
        }
        Log.i(LOG_TAG, "Showing Beginning of Game Dialog");

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.alertdialog_view, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);

        TextView titleTextView = (TextView) view.findViewById(R.id.title_textView);
        TextView messageTextView = (TextView) view.findViewById(R.id.messageTextView);



        titleTextView.setText("Welcome to Tic-Tac-Toe!");
        messageTextView.setText("This just a regular game of tic  tac toe. Think you have what it takes to beat the AI? When you are ready press OK!");




        Button okButton = (Button) view.findViewById(R.id.okButton);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boardLogic.startComputerTurn();
                dialog.dismiss();
            }
        });

        dialog = builder.create();
        dialog.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * This method sets the prompt for the textview so the user can know the state of each turn throughout the game.
     *
     * @param prompt - The prompt in which the textview will hold.
     */
    public static void setPrompt(String prompt)
    {
        promptTextView.setText(prompt);
    }


    /**
     * Creates Facebook's fancy spring animation!
     *
     * Credits : Facebook - Rebound
     * License
     * BSD License
     * For Rebound software
     * Copyright (c) 2013, Facebook, Inc. All rights reserved.
     * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
     * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
     * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
     *THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
     *
     * @param v
     */
    private void performSpringAnimation(final View v) {

        Log.i(LOG_TAG, "Creating Spring");

        springSystem = SpringSystem.create();
        spring = springSystem.createSpring();
        spring.setSpringConfig(new SpringConfig(800, 20));
        spring.setVelocity(10.0);

        spring.addListener(new SpringListener() {
            @Override
            public void onSpringUpdate(Spring spring) {
                float value = (float) spring.getCurrentValue();
                float scale = 1f - (value * 0.5f);
                v.setScaleX(scale);
                v.setScaleY(scale);
            }

            @Override
            public void onSpringAtRest(Spring spring) {

                spring.setEndValue(0);
            }

            @Override
            public void onSpringActivate(Spring spring) {

            }

            @Override
            public void onSpringEndStateChange(Spring spring) {
            }
        });

        spring.setEndValue(1);
    }


    /**
     * Sets the onclick listener for the restart button.
     *
     * @param v - the view that has the listener attached.
     */
    public void restartButtonOnClick(View v)
    {
        Toast.makeText(this, "Restarting game...", Toast.LENGTH_SHORT).show();
        performSpringAnimation(v);
        boardLogic.restartGame();
        Toast.makeText(this, "Game restarted.", Toast.LENGTH_SHORT).show();

    }



    /******************************************************************************************
     *                              CALL BACKS                                                *
     *****************************************************************************************/

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        Log.i(LOG_TAG, "On Back Press");
    }


    @Override
    public void onResume()
    {
        super.onResume();
        if(isActivityGarbageCollected())
        {
            initiateActivity();
        }
        Log.i(LOG_TAG, "On Resume");
    }


    @Override
    public void onStart()
    {
        super.onStart();
        if(isActivityGarbageCollected())
        {
            initiateActivity();
        }
        Log.i(LOG_TAG, "On Start");

    }



    @Override
    public void onDestroy()
    {
        super.onDestroy();
        cleanUpActivity();
        Log.i(LOG_TAG, "On Destroy");

    }



    @Override
    public void onPause()
    {
        super.onPause();
        cleanUpActivity();
        Log.i(LOG_TAG, "On Pause");

    }



    @Override
    public void onStop()
    {
        super.onStop();
        cleanUpActivity();
        Log.i(LOG_TAG, "On Stop");

    }
    /*******************************************************************************************/

    /**
     * This method cleans up the activity by setting the global variables to null so that
     * it can be garbage collected at the next garbage collection event.
     */
    private void cleanUpActivity() {
        Log.i(LOG_TAG, "Cleaning up activity");

        spring = null;
        springSystem = null;
        promptTextView = null;
    }

    private boolean isActivityGarbageCollected()
    {
        if(promptTextView == null|| boardLogic == null || boardAdapter == null)
        {
            return true;
        }
        return false;
    }

}
