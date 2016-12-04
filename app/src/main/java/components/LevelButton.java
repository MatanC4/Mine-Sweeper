package components;


import android.content.Context;
import android.widget.Button;

/**
 * Created by matka on 03/12/16.
 */
public class LevelButton extends Button {

    int level;
    Button button;



    public LevelButton(Context context ,int level){
        super(context);
        setLevel(level);
    }



    public void setLevel(int level){
        this.level = level;
    }
}

