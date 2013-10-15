package ca.ulaval.glo4003.domain.factories;

import org.joda.time.DateTime;

import ca.ulaval.glo4003.domain.pojos.persistable.PersistableGame;

public interface IGameFactory {

	PersistableGame instantiateGame(String opponents, DateTime gameDate);
}
