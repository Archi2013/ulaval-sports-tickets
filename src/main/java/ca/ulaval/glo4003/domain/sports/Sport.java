package ca.ulaval.glo4003.domain.sports;

import org.joda.time.DateTime;

import ca.ulaval.glo4003.domain.game.Game;

public interface Sport {

	public void addGameToCalendar(Game game, DateTime gameDate);
}
