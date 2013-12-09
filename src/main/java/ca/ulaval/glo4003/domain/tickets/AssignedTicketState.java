package ca.ulaval.glo4003.domain.tickets;

import org.joda.time.DateTime;

import ca.ulaval.glo4003.tickets.dto.TicketDto;

public class AssignedTicketState implements TicketAssignationState {

	private String sportName;
	private DateTime gameDate;
	private long ticketNumber;

	public AssignedTicketState(String sportName, DateTime gameDate, long ticketNumber) {
		this.sportName = sportName;
		this.gameDate = gameDate;
		this.ticketNumber = ticketNumber;
	}

	@Override
	public boolean isAssignable() {
		return false;
	}

	@Override
	public TicketAssignationState assign(String sport, DateTime date, long ticketNumber) {
		return this;
	}

	@Override
	public void fillDataInDto(TicketDto data) {
		data.sportName = sportName;
		data.gameDate = gameDate;
		data.ticketId = ticketNumber;
	}

}
