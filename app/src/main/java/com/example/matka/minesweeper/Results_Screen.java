package com.example.matka.minesweeper;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by matka on 10/12/16.
 */
public class Results_Screen extends AppCompatActivity {

    private TextView title;
    private String winTitle = "Well done!";
    private ImageView smiley;
    private Button playAgainBtn;
    private String timer;
    private GoogleApiClient client;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results);
        bindUI();

    }

    private void bindUI() {
        playAgainBtn = (Button)findViewById(R.id.play_again_btn);
        title = (TextView)findViewById(R.id.results_screen_title);
        smiley = (ImageView)findViewById(R.id.sad_smiley_icon_for_results);

        if (getIntent().getStringExtra("status").equals("win")){
           title.setText(winTitle);
        }

        timer =  getIntent().getStringExtra("results");
        playAgainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = anotherGameIntent();
                intent.putExtra("result", timer);
                startActivity(intent);
            }


        });

    }

    private Intent anotherGameIntent() {
      Intent intent  = new Intent(this,Welcome_Screen.class);
        return intent;
    }



}
