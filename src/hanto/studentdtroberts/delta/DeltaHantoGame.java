/*******************************************************************************
 * This file was developed for CS4233: Object-Oriented Analysis & Design. The course was
 * taken at Worcester Polytechnic Institute. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.studentdtroberts.delta;

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
 * Game specification for Delta Hanto.
 * @author Devin
 * @version $Revision: 1.0 $
 */
public class DeltaHantoGame implements HantoGame {
	protected HantoPlayerColor whoseTurn;
	protected int whatTurn;
	protected HantoBoard gameBoard;
	protected List<HantoPieceType> bluePieceBank;
	protected List<HantoPieceType> redPieceBank;
	protected boolean isGameStillOn;
	protected final MoveStrategy validMove;
	protected MoveResult finalResult = MoveResult.OK;
	protected static final int MUST_PLACE_BUTTERFLY = 4;
	protected static final int NUM_SPARROWS = 4;
	protected static final int NUM_CRABS = 4;
	
	/**
	 * Constructor for DeltaHantoGame.
	 */
	public DeltaHantoGame() {
		validMove = new DeltaMoveStrategy();
		initialize();
	}
	
	/**
	 * Constructor for DeltaHantoGame. Takes the player who should go first.
	 * @param color player to have first turn
	 */
	public DeltaHantoGame(HantoPlayerColor color) {
		validMove = new DeltaMoveStrategy();
		initialize(color);
	}

	/**
	 * Initializes a game. Can be called at any time to reset a game.
	
	 */
	public void initialize() {
		setWhoseTurn(HantoPlayerColor.BLUE);
		setWhatTurn(1);
		gameBoard = new DeltaHantoBoard();
		setBluePieceBank(new ArrayList<HantoPieceType>());
		setRedPieceBank(new ArrayList<HantoPieceType>());
		isGameStillOn = true;
		
		bluePieceBank.add(HantoPieceType.BUTTERFLY);
		for (int i = 0; i < NUM_SPARROWS; i++) {
			bluePieceBank.add(HantoPieceType.SPARROW);
		}
		for (int i = 0; i < NUM_CRABS; i++) {
			bluePieceBank.add(HantoPieceType.CRAB);
		}
				
		redPieceBank.add(HantoPieceType.BUTTERFLY);
		for (int i = 0; i < NUM_SPARROWS; i++) {
			redPieceBank.add(HantoPieceType.SPARROW);
		}
		for (int i = 0; i < NUM_CRABS; i++) {
			redPieceBank.add(HantoPieceType.CRAB);
		}
		
		if (whoseTurn == null) {
			whoseTurn = HantoPlayerColor.BLUE;
		}
	}
	
	@Override
	public void initialize(HantoPlayerColor firstPlayer) {
		whoseTurn = firstPlayer;
		initialize();
	}

	@Override
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) throws HantoException {
		MoveResult result;
		HantoPlayerColor nextTurn;
		if (!isGameStillOn) {
			throw new HantoException("The game is over! " + finalResult.toString() + "\n");
		}
		
		// check for resignation
		if (pieceType == null && from == null && to == null) {
			result = resign();
			return result; // multiple returns
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
			// must place butterfly
			if (bank.contains(HantoPieceType.BUTTERFLY) && 
					whatTurn == MUST_PLACE_BUTTERFLY && pieceType != HantoPieceType.BUTTERFLY) {
				throw new HantoException("Must place Butterfly");
			}
			// moving instead of newly placing
			if (from != null) {
				// butterfly must be placed before pieces can move
				if (bank.contains(HantoPieceType.BUTTERFLY)) {
					throw new HantoException("Must place butterfly before moving other pieces");
				}
				gameBoard.movePiece(pieceType, to, from, whoseTurn);
			}
			else {
				if (validMove.isValidMove(to, gameBoard, whoseTurn) || whatTurn == 1) {
					placeNewPiece(pieceType, to, from);
				}
				else {
					throw new HantoException("Location invalid for given player");
				}
			}
		}
		else {
				throw new HantoException("Invalid location");
		}
		result = gameBoard.butterflySurrounded();
		if (whoseTurn == HantoPlayerColor.RED) {
			whatTurn++;
		}
		if (result == MoveResult.BLUE_WINS || result == MoveResult.RED_WINS
				|| result == MoveResult.DRAW) {
			isGameStillOn = false;
			finalResult = result;
		}
		whoseTurn = nextTurn;
		return result;
	}
	
	/**
	 * @return player whose turn it is
	 */
	public HantoPlayerColor getWhoseTurn() {
		// TODO Auto-generated method stub
		return whoseTurn;
	}

	@Override
	public String getPrintableBoard() {
		final List<HantoPiece> board = gameBoard.getBoard();
		String print = new String();
		// insert pieces
		for (HantoPiece p : board) {
			if (p.getPieceType() == HantoPieceType.BUTTERFLY) {
				print = print.concat(p.getPlayer().toString() + 
						" Butterfly at (" + p.getCoords().getX() + "," + 
						p.getCoords().getY() + ")\n");
			}
			else if (p.getPieceType() == HantoPieceType.SPARROW) {
				print = print.concat(p.getPlayer().toString() + 
						" Sparrow at (" + p.getCoords().getX() + "," + 
						p.getCoords().getY() + ")\n");
			}
			else if (p.getPieceType() == HantoPieceType.CRAB) {
				print = print.concat(p.getPlayer().toString() + 
						" Crab at (" + p.getCoords().getX() + "," + 
						p.getCoords().getY() + ")\n");
			}
		}
		// if board is empty, say so
		if (print == null) {
			print = "Board is empty";
		}
		return print;
	}
	
	/**
	 * @return what turn the game is on
	 */
	public int getWhatTurn() {
		return whatTurn;
	}
	
	/**
	 * Check whether a piece can move to the given location.
	 * @param to location to move to
	 * @param from location to move from
	 * @param pieceType HantoPieceType
	 * @throws HantoException
	 */
	protected void placeNewPiece(HantoPieceType pieceType, HantoCoordinate to, 
			HantoCoordinate from) throws HantoException {
		if (canRemovePieceFromBank(pieceType)) {
			gameBoard.setPieceAtCoords(pieceType, whoseTurn, to);
		}
		else {
			throw new HantoException("Player does not have piece in the bank");
		}
	}
	
	
	/**
	 * Returns the game board. Used for testing.
	 * @return board
	 */
	public HantoBoard getGameBoard() {
		return gameBoard;
	}
	
	/**
	 * Takes a piece out of the player's bank when a piece is placed on the board.
	 * @param pieceType type of piece being used
	
	 * @return true if a piece is removed, false otherwise */
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
	 * Resign. For use in makeMove().
	
	 * @return red wins if blue resigns and vice versa */
	public MoveResult resign() {
		MoveResult result;
		if (whoseTurn == HantoPlayerColor.BLUE) {
			result = MoveResult.RED_WINS;
		}
		else {
			result = MoveResult.RED_WINS;
		}
		isGameStillOn = false;
		finalResult = result;
		return result;
	}

	/**
	 * @return the bluePieceBank
	 */
	public List<HantoPieceType> getBluePieceBank() {
		return bluePieceBank;
	}

	/**
	 * @param bluePieceBank the bluePieceBank to set
	 */
	public void setBluePieceBank(List<HantoPieceType> bluePieceBank) {
		this.bluePieceBank = bluePieceBank;
	}

	/**
	 * @return the redPieceBank
	 */
	public List<HantoPieceType> getRedPieceBank() {
		return redPieceBank;
	}

	/**
	 * @param redPieceBank the redPieceBank to set
	 */
	public void setRedPieceBank(List<HantoPieceType> redPieceBank) {
		this.redPieceBank = redPieceBank;
	}

	/**
	 * @return the validMove
	 */
	public MoveStrategy getValidMove() {
		return validMove;
	}

	/**
	 * @param whatTurn the whatTurn to set
	 */
	public void setWhatTurn(int whatTurn) {
		this.whatTurn = whatTurn;
	}

	/**
	 * @param whoseTurn the whoseTurn to set
	 */
	public void setWhoseTurn(HantoPlayerColor whoseTurn) {
		this.whoseTurn = whoseTurn;
	}

	/**
	 * @return final result (null if game is not over)
	 */
	public MoveResult getFinalResult() {
		return finalResult;
	}

	/**
	 * @param finalResult result to set
	 */
	public void setFinalResult(MoveResult finalResult) {
		this.finalResult = finalResult;
	}
}
