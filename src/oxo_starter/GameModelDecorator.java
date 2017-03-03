package oxo_starter;

public abstract class GameModelDecorator extends GameModel {

	/*
	 * Base class for the Decorator pattern, that just delegates all to the object being decorated.
	 * This is done once for all decorators, concrete decorator class will override
	 * only the method that behaves differently.
	 */
	protected GameModel mModel;
	public GameModelDecorator(GameModel model) {
		this.mModel = model;
	}
	
	public GameModel getModel() {
		return mModel;
	}
	
	public int getWinner() {
		return mModel.getWinner();
	}
	
	public boolean isDraw() {
		return mModel.isDraw();
	}
	
	public int getCellState(int pRow, int pCol) {
		return mModel.getCellState(pRow, pCol);
	}
	
	public boolean testCellState(int pRow, int pCol, int pMark) {
		return mModel.testCellState(pRow, pCol, pMark);
	}
	
	public int getMoveNumber(){
		return mModel.getMoveNumber();
	}

	public void setMoveNumber(int moveNumber){
		mModel.setMoveNumber(moveNumber);
	}

	public String play(int pRow, int pCol) {
		return mModel.play(pRow, pCol);
	}

	public String play(int pRow, int pCol, int subjectId) {
		return this.play(pRow, pCol);
	}
	
	public String update(int pRow, int pCol) {
		return mModel.update(pRow, pCol);
	}

	public boolean isGameOver() {
		return mModel.isGameOver();
	}
	
	public int[] getLastMove() {
		return mModel.getLastMove();
	}
	
	public Object getBoard() {
		return mModel.getBoard();
	}

	public void attach(Observer pObserver) {
		mModel.attach(pObserver);
	}
	
	public void detach(Observer pObserver) {
		mModel.detach(pObserver);
	}

	public String getStatusMsg() {
		return mModel.getStatusMsg();
	}

	public void setStatusMsg(String statusMsg) {
		mModel.setStatusMsg(statusMsg);
	}
	
	public int getSize() {
		return mModel.getSize();
	}

	public void draw(){
		mModel.draw();
	}
}
