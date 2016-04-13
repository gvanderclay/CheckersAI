package ai.gage.mitch.checkers.agent;

import ai.gage.mitch.checkers.model.GameBoard;
import ai.gage.mitch.checkers.model.Piece;
import ai.gage.mitch.checkers.model.Player;

/**
 * This class will represent a node in the game tree. It will also contain data so alpha beta pruning can be done
 * Created by gvanderclay on 4/12/16.
 */
public class GameNode {

    // minimum upper bound of possible solutions
    private int alpha;

    // maximum upper bound of possible solutions
    private int beta;

    // whether or not this node is a maximizer
    private boolean isMaximizer;

    // state of the game
    private GameBoard game;

    public GameNode(int alpha, int beta, GameBoard game, boolean isMaximizer) {
        this.alpha = alpha;
        this.beta = beta;
        this.game = game;
        this.isMaximizer = isMaximizer;
    }

    public static void main(String[] args) {
        GameNode node = new GameNode(2342342, 2342424, new GameBoard(), true);
        System.out.println(node.getValue());
    }

    public int getAlpha() {
        return this.alpha;
    }

    public int getBeta() {
        return this.beta;
    }

    public int getValue() {
        int[] pieceCount = countPieces(game.getCurrentPlayer());
        return pieceCount[0] - pieceCount[1];
    }

    /**
     * Count pieces for both players. First value in the array is the count for the given player. Kings count as 2
     *
     * @param player player that will be counted first
     * @return the amounts of pieces for both players
     */
    private int[] countPieces(Player player) {
        int thisPieceCount = 0;
        int nextPieceCount = 0;
        for (int row = 0; row < game.getBoardSize(); row++) {
            for (int column = 0; column < game.getBoardSize(); column++) {
                if (game.pieceAt(row, column) != null) {
                    Piece piece = game.pieceAt(row, column);
                    if (piece.getOwner() == player) {
                        if (piece.isKing()) {
                            thisPieceCount++;
                        }
                        thisPieceCount++;
                    } else {
                        if (piece.isKing()) {
                            nextPieceCount++;
                        }
                        nextPieceCount++;
                    }
                }
            }
        }
        return new int[]{thisPieceCount, nextPieceCount};
    }
}
