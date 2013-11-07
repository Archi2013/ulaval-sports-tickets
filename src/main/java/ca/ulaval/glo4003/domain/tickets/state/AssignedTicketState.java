package ca.ulaval.glo4003.domain.tickets.state;

import org.joda.time.DateTime;

public class AssignedTicketState implements TicketAssignationState {

	public AssignedTicketState(String sport, DateTime date, int ticketNumber) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isAssignable() {
		return false;
	}

	@Override
	public TicketAssignationState assign(String sport, DateTime date, int ticketNumber) {
		return this;
	}

}
