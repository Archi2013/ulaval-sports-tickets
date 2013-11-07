package ca.ulaval.glo4003.domain.tickets.state;

import org.joda.time.DateTime;

public interface TicketAssignationState {

	boolean isAssignable();

	TicketAssignationState assign(String sport, DateTime date, int ticketNumber);

}
