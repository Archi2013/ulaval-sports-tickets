package ca.ulaval.glo4003.domain.tickets.state;

import org.joda.time.DateTime;

public class UnassignedTicketState implements TicketAssignationState {

	@Override
	public boolean isAssignable() {
		return true;
	}

	@Override
	public TicketAssignationState assign(String sport, DateTime date, int ticketNumber) {
		return new AssignedTicketState(sport, date, ticketNumber);
	}

}
