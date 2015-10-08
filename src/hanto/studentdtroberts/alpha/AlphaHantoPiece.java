/*******************************************************************************
 * This file was developed for CS4233: Object-Oriented Analysis & Design. The course was
 * taken at Worcester Polytechnic Institute. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.studentdtroberts.alpha;

import hanto.util.HantoCoordinate;
import hanto.util.HantoPieceType;

/**
 * Game Piece for use in Alpha Hanto.
 * This class differs from HantoPiece because Alpha Hanto has different requirements..
 * @author Devin
 * @version 1.0
 */
public class AlphaHantoPiece {
	private final HantoPieceType pieceType;
	private final HantoCoordinate coords;
	
	/**
	 * Default constructor.
	 * @param pieceType type of game piece
	 * @param coords location on board
	 */
	public AlphaHantoPiece (HantoPieceType pieceType, HantoCoordinate coords) {
		this.pieceType = pieceType;
		this.coords = coords;
	}
	
	public HantoPieceType getPieceType() {
		return pieceType;
	}
	
	public HantoCoordinate getCoords() {
		return coords;
	}
}
