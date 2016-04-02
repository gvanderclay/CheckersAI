package ai.gage.mitch.checkers.model.tests;

import ai.gage.mitch.checkers.model.GameBoard;
import ai.gage.mitch.checkers.model.Move;

import static org.junit.Assert.*;

/**
 * Created by gvanderclay on 3/31/16.
 */
public class PieceTest {

    @org.junit.Test
    public void isValidMove1() throws Exception {
        GameBoard board = new GameBoard();
        Move move = new Move(2,0,3,1);
        assertTrue(board.getBoard()[2][0].isValidMove(move, board.getBoard()));
    }

    @org.junit.Test
    public void isValidMove2() throws Exception {
        GameBoard board = new GameBoard();
        Move move = new Move(2,0,2,1);
        assertFalse(board.getBoard()[2][0].isValidMove(move, board.getBoard()));
    }

    @org.junit.Test
    public void isValidMove3() throws Exception {
        GameBoard board = new GameBoard();
        Move move1 = new Move(2,0,3,1);
        Move move2 = new Move(5,1,4,0);

        board.movePiece(move1);
        System.out.println(board);
        assertFalse(board.movePiece(move2));
    }

    @org.junit.Test
    public void movePiece() throws Exception {
        GameBoard board = new GameBoard();
        Move move1 = new Move(2,0,3,1);
        Move move2 = new Move(3,1,4,2);
        board.movePiece(move1);
        System.out.println(board);
        assertFalse(board.movePiece(move2));
    }


}