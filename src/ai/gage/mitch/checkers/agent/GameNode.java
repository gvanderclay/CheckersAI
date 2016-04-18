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

    private int value;

    // whether or not this node is a maximizer
    private boolean isMaximizer;

    // state of the game
    private GameBoard game;

    // children of this node
    private List<GameNode> children;

    // move that is associated with this game state
    private Move move;

    public GameNode(GameBoard game, boolean isMaximizer) {
        this.game = game;
        this.isMaximizer = isMaximizer;
        this.children = new ArrayList<>();
        this.value = getHeuristic();
        this.move = null;
    }

    public static void main(String[] args) {

    }

    /**
     * Add a single node to the game tree
     * @param node
     */
    public void addChild(GameNode node) {
        children.add(node);
    }

    /**
     * Checks if the node has children
     * @return
     */
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

    /**
     * Get the gameboard associated with this node
     * @return
     */
    public GameBoard getGame(){
        return this.game;
    }

    /**
     * Updates the state of the game
     * @param game
     */
    public void updateGame(GameBoard game){
        this.game = game;
    }

    /**
     * Gets the value of this node
     * @return
     */
    public int getValue(){
        return this.value;
    }

    /**
     * Sets the value of this node
     * @param value
     */
    public void setValue(int value){
        this.value = value;
    }

    /**
     * Checks if this node is a maximizer meaning it chooses the max child node
     * @return
     */
    public boolean isMaximizer(){
        return this.isMaximizer;
    }

    /**
     * Sets the move associated with this node
     * @param move
     */
    public void setMove(Move move){
        this.move = move;
    }

    /**
     * Gets the move associated with this node
     * @return
     */
    public Move getMove(){
        return this.move;
    }

    /**
     * Adds a child for all of the legal moves on this gameboard
     */
    public void addChildren(){
        for(Move move : this.game.getLegalMoves()){
            GameBoard nextGame = new GameBoard(this.game);
            nextGame.movePiece(move);
            GameNode node = new GameNode(nextGame, !isMaximizer);
            node.setMove(move);
            this.addChild(node);
        }
    }

    /**
     * Clears all children from this node
     **/
    public void clearChildren(){
        this.children = new ArrayList<>();
    }

    /**
     * Gets all the children of this node
     * @return
     */
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
