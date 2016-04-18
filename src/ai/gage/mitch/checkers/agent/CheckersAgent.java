package ai.gage.mitch.checkers.agent;

import ai.gage.mitch.checkers.model.GameBoard;
import ai.gage.mitch.checkers.model.Move;
import ai.gage.mitch.checkers.model.Player;

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

    // Player that the agent is playing
    private Player player;

    /**
     * Constructor that sets the player and game of the AI
     * @param player Player of the agent
     * @param game Game the agent is playing on
     */
    public CheckersAgent(Player player, GameBoard game){
        this.player = player;
        this.game = game;
        this.headNode = new GameNode(this.game, true);

    }


    /**
     * Main method for testing
     * @param args
     */
    public static void main(String args[]){
        GameBoard game = new GameBoard();
        CheckersAgent ai = new CheckersAgent(Player.BLACK, game);
        CheckersAgent ai2 = new CheckersAgent(Player.RED, game);
        while(!game.isGameOver()){
            System.out.println(game.getCurrentPlayer());
            System.out.println(game);
            long before = System.currentTimeMillis() / 1000;
            Move move = ai.getNextMove(4);
            long after = System.currentTimeMillis() / 1000;
            System.out.println(after - before);
            System.out.println(move);
            game.movePiece(move);
            if(game.isGameOver()){
                break;
            }
            System.out.println(game.getCurrentPlayer());
            System.out.println(game);
            before = System.currentTimeMillis() / 1000;
            move = ai.getNextMove(4);
            after = System.currentTimeMillis() / 1000;
            System.out.println(after - before);
            System.out.println(move);
            game.movePiece(move);
        }
    }

    /**
     * Gets the next optimal move
     * @param depth How deep to make the search tree
     * @return
     */
    public Move getNextMove(int depth){
        // reset the head node before getting the next move
        this.headNode.updateGame(this.game);
        this.headNode.clearChildren();
        // get the heuristic value of the best node
        int moveIndex = alphaBeta(headNode, depth, Integer.MIN_VALUE, Integer.MAX_VALUE);
        // Go through possible moves and choose one that matches our best hueristic guess
        for(GameNode node: this.headNode.getChildren()){
            if(node.getValue() == moveIndex){
                return node.getMove();
            }
        }
        return null;
    }


    /**
     * Method that steps through the tree and returns the best move
     * Used https://en.wikipedia.org/wiki/Alpha%E2%80%93beta_pruning#Pseudocode as a reference
     * @param node
     * @param depth
     * @param alpha
     * @param beta
     * @return
     */
    private int alphaBeta(GameNode node, int depth, int alpha, int beta) {
        node.addChildren();
        if(!node.hasChildren() || depth == 0){
            return node.getHeuristic(player);
        }
        if(node.isMaximizer()){
            int value = Integer.MIN_VALUE;
            for (GameNode child: node.getChildren()) {
                value = Math.max(value, alphaBeta(child, depth - 1, alpha, beta));
                node.setValue(value);
                alpha = Math.max(alpha, value);

                if(beta <= alpha){
                    break;
                }
            }
            return value;
        }
        else{
            int value = Integer.MAX_VALUE;
            for(GameNode child: node.getChildren()){
                value = Math.min(value, alphaBeta(child, depth - 1, alpha, beta));
                node.setValue(value);
                beta = Math.min(beta, value);
                if(beta <= alpha){
                    break;
                }
            }
            return value;
        }
    }


}
