package ca.ulaval.glo4003.domain.tickets;

import org.joda.time.DateTime;

import ca.ulaval.glo4003.domain.dtos.TicketDto;
import ca.ulaval.glo4003.domain.pojos.persistable.Persistable;
import ca.ulaval.glo4003.domain.tickets.state.TicketAssignationState;

public abstract class Ticket implements Persistable<TicketDto> {

	public long gameId;
	protected boolean available;
	protected TicketAssignationState associationState;
	protected double price;

	public Ticket(TicketAssignationState associationState, double price) {
		this.associationState = associationState;
		this.price = price;
		this.available = true;
	}

	public abstract boolean isSame(Ticket ticketToAdd);

	public boolean isAssignable() {
		return associationState.isAssignable();
	}

	public void assign(String sport, DateTime date, long ticketNumber) {
		associationState = associationState.assign(sport, date, ticketNumber);
	}

	public abstract boolean isSeat(String seat);

	public abstract boolean isSection(String section);

	@Override
	public TicketDto saveDataInDTO() {
		TicketDto data = new TicketDto(null, null, null, null, price, available);
		associationState.fillDataInDto(data);
		return data;

	}

	public boolean isAvailable() {
		return available;
	}

	public void makeUnavailable() {
		this.available = false;
	}

	public void makeAvailable() {
		this.available = true;
	}

	public long getGameId() {
		return gameId;
	}

	public void setGameId(long gameId) {
		this.gameId = gameId;
	}
}
