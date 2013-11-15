package ca.ulaval.glo4003.domain.tickets.state;

import org.joda.time.DateTime;

import ca.ulaval.glo4003.domain.dtos.TicketDto;

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
		System.out.println("UnassignedTicketState: assignation avec donnees: " + sport);
		return new AssignedTicketState(sport, date, ticketNumber);
	}

	@Override
	public void fillDataInDto(TicketDto data) {
	}

}
