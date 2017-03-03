package oxo_starter;

import java.util.ArrayList;

public class SimpleGameModelDecorator extends GameModelDecorator {
	private ArrayList<OXOCell> mBoard = new ArrayList<OXOCell>(BOARD_SIZE*BOARD_SIZE);
	
	public SimpleGameModelDecorator(GameModel model) {
		super(model);
		newGame();
	}
	
	@Override
	public String play(int pRow, int pCol) {
		String result = mModel.getStatusMsg();
		if (!mModel.isGameOver()) {
			result = super.play(pRow, pCol);
			update(pRow, pCol);
			if (result == null) { // this means the regular game move
				int nextPlayer = super.getMoveNumber() % 2;
				result = "Next Player: "+(nextPlayer == CROSS ? "CROSS" : "NOUGHT"); // indicate who is next move.
			}
			setStatusMsg(result);
		}
		return result;
	}

	@Override
	public String play(int pRow, int pCol, int subjectId) {
		return this.play(pRow, pCol);
	}
	
	@Override
	public String update(int pRow, int pCol) {
		int player = mModel.getCellState(pRow, pCol);
		int cellID = BOARD_SIZE * pRow + pCol;
		if (cellID < mBoard.size()) {
			OXOCell cell = mBoard.get(cellID);
			cell.setState(player);		}
		return null;
	}
	
	public void newGame() {
		if (mBoard.size() > 0) {
			mBoard.clear();
		}
		OXOCellFactorySingleton cellFactory = OXOCellFactorySingleton.getInstance();
		for (int i = 0; i < BOARD_SIZE*BOARD_SIZE; i++) {
			mBoard.add(cellFactory.createCell(this,  i));
		}
	}

	@Override
	public Object getBoard() {
		return mBoard;
	}

}
