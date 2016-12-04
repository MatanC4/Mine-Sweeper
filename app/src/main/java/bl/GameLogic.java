package bl;

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

    public GameLogic(int rows, int cols, int mines) {
        this.rows = rows;
        this.cols = cols;
        this.mines = mines;
        this.board = new int [rows][cols];
        this.status = new boolean[rows][cols];
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
        }
        else if(this.board[row][col] > 0) {
            results.add(new CellResult(row, col, this.board[row][col]));
            this.status[row][col] = true;
        }
        else{
            scanAfterClick(row, col, results);
        }

        this.openCells += results.size();
        return results;
    }

    public boolean isWon(){
        return this.openCells+this.mines == this.rows*this.cols;
    }

    private void scanAfterClick(int row, int col, ArrayList<CellResult> results){
        if(this.status[row][col]==false){
            results.add(new CellResult(row, col, 0));
            results.add(new CellResult(row, col, this.board[row][col]));
            status[row][col] = true;
            if(this.board[row][col]==0){
                if(row>0 && col>0 && row<this.rows-1 && col<this.cols-1){
                    scanAfterClick(row-1,col-1,results);
                    scanAfterClick(row-1,col, results);
                    scanAfterClick(row-1,col+1, results);
                    scanAfterClick(row,col-1,results);
                    scanAfterClick(row,col+1, results);
                    scanAfterClick(row+1,col-1,results);
                    scanAfterClick(row+1,col, results);
                    scanAfterClick(row+1,col+1, results);
                }
                else if(row==0){
                    if(col==0){
                        scanAfterClick(row,col+1, results);
                        scanAfterClick(row+1,col, results);
                        scanAfterClick(row+1,col+1, results);
                    }
                    if(col==this.cols-1){
                        scanAfterClick(row,col-1,results);
                        scanAfterClick(row+1,col-1,results);
                        scanAfterClick(row+1,col, results);
                    }
                    else{
                        scanAfterClick(row,col-1,results);
                        scanAfterClick(row,col+1, results);
                        scanAfterClick(row+1,col-1,results);
                        scanAfterClick(row+1,col, results);
                        scanAfterClick(row+1,col+1, results);
                    }
                }
                else if(row==this.rows-1){
                    if(col==0){
                        scanAfterClick(row-1,col, results);
                        scanAfterClick(row-1,col+1, results);
                        scanAfterClick(row,col+1, results);
                    }
                    if(col==this.cols-1){
                        scanAfterClick(row-1,col-1,results);
                        scanAfterClick(row-1,col+1, results);
                        scanAfterClick(row,col-1,results);
                    }
                    else{
                        scanAfterClick(row-1,col-1,results);
                        scanAfterClick(row-1,col, results);
                        scanAfterClick(row-1,col+1, results);
                        scanAfterClick(row,col-1,results);
                        scanAfterClick(row,col+1, results);
                    }
                }
                else if(col==0){
                    scanAfterClick(row-1,col, results);
                    scanAfterClick(row-1,col+1, results);
                    scanAfterClick(row,col+1, results);
                    scanAfterClick(row+1,col, results);
                    scanAfterClick(row+1,col+1, results);
                }
                else if(col==this.cols-1){
                    scanAfterClick(row-1,col-1,results);
                    scanAfterClick(row-1,col, results);
                    scanAfterClick(row,col-1,results);
                    scanAfterClick(row+1,col-1,results);
                    scanAfterClick(row+1,col, results);
                }
            }
        }
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
        for(int i = 0; i < this.rows; i++){
            for(int j = 0; j < this.cols; j++){
                int count = 0;
                if(this.board[i][j] != MINE){
                    if(i>0 && i<this.rows-1 && j>0 && j<this.cols-1)
                        count = this.board[i][j-1] + this.board[i][j+1] + this.board[i+1][j-1] +
                                this.board[i+1][j] + this.board[i+1][j+1] + this.board[i-1][j-1] +
                                this.board[i-1][j] + this.board[i-1][j+1];
                    else if(i==0){
                        if(j==0)
                            count = this.board[i][j+1] + this.board[i+1][j] + this.board[i+1][j+1];
                        else if (j == this.cols-1)
                            count = this.board[i][j-1] + this.board[i+1][j-1] + this.board[i+1][j];
                        else
                            count = this.board[i][j-1] + this.board[i][j+1] + this.board[i+1][j-1] +
                                    this.board[i+1][j] + this.board[i+1][j+1];
                    }
                    else if(i==this.rows-1){
                        if(j==0)
                            count = this.board[i][j+1] + this.board[i-1][j]+this.board[i][j+1];
                        else if(j==this.cols-1)
                            count = this.board[i][j-1] + this.board[i-1][j] + this.board[i][j-1];
                        else
                            count = this.board[i][j-1] + this.board[i][j+1] + this.board[i-1][j-1] +
                                    this.board[i-1][j] + this.board[i][j+1];
                    }
                    else{
                        if(j==0)
                            count = this.board[i-1][j] + this.board[i-1][j+1] + this.board[i][j+1] +
                                    this.board[i+1][j] + this.board[i+1][j+1];
                        else if(j==this.cols-1)
                            count = this.board[i-1][j] + this.board[i-1][j-1] + this.board[i][j-1] +
                                    this.board[i+1][j] + this.board[i+1][j-1];
                    }
                    this.board[i][j] = -count;
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
