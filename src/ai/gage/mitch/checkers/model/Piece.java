package ai.gage.mitch.checkers.model;

/**
 * Created by vandercg on 3/30/16.
 */
public class Piece {


    /* Player that owns this piece */
    private Player owner;


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

    /**
     * Whether or not the move is valid
     ***/
    public boolean isValidMove() {

        return false;
    }
}