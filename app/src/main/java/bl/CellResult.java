package bl;

/**
 * Created by Daniel_m on 03/12/2016.
 */

public class CellResult {
    private int row;
    private int col;
    private int value;

    public CellResult(int row, int col, int value) {
        this.row = row;
        this.col = col;
        this.value = value;
    }

    public CellResult(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
