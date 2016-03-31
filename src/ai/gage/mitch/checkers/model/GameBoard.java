package ai.gage.mitch.checkers.model;

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



    public GameBoard() {
        initBoard();
        currentPlayer = Player.BLACK;
    }

    public static void main(String args[]){
        GameBoard game = new GameBoard();
        for(Piece[] pieces: game.getBoard()){
            for(Piece piece: pieces){
                if(piece != null)
                    System.out.print(piece.getOwner() + " ");
                else
                    System.out.print("EMPTY ");
            }
            System.out.println();
        }
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
    public void initBoard() {
        board = new Piece[BOARD_SIZE][BOARD_SIZE];
        fillPlayerPieces(Player.BLACK, 0);
        fillPlayerPieces(Player.RED, 5);
    }

    /**
     * Move a piece on the board
     * @param move The move to be made
     * @return Whether or not the move can be made
     */
    public boolean movePiece(Move move){
        Piece piece = board[move.fromRow][move.fromColumn];
        if(piece.getOwner() == currentPlayer && piece.isValidMove(move, board)){
            board[move.fromRow][move.fromColumn] = null;
            board[move.fromRow][move.fromColumn] = piece;
            return true;
        }
        return false;
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
}
