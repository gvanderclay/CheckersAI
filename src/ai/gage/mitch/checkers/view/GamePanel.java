package ai.gage.mitch.checkers.view;

import ai.gage.mitch.checkers.model.GameBoard;
import ai.gage.mitch.checkers.model.Move;
import ai.gage.mitch.checkers.model.Player;
import ai.gage.mitch.checkers.agent.CheckersAgent;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;
import javax.swing.*;

/*********************************************************
 * The GUI that creates the display for the Checkers Game.
 * @author Mitchell Couturier & Gage VanderClay
 * @version 4/13/2016
 *********************************************************/

public class GamePanel extends JPanel {
		
	/** the numbers of rows on the board **/
	private final int ROWS = 8;

	/** the number of columns on the board **/
	private final int COLUMNS = 8;
	
	/** the game board of JButtons **/
	private JButton[][] board;
	
	/** the actual Checkers game **/
	private GameBoard game;

	/** the AI player **/
	private CheckersAgent agent;

	/** the difficulty level of the AI on a scale of 1-10 **/
	private int difficulty;
	
	/** the ActionListener for the JButtons **/
	private ButtonListener buttonListener;
	
	/** the master panel that holds all of the other panels **/
	private JPanel masterPanel;
	
	/** the main panel that the game board is in **/
	private JPanel mainPanel;
	
	/** the panel that displays the title of the game **/
	private JPanel titlePanel;
	
	/** the panel that displays whose turn it is **/
	private JPanel turnPanel;
	
	/** the label that contains the title of the game **/
	private JLabel title;

	/** the label that contains the current players turn **/
	private JLabel turn;
	
	/** the Icons for the game pieces **/
	private ImageIcon BlackPiece, RedPiece, BlackKing, RedKing;
	
	/** is true when a piece has already been selected and is waiting to be moved **/
	private boolean isSelected;

	/** used to determine is a player can double jump **/
	private Player lastMove;
	
	/** holds the coordinates for the last piece selected **/
	private int selectedRow, selectedCol;
	
	/********************************************************************************
	 * The constructor that creates the Checkers game GUI
	 ********************************************************************************/
	public GamePanel() {
		// instantiates all instance variables
		board = new JButton[ROWS][COLUMNS];
		game = new GameBoard();
		agent = new CheckersAgent(Player.BLACK,game);
		buttonListener = new ButtonListener();
		masterPanel= new JPanel();
		mainPanel = new JPanel();
		titlePanel = new JPanel();
		turnPanel = new JPanel();
		title = new JLabel("AI Checkers");
		turn = new JLabel(game.getCurrentPlayer().toString()+"'S TURN");
		isSelected = false;
		BlackPiece = new ImageIcon("images/BlackPiece.gif");;
		RedPiece = new ImageIcon("images/RedPiece.gif");;
		BlackKing = new ImageIcon("images/BlackKing.gif");;
		RedKing = new ImageIcon("images/RedKing.gif");;

		//gets the AI difficulty
		String[] difButtons = {"Easy", "Medium", "Hard"};
		int returnValue = JOptionPane.showOptionDialog(null, "Please choose an AI difficulty", "Difficulty",
				JOptionPane.DEFAULT_OPTION, 0, null, difButtons, difButtons[0]);
		if(returnValue == 0) {
			difficulty = 4;
		}else if(returnValue == 1) {
			difficulty = 7;
		}else if(returnValue == 2){
			difficulty = 10;
		}else{
			difficulty = 4;
		}

		// creates the game board
		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLUMNS; col++) {
				board[row][col] = new JButton();
				board[row][col].setPreferredSize(new Dimension(75, 75));
				board[row][col].addActionListener(buttonListener);
				mainPanel.add(board[row][col]);
			}
		}
		displayBoard();

		//sets the formatting and font of the JLabels
		turn.setFont(new Font("Times New Roman", Font.BOLD, 28));
		title.setFont(new Font("Times New Roman", Font.PLAIN, 30));
		title.setForeground(Color.WHITE);

		// sets the layout of the GUI
		titlePanel.add(title);
		turnPanel.add(turn);
		mainPanel.setLayout(new GridLayout(ROWS, COLUMNS));
		masterPanel.setLayout(new BorderLayout(10, 10));
		masterPanel.add(BorderLayout.CENTER, mainPanel);
		masterPanel.add(BorderLayout.NORTH, titlePanel);
		masterPanel.add(BorderLayout.SOUTH, turnPanel);
		titlePanel.setBackground(Color.BLACK);
		masterPanel.setBackground(Color.BLACK);
		setBackground(Color.BLACK);
		add(masterPanel);
	}
	
	/***********************************************************************
	 * Shows where each piece is on the game board using ImageIcons
	 ***********************************************************************/
	private void displayBoard() {
		// adds the chess pieces to the board
		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLUMNS; col++) {
				//displays black pieces
				if(game.pieceAt(row,col) != null && game.pieceAt(row,col).getOwner() == Player.BLACK){
					if(game.pieceAt(row,col).isKing())
						board[row][col].setIcon(BlackKing);
					else
						board[row][col].setIcon(BlackPiece);
				}
				//displays red pieces
				else if(game.pieceAt(row,col) != null && game.pieceAt(row,col).getOwner() == Player.RED){
					if(game.pieceAt(row,col).isKing())
						board[row][col].setIcon(RedKing);
					else
						board[row][col].setIcon(RedPiece);
				}
				else{
					board[row][col].setIcon(null);
				}
				
				// makes a checkered board
				if ((row % 2) == (col % 2))
					board[row][col].setBackground(Color.DARK_GRAY);
				else
					board[row][col].setBackground(Color.WHITE);
			}
		}
	}

	/***************************************************************************
	 * Moves the next piece for the AI Player's turn
	 ***************************************************************************/
	public void moveAIPiece(){
		try {
			TimeUnit.SECONDS.sleep(1);
		}catch (InterruptedException ie){
			System.out.println("OOOOPS");
		}
		Move nextMove = agent.getNextMove(difficulty);
		board[nextMove.fromRow][nextMove.fromColumn].doClick();
		try {
			TimeUnit.SECONDS.sleep(1);
		}catch (InterruptedException ie){
			System.out.println("OOOOPS");
		}
		board[nextMove.toRow][nextMove.toColumn].doClick();
	}

	public GameBoard getGame(){
		return game;
	}
	
	/***************************************************************************
	 * The ActionListener class for the game board of JButtons
	 ***************************************************************************/
	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			for (int row = 0; row < ROWS; row++) {
				for (int col = 0; col < COLUMNS; col++) {
					if (e.getSource() == board[row][col]) {
						//if this is the first piece being selected
						if(!isSelected){
							//if the piece selected is the current player's or a blank space, do nothing
							if(game.getBoard()[row][col] == null
							   || game.getCurrentPlayer() != game.getBoard()[row][col].getOwner()){
								return;
							}
							//otherwise
							else{
								isSelected = true;
								board[row][col].setBackground(Color.YELLOW);
								selectedRow = row;
								selectedCol = col;
							}
							return;
						}
						//if this is the the place on the board you want to move a piece to
						else{
							lastMove = game.getCurrentPlayer();
							Move m1 = new Move(selectedRow, selectedCol, row, col);
							boolean valid = game.movePiece(m1);
							if(game.getCurrentPlayer() == Player.RED) {
								turn.setForeground(Color.RED);
								turn.setText("YOUR TURN");
							}
							else {
								turn.setForeground(Color.BLACK);
								turn.setText("BLACK PLAYER'S TURN");
							}
							displayBoard();
							//checks if double jump is available
							if(!valid || game.getCurrentPlayer() != lastMove) {
								isSelected = false;
							}else{
								board[row][col].setBackground(Color.YELLOW);
								selectedRow = row;
								selectedCol = col;
							}
							if(game.isGameOver()){
								JOptionPane.showMessageDialog(null, "The game is over.\n"
										+game.getCurrentPlayer().next().toString()
										+" WINS!!!");
								turn.setText("GAME OVER");
								turn.setForeground(Color.BLUE);
							}
						}
						return;
					}
				}
			}
		}
	}

	/*****************************************************************
	 * Creates a GamePanel object and displays the game.
	 *****************************************************************/
	public static void main(String[] args) {
		JFrame frame = new JFrame("Checkers Game");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		GamePanel panel = new GamePanel();
		frame.getContentPane().add(panel);

		frame.pack();
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);

		while(!panel.getGame().isGameOver()) {
			if(panel.getGame().getCurrentPlayer() == Player.BLACK) {
				panel.moveAIPiece();
			}
			frame.pack();
		}
	}
}