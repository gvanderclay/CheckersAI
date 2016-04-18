package ai.gage.mitch.checkers.agent;

import ai.gage.mitch.checkers.model.GameBoard;
import ai.gage.mitch.checkers.model.Move;
import ai.gage.mitch.checkers.model.Player;
import com.sun.xml.internal.stream.Entity;
import com.sun.xml.internal.ws.api.model.CheckedException;

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

    public CheckersAgent(Player player, GameBoard game){
        this.player = player;
        this.game = game;
        this.headNode = new GameNode(Integer.MAX_VALUE, Integer.MIN_VALUE, this.game, true);

    }

    public static void main(String args[]){
        GameBoard game = new GameBoard();
        CheckersAgent ai = new CheckersAgent(Player.BLACK, game);
        CheckersAgent ai2 = new CheckersAgent(Player.RED, game);
        while(!game.isGameOver()){
            System.out.println(game.getCurrentPlayer());
            System.out.println(game);
            Move move = ai.getNextMove(10);
            System.out.println(move);
            game.movePiece(move);
            if(game.isGameOver()){
                break;
            }
            System.out.println(game.getCurrentPlayer());
            System.out.println(game);
            move = ai2.getNextMove(10);
            System.out.println(move);
            game.movePiece(move);
        }
//        int i = 0;
//        System.out.println(game.getCurrentPlayer());
//        while(!game.isGameOver()){
//            ai.headNode.updateGame(game);
//            ai2.headNode.updateGame(game);
//            int x = ai.alphaBeta(ai.headNode, 7, Integer.MIN_VALUE, Integer.MAX_VALUE);
//            for(GameNode node: ai.headNode.getChildren()){
//                if(node.getValue() == x){
//                    System.out.println(game.movePiece(node.getMove()));
//                    System.out.println(game);
//                    break;
//                }
//            }
//            ai.headNode.updateGame(game);
//            ai2.headNode.updateGame(game);
//            int y = ai2.alphaBeta(ai2.headNode, 7, Integer.MIN_VALUE, Integer.MAX_VALUE);
//            for(GameNode node: ai2.headNode.getChildren()){
//                if(node.getValue() == y){
//                    System.out.println(game.movePiece(node.getMove()));
//                    System.out.println(game);
//                    break;
//                }
//            }
//            ai.headNode.clearChildren();
//            ai2.headNode.clearChildren();
//        }
    }

    public Move getNextMove(int depth){
        this.headNode.updateGame(this.game);
        this.headNode.clearChildren();
        int moveIndex = alphaBeta(headNode, depth, Integer.MIN_VALUE, Integer.MAX_VALUE);
        for(GameNode node: this.headNode.getChildren()){
            if(node.getValue() == moveIndex){
                return node.getMove();
            }
        }
        return null;
    }


    /**
     * Method that steps through the tree and returns the best move
     * @param node
     * @param depth
     * @param alpha
     * @param beta
     * @return
     */
    private int alphaBeta(GameNode node, int depth, int alpha, int beta) {
        node.addChildren();
        if(!node.hasChildren() || depth == 0){
            return node.getHeuristic();
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
