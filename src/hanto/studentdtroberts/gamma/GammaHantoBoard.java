/*******************************************************************************
 * This file was developed for CS4233: Object-Oriented Analysis & Design. The course was
 * taken at Worcester Polytechnic Institute. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.studentdtroberts.gamma;

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
 * Board used for use in AlphaHanto. 
 * @author Devin
 * @version 1.0
 */
public class GammaHantoBoard implements HantoBoard {
	private final List<HantoPiece> board;
	private final MoveStrategy validMove;
	
	/**
	 * Default constructor.
	 */
	public GammaHantoBoard() {
		board = new ArrayList<HantoPiece>();
		validMove = new GammaMoveStrategy();
	}
	
	/**
	 * @param coords coordinates to check
	 * @return piece at the specified location
	 * @throws HantoException
	 */
	public HantoPieceType getPieceAtCoords(HantoCoordinate coords) throws HantoException {
		for (HantoPiece piece : board) {
			if (piece.getCoords().getX() == coords.getX()
					&& piece.getCoords().getY() == coords.getY()) {
				return piece.getPieceType();
			}
		}
		throw new HantoException("No piece at coordinates");
	}

	/**
	 * @param piece piece to place (null if removing piece from the board)
	 * @param player player to which the piece belongs
	 * @param coords location to set the piece
	 */
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
	public MoveResult butterflySurrounded() {
		HantoPlayerColor loser = null;
		MoveResult result = MoveResult.OK;
		int counter = 0;
		for (HantoPiece piece : board) { // NEED TO FIX 
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
						loser = piece.getPlayer();
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

	@Override
	public boolean isPieceLockedInPlace(HantoPiece piece) {
		int counter = 0;
		boolean locked = false;
		for (HantoPiece p : board) {
			if (Math.abs(piece.getCoords().getX() - p.getCoords().getX()) <= 1
					&& Math.abs(piece.getCoords().getY() - p.getCoords().getY()) <= 1
					// fix
					&& (piece.getCoords().getX() != p.getCoords().getX() 
					|| piece.getCoords().getY() != p.getCoords().getY())) {
				counter++;
			}
		}
		if (counter == 5) {
			locked = true;
		}
		
		return locked;
	}
	

	@Override
	public void movePiece(HantoPieceType pieceType, HantoCoordinate to, 
			HantoCoordinate from, HantoPlayerColor player) throws HantoException {
		isPieceLockedInPlace(
				new HantoPiece(pieceType, player, from));
		boolean pieceThere = false;
		for (HantoPiece p : board) {
			if (p.getPlayer() == player && p.getPieceType() == pieceType && p.getCoords() == from) {
				pieceThere = true;
			}
		}
		if (!pieceThere) {
				throw new HantoException("No piece at given location");
		}
		
		if (pieceType == HantoPieceType.BUTTERFLY && 
				validMove.isValidPieceMove(pieceType, to, from)) {
			// set the piece at its new location, then remove the old
			this.setPieceAtCoords(pieceType, player, to);
			this.setPieceAtCoords(null, null, from);
		}
		else if (pieceType == HantoPieceType.BUTTERFLY) {
			throw new HantoException("Invalid Butterfly move");
		}
		else {
			throw new HantoException("Only Butterfly can move");
		}
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
