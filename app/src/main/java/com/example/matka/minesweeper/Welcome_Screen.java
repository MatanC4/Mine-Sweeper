package com.example.matka.minesweeper;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import bl.GameLogic;
import bl.Level;
import components.LevelButton;


import com.example.matka.minesweeper.R;

public class Welcome_Screen extends AppCompatActivity implements View.OnClickListener {

    private LevelButton [] levelButtons = new LevelButton [3];
    private Button easyBtn;
    private Button medBtn;
    private Button hardBtn;
    private Button startBtn;
    private int easyBS, mediumBS, hardBS;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("Scores", 0);
        easyBS = sharedPref.getInt(Level.easy.toString(), 0);
        mediumBS = sharedPref.getInt(Level.medium.toString(), 0);
        hardBS = sharedPref.getInt(Level.hard.toString(), 0);

        setContentView(R.layout.activity_welcome__screen);
        bindUI();

    }



    public void bindUI(){

        Intent intent = new Intent(this,MineBoard.class);
        startBtn = (Button) findViewById(R.id.play_btn);
        
        

        easyBtn = (Button) findViewById(R.id.level1);
        easyBtn.setOnClickListener(this);
        easyBtn.setText("Easy\nBest Time: " + (easyBS==0?"n/a":easyBS));

        medBtn = (Button) findViewById(R.id.level2);
        medBtn.setOnClickListener(this);
        medBtn.setText("Medium\nBest Time: " + (mediumBS==0?"n/a":mediumBS));


        hardBtn = (Button) findViewById(R.id.level3);
        hardBtn.setOnClickListener(this);
        hardBtn.setText("Hard\nBest Time: " + (hardBS==0?"n/a":hardBS));
    }



    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.level1:
                Intent intent = getLevelIntent(Level.easy);
                startActivity(intent);
                break;
            case R.id.level2:
                Intent intent2 = getLevelIntent(Level.medium);
                startActivity(intent2);
                break;
            case R.id.level3:
                Intent intent3 = getLevelIntent(Level.hard);
                startActivity(intent3);
                break;
        }
    }

    private Intent getLevelIntent(Level levelToIntent) {
        Intent intent = new Intent(this, MineBoard.class);
        intent.putExtra("level",levelToIntent.toString());
        return intent;
    }
}
