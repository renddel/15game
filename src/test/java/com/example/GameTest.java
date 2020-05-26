package com.example;

import game.model.Game;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class GameTest {

    @Test
    public void gameTest() {
        Game game = new Game(1);
        assertEquals(1, game.getId());
    }

    @Test
    public void changeTilesTest() {
        Game game = new Game(1);
        int[][] board = new int[][]{{1, 2, 3, 4},
        {-1, 6, 7, 8},
        {11, 9, 10, 5},
        {12, 13, 14, 15}};
        game.changeTiles(board, 0, 1, 0, 0);
        assertEquals(1, board[1][0]);
    }

    @Test
    public void isMoveAllowedTest() {
        boolean isAllowed = false;
        Game game = new Game(1);
        game.setiCoordinate(0);
        game.setjCoordinate(0);
        isAllowed = game.isMoveAllowed(999);
        assertEquals(false, isAllowed);
    }
}
