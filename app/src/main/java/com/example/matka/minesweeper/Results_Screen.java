package com.example.matka.minesweeper;

import android.app.Activity;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by matka on 10/12/16.
 */
public class Results_Screen extends Activity {

    private TextView title;
    private String winTitle = "Well done!";
    private ImageView smiley;
    private Button playAgainBtn;

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

        playAgainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }


}
