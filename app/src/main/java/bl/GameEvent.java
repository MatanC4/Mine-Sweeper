package bl;

import java.util.ArrayList;

/**
 * Created by Daniel_m on 10/12/2016.
 */
public class GameEvent {
    boolean isWon;
    ArrayList<CellResult> mines;

    public GameEvent(boolean isWon, ArrayList<CellResult> mines){
        this.isWon = isWon;
        this.mines = mines;
    }

    public boolean isWon() {
        return isWon;
    }

    public ArrayList<CellResult> getMines() {
        return mines;
    }
}
