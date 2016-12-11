package components;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageButton;


/**
 * Created by matka on 03/12/16.
 */
public class TileButton extends ImageButton implements View.OnClickListener {

    private TileButtonListener listener;

    private int positionX;
    private int positionY;
    private boolean isRevealed = false;
    private boolean isFlagged = false;
    private int status = 0;

    public TileButton(Context context) {
        super(context);
        setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        listener.buttonClicked(this);
    }

    public void setListener(TileButtonListener listener) {
        this.listener = listener;
    }

    public void setPosition(int row, int column) {
        positionX = row;
        positionY = column;
    }

    public int getPositionY() {
        return positionY;
    }

    public int getPositionX() {
        return positionX;
    }

    public boolean isRevealed(){
        return isRevealed;
    }

    public void reveal() {
        isRevealed = true;
    }

    public boolean isFlagged(){
        return isFlagged;
    }

    public void setFlagged(boolean flag){
        isFlagged = flag;
    }


}

