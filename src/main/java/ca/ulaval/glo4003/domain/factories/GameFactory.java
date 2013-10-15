package ca.ulaval.glo4003.domain.factories;

import org.joda.time.DateTime;

import ca.ulaval.glo4003.domain.pojos.persistable.PersistableGame;

public class GameFactory implements IGameFactory {

	@Override
	public PersistableGame instantiateGame(String opponents, DateTime gameDate) {
		return new PersistableGame(opponents, gameDate);
	}

}
