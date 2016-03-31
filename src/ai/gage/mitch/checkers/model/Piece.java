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
		return correctDirection(move) && move.isDiagonal() && !pieceBlocking(move, board);
    }

    /**
     * @param move being made
     * @return Whether or not the given space is occupied
     */
    private boolean pieceBlocking(Move move, Piece[][] board){
        return board[move.toRow][move.toColumn] != null;
    }

    /**
     *
     * @param move
     * @return
     */
    private boolean correctDirection(Move move){
        int correctDirection = owner == Player.BLACK ? 1 : -1;
        if(isKing || move.toRow - move.fromRow == correctDirection){
            return true;
        }
        return false;
    }

    public boolean isKing() {
        return isKing;
    }

    public void setKing(boolean king) {
        isKing = king;
    }
}