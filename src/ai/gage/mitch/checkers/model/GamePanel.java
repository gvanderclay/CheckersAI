package ai.gage.mitch.checkers.model;

import java.awt.*;
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
	
	/** the Icons for the game pieces **/
	private ImageIcon BlackPiece, RedPiece, BlackKing, RedKing;
	
	/********************************************************************************
	 * The constructor that creates the Checkers game GUI
	 ********************************************************************************/
	public GamePanel() {
		// instantiates all instance variables
		board = new JButton[ROWS][COLUMNS];
		game = new GameBoard();
		masterPanel= new JPanel();
		mainPanel = new JPanel();
		titlePanel = new JPanel();
		turnPanel = new JPanel();
		BlackPiece = new ImageIcon("BlackPiece.gif");;
		RedPiece = new ImageIcon("RedPiece.gif");;
		BlackKing = new ImageIcon("BlackKing.gif");;
		RedKing = new ImageIcon("RedKing.gif");;
		
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
	}
	
	/***********************************************************************
	 * Shows where each piece is on the game board using ImageIcons
	 ***********************************************************************/
	private void displayBoard() {
		// adds the chess pieces to the board
		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLUMNS; col++) {
				//displays black pieces
				if(game.pieceAt(row,col).getOwner() == Player.BLACK){
					if(game.pieceAt(row,col).isKing())
						board[row][col].setIcon(BlackKing);
					else
						board[row][col].setIcon(BlackPiece);
				}
				//displays red pieces
				else if(game.pieceAt(row,col).getOwner() == Player.RED){
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
					board[row][col].setBackground(Color.WHITE);
				else
					board[row][col].setBackground(Color.DARK_GRAY);
			}
		}
	}
	
	/***************************************************************************
	 * The ActionListener class for the game board of JButtons
	 ***************************************************************************/
	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			for (int row = 0; row < ROWS; row++) {
				for (int col = 0; col < COLUMNS; col++) {
					if (e.getSource() == board[row][col]) {
						
					}
				}
			}
		}
	}
}