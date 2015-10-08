/*******************************************************************************
 * This file was developed for CS4233: Object-Oriented Analysis & Design. The course was
 * taken at Worcester Polytechnic Institute. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.studentdtroberts.util;

import hanto.util.HantoCoordinate;

/**
 * Coordinate used in Hanto.
 * @author Devin
 * @version 1.0
 */
public class Coordinate implements HantoCoordinate {
	int x;
	int y;
	
	/**
	 * Default constructor.
	 * @param x x position on the game board
	 * @param y y position on the game board
	 */
	public Coordinate(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		return y;
	}

}
