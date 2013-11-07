package ca.ulaval.glo4003.domain.tickets;

import org.joda.time.DateTime;

public interface Ticket {

	boolean isSame(Ticket ticketToAdd);

	boolean isAssignable();

	void assign(String sport, DateTime date, int ticketNumber);

	boolean isSeat(String seat);

	boolean isSection(String section);

}
