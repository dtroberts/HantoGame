package hanto.studentdtroberts;

import static org.junit.Assert.*;
import hanto.common.HantoException;
import hanto.studentdtroberts.alpha.AlphaHantoGame;
import hanto.studentdtroberts.util.Coordinate;
import hanto.util.*;

import org.junit.Before;
import org.junit.Test;

public class AlphaHantoGameTest {
	AlphaHantoGame game1;
	
	@Before
	public void setup() throws HantoException {
		game1 = new AlphaHantoGame();
		game1.initialize();
	}
	
	@Test
	public void blueMustGoFirst() {
		assertEquals(HantoPlayerColor.BLUE, game1.getWhoseTurn());
	}
	
	@Test
	public void blueMovesButterflyPiece() throws HantoException {
		Coordinate loc = new Coordinate(0,0);
		game1.makeMove(HantoPieceType.BUTTERFLY, null, loc);
		assertEquals(HantoPieceType.BUTTERFLY, 
				game1.getGameBoard().getPieceAtCoords(loc));
	}
	
	@Test
	public void redMovesPieceAdjacentToBluePieceAndEndsInDraw() throws HantoException {
		Coordinate loc1 = new Coordinate(0,0);
		Coordinate loc2 = new Coordinate(0,1);
		game1.makeMove(HantoPieceType.BUTTERFLY, null, loc1);
		MoveResult result = game1.makeMove(HantoPieceType.BUTTERFLY, null, loc2);
		assertEquals(MoveResult.valueOf(MoveResult.DRAW.toString()), result);
	}
	
	@Test(expected=HantoException.class)
	public void redCannotMoveToSPaceNotAdjacentToBluePiece() throws HantoException {
		Coordinate loc1 = new Coordinate(0,0);
		Coordinate loc2 = new Coordinate(0,2);
		game1.makeMove(HantoPieceType.BUTTERFLY, null, loc1);
		game1.makeMove(HantoPieceType.BUTTERFLY, null, loc2);
	}
	
	@Test
	public void canGetNeighboringSpaces() {
		assertNotNull(game1.getGameBoard().getNeighboringSpaces(new Coordinate(0,0)));
	}
}
