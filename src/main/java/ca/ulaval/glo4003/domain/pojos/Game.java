package ca.ulaval.glo4003.domain.pojos;

import org.joda.time.DateTime;

import ca.ulaval.glo4003.domain.tickets.Ticket;

public interface Game {

	boolean acceptsToBeScheduled();

	void beScheduledToThisSport(String newSportName, DateTime gameDate);

	void addTicket(Ticket tickets);

	void removeTicket(Ticket ticket);

}
