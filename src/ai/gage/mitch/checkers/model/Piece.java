package ai.gage.mitch.checkers.model;

/**
 * Created by Gage & Mitch
 */
public class Piece {


    /* Player that owns this piece */
    private Player owner;


    /* Is true if the piece has become a King */
	private boolean isKing;


    /****************************************************************
     * The constructor that assigns a player to the piece
     *
     * @param player current player
     ****************************************************************/
    protected Piece(Player player) {
        this.owner = player;
    }

    public static void main(String[] args){
        Move move = new Move(1,1,0,0);
        System.out.print(new Piece(Player.BLACK).correctDirection(move));
    }

    public Player getOwner() {
        return this.owner;
    }

    /*************************************************************************
     * Determines whether or not the move is valid
	 * @param move represents the move being taken
     * @param board represents the board that this piece is on
	 * ***********************************************************************/
    public boolean isValidMove(Move move, Piece[][] board) {
		return correctDirection(move) && move.isDiagonal() && !pieceBlocking(move, board) &&
                !canJump(move.fromRow, move.fromColumn, board);
    }

    /**
     * @param move being made
     * @return Whether or not the given space is occupied
     */
    private boolean pieceBlocking(Move move, Piece[][] board){
        return board[move.toRow][move.toColumn] != null;
    }

    /**
     * Whether the move is a jump or not
     * @param move The move being made
     * @param board Board being moved on
     * @return Whether the move is a jump
     */
    private boolean isJump(Move move, Piece[][] board){
        return false;
    }

    /**
     * Check if the piece is moving in the correct direction
     * @param move
     * @return
     */
    private boolean correctDirection(Move move){
        int correctDirection = owner == Player.BLACK ? 1 : -1;
        return isKing || move.toRow - move.fromRow == correctDirection;
    }

    /**
     * Check if the piece can make a jump
     * @param currentRow Current row of the piece
     * @param currentColumn Current column of the piece
     * @param board board the piece is on
     * @return whether or not the piece can make a jump
     */
    private boolean canJump(int currentRow, int currentColumn, Piece[][] board){
        // check if the piece can make a jump in correct direction
        return canJumpCorrectDirection(currentRow, currentColumn, 1, 1, board)||
                canJumpCorrectDirection(currentRow, currentColumn, 1,-1,board) ||
                canJumpCorrectDirection(currentRow, currentColumn, -1,1,board) ||
                canJumpCorrectDirection(currentRow, currentColumn, -1,-1,board);
    }

    /**
     * Can jump and the jump is in the correct direction
     * @param currentRow Current row of the piece
     * @param currentColumn Current column of the piece
     * @param rowChange The direction the row is changing
     * @param columnChange The direction the column is changing
     * @param board The board that is being moved on
     * @return Whether the piece can jump in the correct direction
     */
    private boolean canJumpCorrectDirection(int currentRow, int currentColumn, int rowChange, int columnChange, Piece [][] board){
        return canJumpDirection(currentRow, currentColumn, rowChange, columnChange, board) &&
                correctDirection(
                        new Move(currentRow, currentColumn, currentRow + rowChange, currentColumn + columnChange));
    }

    /**
     * Checks if this piece can jump in a certain direction
     * @param currentRow row of the piece
     * @param currentColumn column of the piece
     * @param rowChange direction of row change i.e. 1 is down, -1 is up
     * @param columnChange direction of column change i.e. 1 is right -1 is left
     * @param board board the piece is on
     * @return whether the board can jump in a direction
     */
    private boolean canJumpDirection(int currentRow, int currentColumn ,int rowChange, int columnChange, Piece[][] board){
        // row and column other piece could be in
        int checkRow = currentRow + rowChange;
        int checkColumn = currentColumn + columnChange;
        // row and column we could potentially jump to
        int jumpRow = currentRow + rowChange * 2;
        int jumpColumn = currentColumn + columnChange * 2;
        // if the jump is out of bounds it can't jump
        if(checkRow <= 0 || checkRow >= 7 || checkColumn <= 0 || checkColumn >= 7 ||
                jumpRow <= 0 || jumpRow >= 7 || jumpColumn <= 0 || jumpColumn >= 7){
            return false;
        }
        Piece checkPiece = board[checkRow][checkColumn];
        Piece jumpPiece = board[jumpRow][jumpColumn];
        return checkPiece != null && checkPiece.getOwner() != this.owner && jumpPiece == null;
    }

    /**
     * Getter for isKing
     * @return if this piece is king or not
     */
    public boolean isKing() {
        return isKing;
    }

    public void setKing(boolean king) {
        isKing = king;
    }
}