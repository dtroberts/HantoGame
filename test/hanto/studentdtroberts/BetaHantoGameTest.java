package hanto.studentdtroberts;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import hanto.common.HantoException;
import hanto.studentdtroberts.beta.BetaHantoGame;
import hanto.studentdtroberts.util.Coordinate;
import hanto.util.HantoCoordinate;
import hanto.util.HantoPieceType;
import hanto.util.HantoPlayerColor;
import hanto.util.MoveResult;

import org.junit.Before;
import org.junit.Test;

public class BetaHantoGameTest {

BetaHantoGame game1;
	
	@Before
	public void setup() throws HantoException {
		game1 = new BetaHantoGame();
		game1.initialize();
	}
	
	@Test
	public void blueMustGoFirst() {
		assertEquals(HantoPlayerColor.BLUE, game1.getWhoseTurn());
	}
	
	@Test
	public void blueMovesButterflyPiece() throws HantoException {
		HantoCoordinate loc = new Coordinate(0,0);
		game1.makeMove(HantoPieceType.BUTTERFLY, null, loc);
		assertEquals(HantoPieceType.BUTTERFLY, 
				game1.getGameBoard().getPieceAtCoords(loc));
	}
	
	@Test(expected=HantoException.class)
	public void cannotPlacePieceOnExistingOne() throws HantoException {
		HantoCoordinate loc = new Coordinate(0,0);
		game1.makeMove(HantoPieceType.BUTTERFLY, null, loc);
		game1.makeMove(HantoPieceType.BUTTERFLY, null, loc);
	}
	
	@Test
	public void redMovesPieceAdjacentToBluePiece() throws HantoException {
		HantoCoordinate loc1 = new Coordinate(0,0);
		HantoCoordinate loc2 = new Coordinate(0,1);
		game1.makeMove(HantoPieceType.BUTTERFLY, null, loc1);
		MoveResult result = game1.makeMove(HantoPieceType.BUTTERFLY, null, loc2);
		assertEquals(MoveResult.valueOf(MoveResult.OK.toString()), result);
	}
	
	@Test(expected=HantoException.class)
	public void redCannotMoveToSPaceNotAdjacentToBluePiece() throws HantoException {
		HantoCoordinate loc1 = new Coordinate(0,0);
		HantoCoordinate loc2 = new Coordinate(0,2);
		game1.makeMove(HantoPieceType.BUTTERFLY, null, loc1);
		game1.makeMove(HantoPieceType.BUTTERFLY, null, loc2);
	}
	
	@Test
	public void canPlaceSparrowOnBoard() throws HantoException {
		HantoCoordinate loc = new Coordinate(0,0);
		game1.makeMove(HantoPieceType.SPARROW, null, loc);
		assertEquals(HantoPieceType.SPARROW, 
				game1.getGameBoard().getPieceAtCoords(loc));
	}
	
	@Test
	public void redCanGoFirst() {
		game1.initialize(HantoPlayerColor.RED);
		assertEquals(game1.getWhoseTurn(), HantoPlayerColor.RED);
	}
	
	@Test
	public void playersStartWith1ButterflyAnd5Sparrows() {
		HantoPieceType[] pieces = {
				HantoPieceType.BUTTERFLY,
				HantoPieceType.SPARROW, 
				HantoPieceType.SPARROW,
				HantoPieceType.SPARROW,
				HantoPieceType.SPARROW,
				HantoPieceType.SPARROW
				};
		for (int i = 0; i < 5; i++) {
			assertEquals(pieces[i], game1.
					getPieceBank(HantoPlayerColor.BLUE).get(i));
		}
	}
	
	@Test(expected=HantoException.class)
	public void piecesMustBeAdjacentToEachOther() throws HantoException {
		HantoCoordinate loc1 = new Coordinate(0,0);
		HantoCoordinate loc2 = new Coordinate(0,1);
		HantoCoordinate loc3 = new Coordinate(2,1);
		game1.makeMove(HantoPieceType.SPARROW, null, loc1);
		game1.makeMove(HantoPieceType.SPARROW, null, loc2);
		game1.makeMove(HantoPieceType.SPARROW, null, loc3);
	}
	
	@Test(expected=HantoException.class)
	public void firstPieceMustBePlacedAtOrigin() throws HantoException {
		HantoCoordinate loc = new Coordinate(0,1);
		game1.makeMove(HantoPieceType.SPARROW, null, loc);
	}
	
	@Test(expected=HantoException.class)
	public void mustPlaceButterflyByFourthTurn() throws HantoException {
		HantoCoordinate[] coords = new HantoCoordinate[8];
		for (int i = 0; i < 8; i++) {
			coords[i] = new Coordinate(i,i);
			game1.makeMove(HantoPieceType.SPARROW, null, coords[i]);
		}
	}
	
	@Test
	public void winIfOpponentsButterflyIsSurrounded() throws HantoException {
		HantoCoordinate loc1 = new Coordinate(0,0);
		HantoCoordinate loc2 = new Coordinate(0,1);
		HantoCoordinate loc3 = new Coordinate(1,1);
		HantoCoordinate loc4 = new Coordinate(-1,1);
		HantoCoordinate loc5 = new Coordinate(1,0);
		HantoCoordinate loc6 = new Coordinate(0,-1);
		HantoCoordinate loc7 = new Coordinate(1,-1);
		game1.makeMove(HantoPieceType.BUTTERFLY, null, loc1);
		game1.makeMove(HantoPieceType.BUTTERFLY, null, loc2);
		game1.makeMove(HantoPieceType.SPARROW, null, loc3);
		game1.makeMove(HantoPieceType.SPARROW, null, loc4);
		game1.makeMove(HantoPieceType.SPARROW, null, loc5);
		game1.makeMove(HantoPieceType.SPARROW, null, loc6);
		MoveResult result = game1.makeMove(HantoPieceType.BUTTERFLY, null, loc7);
		assertEquals(MoveResult.RED_WINS, result);
	}
	
	@Test
	public void endInDrawIfButterflyNeverSurrounded() throws HantoException {
		game1.makeMove(HantoPieceType.BUTTERFLY, null, new Coordinate(0,0));
		game1.makeMove(HantoPieceType.BUTTERFLY, null, new Coordinate(1,1));
		game1.makeMove(HantoPieceType.SPARROW, null, new Coordinate(2,2));
		game1.makeMove(HantoPieceType.SPARROW, null, new Coordinate(3,3));
		game1.makeMove(HantoPieceType.SPARROW, null, new Coordinate(4,4));
		game1.makeMove(HantoPieceType.SPARROW, null, new Coordinate(5,5));
		game1.makeMove(HantoPieceType.SPARROW, null, new Coordinate(6,6));
		game1.makeMove(HantoPieceType.SPARROW, null, new Coordinate(7,7));
		game1.makeMove(HantoPieceType.SPARROW, null, new Coordinate(8,8));
		game1.makeMove(HantoPieceType.SPARROW, null, new Coordinate(9,9));
		game1.makeMove(HantoPieceType.SPARROW, null, new Coordinate(10,10));
		MoveResult result = game1.makeMove(HantoPieceType.SPARROW, null, 
				new Coordinate(11,11));
		assertEquals(MoveResult.DRAW, result);
	}
	
	@Test
	public void canGetNeighboringSpaces() {
		assertNotNull(game1.getGameBoard().getNeighboringSpaces(new Coordinate(0,0)));
	}
}
