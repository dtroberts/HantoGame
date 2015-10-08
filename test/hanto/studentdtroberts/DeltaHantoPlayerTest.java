package hanto.studentdtroberts;

import static org.junit.Assert.*;

import org.junit.*;

import hanto.common.HantoException;
import hanto.studentdtroberts.testutil.TestDeltaHantoPlayer;
import hanto.studentdtroberts.tournament.DeltaHantoPlayer;
import hanto.tournament.HantoMoveRecord;
import hanto.util.HantoPieceType;
import hanto.util.HantoPlayerColor;
import hanto.util.MoveResult;
import hanto.studentdtroberts.delta.DeltaHantoGame;

/**
 * The class <code>DeltaHantoPlayerTest</code> contains tests for the class
 * {@link <code>DeltaHantoPlayer</code>}
 *
 * @pattern JUnit Test Case
 *
 * @generatedBy CodePro at 2/21/13 10:01 AM
 *
 * @author Devin
 *
 * @version $Revision$
 */
public class DeltaHantoPlayerTest {
	DeltaHantoPlayer player1;
	DeltaHantoPlayer player2;
	HantoMoveRecord record;

	@Before
	public void setup() throws HantoException {
		player1 = new TestDeltaHantoPlayer(HantoPlayerColor.BLUE, true);
		player2 = new TestDeltaHantoPlayer(HantoPlayerColor.RED, false);
		record = null;
	}
	
	@Test
	public void canCreatePlayerWithAssociatedGame() throws HantoException {
		assertNotNull(player1.getGame());
	}
	
	@Test
	public void canMakeFirstMove() throws HantoException {
		player1.makeMove(null);
		assertEquals(1, player1.getGame().getGameBoard().getBoard().size());
	}
	
	@Test
	public void secondPlayerCanPlacePiece() throws HantoException {
		HantoMoveRecord record = null;
		record = player1.makeMove(record);
		player2.makeMove(record);
		assertEquals(2, player2.getGame().getGameBoard().getBoard().size());
	}
	
	@Test
	public void turnChangesProperly() throws HantoException {
		record = player1.makeMove(record);
		record = player2.makeMove(record);
		record = player1.makeMove(record);
		record = player2.makeMove(record);
		record = player1.makeMove(record);
		
		assertEquals(3, ((DeltaHantoGame) player1.getGame()).getWhatTurn());
	}
	
	@Test
	public void playerCanPlaceAllPieces() throws HantoException {
		record = player1.makeMove(record);
		record = player2.makeMove(record);
		record = player1.makeMove(record);
		record = player2.makeMove(record);
		record = player1.makeMove(record);
		record = player2.makeMove(record);
		record = player1.makeMove(record);
		record = player2.makeMove(record);
		record = player1.makeMove(record);
		record = player2.makeMove(record);
		record = player1.makeMove(record);
		record = player2.makeMove(record);
		record = player1.makeMove(record);
		record = player2.makeMove(record);
		record = player1.makeMove(record);
		record = player2.makeMove(record);
		record = player1.makeMove(record);
		record = player2.makeMove(record);
		
		assertEquals(18, player2.getGame().getGameBoard().getBoard().size());
	}
	
	@Test
	public void playerCanMovePiece() {	
		record = player1.makeMove(record);
		record = player2.makeMove(record);
		((TestDeltaHantoPlayer) player1).setCanPlacePieces(false);
		record = player1.makeMove(record);
		assertEquals(8,((DeltaHantoGame) player1.getGame()).getBluePieceBank().size()); 
		assertEquals(2, player1.getGame().getGameBoard().getBoard().size());
		assertEquals(HantoPieceType.BUTTERFLY, record.getPiece());
	}
	
	@Test
	public void playerCanResign() {
		((TestDeltaHantoPlayer) player1).setCanPlacePieces(false);
		player1.makeMove(record);
		assertEquals(MoveResult.RED_WINS, ((DeltaHantoGame) player1.getGame()).getFinalResult());
	}
}