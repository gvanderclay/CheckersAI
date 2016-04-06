package ai.gage.mitch.checkers.model.tests;

import ai.gage.mitch.checkers.model.GameBoard;
import ai.gage.mitch.checkers.model.Move;
import ai.gage.mitch.checkers.model.Piece;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 * Tests for the GameBoard class
 * Created by Gage Vander Clay and Mitch Couturier
 */
public class GameBoardTest {

    private GameBoard game;

    @Before
    public void setUp() throws Exception {
        game = new GameBoard();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void movePiece1() throws Exception {
        assertTrue(game.movePiece(new Move(2,0,3,1)));
    }

    @Test
    public void movePiece2() throws Exception {
        assertFalse(game.movePiece(new Move(2,0,3,3)));
    }

    @Test
    public void movePiece3() throws Exception {
        game.movePiece(new Move(2,0,3,1));
        assertTrue(game.movePiece(new Move(5,1,4,2)));

    }

    @Test
    public void movePiece4() throws Exception {
        game.movePiece(new Move(2,0,3,1));
        game.movePiece(new Move(5,1,4,2));
        game.movePiece(new Move(2,4,3,5));
        assertFalse(game.movePiece(new Move(4,2,3,3)));
    }

    @Test
    public void movePiece5() throws Exception {
        game.movePiece(new Move(2,0,3,1));
        game.movePiece(new Move(5,1,4,2));
        game.movePiece(new Move(2,4,3,5));
        assertTrue(game.movePiece(new Move(4,2,2,0)));
        System.out.println(game.toString());
    }

    @Test
    public void testDoubleJump() throws Exception {
        game.movePiece(new Move(2,0,3,1));
        game.movePiece(new Move(5,7,4,6));
        game.movePiece(new Move(2,6,3,5));
        game.movePiece(new Move(6,6,5,7));
        game.movePiece(new Move(1,1,2,0));
        game.movePiece(new Move(5,1,4,0));
        game.movePiece(new Move(0,2,1,1));
        game.movePiece(new Move(6,0,5,1));
        game.movePiece(new Move(2,4,3,3));
        game.movePiece(new Move(4,6,2,4));
        game.movePiece(new Move(2,4,0,2));
        System.out.println(game);
    }
}