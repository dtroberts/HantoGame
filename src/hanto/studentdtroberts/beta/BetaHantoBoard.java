/*******************************************************************************
 * This file was developed for CS4233: Object-Oriented Analysis & Design. The course was
 * taken at Worcester Polytechnic Institute. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.studentdtroberts.beta;

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
public class BetaHantoBoard implements HantoBoard {
	private final List<HantoPiece> board;
	
	/**
	 * Default constructor.
	 */
	public BetaHantoBoard() {
		board = new ArrayList<HantoPiece>();
	}
	
	/**
	 * @param coords location to check
	 * @return piece type at location
	 * @throws HantoException
	 */
	public HantoPieceType getPieceAtCoords(HantoCoordinate coords) throws HantoException {
		for (HantoPiece piece : board) {
			if (piece.getCoords() == coords) {
				return piece.getPieceType();
			}
		}
		throw new HantoException("No piece at coordinates");
	}

	/**
	 * @param piece piece to place 
	 * @param player player to which the piece belongs
	 * @param coords coordinates to place the piece
	 */
	public void setPieceAtCoords(HantoPieceType piece, HantoPlayerColor player, 
			HantoCoordinate coords) {
		board.add(new HantoPiece(piece, player, coords));
	}
	
	/**
	 * Check whether a given HantoCoordinate is a valid place to set a piece.
	 * @param coords HantoCoordinate to check
	 * @return true if valid, false otherwise
	 */
	public boolean isValidMove(HantoCoordinate coords) {
		boolean isValid = false;
		for (HantoPiece piece : board) {
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
	
	/**
	 * Check whether any butterflies have been surrounded.
	 * @return player whose butterfly has been surrounded
	 */
	public MoveResult butterflySurrounded() {
		HantoPlayerColor loser = null;
		MoveResult result = MoveResult.OK;
		int counter = 0;
		for (HantoPiece piece : board) {
			if (piece.getPieceType() == HantoPieceType.BUTTERFLY) {
				for (HantoPiece p : board) {
					if (Math.abs(piece.getCoords().getX() - p.getCoords().getX()) <= 1
							&& Math.abs(piece.getCoords().getY() - p.getCoords().getY()) <= 1) {
						counter++;
					}
					if (counter == 6) {
						loser = piece.getPlayer();
					}
				}
			}
		}
		if (loser == HantoPlayerColor.BLUE) {
			result = MoveResult.RED_WINS;
		}
		else if (loser == HantoPlayerColor.RED) {
			result = MoveResult.BLUE_WINS;
		}
		return result;
	}

	@Override
	public List<HantoPiece> getBoard() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void movePiece(HantoPieceType pieceType, HantoCoordinate to,
			HantoCoordinate from, HantoPlayerColor player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isPieceLockedInPlace(HantoPiece piece) {
		// TODO Auto-generated method stub
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
