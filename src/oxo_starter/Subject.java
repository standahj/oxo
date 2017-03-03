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

import java.util.HashSet;
import java.util.Set;

/**
 * <p>One half of a simple implementation of the famous "observer pattern". 
 * This class implements abstract subjects, managing the list of observers 
 * currently receiving notifications from such a subject object.</p>
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

public abstract class Subject {
	
	/**
	 * Subjects maintain a set that references all
	 * of the observers which are currently keeping
	 * tabs on it.
	 */
	private Set<Observer> mObservers;
	
	private int mSubjectId = -1;
	
	/**
	 * Default constructor - simply create a set in which to
	 * store references to the observers currently monitoring this
	 * subject.
	 */
	public Subject() {
		mObservers = new HashSet<Observer>();
	}
	
	public Subject(int id) {
		this();
		setSubjectId(id);
	}
	
	/**
	 * Add an observer to the set of observers to be notified
	 * whenever the state of this subject is updated.
	 * 
	 * @param pObserver the observer to add.
	 */
	public void attach(Observer pObserver) {
		mObservers.add(pObserver);
	}
	
	public void attach(Subject clone) {
		mObservers.addAll(clone.mObservers);
	}

	/**
	 * Remove an observer from the set of observers to be notified
	 * whenever the state of this subject is updated.
	 * 
	 * @param pObserver the observer to remove.
	 */
	public void detach(Observer pObserver) {
		mObservers.remove(pObserver);
	}
	
	/**
	 * Iterate through the set of observers which are currently
	 * monitoring this subject and inform each one that an
	 * update has occurred by calling its {@link Observer#receiveNotification()}
	 * method.
	 */
	protected void sendNotifications() {
		for (final Observer vObserver : mObservers)
			(new Thread(){public void run(){vObserver.receiveNotification(getSubjectId());}}).start();
	}

	public int getSubjectId() {
		return mSubjectId;
	}

	public void setSubjectId(int subjectId) {
		this.mSubjectId = subjectId;
	}
}
