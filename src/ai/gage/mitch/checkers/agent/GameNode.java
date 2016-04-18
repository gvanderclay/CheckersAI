package ai.gage.mitch.checkers.agent;

import ai.gage.mitch.checkers.model.GameBoard;
import ai.gage.mitch.checkers.model.Move;
import ai.gage.mitch.checkers.model.Piece;
import ai.gage.mitch.checkers.model.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * This class will represent a node in the game tree. It will also contain data so alpha beta pruning can be done
 * Created by gvanderclay on 4/12/16.
 */
public class GameNode {

    // minimum upper bound of possible solutions
    private int alpha;

    // maximum upper bound of possible solutions
    private int beta;

    private int value;

    // whether or not this node is a maximizer
    private boolean isMaximizer;

    // state of the game
    private GameBoard game;

    // children of this node
    private List<GameNode> children;

    private Move move;

    public GameNode(int alpha, int beta, GameBoard game, boolean isMaximizer) {
        this.alpha = alpha;
        this.beta = beta;
        this.game = game;
        this.isMaximizer = isMaximizer;
        this.children = new ArrayList<GameNode>();
        this.value = getHeuristic();
        this.move = null;
    }

    public static void main(String[] args) {

    }

    public void addChild(GameNode node) {
        children.add(node);
    }

    public boolean hasChildren() {
        return children.size() > 0;
    }

    /**
     * get the value of the node at this point
     *
     * @return the value of the board
     */
    public int getHeuristic() {
        int[] pieceCount = countPieces(game.getCurrentPlayer());
        return pieceCount[0] - pieceCount[1];
    }

    public GameBoard getGame(){
        return this.game;
    }

    public void updateGame(GameBoard game){
        this.game = game;
    }

    public int getValue(){
        return this.value;
    }

    public void setValue(int value){
        this.value = value;
    }

    public boolean isMaximizer(){
        return this.isMaximizer;
    }

    public void setMove(Move move){
        this.move = move;
    }

    public Move getMove(){
        return this.move;
    }

    public void addChildren(){
        for(Move move : this.game.getLegalMoves()){
            GameBoard nextGame = new GameBoard(this.game);
            nextGame.movePiece(move);
            GameNode node = new GameNode(this.alpha, this.beta, nextGame, !isMaximizer);
            node.setMove(move);
            this.addChild(node);
        }
    }

    public void clearChildren(){
        this.children = new ArrayList<>();
    }

    public List<GameNode> getChildren(){
        return this.children;
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
