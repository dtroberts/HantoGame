package hanto.studentdtroberts;

import static org.junit.Assert.*;
import hanto.common.HantoException;
import hanto.studentdtroberts.gamma.GammaHantoGame;
import hanto.studentdtroberts.util.Coordinate;
import hanto.util.HantoCoordinate;
import hanto.util.HantoPieceType;
import hanto.util.HantoPlayerColor;
import hanto.util.MoveResult;

import org.junit.Before;
import org.junit.Test;

public class GammaHantoGameTest {
	GammaHantoGame game1;
	
	@Before
	public void setup() throws HantoException {
		game1 = new GammaHantoGame();
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
		HantoCoordinate loc3 = new Coordinate(-1,0);
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
		MoveResult result = game1.makeMove(HantoPieceType.SPARROW, null, loc7);
		assertEquals(MoveResult.RED_WINS, result);
	}
	
	@Test
	public void butterflyCanMove() throws HantoException {
		HantoCoordinate loc1 = new Coordinate(0,0);
		HantoCoordinate loc2 = new Coordinate(0,1);
		HantoCoordinate loc3 = new Coordinate(1,0);
		game1.makeMove(HantoPieceType.BUTTERFLY, null, loc1);
		game1.makeMove(HantoPieceType.BUTTERFLY, null, loc2);
		game1.makeMove(HantoPieceType.BUTTERFLY, loc1, loc3);
		assertEquals(HantoPieceType.BUTTERFLY, game1.getGameBoard().getPieceAtCoords(loc3));
	}
	
	@Test(expected=HantoException.class)
	public void butterflyCannotMoveMoreThan1Space() throws HantoException {
		HantoCoordinate loc1 = new Coordinate(0,0);
		HantoCoordinate loc2 = new Coordinate(0,1);
		HantoCoordinate loc3 = new Coordinate(0,2);
		game1.makeMove(HantoPieceType.BUTTERFLY, null, loc1);
		game1.makeMove(HantoPieceType.BUTTERFLY, null, loc2);
		game1.makeMove(HantoPieceType.BUTTERFLY, loc1, loc3);
	}
	
	@Test(expected=HantoException.class)
	public void sparrowsCannotMove() throws HantoException{
		HantoCoordinate loc1 = new Coordinate(0,0);
		HantoCoordinate loc2 = new Coordinate(0,1);
		HantoCoordinate loc3 = new Coordinate(0,2);
		game1.makeMove(HantoPieceType.SPARROW, null, loc1);
		game1.makeMove(HantoPieceType.SPARROW, null, loc2);
		game1.makeMove(HantoPieceType.SPARROW, loc1, loc3);
	}
	
	@Test
	public void endInDrawAfter10Turns() throws HantoException {
		MoveResult result;
		Coordinate tc00 = new Coordinate(0,0);
		Coordinate tc10 = new Coordinate(1,0);
		Coordinate tc01 = new Coordinate(0,1);
		Coordinate tc20 = new Coordinate(2,0);
		game1.makeMove(HantoPieceType.BUTTERFLY, null, tc00);
		game1.makeMove(HantoPieceType.BUTTERFLY, null, tc10);
		game1.makeMove(HantoPieceType.SPARROW, null, new Coordinate(2,0));
		game1.makeMove(HantoPieceType.SPARROW, null, new Coordinate(3,0));
		game1.makeMove(HantoPieceType.SPARROW, null, new Coordinate(4,0));
		game1.makeMove(HantoPieceType.SPARROW, null, new Coordinate(5,0));
		game1.makeMove(HantoPieceType.SPARROW, null, new Coordinate(6,0));
		game1.makeMove(HantoPieceType.SPARROW, null, new Coordinate(7,0));
		game1.makeMove(HantoPieceType.SPARROW, null, new Coordinate(8,0));
		game1.makeMove(HantoPieceType.SPARROW, null, new Coordinate(9,0));
		game1.makeMove(HantoPieceType.SPARROW, null, new Coordinate(10,0));
		game1.makeMove(HantoPieceType.SPARROW, null, new Coordinate(11,0));
		game1.makeMove(HantoPieceType.BUTTERFLY, tc00, tc01);
		game1.makeMove(HantoPieceType.BUTTERFLY, tc10, tc20);
		game1.makeMove(HantoPieceType.BUTTERFLY, tc01, tc00);
		game1.makeMove(HantoPieceType.BUTTERFLY, tc20, tc10);
		game1.makeMove(HantoPieceType.BUTTERFLY, tc00, tc01);
		game1.makeMove(HantoPieceType.BUTTERFLY, tc10, tc20);
		game1.makeMove(HantoPieceType.BUTTERFLY, tc01, tc00);
		result = game1.makeMove(HantoPieceType.BUTTERFLY, 
				tc20, tc10);
		assertEquals(MoveResult.DRAW, result);
	}
	
	@Test
	public void printBoard() throws HantoException {
		game1.makeMove(HantoPieceType.BUTTERFLY, null, new Coordinate(0,0));
		game1.makeMove(HantoPieceType.BUTTERFLY, null, new Coordinate(1,0));
		game1.makeMove(HantoPieceType.SPARROW, null, new Coordinate(2,0));
		game1.makeMove(HantoPieceType.SPARROW, null, new Coordinate(3,0));
		game1.makeMove(HantoPieceType.SPARROW, null, new Coordinate(4,0));
		game1.makeMove(HantoPieceType.SPARROW, null, new Coordinate(5,0));
		game1.makeMove(HantoPieceType.SPARROW, null, new Coordinate(6,0));
		game1.makeMove(HantoPieceType.SPARROW, null, new Coordinate(7,0));
		game1.makeMove(HantoPieceType.SPARROW, null, new Coordinate(8,0));
		game1.makeMove(HantoPieceType.SPARROW, null, new Coordinate(9,0));
		game1.makeMove(HantoPieceType.SPARROW, null, new Coordinate(10,0));
		game1.makeMove(HantoPieceType.SPARROW, null, new Coordinate(11,0));
		assertNotNull(game1.getPrintableBoard());
	}
	
	@Test
	public void canGetNeighboringSpaces() {
		assertNotNull(game1.getGameBoard().getNeighboringSpaces(new Coordinate(0,0)));
	}
}
