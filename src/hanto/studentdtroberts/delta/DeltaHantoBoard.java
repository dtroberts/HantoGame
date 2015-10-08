/*******************************************************************************
 * This file was developed for CS4233: Object-Oriented Analysis & Design. The course was
 * taken at Worcester Polytechnic Institute. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.studentdtroberts.delta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import hanto.common.HantoException;
import hanto.studentdtroberts.util.Coordinate;
import hanto.studentdtroberts.util.HantoBoard;
import hanto.studentdtroberts.util.HantoPiece;
import hanto.studentdtroberts.util.MoveStrategy;
import hanto.util.HantoCoordinate;
import hanto.util.HantoPieceType;
import hanto.util.HantoPlayerColor;
import hanto.util.MoveResult;

/**
 * Board for use in Delta Hanto.
 * @author Devin
 * @version 1.0
 */
public class DeltaHantoBoard implements HantoBoard {
	private final List<HantoPiece> board;
	private final MoveStrategy validMove;
	/**
	 * Default constructor.
	 */
	public DeltaHantoBoard() {
		board = new ArrayList<HantoPiece>();
		validMove = new DeltaMoveStrategy();
	}

	@Override
	public HantoPieceType getPieceAtCoords(HantoCoordinate coords) throws HantoException {
		for (HantoPiece piece : board) {
			if (piece.getCoords() == coords) {
				return piece.getPieceType();
			}
		}
		throw new HantoException("No piece at coordinates");
	}

	@Override
	public void setPieceAtCoords(HantoPieceType piece, HantoPlayerColor player, 
			HantoCoordinate coords) {
		for (HantoPiece p : board) {
			if (p.getCoords().getX() == coords.getX() 
					&& p.getCoords().getY() == coords.getY()) {
				board.remove(p);
				break;
			}
		}
		board.add(new HantoPiece(piece, player, coords));
	}

	@Override
	public List<HantoPiece> getBoard() {
		return board;
	}

	@Override
	public void movePiece(HantoPieceType pieceType, HantoCoordinate to, 
			HantoCoordinate from, HantoPlayerColor player) throws HantoException {
		boolean pieceThere = false;
		for (HantoPiece p : board) {
			if (p.getPlayer() == player && p.getPieceType() == pieceType && 
					(p.getCoords().getX() == from.getX() && p.getCoords().getY() == from.getY())) {
				pieceThere = true;
			}
		}
		if (!pieceThere) {
			throw new HantoException("Piece not listed at given location");
		}
		
		// walking pieces must be checked to see whether they are locked in place or not
		if (pieceType == HantoPieceType.BUTTERFLY || pieceType == HantoPieceType.CRAB) {
			if (isPieceLockedInPlace(new HantoPiece(HantoPieceType.BUTTERFLY, player, from))) {
				throw new HantoException("Cannot move");
			}
		}
		
		if (!validMove.isValidPieceMove(pieceType, to, from)) {
			throw new HantoException("Invalid move");
		}
		if (pieceType == HantoPieceType.BUTTERFLY || pieceType == HantoPieceType.CRAB
				|| pieceType == HantoPieceType.SPARROW) {
			this.setPieceAtCoords(pieceType, player, to);
			removePieceFromBoard(from);
		}
		else {
			throw new HantoException("Not valid move");
		}
	}

	/**
	 * Removes a piece from the board. For use with
	 * movePiece().
	 * @param coord location to remove the piece from 
	 */
	private void removePieceFromBoard(HantoCoordinate from) {
		for (HantoPiece p : board) {
			if (from == p.getCoords()) {
				board.remove(p);
				break;
			}
		}
	}

	@Override
	public MoveResult butterflySurrounded() {
		HantoPlayerColor loser = null;
		MoveResult result = MoveResult.OK;
		int counter = 0;
		for (HantoPiece piece : board) { 
			if (piece.getPieceType() == HantoPieceType.BUTTERFLY) {
				for (HantoPiece p : board) {
					int x_dist = piece.getCoords().getX() - p.getCoords().getX();
					int y_dist = piece.getCoords().getY() - p.getCoords().getY();
					if (Math.abs(x_dist) <= 1
							&& Math.abs(y_dist) <= 1
							&& x_dist != y_dist
							&& (piece.getCoords().getX() != p.getCoords().getX() 
							|| piece.getCoords().getY() != p.getCoords().getY())) {
						counter++;
					}
					if (counter == 6) {
						if (loser == null) {
							loser = piece.getPlayer();
						}
						else {
							result = MoveResult.DRAW;
							loser = null;
						}
						break;
					}
				}
				counter = 0;
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
	
	/**
	 * Determines if a piece is locked in place, thus not allowing it to move.
	 * @param piece piece to check
	 * @throws HantoException
	 */
	public boolean isPieceLockedInPlace(HantoPiece piece) {
		int countFirstSurround = 0;
		int countSecondSurround = 0;
		boolean locked = false;
		for (HantoPiece p : board) {
			if ((piece.getCoords().getX() - p.getCoords().getX() == 0
					&& piece.getCoords().getY() - p.getCoords().getY() == 1)
					|| (piece.getCoords().getX() - p.getCoords().getX() == -1
					&& piece.getCoords().getY() - p.getCoords().getY() == 0)
					|| (piece.getCoords().getX() - p.getCoords().getX() == -1
					&& (piece.getCoords().getY() - p.getCoords().getY() == 0))) {
				countFirstSurround++;
			}
			if ((piece.getCoords().getX() - p.getCoords().getX() == -1
					&& piece.getCoords().getY() - p.getCoords().getY() == 1)
					|| (piece.getCoords().getX() - p.getCoords().getX() == 0
					&& piece.getCoords().getY() - p.getCoords().getY() == -1)
					|| (piece.getCoords().getX() - p.getCoords().getX() == 1
					&& (piece.getCoords().getY() - p.getCoords().getY() == 0))) {
				countSecondSurround++;
			}
		}
		if (countFirstSurround >= 3 || countSecondSurround >= 3) {
			locked = true;
		}
		return locked;
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
