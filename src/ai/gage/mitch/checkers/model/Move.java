package ai.gage.mitch.checkers.model;

/**
 * Created by vandercg on 3/30/16.
 */
public class Move {
    public int fromRow, fromColumn, toRow, toColumn;


    public Move(int fromRow, int fromColumn, int toRow, int toColumn) {
        this.fromRow = fromRow;
        this.fromColumn = fromColumn;
        this.toRow = toRow;
        this.toColumn = toColumn;
    }

    /**
     * @return Whether or not the move is diagonal
     */
    public boolean isDiagonal(){
        return Math.abs(fromRow - toRow) == Math.abs(fromColumn - toColumn);
    }
}