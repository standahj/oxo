/**
 * This file is part of a project entitled NoughtsAndCrosses which is
 * provided as sample code for the following Macquarie University
 * unit of study:
 *
 * COMP229 "Object Oriented Programming Practices" 
 *
 * Copyright (c) 2011 Dominic Verity and Macquarie University.
 *
 * NoughtsAndCrosses is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * NoughtsAndCrosses is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for
 * more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with NoughtsAndCrosses.  (See files COPYING and COPYING.LESSER.)  If not, see
 * <http://www.gnu.org/licenses/>.
 */
package oxo_starter;

/**
 * @author Dominic Verity
 *
 */
public class GameModel extends Subject {

	/*
	 * Constants
	 */
	public static final int BOARD_SIZE = 3;
	public static final int EMPTY_CELL = -1;
	public static final int NOUGHT = 0;
	public static final int CROSS = 1;
	
	/*
	 * Instance variables (fields)
	 * The following variables keep track of the
	 * state of this game.
	 */
	private int [][] mBoard;
	/*
	 * mMoveNumber is used to alter the players.
	 * NOUGHT turn is for even values (that includes 0)
	 * CROSS  turn is for the odd values.
	 * Having default value equal to 0 means that NOuGHT starts.
	 * SHould you need CROSS to start, set default value to 1 
	 */
	private int mMoveNumber = NOUGHT;  // indicate explicitly who will start
	private boolean bIsGameOver = false;
	private int[] mLastMove = null; // Stores coordinates of last move to enable sharing the model between text & frame views
	private String mStatusMsg; // Stores the status message that is displayed in the FrameView as well.
	private int mWinner = EMPTY_CELL;
 	/*
	 * Constructor
	 */
	public GameModel() {
		super();
		mBoard = new int[BOARD_SIZE][BOARD_SIZE];
		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				mBoard[i][j] = EMPTY_CELL;
			}
		}
	}
	
	public GameModel(int subjectId) {
		this();
		setSubjectId(subjectId);
	}
	
	// TODO: Implement game won behaviour
	
	/*
	 * Methods
	 */
	
	/*
	 * test if someone won the game.
	 * how to test:
	 * a) test rows to contain only the same mark (but not EMPTY_CELL)
	 * b) test columns/diagonals for the same
	 */
	public int getWinner() {
		// test rows 
		for (int i = 0; i < BOARD_SIZE; i++) {
			int potentialWinner = getCellState(i,0); // the would be winner mark must be equal to first cell mark
			boolean isWinner = potentialWinner != EMPTY_CELL; // and it must not be an EMPTY_CELL
			// the nested loop will not commence if it cannot be winning row
			// also if there is an EMPTY cell or opponents mark, it cannot be winning row 
			for (int j = 1; j < BOARD_SIZE && isWinner; j++) {
				isWinner = testCellState(i,j,potentialWinner); 
			}
			if (isWinner) {
System.out.println("WIN: Row="+i+" player:"+potentialWinner);
				return potentialWinner;  // break out as soon as we know there's winner
			}
		}
		// test columns - identical as rows, just swap indexes
		for (int i = 0; i < BOARD_SIZE; i++) {
			int potentialWinner = getCellState(0,i); // the would be winner mark must be equal to first cell mark
			boolean isWinner = potentialWinner != EMPTY_CELL; // and it must not be an EMPTY_CELL
			// the nested loop will not commence if it cannot be winning row
			// also if there is an EMPTY cell or opponents mark, it cannot be winning row 
			for (int j = 1; j < BOARD_SIZE && isWinner; j++) {
				isWinner = testCellState(j,i,potentialWinner); 
			}
			if (isWinner) {
System.out.println("WIN: Column="+i+" player:"+potentialWinner);
				return potentialWinner;  // break out as soon as we know there's winner
			}
		}
		// test diagonals
		int potentialWinnerLR = getCellState(0,0); // the would be winner mark must be equal to first cell mark, left-right diagonal
		boolean isWinnerLR = potentialWinnerLR != EMPTY_CELL; // and it must not be an EMPTY_CELL
		int potentialWinnerRL = getCellState(0,BOARD_SIZE-1); // the would be winner mark must be equal to first cell mark, right-left diagonal
		boolean isWinnerRL = potentialWinnerRL != EMPTY_CELL; // and it must not be an EMPTY_CELL
		// the nested loop will not commence if it cannot be winning row
		// also if there is an EMPTY cell or opponents mark, it cannot be winning row 
		for (int j = 1; j < BOARD_SIZE && (isWinnerLR || isWinnerRL); j++) {
			isWinnerLR = testCellState(j,j,potentialWinnerLR); 
			isWinnerRL = testCellState(j,BOARD_SIZE-1-j,potentialWinnerRL); 
		}
		if (isWinnerLR) {
System.out.println("WIN: LRDiag player:"+potentialWinnerLR);
			return potentialWinnerLR;  
		}
		if (isWinnerRL) {
System.out.println("WIN: RLDiag player:"+potentialWinnerRL);
			return potentialWinnerRL;  
		}
		return EMPTY_CELL; // no one won yet
	}
	
	public boolean isDraw() {
		// no winner - test for draw
		boolean isDraw = getCellState(0,0) != EMPTY_CELL; // must not have an EMPTY_CELL
		for (int i = 0; i < BOARD_SIZE; i++) {
			// the nested loop will not commence if it cannot be winning row
			// also if there is an EMPTY cell or opponents mark, it cannot be winning row 
			for (int j = 0; j < BOARD_SIZE && isDraw; j++) {
				isDraw = getCellState(i,j) != EMPTY_CELL; 
			}
		}
		return isDraw;
	}
	
	public int getCellState(int pRow, int pCol) {
		// this if statement is a guard
		if (pRow < 0 || pRow >= BOARD_SIZE ||
			pCol < 0 || pCol >= BOARD_SIZE) {
			return EMPTY_CELL;
		}
		return mBoard[pRow][pCol];
	}
	/*
	 * Test if cell contains given mark (Nought or Cross)
	 */
	public boolean testCellState(int pRow, int pCol, int pMark) {
		int cellState = getCellState(pRow,pCol);
		return cellState != EMPTY_CELL && cellState == pMark;
	}
	
	public int getSize(){
		return BOARD_SIZE;
	}
	
	public int getMoveNumber(){
		return mMoveNumber;
	}
	/*
	 * This is needed in Recursive Game - if first player wins the board, then second player
	 * continues on other board, but this requires change in the mMoveNumber so that correct player
	 * is marked on board 
	 */
	public void setMoveNumber(int moveNumber){
		mMoveNumber = moveNumber;
	}

	/*
	 * To improve user experience with the game, the play method now returns a String that
	 * gives friendly message about potential invalid move (outside of the board or to already occupied cell.
	 * Returns: String with error msg or Null in case the input is valid.   
	 */
	public String play(int pRow, int pCol) {
		// this if statement is a guard
		if (pRow < 0 || pRow >= BOARD_SIZE ||
			pCol < 0 || pCol >= BOARD_SIZE) {
			return "Wrong destination: cell ["+pRow+","+pCol+"] is outside of the playing board, both row and column must be in range of <0,"+(BOARD_SIZE-1)+">."; 
		}
		if (mBoard[pRow][pCol] != EMPTY_CELL) {
			return "Destination in use: cell ["+pRow+","+pCol+"] is already taken by "+(mBoard[pRow][pCol]==NOUGHT?"NOUGHT":"CROSS");
		}
		mBoard[pRow][pCol] = mMoveNumber % 2;
		mMoveNumber++;
		if (mLastMove == null) {
			mLastMove = new int[2];
		}
		mLastMove[0] = pRow;
		mLastMove[1] = pCol;
		// Check if there's a winner already
		mWinner = getWinner();
		bIsGameOver = mWinner != EMPTY_CELL;
		String result = null;
		if (bIsGameOver)
			result = "GAME OVER!!!  "+(mWinner==CROSS?"CROSS":"NOUGHT")+" has won the game.";
		if (isDraw()) {
			bIsGameOver=true;
			result = "GAME OVER!  there is no winner this time.";
		}
		sendNotifications();
		return result;
	}

	public String play(int pRow, int pCol, int subjectId) {
		return this.play(pRow, pCol);
	}
	
	public boolean isGameOver() {
		return bIsGameOver;
	}
	
	public int[] getLastMove() {
		return mLastMove;
	}
	
	public String update(int pRow, int pCol) {
		mBoard[pRow][pCol] = mMoveNumber % 2;
		mMoveNumber++;
		if (mLastMove == null) {
			mLastMove = new int[2];
		}
		mLastMove[0] = pRow;
		mLastMove[1] = pCol;
		// Check if there's a winner already
		mWinner = getWinner();
		bIsGameOver = mWinner != EMPTY_CELL;
		if (bIsGameOver)
			return "GAME OVER!!!  "+(mWinner==CROSS?"CROSS":"NOUGHT")+" has won the game.";
		if (isDraw()) {
			bIsGameOver=true;
			return "GAME OVER!  there is no winner this time.";
		}
		return null;
	}
	
	public Object getBoard() {
		return mBoard;
	}

	public String getStatusMsg() {
		return mStatusMsg;
	}

	public void setStatusMsg(String statusMsg) {
		this.mStatusMsg = statusMsg;
//System.out.println("setStatusMsg: "+statusMsg);
	}
	
	public void draw() {
		for(int i = 0; i < BOARD_SIZE; i++){
			for(int j = 0; j < BOARD_SIZE; j++){
				switch (getCellState(i, j)){
				  case GameModel.NOUGHT : System.out.print("o"); break;
				  case GameModel.CROSS : System.out.print("x"); break;
				  default: System.out.print("-");
				}
			}
			System.out.print("\n");
		}
		System.out.println("***********");
	}

	public int getWinningPlayer() {
		return mWinner;
	}
}
