package ai.gage.mitch.checkers.model;

/**
 * Class that represents a move on the checker board
 * Created by Gage Vander Clay and Mitch Couturier
 */
public class Move {
    public int fromRow, fromColumn, toRow, toColumn;


    public Move(int fromRow, int fromColumn, int toRow, int toColumn) {
        this.fromRow = fromRow;
        this.fromColumn = fromColumn;
        this.toRow = toRow;
        this.toColumn = toColumn;
    }

    public boolean isJump(){
        return Math.abs(toRow - fromRow) == 2 && Math.abs(toColumn - fromColumn) == 2;
    }

    public String toString(){
        return "From Row: " + fromRow + ", From Column: " + fromColumn
                            + ", To Row: " + toRow + ", To Column: " + toColumn + "\n";
    }

    @Override
    public boolean equals(Object object) {
        Move move = (Move) object;
        return object != null && object instanceof Move && move.toRow == this.toRow && move.toColumn == this.toColumn
                && move.fromRow == this.fromRow && move.fromColumn == this.fromColumn;
    }
}