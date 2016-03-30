package ai.gage.mitch.checkers.model;

/**
 * Holds data that represents the gameboard
 */
public class GameBoard {

    /* A checkers board is 8x8 */
    private final int BOARD_SIZE = 8;

    /* 2d array that represents the board */
    private Piece[][] board;

    public GameBoard() {

    }

    public Piece[][] getBoard() {
        return this.board;
    }

    public void fillBoard() {

    }

}
