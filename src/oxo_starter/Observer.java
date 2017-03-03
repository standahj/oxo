/**
 * This file is part of a project entitled Week5NoughtsAndCrosses which is
 * provided as sample code for the following Macquarie University
 * unit of study:
 *
 * COMP229 "Object Oriented Programming Practices" 
 *
 * Copyright (c) 2011 Dominic Verity and Macquarie University.
 *
 * Week5NoughtsAndCrosses is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * Week5NoughtsAndCrosses is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for
 * more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Week5NoughtsAndCrosses.  (See files COPYING and COPYING.LESSER.)  If not, see
 * <http://www.gnu.org/licenses/>.
 */

package oxo_starter;

/**
 * <p>The other half of a simple implementation of the famous "observer pattern". 
 * This interface should be implemented by observers.</p>
 * 
 * <p>A very basic description of the observer pattern can be found on Wikipedia:</p>
 * 
 * <a href="http://en.wikipedia.org/wiki/Observer_pattern">
 * http://en.wikipedia.org/wiki/Observer_pattern</a>
 * 
 * <p>But a more useful source of information about using the observer pattern 
 * in Java is chapter 2 (pages 37-78) of the COMP229 textbook:</p>
 * 
 * <p><em>Head First Design Patterns</em> 
 * <br>by Elizabeth Freeman, Eric Freeman, Bert Bates and Kathy Sierra</p>
 * 
 * <p>A slightly less comprehensive, yet free, account of this pattern can be 
 * found in:</p>
 * 
 * <p><em>The Design Patterns Java Companion</em> <br>by James W. Cooper</p>
 * 
 * <p>an online book available at:
 * 
 * <a href="http://www.patterndepot.com/put/8/JavaPatterns.htm">
 * http://www.patterndepot.com/put/8/JavaPatterns.htm</a>
 * 
 * whose chapter on observer can be found at
 * 
 * <a href="http://www.patterndepot.com/put/8/observer.pdf">
 * http://www.patterndepot.com/put/8/observer.pdf</a>.</p>
 *
 * <p>Then, of course, the original "Gang-of-Four Pattern Book" is an invaluable resource 
 * for the serious OO designer / programmer:</p>
 * 
 * <p><em>Design Patterns: Elements of Reusable Object-Oriented Software</em>
 * <br>by Erich Gamma, Richard Helm, Ralph Johnson, and John Vlissides,
 * ISBN 0-201-63361-2</p>
 * 
 * @author Dominic Verity
 *
 */

public interface Observer {

	/**
	 * Called by the subject under observation
	 * whenever its state changes.
	 */
	public void receiveNotification(int modelID);

}
