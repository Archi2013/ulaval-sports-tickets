package ca.ulaval.glo4003.domain.pojos;

import ca.ulaval.glo4003.domain.tickets.Ticket;

public interface Game {

	boolean acceptsToBeScheduled();

	void beScheduledToThisSport(String newSportName);

	void addTicket(Ticket tickets);

	void removeTicket(Ticket ticket);

}
