package ca.ulaval.glo4003.domain.tickets.state;

import org.joda.time.DateTime;

import ca.ulaval.glo4003.domain.dtos.TicketDto;

public class AssignedTicketState implements TicketAssignationState {

	private String sportName;
	private DateTime gameDate;
	private long ticketNumber;

	public AssignedTicketState(String sportName, DateTime gameDate, long ticketNumber) {
		System.out.println("AssignedTicketState: initiation avec donnees: " + sportName);
		System.out.println(gameDate.toString());
		System.out.println(ticketNumber);
		System.out.println();
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
		System.out.println("AssignedTicketState: sauvegarde avec donnees: " + sportName);
		System.out.println(gameDate.toString());
		System.out.println(ticketNumber);
		System.out.println();
		data.sportName = sportName;
		data.gameDate = gameDate;
		data.ticketId = ticketNumber;
	}

}
