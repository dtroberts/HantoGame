/*******************************************************************************
 * This file was developed for CS4233: Object-Oriented Analysis & Design. The course was
 * taken at Worcester Polytechnic Institute. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.studentdtroberts.beta;

import java.util.ArrayList;
import java.util.List;

import hanto.common.HantoException;
import hanto.common.HantoGame;
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
public class BetaHantoGame implements HantoGame {
	private HantoPlayerColor whoseTurn;
	private int whatTurn;
	private BetaHantoBoard gameBoard;
	private List<HantoPieceType> bluePieceBank;
	private List<HantoPieceType> redPieceBank;
	
	/**
	 * Default constructor.
	 * @throws HantoException 
	 */
	public BetaHantoGame() {
		initialize();
	}
	
	/**
	 * Initializes the game.
	 */
	public void initialize() {
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
		gameBoard = new BetaHantoBoard();
	}

	@Override
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) throws HantoException {
		MoveResult result;
		if (gameBoard.isValidMove(to)) {
			if (whoseTurn == HantoPlayerColor.BLUE) {
				// Check if Butterfly has been placed by fourth turn
				if (bluePieceBank.contains(HantoPieceType.BUTTERFLY) && 
						whatTurn == 7 && pieceType != HantoPieceType.BUTTERFLY) {
					throw new HantoException("Must place Butterfly");
				}
				gameBoard.setPieceAtCoords(pieceType, whoseTurn, to);
				setWhoseTurn(HantoPlayerColor.RED);
			}
			else {
				if (redPieceBank.contains(HantoPieceType.BUTTERFLY) && 
						whatTurn == 8 && pieceType != HantoPieceType.BUTTERFLY) {
					throw new HantoException("Must place Butterfly");
				}
				gameBoard.setPieceAtCoords(pieceType, whoseTurn, to);
				setWhoseTurn(HantoPlayerColor.BLUE);
			}
		}
		else {
				throw new HantoException("Invalid location");
		}
		result = gameBoard.butterflySurrounded();
		if (from == null) {
			removePieceFromBank(pieceType);
		}
		if (whatTurn == 12 && result == MoveResult.OK) {
			result = MoveResult.DRAW;
		}
		whatTurn++;

		return result;
	}

	/* 
	 * @see hanto.common.HantoGame#getPrintableBoard()
	 */
	@Override
	public String getPrintableBoard() {
		// TODO Auto-generated method stub
		return null;
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
	public BetaHantoBoard getGameBoard() {
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
	 */
	public void removePieceFromBank(HantoPieceType pieceType) {
		if (whoseTurn == HantoPlayerColor.BLUE) {
			bluePieceBank.remove(pieceType);
		}
		else {
			redPieceBank.remove(pieceType);
		}
	}
}
