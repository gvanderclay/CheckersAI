package ai.gage.mitch.checkers.model;

/**
 * Holds data that represents the gameboard
 */
public class GameBoard {

    /* A checkers board is 8x8 */
    private final int BOARD_SIZE = 8;

    /* 2d array that represents the board */
    private Piece[][] board;

    public GameBoard() {
        initBoard();
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

    public Piece[][] getBoard() {
        return this.board;
    }

    public void initBoard() {
        board = new Piece[BOARD_SIZE][BOARD_SIZE];
        fillPlayerPieces(Player.BLACK, 0);
        fillPlayerPieces(Player.RED, 5);
    }

    /**
     * Fill one half of the board for one player
     * @param player Player that is being filled in
     * @param start Start point of the player being added. i.e. red player start
     *              would be 5 and black player start is 0
     */
    private void fillPlayerPieces(Player player, int start){
        for(int x = start; x < start + 3; x++){
            for(int y = 0; y < BOARD_SIZE; y++){
                if((x+y)%2 == 0){
                    board[x][y] = new Piece(player);
                }
            }
        }
    }
}
