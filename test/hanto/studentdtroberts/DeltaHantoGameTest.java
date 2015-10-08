package hanto.studentdtroberts;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import hanto.common.HantoException;
import hanto.common.HantoGame;
import hanto.testutil.TestHantoCoordinate;
import hanto.util.HantoCoordinate;
import hanto.util.HantoGameID;
import static hanto.util.HantoPieceType.*;
import static hanto.util.HantoPlayerColor.*;
import hanto.util.MoveResult;

/**
 * The class <code>DeltaHantoGameTest</code> contains tests for the class
 * {@link <code>DeltaHantoGame</code>}
 *
 * @pattern JUnit Test Case
 *
 * @generatedBy CodePro at 2/4/13 10:47 AM
 *
 * @author Devin
 *
 * @version $Revision$
 */
public class DeltaHantoGameTest {
	static HantoGame game;
	
	private final HantoCoordinate
		tc00 = new TestHantoCoordinate(0, 0),
		tc01 = new TestHantoCoordinate(0, 1),
		tc0_1 = new TestHantoCoordinate(0, -1),
		tc10 = new TestHantoCoordinate(1, 0),
		tc20 = new TestHantoCoordinate(2,0),
		tc02 = new TestHantoCoordinate(0,2),
		tc_10 = new TestHantoCoordinate(-1,0),
		tc_11 = new TestHantoCoordinate(-1,1),
		tc1_1 = new TestHantoCoordinate(1,-1),
		tc_20 = new TestHantoCoordinate(-2,0);
	
	@BeforeClass
	public static void setupGame() throws HantoException {
		game = HantoGameFactory.getInstance().makeHantoGame(HantoGameID.DELTA_HANTO);
	}
	
	@Before
	public void setup() throws HantoException {
		game.initialize(BLUE);
	}
	
	@Test
	public void blueMovesButterflyPiece() throws HantoException {
		HantoCoordinate loc = new TestHantoCoordinate(0,0);
		game.makeMove(BUTTERFLY, null, loc);
		assertEquals(BUTTERFLY, 
				game.getGameBoard().getPieceAtCoords(loc));
	}
	
	@Test(expected=HantoException.class)
	public void cannotPlacePieceOnExistingOne() throws HantoException {
		HantoCoordinate loc = new TestHantoCoordinate(0,0);
		game.makeMove(BUTTERFLY, null, loc);
		game.makeMove(BUTTERFLY, null, loc);
	}
	
	@Test
	public void redMovesPieceAdjacentToBluePiece() throws HantoException {
		HantoCoordinate loc1 = new TestHantoCoordinate(0,0);
		HantoCoordinate loc2 = new TestHantoCoordinate(0,1);
		game.makeMove(BUTTERFLY, null, loc1);
		MoveResult result = game.makeMove(BUTTERFLY, null, loc2);
		assertEquals(MoveResult.valueOf(MoveResult.OK.toString()), result);
	}
	
	@Test(expected=HantoException.class)
	public void redCannotMoveToSpaceNotAdjacentToBluePiece() throws HantoException {
		HantoCoordinate loc1 = new TestHantoCoordinate(0,0);
		HantoCoordinate loc2 = new TestHantoCoordinate(0,2);
		game.makeMove(BUTTERFLY, null, loc1);
		game.makeMove(BUTTERFLY, null, loc2);
	}
	
	@Test
	public void canPlaceSparrowOnBoard() throws HantoException {
		HantoCoordinate loc = new TestHantoCoordinate(0,0);
		game.makeMove(SPARROW, null, loc);
		assertEquals(SPARROW, 
				game.getGameBoard().getPieceAtCoords(loc));
	}
	
	@Test(expected=HantoException.class)
	public void piecesMustBeAdjacentToEachOther() throws HantoException {
		HantoCoordinate loc1 = new TestHantoCoordinate(0,0);
		HantoCoordinate loc2 = new TestHantoCoordinate(0,1);
		HantoCoordinate loc3 = new TestHantoCoordinate(2,1);
		game.makeMove(SPARROW, null, loc1);
		game.makeMove(SPARROW, null, loc2);
		game.makeMove(SPARROW, null, loc3);
	}
	
	@Test(expected=HantoException.class)
	public void firstPieceMustBePlacedAtOrigin() throws HantoException {
		HantoCoordinate loc = new TestHantoCoordinate(0,1);
		game.makeMove(SPARROW, null, loc);
	}
	
	@Test(expected=HantoException.class)
	public void mustPlaceButterflyByFourthTurn() throws HantoException {
		HantoCoordinate[] coords = new HantoCoordinate[8];
		for (int i = 0; i < 8; i++) {
			coords[i] = new TestHantoCoordinate(i,i);
			game.makeMove(SPARROW, null, coords[i]);
		}
	}
	
	@Test
	public void winIfOpponentsButterflyIsSurrounded() throws HantoException {
		game.makeMove(BUTTERFLY, null, tc00); // blue
		game.makeMove(BUTTERFLY, null, tc01); // red
		game.makeMove(SPARROW, null, tc0_1); // blue
		game.makeMove(SPARROW, null, tc02); // red
		game.makeMove(SPARROW, null, tc_10); // blue
		game.makeMove(SPARROW, tc02, tc_11); // red
		game.makeMove(SPARROW, null, new TestHantoCoordinate(0,-2)); // blue
		game.makeMove(SPARROW, null, tc02); // red
		game.makeMove(CRAB, null, tc_20); // blue
		game.makeMove(SPARROW, tc02, tc1_1); // red
		MoveResult result = game.makeMove(SPARROW, new TestHantoCoordinate(0,-2), tc10); // blue
		
		System.out.println(game.getPrintableBoard());
		assertEquals(MoveResult.RED_WINS, result);
	}
	
	@Test
	public void butterflyCanMove() throws HantoException {
		HantoCoordinate loc1 = new TestHantoCoordinate(0,0);
		HantoCoordinate loc2 = new TestHantoCoordinate(0,1);
		HantoCoordinate loc3 = new TestHantoCoordinate(1,0);
		game.makeMove(BUTTERFLY, null, loc1);
		game.makeMove(BUTTERFLY, null, loc2);
		game.makeMove(BUTTERFLY, loc1, loc3);
		assertEquals(BUTTERFLY, game.getGameBoard().getPieceAtCoords(loc3));
	}
	
	@Test(expected=HantoException.class)
	public void butterflyCannotMoveMoreThan1Space() throws HantoException {
		HantoCoordinate loc1 = new TestHantoCoordinate(0,0);
		HantoCoordinate loc2 = new TestHantoCoordinate(0,1);
		HantoCoordinate loc3 = new TestHantoCoordinate(0,2);
		game.makeMove(BUTTERFLY, null, loc1);
		game.makeMove(BUTTERFLY, null, loc2);
		game.makeMove(BUTTERFLY, loc1, loc3);
	}
	
	@Test(expected=HantoException.class)
	public void sparrowsCannotMove() throws HantoException{
		HantoCoordinate loc1 = new TestHantoCoordinate(0,0);
		HantoCoordinate loc2 = new TestHantoCoordinate(0,1);
		HantoCoordinate loc3 = new TestHantoCoordinate(0,2);
		game.makeMove(SPARROW, null, loc1);
		game.makeMove(SPARROW, null, loc2);
		game.makeMove(SPARROW, loc1, loc3);
	}
	
	@Test
	public void printBoard() throws HantoException {
		game.makeMove(BUTTERFLY, null, new TestHantoCoordinate(0,0));
		game.makeMove(BUTTERFLY, null, new TestHantoCoordinate(1,0));
		assertNotNull(game.getPrintableBoard());
	}
	
	@Test
	public void gameCreationWasSuccessful() throws HantoException {
		assertNotNull(game);

		assertNotNull(HantoGameFactory.getInstance().makeHantoGame(HantoGameID.ALPHA_HANTO));
		assertNotNull(HantoGameFactory.getInstance().makeHantoGame(HantoGameID.BETA_HANTO));
		assertNotNull(HantoGameFactory.getInstance().makeHantoGame(HantoGameID.GAMMA_HANTO));
	}
	
	@Test(expected=HantoException.class)
	public void piecesCannotMoveUntilButterflyIsPlaced() throws HantoException {
		game.makeMove(SPARROW, null, tc00);
		game.makeMove(SPARROW, null, tc01);
		game.makeMove(SPARROW, tc00, tc10);
	}
	
	@Test
	public void sparrowCanFly() throws HantoException {
		game.makeMove(BUTTERFLY, null, tc00);
		game.makeMove(BUTTERFLY, null, tc01);
		game.makeMove(SPARROW, null, tc_10);
		game.makeMove(SPARROW, null, tc02);
		game.makeMove(SPARROW, tc_10, tc_11);
		assertEquals(game.getGameBoard().getPieceAtCoords(tc_11), SPARROW);
	}
	
	@Test(expected=HantoException.class)
	public void onlySparrowCanFly() throws HantoException {
		game.makeMove(BUTTERFLY, null, tc00);
		game.makeMove(CRAB, null, tc01);
		game.makeMove(CRAB, null, tc_10);
		game.makeMove(CRAB, null, tc10);
		game.makeMove(CRAB, tc_10, tc20);
	}
	
	@Test
	public void crabMayWalk() throws HantoException {
		game.makeMove(BUTTERFLY, null, tc00);
		game.makeMove(BUTTERFLY, null, tc01);
		game.makeMove(CRAB, null, tc_10);
		game.makeMove(CRAB, null, tc02);
		game.makeMove(CRAB, tc_10, tc_11);
		assertEquals(game.getGameBoard().getPieceAtCoords(tc_11), CRAB);
	}
	
	@Test(expected=HantoException.class)
	public void surroundedWalkingPieceCannotMove() throws HantoException {
		game.makeMove(BUTTERFLY, null, tc00);
		game.makeMove(BUTTERFLY, null, tc01);
		game.makeMove(CRAB, null, tc_10);
		game.makeMove(CRAB, null, tc10);
		game.makeMove(CRAB, null, tc1_1);
		game.makeMove(BUTTERFLY, tc00, tc_11);	
	}
	
	@Test(expected=HantoException.class)
	public void piecesCanOnlyBePlacedNextToThoseOfTheSameColor() throws HantoException {
		game.makeMove(BUTTERFLY, null, tc00);
		game.makeMove(BUTTERFLY, null, tc01);
		game.makeMove(CRAB, null, tc02);
	}
	
	@Test
	public void piecesCanBePlacedNextToOtherPlayersPiecesForFirstTurn() throws HantoException {
		game.makeMove(BUTTERFLY, null, tc00);
		MoveResult result = game.makeMove(BUTTERFLY, null, tc01);
		assertEquals(result, MoveResult.OK);
	}
	
	@Test
	public void resignByCallingMakeMoveWithAllNulls() throws HantoException {
		assertEquals(game.makeMove(null,null,null), MoveResult.RED_WINS);
		assertEquals(0, game.getGameBoard().getBoard().size());
	}
	
	@Test(expected=HantoException.class)
	public void testTest() throws HantoException {
		game.makeMove(BUTTERFLY, null, tc00); // Blue
		game.makeMove(SPARROW, null, tc01); // Blue
		game.makeMove(BUTTERFLY, tc10, tc0_1); // Blue
	}
}