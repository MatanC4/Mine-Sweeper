package com.example.matka.minesweeper;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
    private String lastLevel = null;
    private int easyBS, mediumBS, hardBS;
    private Intent intent;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences scoresDB = getApplicationContext().getSharedPreferences("Scores", 0);

        easyBS = scoresDB.getInt(Level.easy.toString(), 0);
        mediumBS = scoresDB.getInt(Level.medium.toString(), 0);
        hardBS = scoresDB.getInt(Level.hard.toString(), 0);

        setContentView(R.layout.activity_welcome__screen);
        bindUI();

    }



    public void bindUI(){
        intent = new Intent(this,MineBoard.class);
        startBtn = (Button) findViewById(R.id.play_btn);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });
        SharedPreferences lastPlayed = getApplicationContext().getSharedPreferences("last_game", 0);
        lastLevel = lastPlayed.getString("last_played", "");
        if(!lastLevel.equals("")){
            intent.putExtra("level",lastLevel);
            startBtn.setText("START\n" + lastLevel);
        }
        

        easyBtn = (Button) findViewById(R.id.level1);
        easyBtn.setOnClickListener(this);
        TextView txtvieweasy = (TextView)findViewById(R.id.easyLabel);
        txtvieweasy.setText("Best Time: " + (easyBS==0?"n/a":easyBS+"s"));

        medBtn = (Button) findViewById(R.id.level2);
        medBtn.setOnClickListener(this);
        TextView txtviewmed = (TextView)findViewById(R.id.mediumLabel);
        txtviewmed.setText("Best Time: " + (mediumBS==0?"n/a":mediumBS+"s"));


        hardBtn = (Button) findViewById(R.id.level3);
        hardBtn.setOnClickListener(this);
        TextView txtviewhard = (TextView)findViewById(R.id.hardLabel);
        txtviewhard.setText("Best Time: " + (hardBS==0?"n/a":hardBS+"s"));
    }



    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.level1:
                intent.putExtra("level",Level.easy.toString());
                startBtn.setText("START\n" + Level.easy.toString());
                break;
            case R.id.level2:
                intent.putExtra("level",Level.medium.toString());
                startBtn.setText("START\n" + Level.medium.toString());
                break;
            case R.id.level3:
                intent.putExtra("level",Level.hard.toString());
                startBtn.setText("START\n" + Level.hard.toString());
                break;
        }
    }

    private Intent getLevelIntent(Level levelToIntent) {
        Intent intent = new Intent(this, MineBoard.class);
        intent.putExtra("level",levelToIntent.toString());
        return intent;
    }
}
