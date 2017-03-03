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
/**
 * An interface containing all the methods needed to act as a view of
 * an oxo game during progress.  Methods are required for setting up and
 * starting the view (run, which is inherited from Runnable) and being
 * notified of changes to the underlying model (recieveNotification, 
 * which is inherited from Observer).  Each game view needs to be 
 * instantiated (constructed) with a reference to a game model (which
 * should implement Subject).
 * @author Matt Roberts
 */
public interface GameView extends Runnable, Observer {
	
	GameModel model = null;
	
/*
 * Note on the Strategy pattern. The method 'public void run()' as defined in Runnable interface
 * represents a behavior of the concrete class that implements the run() method, thus it requires
 * no modification at the interface level (in the GameView interface) in order to participate in Strategy pattern.
 * 
 *    The Strategy pattern further assumes that the object implementing given strategy is included 
 *    to the owner object via composition. In the oxo_starter application, classes TextView & FrameView
 *    both implement GameView interface, thus these two classes are concrete (but different) instantiations
 *    of the Strategy object. The "Main" class chooses the desired strategy based on cmd line parameters.
 *    This leads to conclusion that the Strategy pattern is implemented in the oxo_starter application.
 *    
 *    Note - the cmd line parameters are not mutually exclusive, 
 *    by providing specific parameters to the Main class we can have an arbitrary combination of strategies,
 *    that are all applied the same time (Text or Frame or both views)
 *    
 */
}
