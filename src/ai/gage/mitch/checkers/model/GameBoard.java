package ai.gage.mitch.checkers.model;

import java.util.ArrayList;

/**
 * Holds data that represents the gameboard
 */
public class GameBoard {

    /* A checkers board is 8x8 */
    private final int BOARD_SIZE = 8;

    /* 2d array that represents the board */
    private Piece[][] board;

    /* Current player */
    private Player currentPlayer;

    /* List of legal moves */
    private ArrayList<Move> legalMoves;

    /* Boolean that represents if the game is over or not */
    private boolean gameOver;

    public GameBoard() {
        initBoard();
        this.currentPlayer = Player.BLACK;
        this.legalMoves = getLegalMoves();
        this.gameOver = false;
    }

    public static void main(String args[]){
        GameBoard game = new GameBoard();
        System.out.println(game.movePiece(new Move(2,0, 3,1)));
    }

    /**
     * @return contents of the gameboard
     */
    public Piece[][] getBoard() {
        return this.board;
    }

    /**
     * Initializes the board
     */
    private void initBoard() {
        board = new Piece[BOARD_SIZE][BOARD_SIZE];
        fillPlayerPieces(Player.BLACK, 0);
        fillPlayerPieces(Player.RED, 5);
    }

    /**
     * Return the contents at the point on the board
     *
     * @param row    row of the board
     * @param column column of the board
     * @return Piece at this point on the board. Null if empty
     */
    public Piece pieceAt(int row, int column) {
        return board[row][column];
    }

    /**
     * Get an ArrayList of all the legal moves
     * @return list of legal moves
     */
    public ArrayList<Move> getLegalMoves() {
        ArrayList<Move> legalMoves = new ArrayList<>();
        // check for legal jumps for all pieces
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int column = 0; column < BOARD_SIZE; column++) {
                legalMoves.addAll(getLegalJumps(row, column));
            }
        }
        // if there are legal jumps, other moves aren't possible so don't check for moves that aren't jumps
        if(legalMoves.size() == 0){
            for (int row = 0; row < BOARD_SIZE; row++) {
                for (int column = 0; column < BOARD_SIZE; column++) {
                    legalMoves.addAll(getLegalMovesForPiece(row, column));
                }
            }
        }
        return legalMoves;
    }

    /**
     * Whether or not a piece can make a move. (No jumps included)
     * @param row row of the piece being checked
     * @param column column of the piece being checked
     * @return List of legal moves
     */
    private ArrayList<Move> getLegalMovesForPiece(int row, int column){
        ArrayList<Move> legalMoves = new ArrayList<>();
        Piece piece = board[row][column];
        if(piece == null){
            return legalMoves;
        }
        if(piece.getOwner() == currentPlayer){
            Move move = new Move(row, column, row + 1, column + 1);
            if(piece.isValidMove(move, board)){
                legalMoves.add(move);
            }
            move = new Move(row, column, row + 1, column - 1);
            if(piece.isValidMove(move, board)){
                legalMoves.add(move);
            }
            move = new Move(row, column, row - 1, column + 1);
            if(piece.isValidMove(move, board)){
                legalMoves.add(move);
            }
            move = new Move(row, column, row - 1, column - 1);
            if(piece.isValidMove(move, board)){
                legalMoves.add(move);
            }
        }
        return legalMoves;
    }

    /**
     * Gets all of the jumps a Piece can perform
     *
     * @param row    row of the piece
     * @param column column of the piece
     * @return The legal jumps that a piece can perform
     */
    private ArrayList<Move> getLegalJumps(int row, int column) {
        ArrayList<Move> legalJumps = new ArrayList<>();
        Piece piece = board[row][column];
        // if there isn't a piece in this location, there are no legal jumps
        if (piece == null) {
            return legalJumps;
        }
        // check if there are legal jumps for this piece
        if (piece.getOwner() == currentPlayer) {
            if (piece.isValidJump(row, column, row + 1, column + 1, row + 2, column + 2, board)){
                legalJumps.add(new Move(row, column, row + 2, column + 2));
            }
            if (piece.isValidJump(row, column, row + 1, column + -1, row + 2, column + -2, board)) {
                legalJumps.add(new Move(row, column, row + 2, column + -2));
            }
            if (piece.isValidJump(row, column, row + -1, column + 1, row + -2, column + 2, board)) {
                legalJumps.add(new Move(row, column, row + -2, column + 2));
            }
            if (piece.isValidJump(row, column, row + -1, column + -1, row + -2, column + -2, board)) {
                legalJumps.add(new Move(row, column, row + -2, column + -2));
            }
        }
        return legalJumps;
    }

    /**
     * Move a piece on the board
     * @param move The move to be made
     * @return Whether or not the move can be made
     */
    public boolean movePiece(Move move){
        Piece piece = board[move.fromRow][move.fromColumn];
        if(legalMoves.contains(move)){
            // delete the piece from the old location
            board[move.fromRow][move.fromColumn] = null;
            // put the piece in the new location
            board[move.toRow][move.toColumn] = piece;
            // If the piece should be kinged, king it
            kingPiece(move.toRow, move.toColumn);
            // if th move is a jump, delete the piece that is being jumped
            if(move.isJump()){
                removedJumpedPiece(move);
                // check for a double jump
                if(doubleJumpPossible(move.toRow, move.toColumn)){
                    // if a double jump is possible, update legal moves to only be
                    // the jumps the piece that just moved can make
                    legalMoves = getLegalJumps(move.toRow, move.toColumn);
                    // keep the player the same and don't update the legal moves again
                    return true;
                }
            }
            currentPlayer = currentPlayer.next();
            legalMoves = getLegalMoves();
            checkGameOverStatus();
            return true;
        }
        return false;
    }


    private boolean checkGameOverStatus(){
        gameOver = legalMoves.size() == 0;
        return gameOver;
    }

    /**
     * Make a piece a king if it should be kinged
     * @param row row of the piece being kinged
     * @param column column of the piece being kinged
     */
    private void kingPiece(int row, int column){
        if (currentPlayer == Player.BLACK && row == 7) {
            board[row][column].setKing(true);
        }
        else if(currentPlayer == Player.RED && row == 0){
            board[row][column].setKing(true);
        }
    }

    /**
     * Check if a jump is possible after a previous jump
     * @param row row of the piece being checked
     * @param column column of the piece being checked
     * @return whether a double jump is possible
     */
    private boolean doubleJumpPossible(int row, int column){
        ArrayList doubleJumps = getLegalJumps(row, column);
        return doubleJumps.size() > 0;
    }

    /**
     * Remove the jumped piece
     * @param move Jump that was made
     */
    private void removedJumpedPiece(Move move){
        int jumpedColumn = (move.toColumn + move.fromColumn) / 2;
        int jumpedRow = (move.toRow + move.fromRow) / 2;
        board[jumpedRow][jumpedColumn] = null;
    }

    /**
     * Fill one half of the board for one player
     * @param player Player that is being filled in
     * @param start Start point of the player being added. i.e. red player start
     *              would be 5 and black player start is 0
     */
    private void fillPlayerPieces(Player player, int start){
        for(int row = start; row < start + 3; row++){
            for(int column = 0; column < BOARD_SIZE; column++){
                if((row+column)%2 == 0){
                    board[row][column] = new Piece(player);
                }
            }
        }
    }


    /**
     * Returns the current player
     * @return the current player
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Getter for the game over status
     * @return whether or not the game is over
     */
    public boolean isGameOver(){
        return gameOver;
    }

    public String toString(){
        String str = "";
        for(Piece[] pieces: board) {
            for (Piece piece : pieces) {
                if (piece != null)
                    if(piece.isKing()){
                        str += piece.getOwner().toString().charAt(0) + "K ";
                    }else {
                        str += piece.getOwner().toString().charAt(0) + "  ";
                    }
                else
                    str += "X  ";
            }
            str += "\n";
        }
        return str;
    }
}
