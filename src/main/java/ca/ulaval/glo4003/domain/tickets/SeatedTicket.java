package ca.ulaval.glo4003.domain.tickets;

import ca.ulaval.glo4003.domain.tickets.state.TicketAssignationState;

public class SeatedTicket extends PersistableTicket {

	private String seat;
	private String section;

	public SeatedTicket(String seat, String section, TicketAssignationState associationState) {
		super(associationState);
		this.seat = seat;
		this.section = section;
	}

	@Override
	public boolean isSame(Ticket ticketToAdd) {
		return ticketToAdd.isSeat(seat) && ticketToAdd.isSection(section);
	}

	@Override
	public boolean isSeat(String seat) {
		return this.seat.equals(seat);
	}

	@Override
	public boolean isSection(String section) {
		return this.section.equals(section);
	}

}
