package ai.gage.mitch.checkers.model;

/**
 * Created by vandercg on 3/30/16.
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

    public Player getOwner() {
        return this.owner;
    }

    /*************************************************************************
     * Determines whether or not the move is valid
	 * @param Move represents the move being taken
	 * ***********************************************************************/
    public boolean isValidMove(Move move) {
		//If this piece is not a King
		if(!isKing){
			if(owner == Player.RED){
				//check moves for one direction (SOUTH)
			}
			else if(owner == Player.BLACK){
				//check moves for other direction (NORTH)
			}
		}
		//If this piece is a King
		else{
			//check moves for any direction
		}
	
        return false;
    }
}