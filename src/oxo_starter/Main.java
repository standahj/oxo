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

import javax.swing.JFrame;

/**
 * @author Matt Roberts
 */
@SuppressWarnings("serial")
public class Main extends JFrame {

	public static void main(String[] args) {
		final GameModel mGameModel;
		mGameModel = new GameModel();
		for (int i = 0; i < args.length; i++){
			if (args[i].equals("t")){
				(new Thread(new TextView(mGameModel))).start();
			}
			else if (args[i].equals("f")){
				//Converting the above two lines into one line: let's use small trick NetBeans IDE does 
				//by default when creating a Swing app - Schedule a job for the event dispatch thread.
				//This should make application safer from the perspective of multithreading too.
		        javax.swing.SwingUtilities.invokeLater(new Runnable() {
		            public void run() {
		                FrameView.createAndShowGUI(mGameModel);
		            }
		        });
			}

		}
	}

}
