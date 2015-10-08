/*******************************************************************************
 * This file was developed for CS4233: Object-Oriented Analysis & Design. The course was
 * taken at Worcester Polytechnic Institute. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.studentdtroberts.gamma;

import hanto.studentdtroberts.util.HantoBoard;
import hanto.studentdtroberts.util.HantoPiece;
import hanto.studentdtroberts.util.MoveStrategy;
import hanto.util.HantoCoordinate;
import hanto.util.HantoPieceType;
import hanto.util.HantoPlayerColor;

/**
 * Movement strategy for use with GammaHanto.
 * @author Devin
 *
 * @version $Revision: 1.0 $
 */
public class GammaMoveStrategy implements MoveStrategy {

	@Override
	public boolean isValidPieceMove(HantoPieceType piece, HantoCoordinate to,
			HantoCoordinate from) {
		boolean isValid = false;
		switch(piece) {
			case BUTTERFLY:
				isValid = isValidButterflyMove(to, from);
		}
		return isValid;
	}

	@Override
	public boolean isValidMove(HantoCoordinate coords, HantoBoard board) {
		boolean isValid = false;
		for (HantoPiece piece : board.getBoard()) {
			if (Math.abs(piece.getCoords().getX() - coords.getX()) <= 1 
					&& Math.abs(piece.getCoords().getY() - coords.getY()) <= 1
					&& (piece.getCoords().getX() - coords.getX()) != 
						(piece.getCoords().getY() - coords.getY())) {
				isValid = true;
				break;
			}
		}
		if (board.getBoard().isEmpty() && coords.getX() == 0 && coords.getY() == 0) {
			isValid = true;
		}
		return isValid;
	}

	
	/**
	 * Checks whether the move is valid specifically for a Butterfly.
	 * @param to coordinate to move the piece to
	 * @param from coordinate from which the piece is moving
	
	 * @return true if valid, false otherwise */
	public boolean isValidButterflyMove(HantoCoordinate to, HantoCoordinate from) {
		boolean isValid = false;
		if (Math.abs(to.getX() - from.getX()) <= 1
				&& Math.abs(to.getY() - from.getY()) <= 1) {
			isValid = true;
		}
		return isValid;
	}

	// Not used in this game
	@Override
	public boolean isValidMove(HantoCoordinate coords, HantoBoard board,
			HantoPlayerColor player) {
				return false;
	}
}
