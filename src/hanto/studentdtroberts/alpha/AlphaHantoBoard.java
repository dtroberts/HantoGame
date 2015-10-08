/*******************************************************************************
 * This file was developed for CS4233: Object-Oriented Analysis & Design. The course was
 * taken at Worcester Polytechnic Institute. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.studentdtroberts.alpha;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import hanto.common.HantoException;
import hanto.studentdtroberts.util.Coordinate;
import hanto.studentdtroberts.util.HantoBoard;
import hanto.studentdtroberts.util.HantoPiece;
import hanto.util.HantoCoordinate;
import hanto.util.HantoPieceType;
import hanto.util.HantoPlayerColor;
import hanto.util.MoveResult;

/**
 * Board used for use in AlphaHanto. 
 * @author Devin
 * @version 1.0
 */
public class AlphaHantoBoard implements HantoBoard {
	private final List<AlphaHantoPiece> board;
	
	/**
	 * Default constructor.
	 */
	public AlphaHantoBoard() {
		board = new ArrayList<AlphaHantoPiece>();
	}
	
	@Override
	public HantoPieceType getPieceAtCoords(HantoCoordinate coords) throws HantoException {
		for (AlphaHantoPiece piece : board) {
			if (piece.getCoords() == coords) {
				return piece.getPieceType();
			}
		}
		throw new HantoException("No piece at coordinates");
	}

	@Override
	public void setPieceAtCoords(HantoPieceType piece, 
			HantoPlayerColor player, HantoCoordinate coords) {
		board.add(new AlphaHantoPiece(piece, coords));
	}
	
	/**
	 * Check whether a given HantoCoordinate is a valid place to set a piece.
	 * @param coords HantoCoordinate to check
	 * @return true if valid, false otherwise
	 */
	public boolean isValidLocation(HantoCoordinate coords) {
		boolean isValid = false;
		for (AlphaHantoPiece piece : board) {
			if (Math.abs(piece.getCoords().getX() - coords.getX()) <= 1 
					&& Math.abs(piece.getCoords().getY() - coords.getY()) <= 1
					&& coords != piece.getCoords()) {
				isValid = true;
				}
		}
		if (board.isEmpty() && coords.getX() == 0 && coords.getY() == 0) {
			isValid = true;
		}
		return isValid;
	}


	@Override
	public List<HantoPiece> getBoard() {
		return null;
	}
	
	public List<AlphaHantoPiece> getAlphaBoard() {
		return board;
	}

	@Override
	public void movePiece(HantoPieceType pieceType, HantoCoordinate to,
			HantoCoordinate from, HantoPlayerColor whoseTurn) {
	}

	@Override
	public MoveResult butterflySurrounded() {
		return MoveResult.OK;
	}

	@Override
	public boolean isPieceLockedInPlace(HantoPiece piece) {
		return false;
	}
	
	@Override
	public List<Coordinate> getNeighboringSpaces(HantoCoordinate coord) {
		final Coordinate[] neighbors = new Coordinate[6];
		neighbors[0] = new Coordinate(coord.getX(), coord.getY() + 1);
		neighbors[1] = new Coordinate(coord.getX(), coord.getY() - 1);
		neighbors[2] = new Coordinate(coord.getX() + 1, coord.getY() - 1);
		neighbors[3] = new Coordinate(coord.getX() + 1, coord.getY());
		neighbors[4] = new Coordinate(coord.getX() - 1, coord.getY());
		neighbors[5] = new Coordinate(coord.getX() - 1, coord.getY() + 1);
		
		return Arrays.asList(neighbors);
	}
}
