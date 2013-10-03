package ca.ulaval.glo4003.factories;

import org.joda.time.DateTime;

import ca.ulaval.glo4003.pojos.Game;

public interface IGameFactory {

	Game instantiateGame(String opponents, DateTime gameDate);
}
