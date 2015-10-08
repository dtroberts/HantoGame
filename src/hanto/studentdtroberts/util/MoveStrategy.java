/**
 * This file was developed at WPI for CS4233: Object-oriented Analysis and Design.
 * @author Devin
 */

package hanto.studentdtroberts.util;

import hanto.util.HantoCoordinate;
import hanto.util.HantoPieceType;
import hanto.util.HantoPlayerColor;


/**
 * Interface for all valid move classes.
 * Valid move classes are used to check whether attempted moves are possible.
 * @author Devin
 * @version 1.0
 */
public interface MoveStrategy {
	
	/**
	 * Check whether a specified piece can make the given movement.
	 * Check only if the piece can legally make the move as determined by the rule set,
	 * not whether a specific piece can make the move.
	 * @param piece type of piece to check
	 * @param to coordinate the piece will move to
	 * @param from coordinate the piece will move from
	 * @return true if this type of piece can make the movement
	 */
	boolean isValidPieceMove(HantoPieceType piece, HantoCoordinate to, 
			HantoCoordinate from);
	
	/**
	 * Check whether a location on the board is valid for movement.
	 * This is true if the hex is not taken and if there exists at least one piece 
	 * adjacent to it.
	 * @param coords location to check
	 * @param board board to check
	 * @return true if the location is valid
	 */
	boolean isValidMove(HantoCoordinate coords, HantoBoard board);

	/**
	 * Allows for a player color constraint.
	 * @param coords location to check
	 * @param board board to check
	 * @param player color of the piece
	 * @return true if the move is valid
	 */
	boolean isValidMove(HantoCoordinate coords, HantoBoard board,
			HantoPlayerColor player);
}
