package ca.ulaval.glo4003.domain.tickets;

import org.joda.time.DateTime;

public class UnassignedTicketState implements TicketAssignationState {

	public UnassignedTicketState() {
		System.out.println("UnassignedTicketState: initiation");
	}

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
