package ai.gage.mitch.checkers.model;

/**
 * Created by vandercg on 3/30/16.
 */
public enum Player {

    BLACK, WHITE;

    /**
     * Return the {@code Player} whose turn is next.
     *
     * @return the {@code Player} whose turn is next
     */
    public Player next() {
        return this == BLACK ? WHITE : BLACK;
    }
}