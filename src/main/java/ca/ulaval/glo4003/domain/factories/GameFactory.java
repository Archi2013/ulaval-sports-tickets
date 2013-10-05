package ca.ulaval.glo4003.domain.factories;

import org.joda.time.DateTime;

import ca.ulaval.glo4003.domain.pojos.Game;

public class GameFactory implements IGameFactory {

	@Override
	public Game instantiateGame(String opponents, DateTime gameDate) {
		return new Game(opponents, gameDate);
	}

}
