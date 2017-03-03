package oxo_starter;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JLabel;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

/**
 *
 * @author Daniel
 */
public class OXOCell implements MouseListener {

    private JLabel mUICell = new JLabel();
    private int mState = GameModel.EMPTY_CELL;
    private int mId;
    private GameModel mModel = null;
    private int mSubjectId = -1;
    
    public OXOCell(int id, int size, GameModel model) {
        this.mId = id;
        this.setModel(model);
        mUICell.setPreferredSize(new Dimension(size==1?35:99,size==1?35:99));
        mUICell.setFont(new Font(Font.SANS_SERIF,Font.BOLD,size==1?33:170));
        initializeForState();
        mUICell.addMouseListener(this);
        mUICell.setBorder(new LineBorder(Color.black,size==1?1:5));
    }
    
    
    private void initializeForState() {
        if (mState == GameModel.EMPTY_CELL) {
            mUICell.setText(" ");
            mUICell.setForeground(Color.WHITE);
        } else if (mState == GameModel.CROSS) {
            mUICell.setText("X");
            mUICell.setForeground(Color.RED);
        } else if (mState == GameModel.NOUGHT) {
            mUICell.setText("O");
            mUICell.setForeground(Color.BLUE);
        }
    }

    /**
     * @return the uiCell
     */
    public JLabel getUiCell() {
        return mUICell;
    }

    /**
     * @return the state
     */
    public int getState() {
        return mState;
    }

    /**
     * @param state the state to set
     */
    public void setState(int state) {
        this.mState = state;
        initializeForState();
    }

    /*
     * Here I implement the MouseListener interface.
     * I'm actually only after the mouse click, other events are ignored.
     * note to self: use @Override annotation for compiler to ensure this class actually implements the interface method.
     */
    @Override
    public void mouseClicked(MouseEvent e) {
    	System.out.println("MouseClick cell: "+mSubjectId+"/"+mId+" ("+mSubjectId/GameModel.BOARD_SIZE+","+mSubjectId%GameModel.BOARD_SIZE+"/"+
    			(mId/GameModel.BOARD_SIZE)+","+mId % GameModel.BOARD_SIZE+")");
        if (mModel != null) {
            mModel.play((int)(mId/GameModel.BOARD_SIZE), mId % GameModel.BOARD_SIZE, mSubjectId);
        } else {
            throw new NullPointerException("Cell id("+mId+") is not associated with any play board.");
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }


	public GameModel getModel() {
		return mModel;
	}


	public void setModel(GameModel mModel) {
		this.mModel = mModel;
	}


	public int getSubjectId() {
		return mSubjectId;
	}


	public void setSubjectId(int mSubjectId) {
		this.mSubjectId = mSubjectId;
	}
}
