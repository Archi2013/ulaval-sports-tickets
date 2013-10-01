package ca.ulaval.glo4003.factories;

import org.joda.time.DateTime;

import ca.ulaval.glo4003.pocos.Game;

public interface IGameFactory {

	Game createNewGame(String opponents, DateTime gameDate);
}
