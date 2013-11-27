package ca.ulaval.glo4003.domain.tickets;

import org.joda.time.DateTime;

import ca.ulaval.glo4003.utilities.persistence.Persistable;

public abstract class Ticket implements Persistable<TicketDto> {

	protected TicketAssignationState associationState;
	protected double price;
	protected boolean available;

	public Ticket(TicketAssignationState associationState, double price) {
		this.associationState = associationState;
		this.price = price;
		this.available = true;
	}

	public abstract boolean isSame(Ticket ticketToAdd);

	public abstract boolean hasSeat(String seat);

	public abstract boolean hasSection(String section);

	public boolean isAssignable() {
		return associationState.isAssignable();
	}

	public void assign(String sport, DateTime date, long ticketNumber) {
		associationState = associationState.assign(sport, date, ticketNumber);
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
}
