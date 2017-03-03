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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Matt Roberts
 */
public class TextView implements GameView {

	GameModel mModel; 
	
	public TextView(GameModel model){
		this.mModel = model;
		this.mModel.attach(this);
	}
	
	public void run() {
		draw();
		GameInputReader inputReader = new GameInputReader(mModel);
		inputReader.start();
	}

	public void receiveNotification(int subjectId){
//		System.out.println("TextView NOTIFICATION");
		if (!mModel.isGameOver()) {
			draw();
		}
		System.out.println(mModel.getStatusMsg());
	}
	
	
	/*
	 * note to self: draw() is called over&over to keep looping/reading user input from thread(s) started from notification.
	 */
	private void draw(){
		mModel.draw();
		// Exit only after printing the winning board (note - FrameView will exit too, there'll be no time to read the GAME Over message).
//		if (model.isGameOver())
//			System.exit(0); 
	}
	
	/*
	 * Reading of input is blocking call. Blocking TextView on read will prevent interactions with FrameView
	 * if player would prefer to use the FrameView as playing field, rather then manually inserting x,y coordinates.
	 * To enable this interaction, the blocking read is moved to the dedicated thread, that waits for input, and if 
	 * there's any, it interact with the model in principle the same way as the FrameView.  
	 */
	public class GameInputReader extends Thread {
		GameModel mModelReader;
		public GameInputReader(GameModel model) {
			mModelReader = model;
		}
		public void run() {
			while (true) {
				System.out.println("Where would you like to move next? x,y");
				try {while (System.in.available() == 0){Thread.sleep(500);}} catch (IOException e1) {e1.printStackTrace();} catch (InterruptedException e1) {e1.printStackTrace();}
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
				String response = null;
				try {
					response = bufferedReader.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
				if (response.equals("q"))
					System.exit(0);
				String[] pos = response.split(",");
				// improved error handling. There is either wrong input or invalid move request.
				try {
					// Returns error msg describing invalid input, or Null for valid input
					// can throw exception if there is parser error, which will cause premature exit. 
					// catching the exception allows user to correct wrong input.
					String error = mModelReader.play(Integer.parseInt(pos[1]), Integer.parseInt(pos[0]));
					if (error != null) {
						System.out.println(error);
					}
				} catch (Exception e) {
					System.out.println("Error: "+e.getMessage());
					System.out.println("Please use only numbers in range <0-2> separated by comma");
				}
			}
		}
	}
}
