package ai.gage.mitch.checkers.model;

/**
 * Created by vandercg on 3/30/16.
 */
public enum Player {

    BLACK, RED;

    /**
     * Return the {@code Player} whose turn is next.
     *
     * @return the {@code Player} whose turn is next
     */
    public Player next() {
        return this == BLACK ? RED : BLACK;
    }
    public String toString(){
        if(this == BLACK)
            return "BLACK PLAYER'S TURN";
        else
            return "RED PLAYER'S TURN";
    }
}