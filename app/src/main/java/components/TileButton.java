package components;


import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;


/**
 * Created by matka on 03/12/16.
 */
public class TileButton extends ImageButton implements View.OnClickListener {

    private TileButtonListener listener;

    private int positionX;
    private int positionY;
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
}

