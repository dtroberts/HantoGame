/*******************************************************************************
 * This file was developed for CS4233: Object-Oriented Analysis & Design. The course was
 * taken at Worcester Polytechnic Institute. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.studentdtroberts.delta;

import java.util.List;

import hanto.studentdtroberts.util.Coordinate;
import hanto.studentdtroberts.util.HantoBoard;
import hanto.studentdtroberts.util.HantoPiece;
import hanto.studentdtroberts.util.MoveStrategy;
import hanto.util.HantoCoordinate;
import hanto.util.HantoPieceType;
import hanto.util.HantoPlayerColor;

/**
 * Valid move rule set for use in Delta Hanto.
 * @author Devin
 * @version 1.0
 */
public class DeltaMoveStrategy implements MoveStrategy {
	@Override
	public boolean isValidPieceMove(HantoPieceType piece, HantoCoordinate to,
			HantoCoordinate from) {
		boolean isValid = false;
		if (piece == HantoPieceType.BUTTERFLY || piece == HantoPieceType.CRAB) {
				isValid = isValidWalk(to, from);
		}
		else if (piece == HantoPieceType.SPARROW) {
				isValid = isValidFly(to, from);
		}
		return isValid;
	}
	
	@Override
	public boolean isValidMove(HantoCoordinate coords, HantoBoard board, HantoPlayerColor player) {
		boolean isValid = false;
		for (HantoPiece piece : board.getBoard()) {
			if (Math.abs(piece.getCoords().getX() - coords.getX()) <= 1 
					&& Math.abs(piece.getCoords().getY() - coords.getY()) <= 1
					&& (piece.getCoords().getX() - coords.getX()) != 
						(piece.getCoords().getY() - coords.getY())
					&& piece.getPlayer() == player) {
				isValid = true;
				break;
			}
		}
		if (board.getBoard().isEmpty() && coords.getX() == 0 && coords.getY() == 0) {
			isValid = true;
		}
		if (isValid) {
			for (HantoPiece piece : board.getBoard()) {
				if (piece.getCoords().getX() == coords.getX()
						&& piece.getCoords().getY() == coords.getY()) {
					isValid = false;
					break;
				}
			}
			
			final List<Coordinate> neighbors = board.getNeighboringSpaces(coords);
			for (HantoPiece piece : board.getBoard()) {
				for (Coordinate c : neighbors) {
					if (c.getX() == piece.getCoords().getX() 
							&& c.getY() == piece.getCoords().getY()
							&& piece.getPlayer() != player) {
						isValid = false;
						break;
					}
				}
			}
		}
		return isValid;
	}

	/**
	 * Checks whether the move is valid specifically for a piece that can Walk. 
	 * For use in isValidPieceMove().
	 * @param to coordinate to move the piece to
	 * @param from coordinate from which the piece is moving
	 * @return true if valid, false otherwise
	 */
	private boolean isValidWalk(HantoCoordinate to, HantoCoordinate from) {
		boolean isValid = false;
		if (Math.abs(to.getX() - from.getX()) <= 1
				&& Math.abs(to.getY() - from.getY()) <= 1) {
			isValid = true;
		}
		return isValid;
	}
	
	/**
	 * Checks whether the move is valid for a piece that can Fly. For use in isValidPieceMove().
	 * @param to coordinate to move the piece to
	 * @param from coordinate from which the piece is moving
	 * @return true if valid, false otherwise
	 */
	private boolean isValidFly(HantoCoordinate to, HantoCoordinate from) {
		final boolean isValid = true;
		return isValid;
	}

	@Override
	public boolean isValidMove(HantoCoordinate coords, HantoBoard board) {
		boolean isValid = false;
		for (HantoPiece piece : board.getBoard()) {
			if (Math.abs(piece.getCoords().getX() - coords.getX()) <= 1 
					&& Math.abs(piece.getCoords().getY() - coords.getY()) <= 1
					&& (piece.getCoords().getX() - coords.getX()) != 
						(piece.getCoords().getY() - coords.getY())
					&& (piece.getCoords().getX() - coords.getX()) != 
						(piece.getCoords().getY() - coords.getY())) {
				isValid = true;
				break;
			}
		}
		
		if (board.getBoard().isEmpty() && coords.getX() == 0 && coords.getY() == 0) {
			isValid = true;
		}
		if (isValid) {
			for (HantoPiece piece : board.getBoard()) {
				if (piece.getCoords().getX() == coords.getX()
						&& piece.getCoords().getY() == coords.getY()) {
					isValid = false;
					break;
				}
			}
		}
		return isValid;
	}
}
