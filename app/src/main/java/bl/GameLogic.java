package bl;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Daniel_m on 03/12/2016.
 */

public class GameLogic {
    public static final int MINE = -1;
    private int rows;
    private int cols;
    private int mines;
    private int openCells = 0;

    private int [][] board;
    private boolean [][] status;
    private GameListener listener;

    public GameLogic(int rows, int cols, int mines, GameListener listener) {
        this.rows = rows;
        this.cols = cols;
        this.mines = mines;
        this.board = new int [rows][cols];
        this.status = new boolean[rows][cols];
        this.listener = listener;
        resetBoard();
        resetStatus();
        paintMines();
        scanAndPaintBoard();
    }

    private void resetStatus(){
        for(int i=0; i<this.rows; i++)
            for(int j=0; j<this.cols; j++)
                this.status[i][j] = false;
    }

    private void resetBoard(){
        for(int i=0; i<this.rows; i++)
            for(int j=0; j<this.cols; j++)
                this.board[i][j] = 0;
    }


    public ArrayList<CellResult> click(int row, int col){
        ArrayList<CellResult> results = new ArrayList<>();
        if(this.board[row][col] == MINE){
            results.add(new CellResult(row, col, MINE));
            this.status[row][col] = true;
            processEvent(false);
        }
        else if(this.board[row][col] > 0) {
            results.add(new CellResult(row, col, this.board[row][col]));
            this.status[row][col] = true;
        }
        else{
            scanAfterClick(row, col, results);
        }
        this.openCells += results.size();
        if(isWon())
            processEvent(true);
        return results;
    }

    private void processEvent(boolean isWon ) {
        ArrayList<CellResult> mines = getMines();
        listener.onGameEnd(new GameEvent(isWon, mines));
    }

    private ArrayList<CellResult> getMines() {
        ArrayList<CellResult> mines = new ArrayList<>();
        for(int row = 0; row< this.rows; row++)
            for(int col = 0; col<this.cols; col++)
                if(this.board[row][col]==-1)
                    mines.add(new CellResult(row, col, MINE));
        return mines;
    }

    public boolean isWon(){
        return this.openCells+this.mines == this.rows*this.cols;
    }

    private void scanAfterClick(int row, int col, ArrayList<CellResult> results) {
        try {
            if (this.status[row][col] == false) {
                Log.d("row/col", row + " " + col);
                results.add(new CellResult(row, col, this.board[row][col]));
                status[row][col] = true;
                if (this.board[row][col] == 0) {
                    if (row > 0 && col > 0 && row < this.rows - 1 && col < this.cols - 1) {
                        scanAfterClick(row - 1, col - 1, results);
                        scanAfterClick(row - 1, col, results);
                        scanAfterClick(row - 1, col + 1, results);
                        scanAfterClick(row, col - 1, results);
                        scanAfterClick(row, col + 1, results);
                        scanAfterClick(row + 1, col - 1, results);
                        scanAfterClick(row + 1, col, results);
                        scanAfterClick(row + 1, col + 1, results);
                    } else if (row == 0) {
                        if (col == 0) {
                            scanAfterClick(row, col + 1, results);
                            scanAfterClick(row + 1, col, results);
                            scanAfterClick(row + 1, col + 1, results);
                        }
                        else if (col == this.cols - 1) {
                            scanAfterClick(row, col - 1, results);
                            scanAfterClick(row + 1, col - 1, results);
                            scanAfterClick(row + 1, col, results);
                        } else {
                            scanAfterClick(row, col - 1, results);
                            scanAfterClick(row, col + 1, results);
                            scanAfterClick(row + 1, col - 1, results);
                            scanAfterClick(row + 1, col, results);
                            scanAfterClick(row + 1, col + 1, results);
                        }
                    } else if (row == this.rows - 1) {
                        // left lower corner
                        if (col == 0) {
                            scanAfterClick(row - 1, col, results);
                            scanAfterClick(row - 1, col + 1, results);
                            scanAfterClick(row, col + 1, results);
                        }
                        //right lower corner
                        else if (col == this.cols - 1) {
                            scanAfterClick(row - 1, col - 1, results);
                            scanAfterClick(row - 1, col, results);
                            scanAfterClick(row, col - 1, results);
                        } else {
                            //last row not including ends
                            scanAfterClick(row - 1, col - 1, results);
                            scanAfterClick(row - 1, col, results);
                            scanAfterClick(row - 1, col + 1, results);
                            scanAfterClick(row, col - 1, results);
                            scanAfterClick(row, col + 1, results);
                        }
                    } else if (col == 0) {
                        scanAfterClick(row - 1, col, results);
                        scanAfterClick(row - 1, col + 1, results);
                        scanAfterClick(row, col + 1, results);
                        scanAfterClick(row + 1, col, results);
                        scanAfterClick(row + 1, col + 1, results);
                    } else if (col == this.cols - 1) {
                        scanAfterClick(row - 1, col - 1, results);
                        scanAfterClick(row - 1, col, results);
                        scanAfterClick(row, col - 1, results);
                        scanAfterClick(row + 1, col - 1, results);
                        scanAfterClick(row + 1, col, results);
                    }
                }
            }
        }
        catch(Exception e){}
    }


    private void paintMines() {
        int count = 0;
        while(count < this.mines){
            int row = (int)(Math.random()*this.rows);
            int col = (int)(Math.random()*this.cols);
            if(board[row][col] != MINE){
                board[row][col] = MINE;
                count += 1;
            }
        }
    }

    private void scanAndPaintBoard() {
        for(int row = 0; row < this.rows; row++){
            for(int col = 0; col < this.cols; col++){
                int count = 0;
                if(this.board[row][col] != MINE){
                    if(row>0 && row<this.rows-1 && col>0 && col<this.cols-1)
                        count = Math.min(this.board[row][col-1],0) +  Math.min(this.board[row][col+1],0) +  Math.min(this.board[row+1][col-1],0) +
                                Math.min(this.board[row+1][col],0) +  Math.min(this.board[row+1][col+1],0) +  Math.min(this.board[row-1][col-1],0) +
                                Math.min(this.board[row-1][col],0) +  Math.min(this.board[row-1][col+1],0);
                    else if(row==0){
                        if(col==0)
                            count =  Math.min(this.board[row][col+1],0) +  Math.min(this.board[row+1][col],0) +  Math.min(this.board[row+1][col+1],0);
                        else if (col == this.cols-1)
                            count =  Math.min(this.board[row][col-1],0) +  Math.min(this.board[row+1][col-1],0) +  Math.min(this.board[row+1][col],0);
                        else
                            count =  Math.min(this.board[row][col-1],0) +  Math.min(this.board[row][col+1],0) +  Math.min(this.board[row+1][col-1],0) +
                                    Math.min(this.board[row+1][col],0) +  Math.min(this.board[row+1][col+1],0);
                    }
                    else if(row==this.rows-1){
                        if(col==0)
                            count =  Math.min(this.board[row][col+1],0) +  Math.min(this.board[row-1][col],0) + Math.min(this.board[row-1][col+1],0);
                        else if(col==this.cols-1)
                            count = Math.min(this.board[row][col-1],0) + Math.min(this.board[row-1][col],0) + Math.min(this.board[row-1][col-1],0);
                        else
                            count = Math.min(this.board[row][col-1],0) + Math.min(this.board[row][col+1],0) + Math.min(this.board[row-1][col-1],0) +
                                    Math.min(this.board[row-1][col],0) + Math.min(this.board[row-1][col+1],0);
                    }
                    else{
                        if(col==0)
                            count = Math.min(this.board[row-1][col],0) + Math.min(this.board[row-1][col+1],0) + Math.min(this.board[row][col+1],0) +
                                    Math.min(this.board[row+1][col],0) + Math.min(this.board[row+1][col+1],0);
                        else if(col==this.cols-1)
                            count = Math.min(this.board[row-1][col],0) + Math.min(this.board[row-1][col-1],0) + Math.min(this.board[row][col-1],0) +
                                    Math.min(this.board[row+1][col],0) + Math.min(this.board[row+1][col-1],0);
                    }
                    this.board[row][col] = -count;
                }
            }
        }
    }

    public int getNumOfRows() {
        return rows;
    }

    public int getNumOfCols() {
        return cols;
    }



}
