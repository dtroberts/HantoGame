/*******************************************************************************
 * This file was developed for CS4233: Object-Oriented Analysis & Design. The course was
 * taken at Worcester Polytechnic Institute. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.studentdtroberts.gamma;

import java.util.ArrayList;
import java.util.List;

import hanto.common.HantoException;
import hanto.common.HantoGame;
import hanto.studentdtroberts.util.HantoBoard;
import hanto.studentdtroberts.util.HantoPiece;
import hanto.studentdtroberts.util.MoveStrategy;
import hanto.util.HantoCoordinate;
import hanto.util.HantoPieceType;
import hanto.util.HantoPlayerColor;
import hanto.util.MoveResult;

/**
 * An AlphaHantoGame is the first implementation of HantoGame.
 * 
 * @author Devin
 * @version 1.0
 */
public class GammaHantoGame implements HantoGame {
	private HantoPlayerColor whoseTurn;
	private int whatTurn;
	private HantoBoard gameBoard;
	List<HantoPieceType> bluePieceBank;
	List<HantoPieceType> redPieceBank;
	private boolean isGameStillOn;
	private final MoveStrategy validMove;
	private static final int MUST_PLACE_BUTTERFLY = 4;
	
	
	/**
	 * Default constructor.
	 * @throws HantoException 
	 */
	public GammaHantoGame() {
		validMove = new GammaMoveStrategy();
		initialize();
	}
	
	/**
	 * Initializes the game.
	 */
	public void initialize() {
		isGameStillOn = true;
		bluePieceBank = new ArrayList<HantoPieceType>();
		redPieceBank = new ArrayList<HantoPieceType>();
		bluePieceBank.add(HantoPieceType.BUTTERFLY);
		bluePieceBank.add(HantoPieceType.SPARROW);
		bluePieceBank.add(HantoPieceType.SPARROW);
		bluePieceBank.add(HantoPieceType.SPARROW);
		bluePieceBank.add(HantoPieceType.SPARROW);
		bluePieceBank.add(HantoPieceType.SPARROW);
		
		redPieceBank.add(HantoPieceType.BUTTERFLY);
		redPieceBank.add(HantoPieceType.SPARROW);
		redPieceBank.add(HantoPieceType.SPARROW);
		redPieceBank.add(HantoPieceType.SPARROW);
		redPieceBank.add(HantoPieceType.SPARROW);
		redPieceBank.add(HantoPieceType.SPARROW);
		
		if (whoseTurn == null) {
			whoseTurn = HantoPlayerColor.BLUE;
		}
		whatTurn = 1;
		gameBoard = new GammaHantoBoard();
	}

	@Override
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) throws HantoException {
		MoveResult result;
		HantoPlayerColor nextTurn;
		// valid move
		if (!isGameStillOn) {
			throw new HantoException("The game is over!");
		}
		if (validMove.isValidMove(to, gameBoard)) {
			List<HantoPieceType> bank;
			if (whoseTurn == HantoPlayerColor.BLUE) {
				bank = bluePieceBank;
				nextTurn = HantoPlayerColor.RED;
			}
			else {
				bank = redPieceBank;
				nextTurn = HantoPlayerColor.BLUE;
			}
			if (bank.contains(HantoPieceType.BUTTERFLY) && 
					whatTurn == MUST_PLACE_BUTTERFLY && pieceType != HantoPieceType.BUTTERFLY) {
				throw new HantoException("Must place Butterfly");
			}
			// moving instead of newly placing
			if (from != null) {
				gameBoard.movePiece(pieceType, to, from, whoseTurn);
			}
			else {
				placeNewPiece(pieceType, to, from);
			}
		}
		else {
				throw new HantoException("Invalid location");
		}
		result = gameBoard.butterflySurrounded();
		if (whoseTurn == HantoPlayerColor.RED) {
			whatTurn++;
		}
		if (whatTurn > 10) {
			result = MoveResult.DRAW;
		}
		
		if (result == MoveResult.BLUE_WINS || result == MoveResult.RED_WINS 
				|| result == MoveResult.DRAW) {
			isGameStillOn = false;
		}
		
		setWhoseTurn(nextTurn);
		return result;
	}
	

	/* 
	 * @see hanto.common.HantoGame#getPrintableBoard()
	 */
	@Override
	public String getPrintableBoard() {
		final List<HantoPiece> board = gameBoard.getBoard();
		String print = new String();
		// insert pieces
		for (HantoPiece p : board) {
			if (p.getPieceType() == HantoPieceType.BUTTERFLY) {
				print = print.concat("Butterfly at (" + p.getCoords().getX() 
						+ "," + p.getCoords().getY() + ")\n");
			}
			else if (p.getPieceType() == HantoPieceType.SPARROW) {
				print = print.concat("Sparrow at (" + p.getCoords().getX() 
						+ "," + p.getCoords().getY() + ")\n");
			}
		}
		// if board is empty, say so
		if (print == null) {
			print = "Board is empty";
		}
		return print;
	}

	/* 
	 * @see hanto.common.HantoGame#initialize(hanto.util.HantoPlayerColor)
	 */
	@Override
	public void initialize(HantoPlayerColor firstPlayer) {
		whoseTurn = firstPlayer;
		initialize();
	}

	/**
	 * @return player whose turn it is
	 */
	public HantoPlayerColor getWhoseTurn() {
		// TODO Auto-generated method stub
		return whoseTurn;
	}

	/**
	 * @param whoseTurn the whoseTurn to set
	 */
	public void setWhoseTurn(HantoPlayerColor whoseTurn) {
		this.whoseTurn = whoseTurn;
	}

	/**
	 * @return current game board
	 */
	public HantoBoard getGameBoard() {
		return gameBoard;
	}

	/**
	 * @param player player for which to check bank
	 * @return pieces left in the player's bank
	 */
	public List<HantoPieceType> getPieceBank(HantoPlayerColor player) {
		List<HantoPieceType> bank;
		if (player == HantoPlayerColor.BLUE) {
			bank = bluePieceBank;
		}
		else {
			bank = redPieceBank;
		}
		return bank;
	}
	
	/**
	 * @return what turn the game is on
	 */
	public int getWhatTurn() {
		return whatTurn;
	}
	
	/**
	 * Takes a piece out of the player's bank when a piece is placed on the board.
	 * @param pieceType type of piece being used
	 * @return true if a piece is removed, false otherwise
	 */
	public boolean canRemovePieceFromBank(HantoPieceType pieceType) {
		boolean can;
		if (whoseTurn == HantoPlayerColor.BLUE) {
			can = bluePieceBank.remove(pieceType);
		}
		else {
			can = redPieceBank.remove(pieceType);
		}
		return can;
	}
	
	/**
	 * Check whether a butterfly can move to the given location.
	 * @param to location to move to
	 * @param from location to move from
	 * @return whether a butterfly can make the move or not
	 */
	private void placeNewPiece(HantoPieceType pieceType, HantoCoordinate to, 
			HantoCoordinate from) throws HantoException {
		if (canRemovePieceFromBank(pieceType)) {
			gameBoard.setPieceAtCoords(pieceType, whoseTurn, to);
		}
		else {
			throw new HantoException("Player does not have piece in the bank");
		}
	}
}
