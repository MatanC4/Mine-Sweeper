package com.example.matka.minesweeper;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


import bl.GameLogic;
import components.*;

public class MineBoard extends AppCompatActivity implements TileButtonListener{

    private GameLogic gameLogic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //gameLogic = new GameLogic(3,3);

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
                // every button will get his default image
            }
            rowsLayout.addView(colsLayout);
        }

        RelativeLayout mainLayout = (RelativeLayout) findViewById(R.id.mainLayout);
        mainLayout.addView(rowsLayout);
        mainLayout.setGravity(Gravity.CENTER);
    }





    @Override
    public void buttonClicked(TileButton tileButton) {
        //Toast.makeText(this, tileButton.getPositionX() + "," + tileButton.getPositionY(), Toast.LENGTH_SHORT).show();

    }

}
