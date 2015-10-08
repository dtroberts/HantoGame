/**
 * This file was developed at WPI for CS4233: Object-oriented Analysis and Design.
 * @author Devin
 */

package hanto.studentdtroberts.util;

import hanto.util.HantoCoordinate;
import hanto.util.HantoPieceType;
import hanto.util.HantoPlayerColor;


/**
 * Interface for Hanto pieces.
 * @author Devin
 * @version 1.0
 */
public class HantoPiece {
	private final HantoPieceType pieceType;
	private HantoCoordinate coords;
	private final HantoPlayerColor player;
	private final HantoMovement movement;
	
	/**
	 * Default constructor.
	 * @param pieceType type of game piece
	 * @param player player to which the piece belongs
	 */
	public HantoPiece (HantoPieceType pieceType, HantoPlayerColor player) {
		this.pieceType = pieceType;
		this.player = player;
		if (pieceType == HantoPieceType.BUTTERFLY || pieceType == HantoPieceType.CRAB) {
			movement = HantoMovement.WALK;
		}
		else {
			movement = HantoMovement.FLY;
		}
	}
	
	/**
	 * Constructor with coordinate field.
	 * @param pieceType type of game piece
	 * @param player player to which the piece belongs
	 * @param coords coordinates to place the piece
	 */
	public HantoPiece (HantoPieceType pieceType, 
			HantoPlayerColor player, HantoCoordinate coords) {
		this.pieceType = pieceType;
		this.player = player;
		this.coords = coords;
		if (pieceType == HantoPieceType.BUTTERFLY || pieceType == HantoPieceType.CRAB) {
			movement = HantoMovement.WALK;
		}
		else {
			movement = HantoMovement.FLY;
		}
	}
	
	/**
	 * @return piece type
	 */
	public HantoPieceType getPieceType() {
		return pieceType;
	}
	
	/**
	 * @return the coordinates
	 */
	public HantoCoordinate getCoords() {
		return coords;
	}
	/**
	 * @return which player the piece belongs to
	 */
	public HantoPlayerColor getPlayer() {
		return player;
	}
	
	/**
	 * @param x x coordinate 
	 * @param y y coordinate
	 */
	public void setCoords(int x, int y) {
		coords = new Coordinate(x, y);
	}
	
	public HantoMovement getMovement() {
		return movement;
	}
}
