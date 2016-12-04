package com.example.matka.minesweeper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import bl.GameLogic;

import com.example.matka.minesweeper.R;

public class Welcome_Screen extends AppCompatActivity {


    Button[] levelButtons = new Button [3];
    private GameLogic gameLogic;
    int [][] board;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome__screen);
        bindUI();

    }



    public void bindUI(){

        levelButtons[0] = (Button)findViewById(R.id.level1);
        levelButtons[1] = (Button)findViewById(R.id.level2);
        levelButtons[2] = (Button)findViewById(R.id.level3);

        for (int i = 0; i < levelButtons.length ; i++) {
            levelButtons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    board = new int [5][10];

                }
            });
        }
    }
}
