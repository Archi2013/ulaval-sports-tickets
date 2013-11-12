package ca.ulaval.glo4003.domain.tickets;

import org.joda.time.DateTime;

public abstract class Ticket {

	protected boolean available;

	public abstract boolean isSame(Ticket ticketToAdd);

	public abstract boolean isAssignable();

	public abstract void assign(String sport, DateTime date, int ticketNumber);

	public abstract boolean isSeat(String seat);

	public abstract boolean isSection(String section);

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
