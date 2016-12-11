package components;


import android.content.Context;
import android.widget.Button;
import android.view.View;

/**
 * Created by matka on 03/12/16.
 */
public class LevelButton extends Button implements View.OnClickListener {

    int rows, cols, mines;
    Button button;
    private LevelButtonListener listener;


    public LevelButton(Context context) {
        super(context);
        setOnClickListener(this);
    }

    public void onClick(View v) {
        listener.buttonClicked(this);
    }

    public void setListener(LevelButtonListener listener) {
        this.listener = listener;
    }


    public LevelButton(Context context, int rows, int cols, int mines) {
        super(context);
        setLevel(rows, cols, mines);
        setOnClickListener(this);
    }


    public void setLevel(int rows, int cols, int mines) {
        this.rows = rows;
        this.cols = cols;
        this.mines = mines;

    }

}

