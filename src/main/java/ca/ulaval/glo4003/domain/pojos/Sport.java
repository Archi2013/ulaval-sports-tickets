package ca.ulaval.glo4003.domain.pojos;

import org.joda.time.DateTime;

public interface Sport {

	public void addGameToCalendar(Game game, DateTime gameDate);
}
