package ca.ulaval.glo4003.domain.factories;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.domain.pojos.persistable.PersistableGame;

@Component
public class GameFactory implements IGameFactory {

	@Override
	public PersistableGame instantiateGame(String opponents, DateTime gameDate) {
		return new PersistableGame(opponents, gameDate);
	}

}
