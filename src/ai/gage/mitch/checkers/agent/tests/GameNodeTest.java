package ai.gage.mitch.checkers.agent.tests;

import ai.gage.mitch.checkers.agent.GameNode;
import ai.gage.mitch.checkers.model.GameBoard;
import ai.gage.mitch.checkers.model.Move;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Test for the game node
 * Created by vandercg on 4/13/16.
 */
public class GameNodeTest {

    private GameBoard game;

    @Before
    public void setUp() throws Exception {
        game = new GameBoard();
    }

    @Test
    public void getValue() throws Exception {
        game.movePiece(new Move(2, 0, 3, 1));
        game.movePiece(new Move(5, 7, 4, 6));
        game.movePiece(new Move(2, 6, 3, 5));
        game.movePiece(new Move(6, 6, 5, 7));
        game.movePiece(new Move(1, 1, 2, 0));
        game.movePiece(new Move(5, 1, 4, 0));
        game.movePiece(new Move(0, 2, 1, 1));
        game.movePiece(new Move(6, 0, 5, 1));
        game.movePiece(new Move(2, 4, 3, 3));
        game.movePiece(new Move(4, 6, 2, 4));
        game.movePiece(new Move(2, 4, 0, 2));
        GameNode node = new GameNode(game, true);
        assertTrue(node.getHeuristic() == -3);
    }
}