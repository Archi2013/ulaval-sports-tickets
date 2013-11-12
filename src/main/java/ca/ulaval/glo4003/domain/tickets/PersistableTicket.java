package ca.ulaval.glo4003.domain.tickets;

import org.joda.time.DateTime;

import ca.ulaval.glo4003.domain.dtos.TicketDto;
import ca.ulaval.glo4003.domain.pojos.persistable.Persistable;
import ca.ulaval.glo4003.domain.tickets.state.TicketAssignationState;

public abstract class PersistableTicket extends Ticket implements Persistable<TicketDto> {

	private TicketAssignationState associationState;
	public double price;
	public String section;
	public String seat;
	public String sportName;
	public DateTime gameDate;
	public int ticketNumber;
	public boolean available;

	public PersistableTicket(TicketAssignationState associationState) {
		this.associationState = associationState;
		this.available = true;
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
		return new TicketDto(sportName, gameDate, ticketNumber, price, seat, section, available);
	}
}
