package game.model;

import game.rest.InvalidMoveException;

public class Game {

    private int[][] board;
    private int id;

    private Integer iCoordinate;
    private Integer jCoordinate;

    public Game(int id) {
        this.id = id;
        board = new int[][]{{1, 2, 3, 4},
        {-1, 6, 7, 8},
        {11, 9, 10, 5},
        {12, 13, 14, 15}};
        randomShuffle();
    }

    public int[][] getBoard() {
        return board;
    }

    public void setBoard(int[][] board) {
        this.board = board;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getiCoordinate() {
        return iCoordinate;
    }

    public void setiCoordinate(Integer iCoordinate) {
        this.iCoordinate = iCoordinate;
    }

    public Integer getjCoordinate() {
        return jCoordinate;
    }

    public void setjCoordinate(Integer jCoordinate) {
        this.jCoordinate = jCoordinate;
    }
    
    public void setBoardWithIndex(int[][] board, int indexI, int indexJ, int value){
        this.board[indexI][indexJ]=value;
    }

    //finding coordinates of specific tile
    public void findCoordinate(int number) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == number) {
                    this.iCoordinate = i;
                    this.jCoordinate = j;
                    return;
                }
            }
        }
    }

    //checking nearby tiles if containing swichable value
    //can be done better with bit shifting
    public boolean isMoveAllowed(int number) {

        boolean isAllowed = false;
        try {
            if (board[this.iCoordinate - 1][this.jCoordinate] == number) {
                isAllowed = true;
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        try {
            if (board[this.iCoordinate + 1][this.jCoordinate] == number) {
                isAllowed = true;
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        try {
            if (board[this.iCoordinate][this.jCoordinate - 1] == number) {
                isAllowed = true;
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        try {
            if (board[this.iCoordinate][this.jCoordinate + 1] == number) {
                isAllowed = true;
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return isAllowed;
    }

    // method for swapping tiles
    public void changeTiles(int[][] board, int i1, int i2, int j1, int j2) {
        int tempObject = board[i1][j1];
        board[i1][j1] = board[i2][j2];
        board[i2][j2] = tempObject;
    }

    public void randomShuffle() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                int i1 = (int) (Math.random() * this.board.length);
                int j1 = (int) (Math.random() * this.board[i].length);

                int temp = this.board[i][j];
                this.board[i][j] = this.board[i1][j1];
                this.board[i1][j1]= temp;
            }
        }
    }
}
