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
        return !outOfBounds(move) && correctLength(move) && !pieceBlocking(move, board) && correctDirection(move);
    }

    /**
     * Check if a piece can jump from one point to another
     *  Cases that will fail:
     *  case1:
     *      move is off the board
     *  case2:
     *      place being jumped too is occupied
     *  case3:
     *      Piece being jumped over has the same owner as the piece doing the jumping
     *  case4:
     *      Move is not in the correct direction
     * @param row1 row of the piece doing the jump
     * @param column1 column of the piece doing the jump
     * @param row2 row of the piece being jumped
     * @param column2 column of the piece being jumped
     * @param row3 row of the board being jumped too
     * @param column3 column of the board being jumped too
     * @param board board that jump is being performed on
     * @return Whether or not a piece can jump
     */
    public boolean isValidJump(int row1, int column1, int row2, int column2, int row3, int column3, Piece board[][]) {
        if (row2 < 0 || row2 >= 8 || row3 < 0 || row3 >= 8 || column2 < 0 || column2 >= 8 || column3 < 0 || column3 >= 8) {
            return false; // off the board
        }
        // place being jumped too is occupied
        if (board[row3][column3] != null) {
            return false;
        }
        // piece being jumped over is the same player as the jumpee
        if(board[row2][column2] == null){
            return false;
        }
        if (board[row2][column2] != null && board[row2][column2].getOwner() == this.owner) {
            return false;
        }
        Move move = new Move(row1, column1, row3, column3);
        // check whether the jump is in the correct direction
        return correctDirection(move);
    }

    private boolean outOfBounds(Move move){
        return move.toRow >= 8 || move.toRow < 0 || move.toColumn >= 8 || move.toColumn < 0;
    }

    /**
     * Is the move the correct length for a move
     * @param move Move being made
     * @return if the move is 1 block away
     */
    private boolean correctLength(Move move){
        int rowLength = Math.abs(move.toRow - move.fromRow);
        int columnLength = Math.abs(move.toColumn - move.fromColumn);
        return rowLength == 1 && columnLength == 1;
    }

    /**
     * @param move being made
     * @return Whether or not the given space is occupied
     */
    private boolean pieceBlocking(Move move, Piece[][] board){
        return board[move.toRow][move.toColumn] != null;
    }


    /**
     * Check if the piece is moving in the correct direction
     * @param move Move being checked
     * @return Whether the move is in the correct direction or not
     */
    private boolean correctDirection(Move move){
        int correctDirection = owner == Player.BLACK ? 1 : -1;
        return isKing || (move.toRow - move.fromRow) / correctDirection > 0;
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