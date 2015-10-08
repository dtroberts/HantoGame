/*******************************************************************************
 * This file was developed for CS4233: Object-Oriented Analysis & Design. The course was
 * taken at Worcester Polytechnic Institute. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.studentdtroberts.util;

import java.util.List;

import hanto.common.HantoException;
import hanto.util.*;

/**
 * Hanto board interface.
 * @author Devin
 * @version 1.0
 */
public interface HantoBoard {
	/**
	 * Show the piece at the given coordinates.
	 * @param coords coordinates to check
	 * @return HantoPieceType at the specified coordinates
	 * @throws HantoException 
	 */
	HantoPieceType getPieceAtCoords(HantoCoordinate coords) throws HantoException;
	
	
	/**
	 * Set a game piece at a location.
	 * @param piece piece to place
	 * @param player player which the piece belongs to
	 * @param coords location to place the piece
	 */
	void setPieceAtCoords(HantoPieceType piece, HantoPlayerColor player, 
			HantoCoordinate coords);
	
	/**
	 * Get the board.
	 * @return list of pieces on the board
	 */
	List<HantoPiece> getBoard();
	
	/**
	 * Move a piece on the board. For use in makeMove().
	 * @param pieceType
	 * @param to coordinates to place the piece
	 * @param from coordinates to remove the piece from
	 * @param player player who the piece belongs to
	 * @throws HantoException
	 */
	void movePiece(HantoPieceType pieceType, HantoCoordinate to, 
			HantoCoordinate from, HantoPlayerColor player) throws HantoException;


	/**
	 * Check whether a butterfly is surrounded (six pieces surrounding).
	 * @return winner if applicable, OK otherwise
	 */
	MoveResult butterflySurrounded();
	
	/**
	 * Check if a piece is locked in place.
	 * @param piece piece to check
	 * @return true if the piece cannot move
	 * @throws HantoException
	 */
	boolean isPieceLockedInPlace(HantoPiece piece) throws HantoException;
	
	/**
	 * Find all neighboring hexes, regardless of whether they are occupied or not.
	 * @param coord location to calculate neighbors for
	 * @return array of all neighboring coordinates
	 */
	List<Coordinate> getNeighboringSpaces(HantoCoordinate coord);
}
