package ca.ulaval.glo4003.domain.tickets;

import org.joda.time.DateTime;

import ca.ulaval.glo4003.domain.dtos.TicketDto;
import ca.ulaval.glo4003.domain.pojos.persistable.Persistable;
import ca.ulaval.glo4003.domain.tickets.state.TicketAssociationState;

public class PersistableTicket implements Ticket, Persistable<TicketDto> {

	private String seat;
	private String section;
	private TicketAssociationState associationState;

	public PersistableTicket(String seat, String section, TicketAssociationState associationState) {
		this.seat = seat;
		this.section = section;
		this.associationState = associationState;
	}

	@Override
	public boolean isSame(Ticket ticketToAdd) {
		return ticketToAdd.isSeat(seat) && ticketToAdd.isSection(section);
	}

	@Override
	public boolean isAssociable() {
		return associationState.isAssociable();
	}

	@Override
	public void associate(String sport, DateTime date, int ticketNumber) {

	}

	public boolean compareSeatAndSection(String aSeat, String aSection) {
		return true;
	}

	@Override
	public boolean isSeat(String seat) {
		return this.seat.equals(seat);
	}

	@Override
	public boolean isSection(String section) {
		return this.section.equals(section);
	}

	@Override
	public TicketDto saveDataInDTO() {
		// TODO Auto-generated method stub
		return null;
	}

}
