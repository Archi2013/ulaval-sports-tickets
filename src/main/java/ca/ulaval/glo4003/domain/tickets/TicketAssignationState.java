package ca.ulaval.glo4003.domain.tickets;

import org.joda.time.DateTime;

public interface TicketAssignationState {

	boolean isAssignable();

	TicketAssignationState assign(String sport, DateTime date, long ticketNumber);

	void fillDataInDto(TicketDto data);

}
