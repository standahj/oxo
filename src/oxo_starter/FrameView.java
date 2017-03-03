/**
 * This file is part of a project entitled NoughtsAndCrosses which is
 * provided as sample code for the following Macquarie University
 * unit of study:
 *
 * COMP229 "Object Oriented Programming Practices" 
 *
 * Copyright (c) 2013 Matt Roberts and Macquarie University.
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
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;

/**
 * A view of a oxo game which displays the number of moves played in a graphical
 * window.
 * 
 * @author Matt Roberts
 */
@SuppressWarnings("serial")
public class FrameView extends JFrame implements GameView {

	GameModel mModel = null; 
	RecursiveGameModelDecorator mTopRecursiveModel = null;
	ArrayList<RecursiveGameModelDecorator> mRecursiveModels = null;
    JTabbedPane mOxoGameSelector = new JTabbedPane();
    JPanel mOxoSimpleGame = new JPanel();
    JPanel mOxoRecursiveGame = new JPanel();
    JLabel mStatusMsg = new JLabel();
	
    public FrameView(String name) {
		super(name);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public FrameView(GameModel model){
		this("Daniel's OXO Game");
		this.mModel = model;
		this.mModel.attach(this);
	}

    public void setModel(GameModel model) {
    	if (this.mModel != null)
    		this.mModel.detach(this);
		this.mModel = model;
		this.mModel.attach(this);
    }
    
	public void receiveNotification(int subjectID) {
//		numLabel.setText(Integer.toString(model.getMoveNumber()));
//System.out.println("FrameView NOTIFICATION id="+subjectID);
		if (subjectID == -1) {
			// Simple Game
			int[] m = mModel.getLastMove();
			if (!mModel.isGameOver() && m != null)
				mModel.update(m[0],m[1]);
			mStatusMsg.setText(mModel.getStatusMsg());
		} else {
			// Recursive Game
			if (mRecursiveModels != null && subjectID < mRecursiveModels.size() && subjectID >= 0) {
				RecursiveGameModelDecorator rModel = mRecursiveModels.get(subjectID); 
				int[] m = rModel.getLastMove();
				if (!rModel.isGameOver() && m != null)
					rModel.update(m[0],m[1]);
			}
			mStatusMsg.setText(mTopRecursiveModel.getStatusMsg()); // take status from the "master" model that controls the game
		}
	}
	
	private void setupSimpleGame(JPanel p, GameModel m) {
        Object list = m.getBoard();
        if (list != null && list.getClass().isAssignableFrom(ArrayList.class))  {
        	List cells = (List)list;
        	for (Object cell : cells) {
        		if (OXOCell.class.isInstance(cell)) {
        			p.add(((OXOCell)cell).getUiCell());
        		}
        	}
        }
	}
	private void setupRecursiveGame(JPanel board) {
		mTopRecursiveModel = new RecursiveGameModelDecorator(((SimpleGameModelDecorator)mModel).getModel(), true);
		mTopRecursiveModel.buildModel();
		mTopRecursiveModel.setSubjectId(-9); // differentiate simple game from recursive, simple game has subject id = -1
		ArrayList<JPanel> panes = mTopRecursiveModel.getPanes();
		mRecursiveModels = mTopRecursiveModel.getRecursiveBoard();
		//get the nested model that has all needed Observers, otherwise here I won't be able to attach TextView observer
//		GameModel rootModel = mTopRecursiveModel.getModel();
		for (int i = 0; i < panes.size() && i < mRecursiveModels.size(); i++) {
			JPanel pane = panes.get(i);
			RecursiveGameModelDecorator model = mRecursiveModels.get(i);
			setupSimpleGame(pane, model);
//			model.getModel().attach(rootModel); // attach all Observers like in the referenced model
			model.getModel().attach(this); 
			board.add(pane);
		}
	}
	
    public void addComponentsToPane(final Container pane) {
    	// let's make the game types available as Tabs on screen
    	/*
    	 * Simple OXO Game setup
    	 */
        mOxoGameSelector.addTab("Simple OXO Game", mOxoSimpleGame);
        mOxoGameSelector.setPreferredSize(new Dimension(450, 450));
        mOxoSimpleGame.setLayout(new GridLayout(3,3,5,5));
        setupSimpleGame(mOxoSimpleGame, mModel);
        /*
         * Recursive Game setup
         */
        mOxoGameSelector.addTab("Recursive OXO Game", mOxoRecursiveGame);
        mOxoRecursiveGame.setLayout(new GridLayout(3,3,5,5));
        setupRecursiveGame(mOxoRecursiveGame);
        /*
         * Common setup
         */
        GroupLayout layout = new GroupLayout(pane);
        pane.setLayout(layout);
        
        layout.setHorizontalGroup(
        		  layout.createParallelGroup(GroupLayout.Alignment.LEADING)
        		           .addComponent(mOxoGameSelector,450, 450, 450)
        		           .addComponent(mStatusMsg,GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,GroupLayout.PREFERRED_SIZE))
        		;
		layout.setVerticalGroup(
        		   layout.createSequentialGroup()
      		           .addComponent(mOxoGameSelector, 450,450,450)
        		      .addComponent(mStatusMsg, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE)
        		);
        
        //-----
		/*
        pane.setLayout(new BoxLayout(pane, BoxLayout.PAGE_AXIS));
        pane.add(mOxoGameSelector);//, BorderLayout.NORTH);
        pane.add(new JSeparator());//, BorderLayout.CENTER);
        JPanel statusPanel = new JPanel();
        statusPanel.setPreferredSize(new Dimension(450,200));
        statusPanel.setBackground(Color.ORANGE);
        statusPanel.setAlignmentY(BOTTOM_ALIGNMENT);
        statusPanel.setAlignmentX(LEFT_ALIGNMENT);
        statusPanel.add(mStatusMsg);
        pane.add(statusPanel);
        mStatusMsg.setAlignmentY(BOTTOM_ALIGNMENT);
        mStatusMsg.setAlignmentX(LEFT_ALIGNMENT);
        */
        mStatusMsg.setText("Next Player: "+(mModel.getMoveNumber()%2==GameModel.CROSS?"CROSS":"NOUGHT"));
    }

	public void run() {
        setModel(new SimpleGameModelDecorator(mModel));
		addComponentsToPane(getContentPane());
        setPreferredSize(new Dimension(455, 500));
	     // ready now, show it on screen...
	    pack();
	    setVisible(true);
	}
    /**
     * Create the GUI and show it.  For thread safety,
     * this method is invoked from the
     * event dispatch thread.
     */
    public static void createAndShowGUI(GameModel model) {
        //Create and set up the window.
        FrameView frame = new FrameView("Daniel's OXO Game");
        //First set the model
        frame.setModel(new SimpleGameModelDecorator(model));
        //and only after model set up the content pane.
        frame.addComponentsToPane(frame.getContentPane());
        frame.setPreferredSize(new Dimension(455, 500));
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

	/*
	 * original run() method
	public void run() {
		/ *
		 * TODO add space around the text in the frame.
		 * Solution: use FlowLayout (note: it organizes objects onto row) 
		 * and insert a placeholder(s) before (and after) the panel with the text.
		 * Using JPanel for the placeholder/spacer allows also increasing height of the frame.
		 * /
	 getContentPane().setLayout(new FlowLayout());
	 // leading spacer
	 JPanel leadSpacer = new JPanel();
	 leadSpacer.setPreferredSize(new Dimension(10, 70));
	 / *
	  * Originally the label and text were added to the root pane. Now the root pane
	  * has been effectively taken over by the layout manager, thus to have the text to
	  * appear in the middle (between spacers), I need to add it to new JPanel: 
	  * /
	 JPanel reporter = new JPanel();
	 reporter.setPreferredSize(new Dimension(80, 70));
     reporter.add(new JLabel("move number:"), BorderLayout.CENTER);
     reporter.add(numLabel, BorderLayout.SOUTH);
     // and now make some trailing space placeholder too, for the symmetry....
     JPanel trailSpacer = new JPanel();
	 trailSpacer.setPreferredSize(new Dimension(10, 70));
	 // the default pane is now FlowLayout managed, adding the components in display order.
     getContentPane().add(leadSpacer);
     getContentPane().add(reporter);
     getContentPane().add(trailSpacer);
     // ready now, show it on screen...
     pack();
     setVisible(true);
	}
	*/
}
