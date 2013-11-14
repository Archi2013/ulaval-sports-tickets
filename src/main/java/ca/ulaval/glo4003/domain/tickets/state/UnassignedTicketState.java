package ca.ulaval.glo4003.domain.tickets.state;

import org.joda.time.DateTime;

import ca.ulaval.glo4003.domain.dtos.TicketDto;

public class UnassignedTicketState implements TicketAssignationState {

	@Override
	public boolean isAssignable() {
		return true;
	}

	@Override
	public TicketAssignationState assign(String sport, DateTime date, long ticketNumber) {
		return new AssignedTicketState(sport, date, ticketNumber);
	}

	@Override
	public void fillDataInDto(TicketDto data) {
	}

}
