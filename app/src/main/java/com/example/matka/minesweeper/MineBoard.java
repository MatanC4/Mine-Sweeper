package com.example.matka.minesweeper;

import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.util.Log;
import android.widget.TableLayout;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;

import bl.CellResult;
import bl.GameLogic;
import components.*;

public class MineBoard extends AppCompatActivity implements TileButtonListener {

    private GameLogic gameLogic;
    private TableLayout tableLayout;
    private String level;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        gameLogic = initGameLogic(gameLogic);


        LinearLayout rowsLayout = new LinearLayout(this);
        rowsLayout.setBackgroundColor(Color.CYAN);
        rowsLayout.setOrientation(LinearLayout.VERTICAL);
        rowsLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        for (int col = 0; col < gameLogic.getNumOfRows(); col++) {
            LinearLayout colsLayout = new LinearLayout(this);
            colsLayout.setBackgroundColor(Color.CYAN);
            colsLayout.setOrientation(LinearLayout.HORIZONTAL);
            colsLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            for (int row = 0; row < gameLogic.getNumOfCols(); row++) {
                TileButton tileButton = new TileButton(this);
                tileButton.setPosition(row, col);
                tileButton.setListener(this);
                tileButton.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                colsLayout.addView(tileButton);
                if(level.equals("hard")) {
                    tileButton.setBackgroundResource(R.drawable.hard_empty_tile);
                }else{
                    if(row%2==0)
                        tileButton.setBackgroundResource(R.drawable.digit_1);
                    else
                        tileButton.setBackgroundResource(R.drawable.easy_med_empty_tile);

                }

            }
            rowsLayout.addView(colsLayout);
        }


        RelativeLayout mainLayout = (RelativeLayout) findViewById(R.id.mainLayout);
        mainLayout.addView(rowsLayout);
        mainLayout.setGravity(Gravity.CENTER);
    }



    public void buttonClicked(TileButton tileButton){
        if(!tileButton.isFlagged() && !tileButton.isRevealed()){
            ArrayList<CellResult> results = gameLogic.click(tileButton.getPositionX(),tileButton.getPositionY());
            for(CellResult cell : results){

            }
        }
    }



    public GameLogic initGameLogic(GameLogic gameLogic) {
        //GameLogic gameLogic;
       // Intent intent = getIntent();
           this.level =  getIntent().getStringExtra("level");

            try {
                switch (level) {

                    case "easy":
                        gameLogic = new GameLogic(10, 10, 5);
                        break;
                    case "medium":
                        gameLogic = new GameLogic(10, 10, 10);
                        break;
                    case "hard":
                        gameLogic = new GameLogic(5, 5, 10);
                        break;
                }

            } catch (Exception e) {
                Log.d("Error", "error in creating gamelogic instance" + e);
            }

        return gameLogic;
    }

    @Override
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
    public void onStop() {
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
    }
}
