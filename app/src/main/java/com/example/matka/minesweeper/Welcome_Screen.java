package com.example.matka.minesweeper;

import android.content.Intent;
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


    LevelButton [] levelButtons = new LevelButton [3];
    Button easyBtn;
    Button medBtn;
    Button hardBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome__screen);
        bindUI();

    }



    public void bindUI(){

        easyBtn = (Button) findViewById(R.id.level1);
        easyBtn.setOnClickListener(this);

        medBtn = (Button) findViewById(R.id.level2);
        medBtn.setOnClickListener(this);

        hardBtn = (Button) findViewById(R.id.level3);
        hardBtn.setOnClickListener(this);

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
