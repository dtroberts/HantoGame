/*******************************************************************************
 * This file was developed for CS4233: Object-Oriented Analysis & Design. The course was
 * taken at Worcester Polytechnic Institute. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.studentdtroberts;

import hanto.common.HantoException;
import hanto.common.HantoGame;
import hanto.studentdtroberts.alpha.AlphaHantoGame;
import hanto.studentdtroberts.beta.BetaHantoGame;
import hanto.studentdtroberts.delta.DeltaHantoGame;
import hanto.studentdtroberts.gamma.GammaHantoGame;
import hanto.util.HantoGameID;

/**
 * Factory to handle Hanto game creation.
 * @author Devin
 *
 * @version $Revision: 1.0 $
 */
public class HantoGameFactory {
	public static final HantoGameFactory theGameFactory = new HantoGameFactory();
	
	private HantoGameFactory() {
		
	}
	
	/**
	 * Get the Hanto game factory.
	 * @return the factory
	 */
	public static HantoGameFactory getInstance() {
		return theGameFactory;
	}
	
	/**
	 * Creates a Hanto game. 
	 * @param gameID game version
	 * @return the Hanto game * 
	 * @throws HantoException */
	public HantoGame makeHantoGame(HantoGameID gameID) throws HantoException {
		HantoGame game = null;
		switch(gameID) {
			case ALPHA_HANTO: 
				game =  new AlphaHantoGame();
			case BETA_HANTO:
				game = new BetaHantoGame();
			case GAMMA_HANTO:
				game = new GammaHantoGame();
			case DELTA_HANTO:
				game = new DeltaHantoGame();
		}
		if(game == null) {
			throw new HantoException("Invalid game specified");
		}
		return game;
	}
}
