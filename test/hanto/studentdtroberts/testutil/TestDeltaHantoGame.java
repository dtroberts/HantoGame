package hanto.studentdtroberts.testutil;

import java.util.List;

import hanto.common.HantoException;
import hanto.studentdtroberts.delta.DeltaHantoGame;
import hanto.util.HantoCoordinate;
import hanto.util.HantoPieceType;
import hanto.util.HantoPlayerColor;
import hanto.util.MoveResult;

/**
 * This class is used for testing DeltaHantoPlayer.
 * The only modification is that the game continues to run even after
 * a winner has been declared.
 * @author Devin
 */
public class TestDeltaHantoGame extends DeltaHantoGame {
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
		
		// location is not taken and valid
		if (getValidMove().isValidMove(to, gameBoard)) {
			List<HantoPieceType> bank;
			if (getWhoseTurn() == HantoPlayerColor.BLUE) {
				bank = getBluePieceBank();
				nextTurn = HantoPlayerColor.RED;
			}
			else {
				bank = getRedPieceBank();
				nextTurn = HantoPlayerColor.BLUE;
			}
			// must place butterfly
			if (bank.contains(HantoPieceType.BUTTERFLY) && 
					getWhatTurn() == MUST_PLACE_BUTTERFLY && pieceType != HantoPieceType.BUTTERFLY) {
				throw new HantoException("Must place Butterfly");
			}
			// moving instead of newly placing
			if (from != null) {
				// butterfly must be placed before pieces can move
				if (bank.contains(HantoPieceType.BUTTERFLY)) {
					throw new HantoException("Must place butterfly before moving other pieces");
				}
				gameBoard.movePiece(pieceType, to, from, getWhoseTurn());
			}
			else {
				if (validMove.isValidMove(to, gameBoard, getWhoseTurn()) || getWhatTurn() == 1) {
					placeNewPiece(pieceType, to, from);
				}
				else {
					throw new HantoException("Must place adjacent to a piece of the same color");
				}
			}
		}
		else {
				throw new HantoException("Invalid location");
		}
		result = gameBoard.butterflySurrounded();
		if (getWhoseTurn() == HantoPlayerColor.RED) {
			setWhatTurn(getWhatTurn() + 1);
		}
		if (result == MoveResult.BLUE_WINS || result == MoveResult.RED_WINS) {
			finalResult = result; // Does not enforce the end of the game; meant for testing
		}
		setWhoseTurn(nextTurn);
		return result;
	}
}
