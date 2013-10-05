package ca.ulaval.glo4003.domain.factories;

import org.joda.time.DateTime;

import ca.ulaval.glo4003.domain.pojos.Game;

public interface IGameFactory {

	Game instantiateGame(String opponents, DateTime gameDate);
}
