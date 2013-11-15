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
	public long gameId;
	public long ticketId;

	public long getTicketId() {
		return ticketId;
	}

	public void setTicketId(long ticketId) {
		this.ticketId = ticketId;
	}

	public long getGameId() {
		return gameId;
	}

	public void setGameId(long gameId) {
		this.gameId = gameId;
	}

	public PersistableTicket(TicketAssignationState associationState, double price) {
		this.associationState = associationState;
		this.price = price;
		this.available = true;
	}

	@Override
	public boolean isAssignable() {
		return associationState.isAssignable();
	}

	@Override
	public void assign(String sport, DateTime date, long ticketNumber) {
		associationState = associationState.assign(sport, date, ticketNumber);
	}

	@Override
	public TicketDto saveDataInDTO() {
		TicketDto data = new TicketDto(null, null, ticketId, price, null, null, available);
		data.gameId = gameId;
		associationState.fillDataInDto(data);
		return data;

	}
}
