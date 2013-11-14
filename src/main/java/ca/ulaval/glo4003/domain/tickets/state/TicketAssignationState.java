package ca.ulaval.glo4003.domain.tickets.state;

import org.joda.time.DateTime;

import ca.ulaval.glo4003.domain.dtos.TicketDto;

public interface TicketAssignationState {

	boolean isAssignable();

	TicketAssignationState assign(String sport, DateTime date, long ticketNumber);

	void fillDataInDto(TicketDto data);

}
