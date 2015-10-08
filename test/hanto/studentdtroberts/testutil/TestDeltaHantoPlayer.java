package hanto.studentdtroberts.testutil;

import java.util.ArrayList;
import java.util.List;

import hanto.studentdtroberts.tournament.DeltaHantoPlayer;
import hanto.tournament.HantoMoveRecord;
import hanto.util.HantoPieceType;
import hanto.util.HantoPlayerColor;

/**
 * This class is meant for testing the functionality of DeltaHantoPlayer.
 * The following changes have been made:
 * * Instantiates a TestDeltaHantoGame as opposed to a DeltaHantoGame
 * * Contains a boolean disallowing placement of new pieces in makeMove()
 * @author Devin
 *
 */
public class TestDeltaHantoPlayer extends DeltaHantoPlayer {
	private boolean canPlacePieces = true;

	public TestDeltaHantoPlayer(HantoPlayerColor color, boolean isFirst) {
		super(color, isFirst);
		this.game = new TestDeltaHantoGame();
	}
	
	public void setCanPlacePieces(boolean canPlacePieces) {
		this.canPlacePieces = canPlacePieces;		
	}
	
	public HantoMoveRecord makeMove(HantoMoveRecord opponentsMove) {
		if (canPlacePieces) {
			return super.makeMove(opponentsMove);
		}
		else {
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
		
			// place the butterfly
			if (game.getWhatTurn() == 1 && canPlacePieces) {
				record = placeButterflyPiece();
			}
		
			else {
				// take a piece from the bank
				if (!bank.isEmpty() && canPlacePieces) {
					record = placeNewPiece(bank);
				}
				else {
					record = movePieceOnBoard();
				}
			}
			if (record == null) {
				resign();
			}
		return record;
		}
	}
}
