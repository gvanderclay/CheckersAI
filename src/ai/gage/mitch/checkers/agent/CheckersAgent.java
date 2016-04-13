package ai.gage.mitch.checkers.agent;

import ai.gage.mitch.checkers.model.GameBoard;
import ai.gage.mitch.checkers.model.Move;

/**
 * This class contains the agent that will play Checkers
 * Created by gvanderclay on 4/7/16.
 */
public class CheckersAgent {

    // game the agent is playing on
    private GameBoard game;

    // value of the current game board
    private int currentGameValue;

    // node that is at the top of the game tree
    private GameNode headNode;


    public Move alphaBeta(GameNode node, int depth, int alpha, int beta) {

        return null;
    }
}
