package ca.ulaval.glo4003.domain.tickets;

import org.joda.time.DateTime;

import ca.ulaval.glo4003.domain.dtos.TicketDto;
import ca.ulaval.glo4003.domain.pojos.persistable.Persistable;
import ca.ulaval.glo4003.domain.tickets.state.TicketAssignationState;

public abstract class PersistableTicket implements Ticket, Persistable<TicketDto> {

	private TicketAssignationState associationState;

	public PersistableTicket(TicketAssignationState associationState) {
		this.associationState = associationState;
	}

	@Override
	public boolean isAssignable() {
		return associationState.isAssignable();
	}

	@Override
	public void assign(String sport, DateTime date, int ticketNumber) {
		associationState = associationState.assign(sport, date, ticketNumber);
	}

	@Override
	public TicketDto saveDataInDTO() {
		// TODO Auto-generated method stub
		return null;
	}

}
