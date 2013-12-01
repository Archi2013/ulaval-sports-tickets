package ca.ulaval.glo4003.domain.tickets;

public class SeatedTicket extends Ticket {

	private String seat;
	private String section;

	public SeatedTicket(String seat, String section, double price, boolean availability,
			TicketAssignationState associationState) {
		super(associationState, price, availability);
		this.seat = seat;
		this.section = section;
	}

	@Override
	public boolean isSame(Ticket ticketToAdd) {
		return ticketToAdd.hasSeat(seat) && ticketToAdd.hasSection(section);
	}

	@Override
	public boolean hasSeat(String seat) {
		return this.seat.equals(seat);
	}

	@Override
	public boolean hasSection(String section) {
		return this.section.equals(section);
	}

	@Override
	public TicketDto saveDataInDTO() {
		TicketDto data = new SeatedTicketDto(null, null, null, section, seat, price, available);
		assignationState.fillDataInDto(data);
		return data;

	}

}
