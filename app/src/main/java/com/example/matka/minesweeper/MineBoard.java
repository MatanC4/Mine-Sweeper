package com.example.matka.minesweeper;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.util.Log;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.HashMap;

import bl.CellResult;
import bl.GameEvent;
import bl.GameListener;
import bl.GameLogic;
import bl.Level;
import components.*;
import timer.GameTimer;


public class MineBoard extends AppCompatActivity implements TileButtonListener , GameListener{

    private GameLogic gameLogic;
    private TableLayout tableLayout;
    private String level;
    private ImageButton flag;
    private TileButton [][] board;
    private android.os.Handler handler , handlerDelayEndGame;
    private int counter = 0 , counterDelay = 0;
    private GameTimer timer, timerDelayEndGame;
    private HashMap <Integer, Integer> resultsMapping;
    private GoogleApiClient client;
    private boolean isWon;
    private boolean flagMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        resultsMapping = initCellImagesMapping(getIntent().getStringExtra("level"));
        LinearLayout rowsLayout;
        LinearLayout colsLayout;
        gameLogic = initGameLogic(gameLogic);

        SharedPreferences lastPlayed = getApplicationContext().getSharedPreferences("last_game", 0);
        SharedPreferences.Editor editor = lastPlayed.edit();
        editor.putString("last_played", level);
        editor.apply();
        board = new TileButton[gameLogic.getNumOfRows()][gameLogic.getNumOfCols()];

        rowsLayout = new LinearLayout(this);
        rowsLayout.setBackgroundColor(Color.TRANSPARENT);
        rowsLayout.setOrientation(LinearLayout.VERTICAL);
        //rowsLayout.setPadding(0,50,2,0);

        rowsLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        for (int col = 0; col < gameLogic.getNumOfRows(); col++) {
             colsLayout = new LinearLayout(this);
            colsLayout.setBackgroundColor(Color.TRANSPARENT);
            colsLayout.setOrientation(LinearLayout.HORIZONTAL);
            //colsLayout.setPadding(5,5,5,5);
            colsLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            for (int row = 0; row < gameLogic.getNumOfCols(); row++) {
                board[col][row]  = new TileButton(this);
                board[col][row].setPosition(row, col);
                board[col][row].setListener(this);
                board[col][row].setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                colsLayout.addView( board[col][row]);
                if(level.equals("hard")) {
                    board[col][row].setBackgroundResource(R.drawable.box_grey_hard);
                }else{
                        board[col][row].setBackgroundResource(R.drawable.box_grey);
                }
            }
            rowsLayout.addView(colsLayout);

        }


        RelativeLayout mainLayout = (RelativeLayout) findViewById(R.id.mainLayout);
        mainLayout.addView(rowsLayout);
        mainLayout.setGravity(Gravity.CENTER);
        handleFlag();
        timerRun();

    }

    private void handleFlag() {
        flag = (ImageButton) findViewById(R.id.flagMode);
        flag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!flagMode){
                    flag.setBackgroundResource(R.drawable.pressed_flag);
                    flagMode = true;
                }else{
                    flag.setBackgroundResource(R.drawable.flag_white);
                    flagMode = false;
                }
            }
        });

    }

    private HashMap<Integer,Integer> initCellImagesMapping(String level) {
        HashMap <Integer, Integer> resultsMapping = new HashMap<Integer , Integer>();

        if(level.equals(Level.hard.toString())){
            resultsMapping.put(0, R.drawable.blank_tile_hard);
            resultsMapping.put(1, R.drawable.digit_1_hard);
            resultsMapping.put(2, R.drawable.digit_2_hard);
            resultsMapping.put(3, R.drawable.digit_3_hard);
            resultsMapping.put(4, R.drawable.digit_4_hard);
            resultsMapping.put(5, R.drawable.digit_5_hard);
            resultsMapping.put(6, R.drawable.digit_6_hard);
            resultsMapping.put(7, R.drawable.digit_7_hard);
            resultsMapping.put(8, R.drawable.digit_8_hard);
            resultsMapping.put(-1, R.drawable.red_mine_hard);
            resultsMapping.put(10, R.drawable.red_flag_hard);
            resultsMapping.put(11, R.drawable.box_grey_hard);

        }
        else {
            resultsMapping.put(0, R.drawable.blank_tile);
            resultsMapping.put(1, R.drawable.digit_1);
            resultsMapping.put(2, R.drawable.digit_2);
            resultsMapping.put(3, R.drawable.digit_3);
            resultsMapping.put(4, R.drawable.digit_4);
            resultsMapping.put(5, R.drawable.digit_5);
            resultsMapping.put(6, R.drawable.digit_6);
            resultsMapping.put(7, R.drawable.digit_7);
            resultsMapping.put(8, R.drawable.digit_8);
            resultsMapping.put(-1, R.drawable.red_mine);
            resultsMapping.put(10, R.drawable.red_flag);
            resultsMapping.put(11, R.drawable.box_grey);
        }
        return resultsMapping;
    }

    private void timerRun() {
        // Timer running
        handler = new Handler() {
            public void handleMessage (Message message){
                TextView time = (TextView)findViewById(R.id.textView2);
                time.setText("Time: " + String.valueOf(Math.round(counter)));
                counter++;
            }
        };
         timer = new GameTimer(handler);
         timer.start();
    }

    @Override
    public void buttonClicked(TileButton tileButton){

        if(flagMode) {
            if (tileButton.isFlagged()) {
                tileButton.setFlagged(false);
                tileButton.setBackgroundResource(resultsMapping.get(11));
            } else {
                tileButton.setFlagged(true);
                tileButton.setBackgroundResource(resultsMapping.get(10));
            }
        }

        else if (!tileButton.isFlagged() && !tileButton.isRevealed()){
            ArrayList<CellResult> results = gameLogic.click(tileButton.getPositionX(),tileButton.getPositionY());
            try {
                for(CellResult cell : results){
                    board[cell.getCol()][cell.getRow()].setBackgroundResource(resultsMapping.get(cell.getValue()));
                    board[cell.getCol()][cell.getRow()].reavil();
                }
            }catch (Exception e) {
                Log.d("Error","" + e);
            }
        }

    }


    public void endOfGameDelay(boolean isWon){
        this.isWon = isWon;
        handlerDelayEndGame = new Handler(){
            public void handleMessage (Message message){
                if (counterDelay > 0) {
                    timerDelayEndGame.stopTimer();
                    gameOver();
                }
                counterDelay++;
            }
        };

        timerDelayEndGame = new GameTimer(handlerDelayEndGame);
        timerDelayEndGame.start();
    }

    // currently only for losing state
    private void gameOver() {
        Intent intent = new Intent(this,Results_Screen.class);
        if(this.isWon) {
            intent.putExtra("status", "win");
        }else{
            intent.putExtra("status", "lose");
        }
        intent.putExtra("result",String.valueOf(counter));
        intent.putExtra("level", level);
        startActivity(intent);
    }


    public GameLogic initGameLogic(GameLogic gameLogic) {
        //GameLogic gameLogic;
       // Intent intent = getIntent();
           this.level =  getIntent().getStringExtra("level");

            try {
                switch (level) {

                    case "easy":
                        gameLogic = new GameLogic(10, 10, 5, this);
                        break;
                    case "medium":
                        gameLogic = new GameLogic(10, 10, 10, this);
                        break;
                    case "hard":
                        gameLogic = new GameLogic(5, 5, 10, this);
                        break;
                }

            } catch (Exception e) {
                Log.d("Error", "error in creating gamelogic instance" + e);
            }

        return gameLogic;
    }

    @Override
    public void onGameEnd(GameEvent event) {
        timer.stopTimer();
        for(CellResult cell : event.getMines()){
            board[cell.getCol()][cell.getRow()].setBackgroundResource(resultsMapping.get(cell.getValue()));
        }
        endOfGameDelay(event.isWon());
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        finish();
    }

    //@Override
    /**public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "MineBoard Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.matka.minesweeper/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }**/

   // @Override
   /** public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "MineBoard Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.matka.minesweeper/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }**/

}
