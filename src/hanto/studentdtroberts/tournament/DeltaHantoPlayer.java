/*******************************************************************************
 * This file was developed for CS4233: Object-Oriented Analysis & Design. The course was
 * taken at Worcester Polytechnic Institute. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.studentdtroberts.tournament;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import hanto.common.HantoException;
import hanto.common.HantoGame;
import hanto.studentdtroberts.delta.DeltaHantoGame;
import hanto.studentdtroberts.util.Coordinate;
import hanto.studentdtroberts.util.HantoPiece;
import hanto.tournament.HantoGamePlayer;
import hanto.tournament.HantoMoveRecord;
import hanto.util.HantoPieceType;
import hanto.util.HantoPlayerColor;
import hanto.studentdtroberts.delta.DeltaHantoBoard;


/*******************************************************************************
 * This file was developed for CS4233: Object-Oriented Analysis & Design. The course was
 * taken at Worcester Polytechnic Institute. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * @author Devin
 * @version $Revision: 1.0 $
 *******************************************************************************/
public class DeltaHantoPlayer implements HantoGamePlayer {
	protected final HantoPlayerColor color;
	private final boolean isFirst;
	protected DeltaHantoGame game;
	
	/**
	 * This is a player for Delta Hanto.
	
	
	 * @param color HantoPlayerColor
	 * @param isFirst boolean
	 */
	public DeltaHantoPlayer(HantoPlayerColor color, boolean isFirst) {
		this.color = color;
		this.isFirst = isFirst;
		if (isFirst) {
			game = new DeltaHantoGame(color);
		}
		else {
			if (color == HantoPlayerColor.BLUE) {
				game = new DeltaHantoGame();
			}
			else {
				game = new DeltaHantoGame(HantoPlayerColor.BLUE);
			}
		}
	}
	
	@Override
	public HantoMoveRecord makeMove(HantoMoveRecord opponentsMove) {
		HantoMoveRecord record = null;
		// get game to the state it should be
		applyOpponentsMove(opponentsMove);
		// use the proper piece bank 
		List<HantoPieceType> bank = new ArrayList<HantoPieceType>();
		if (color == HantoPlayerColor.BLUE) {
			bank = game.getBluePieceBank();
		}
		else {
			bank = game.getRedPieceBank();
		}
		
		if (game.getWhatTurn() == 1) {
			record = placeButterflyPiece();
		}
		
		else {
			if (!bank.isEmpty()) {
				record = placeNewPiece(bank);
			}
			// move a piece
			else {
				record = movePieceOnBoard();
			}
		}
		if (record == null) {
			resign();
		}
		return record;
	}


	/**
	 * Moves a piece which is already on the board. For use in makeMove().
	
	 * @return move record */
	protected HantoMoveRecord movePieceOnBoard() {
		HantoMoveRecord record = null;
		HantoPieceType piece = null;
		Coordinate from = null;
		Coordinate to = null;
		HantoPiece pieceToMove = null;
		final List<HantoPiece> validMovePieces = new ArrayList<HantoPiece>();
		final List<Coordinate> validMoves = new ArrayList<Coordinate>();
		if (game.getGameBoard().getBoard().size() == 0) {
			return null;
		}
		for (HantoPiece p : game.getGameBoard().getBoard()) {
			try {
				if (p.getPlayer() == color && !game.getGameBoard().isPieceLockedInPlace(p)) {
					validMovePieces.add(p);
				}
			} catch (HantoException e) {
				e.printStackTrace();
			}
		}
		
		pieceToMove = validMovePieces.get(randInt(validMovePieces.size()));
		piece = pieceToMove.getPieceType();
		from = (Coordinate) pieceToMove.getCoords();
		
		List<Coordinate> neighbors = null;
		for (HantoPiece p : game.getGameBoard().getBoard()) {
			neighbors = ((DeltaHantoBoard) game.getGameBoard()).getNeighboringSpaces(p.getCoords());
			for (Coordinate c : neighbors) {
				if (game.getValidMove().isValidMove(c, game.getGameBoard())
						&& game.getValidMove().isValidPieceMove(piece, c, from)) {
					validMoves.add(c);
				}
			}
		}

		to = validMoves.get(randInt(validMovePieces.size()));

		try {
			game.makeMove(piece, from, to);
		} catch (HantoException e) {
			e.printStackTrace();
		}
		record = new HantoMoveRecord(piece, from, to);
		return record;
	}

	/**
	 * Place a new piece on the board and remove it from the bank.
	 * @param bank piece bank to take from
	
	 * @return move record */
	protected HantoMoveRecord placeNewPiece(List<HantoPieceType> bank) {
		HantoMoveRecord record = null;
		HantoPieceType piece = null;
		Coordinate to = null;
		final List<Coordinate> validCoords = new ArrayList<Coordinate>();
		piece = bank.get(randInt(bank.size()));
		// check all available locations on the board
		for (HantoPiece p : game.getGameBoard().getBoard()) {
			List<Coordinate> neighbors = ((DeltaHantoBoard) game.getGameBoard())
					.getNeighboringSpaces(p.getCoords());
			for (Coordinate c : neighbors) {
				if (game.getValidMove().isValidMove(c, game.getGameBoard(), color)) {
					validCoords.add(c);
				}
			}
		}
		to = validCoords.get(randInt(validCoords.size()));
		try {
			game.makeMove(piece, null, to);
			record = new HantoMoveRecord(piece, null, to);
		} catch (HantoException e) {
			e.printStackTrace();
		}
		return record;
	}

	/**
	 * Moves the butterfly piece. Meant only for the first turn.
	
	 * @return resulting HantoMoveRecord */
	protected HantoMoveRecord placeButterflyPiece() {
		Coordinate to = null;
		HantoMoveRecord record = null;
		if (isFirst) {
			try {
				record = new HantoMoveRecord(HantoPieceType.BUTTERFLY, null, 
						new Coordinate(0, 0));
				game.makeMove(HantoPieceType.BUTTERFLY, null, new Coordinate(0, 0));
			} catch (HantoException e) {
				e.printStackTrace();
			}
		}
		else {
			final List<Coordinate> neighbors = ((DeltaHantoBoard) game.getGameBoard())
					.getNeighboringSpaces(new Coordinate(0, 0));
			try {
				to = neighbors.get(randInt(neighbors.size()));
				game.makeMove(HantoPieceType.BUTTERFLY, null, to);
				record = new HantoMoveRecord(HantoPieceType.BUTTERFLY, null, to);
			} catch (HantoException e) {
				e.printStackTrace();
			}
		}
		return record;
	}

	/**
	 * Makes the opponent's move based on their MoveRecord.
	 * @param opponentsMove move to make
	 */
	protected void applyOpponentsMove(HantoMoveRecord opponentsMove) {
		if (opponentsMove != null) {
			try {
				game.makeMove(opponentsMove.getPiece(), 
						opponentsMove.getFrom(), opponentsMove.getTo());
			} catch (HantoException e) {
				System.out.println("Could not make opponent's move: " + e.getMessage());
			}
		}
	}

	/**
	 * Generates a random integer; 0<= x<= max
	 * @param max maximum integer value
	
	 * @return random integer */
	protected int randInt(int max) {
		int x;
		final Random gen = new Random();
		if (max == 0) {
			x = 0;
		}
		else {
			x = gen.nextInt(max);
		}
		return x;
	}
	
	/**
	 * Resign. May be called at any point.
	 */
	protected void resign() {
		try {
			game.makeMove(null, null, null);
		} catch (HantoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * @return the game
	 */
	public HantoGame getGame() {
		return game;
	}
}
