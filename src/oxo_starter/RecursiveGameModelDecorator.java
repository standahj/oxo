package oxo_starter;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class RecursiveGameModelDecorator extends SimpleGameModelDecorator {

	private RecursiveGameModelDecorator[][] mRecursiveBoard = null;
	private JPanel mPanes[][] = null;
	private int boardSize = BOARD_SIZE;
	private int mRecursiveMoveNumber = 0; 
	private int mTargetSubjectId = 0;
	private boolean bNeedSwitchBoard = true;
	
	public RecursiveGameModelDecorator(GameModel pModel, boolean pBuildModel) {
		super(pModel);
		boardSize = pModel.getSize();
		if (!pBuildModel) {
			newGame();
		}
		mRecursiveMoveNumber = pModel.getMoveNumber(); // ensure consistent behavior, sync the who starts
	}

	/*
	 * buildModel cannot be part of constructor, even though it is critical part of the init procedure,
	 * because I need to use 'this' operator to enable play() event being handled by correct decorator
	 * for Recursive Game.
	 * The play() comes from cell, which is part of the nested model, but the nested model has no visibility
	 * of the overall game thus it cannot be primary recipient of the event.  
	 */
	public void buildModel() {
		mRecursiveBoard = new RecursiveGameModelDecorator[boardSize][boardSize];
		mPanes = new JPanel[boardSize][boardSize];
		// Recursive game is composed of other games, create the actual model now
		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++) {
				mRecursiveBoard[i][j] = new RecursiveGameModelDecorator(new GameModel(boardSize*i+j), false);
				mPanes[i][j] = new JPanel();
				mPanes[i][j].setPreferredSize(new Dimension(105, 105));
				mPanes[i][j].setBorder(new LineBorder(boardSize*i+j == mTargetSubjectId ? Color.CYAN : Color.BLACK, 2));
				Object list = mRecursiveBoard[i][j].getBoard();
		        if (list != null && list.getClass().isAssignableFrom(ArrayList.class))  {
		        	List cells = (List)list;
		        	for (Object cell : cells) {
		        		if (OXOCell.class.isInstance(cell)) {
		        			mPanes[i][j].add(((OXOCell)cell).getUiCell());
		        			// This is very IMPORTANT - the play() events MUST be handled by single model, 
		        			// and re-distributed to child models according the Recursive OXO logic.
		        			// Thus I have to override the default model in Cell with the common model, that is as parameter
		        			((OXOCell)cell).setModel(this);  
		        			((OXOCell)cell).setSubjectId(boardSize*i+j);
		        		}
		        	}
		        }
			}
		}
	}
	
	public ArrayList<RecursiveGameModelDecorator> getRecursiveBoard() {
		ArrayList<RecursiveGameModelDecorator> models = new ArrayList<RecursiveGameModelDecorator>(boardSize*boardSize);
		for (int i = 0; i < boardSize; i++)
			for (int j = 0; j < boardSize; j++)
				models.add(mRecursiveBoard[i][j]);
		return models;
	}
	
	public ArrayList<JPanel> getPanes() {
		ArrayList<JPanel> panes = new ArrayList<JPanel>(boardSize*boardSize);
		for (int i = 0; i < boardSize; i++)
			for (int j = 0; j < boardSize; j++)
				panes.add(mPanes[i][j]);
		return panes;
	}

	@Override
	public String play(int pRow, int pCol, int pSubjectId) {
		if (pSubjectId != mTargetSubjectId && mTargetSubjectId != -1) {
			setStatusMsg("Play on Wrong Board");
			// disallow play on wrong board
System.out.println("Play on Wrong Board, expected:"+mTargetSubjectId+", actual:"+pSubjectId);
			return null;
		}
		// play on currently targeted board
		RecursiveGameModelDecorator recursiveModelCurrent = 
				pSubjectId == -1 ? (RecursiveGameModelDecorator)getModel() : mRecursiveBoard[(int)pSubjectId/boardSize][pSubjectId%boardSize];
		String recursiveResult = recursiveModelCurrent.getStatusMsg();
		boolean recursiveGameOver = false;
		if (!recursiveModelCurrent.isGameOver()) {
			recursiveResult = recursiveModelCurrent.mModel.play(pRow, pCol);
			// output also textview form to console
			recursiveModelCurrent.mModel.draw();
			recursiveModelCurrent.update(pRow, pCol);
			recursiveGameOver = recursiveModelCurrent.isGameOver();
		}
		// count local moves (only the correct moves)
		mRecursiveMoveNumber++;
		int nextPlayer = mRecursiveMoveNumber % 2;
		// if player is allowed to choose board, ensure that second player plays the same board
		if (mTargetSubjectId == -1)
			bNeedSwitchBoard = true;
		bNeedSwitchBoard = !bNeedSwitchBoard;
		String result = "Next Player: "+(nextPlayer == CROSS ? "CROSS" : "NOUGHT"); // indicate who is next move.
		setStatusMsg(result);
		// deal with the completed board - update the master board
		if (recursiveGameOver) {
			 if (pSubjectId != -1) 
				 mPanes[(int)pSubjectId/boardSize][pSubjectId%boardSize].setBackground(Color.GRAY);
			result = mModel.getStatusMsg();
			if (!mModel.isGameOver() && pSubjectId >= 0) { 
				mModel.setMoveNumber(recursiveModelCurrent.getModel().getWinningPlayer());
				result = super.play((int)pSubjectId/boardSize, pSubjectId%boardSize);
				update((int)pSubjectId/boardSize, pSubjectId%boardSize);
			}
			bNeedSwitchBoard = true;
		}
		// determine next target board
		if (recursiveGameOver || mTargetSubjectId == -1 || bNeedSwitchBoard) {
			// note - only second move on the board determines next target, it is when the move number is odd (i.e. nextPlayer = 1)
			if (recursiveGameOver)
				bNeedSwitchBoard = true; // to achieve this count, toggle the boolean value, it has more reliable outcome the MODULO 2 on move number, if there's extraordinary change
			
			// note: if user can select arbitrary board (mTargetSubjectId == -1) then go with user board selection (indicated in pSubjectId)
			// otherwise the rules say move to board positionally corresponding to the last played field
			int potentialNextTarget = mTargetSubjectId == -1 ? pSubjectId : boardSize*pRow+pCol;
			
			// need to test if the next target board is available for play
			RecursiveGameModelDecorator pntBoard = mRecursiveBoard[(int)potentialNextTarget/boardSize][potentialNextTarget%boardSize];
			if (pntBoard.isGameOver() || recursiveGameOver) {
				potentialNextTarget = -1; // allow play anywhere, if target completed, or first player won the board.
			}
			// remove the border highlight on current board
			if (mTargetSubjectId != -1) {
				mPanes[(int)mTargetSubjectId/boardSize][mTargetSubjectId%boardSize].setBorder(new LineBorder(Color.BLACK, 2));
			}
			mTargetSubjectId = potentialNextTarget;
			// highlight new target board
			if (mTargetSubjectId != -1) {
				// if I know which is the target, do it
				mPanes[(int)mTargetSubjectId/boardSize][mTargetSubjectId%boardSize].setBorder(new LineBorder(Color.CYAN, 2));
				mRecursiveBoard[(int)potentialNextTarget/boardSize][potentialNextTarget%boardSize].setMoveNumber(mRecursiveMoveNumber);
			} else {
				// player can choose a target, need to initialize move number for all applicable 
				for (int i = 0; i < boardSize; i++)
					for (int j = 0; j < boardSize; j++)
						if(!mRecursiveBoard[i][j].isGameOver())
							mRecursiveBoard[i][j].setMoveNumber(mRecursiveMoveNumber);

			}
		}
		return result;
	}

}
