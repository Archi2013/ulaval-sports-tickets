package ca.ulaval.glo4003.domain.tickets;

import org.joda.time.DateTime;

public interface Ticket {

	boolean isSame(Ticket ticketToAdd);

	boolean isAssociable();

	void associate(String sport, DateTime date, int ticketNumber);

	boolean compareSeatAndSection(String seat, String section);

	boolean isSeat(String seat);

	boolean isSection(String section);

}
