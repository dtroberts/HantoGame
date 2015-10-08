/*******************************************************************************
 * This file was developed for CS4233: Object-Oriented Analysis & Design. The course was
 * taken at Worcester Polytechnic Institute. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.studentdtroberts.alpha;

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
public class AlphaHantoGame implements HantoGame {
	private HantoPlayerColor whoseTurn;
	private AlphaHantoBoard gameBoard;
	
	/**
	 * Default constructor.
	 * @throws HantoException 
	 */
	public AlphaHantoGame() {
		initialize();
	}
	
	/**
	 * Initializes the game. Blue will always go first.
	 */
	public void initialize() {
		whoseTurn = HantoPlayerColor.BLUE;
		gameBoard = new AlphaHantoBoard();
	}

	@Override
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) throws HantoException {
		MoveResult result;
		if (whoseTurn == HantoPlayerColor.BLUE) {
			if (gameBoard.isValidLocation(to)) {
				gameBoard.setPieceAtCoords(pieceType, whoseTurn, to);
				setWhoseTurn(HantoPlayerColor.RED);
				result = MoveResult.OK;
			}
			else {
				throw new HantoException("Invalid location");
			}
		}
		else {
			if (gameBoard.isValidLocation(to)) {
				gameBoard.setPieceAtCoords(pieceType, whoseTurn, to);
				result = MoveResult.DRAW;
			}
			else
			{
				throw new HantoException("Invalid location");
			}
		}
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
	public AlphaHantoBoard getGameBoard() {
		return gameBoard;
	}
}
