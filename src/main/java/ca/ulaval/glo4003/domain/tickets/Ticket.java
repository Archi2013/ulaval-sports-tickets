package ca.ulaval.glo4003.domain.tickets;

import org.joda.time.DateTime;

import ca.ulaval.glo4003.utilities.persistence.Persistable;

public abstract class Ticket implements Persistable<TicketDto> {

	protected TicketAssignationState assignationState;
	protected double price;
	protected boolean available;

	public Ticket(TicketAssignationState assignationState, double price, boolean available) {
		this.assignationState = assignationState;
		this.price = price;
		this.available = available;
	}

	public abstract boolean isSame(Ticket ticketToAdd);

	public abstract boolean hasSeat(String seat);

	public abstract boolean hasSection(String section);

	public boolean isAssignable() {
		return assignationState.isAssignable();
	}

	public void assign(String sport, DateTime date, long ticketNumber) {
		assignationState = assignationState.assign(sport, date, ticketNumber);
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

	TicketAssignationState getAssignationState() {
		return assignationState;
	}
}
