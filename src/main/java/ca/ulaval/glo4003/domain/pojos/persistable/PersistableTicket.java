package ca.ulaval.glo4003.domain.pojos.persistable;

import ca.ulaval.glo4003.domain.dtos.TicketDto;
import ca.ulaval.glo4003.domain.pojos.Ticket;

public class PersistableTicket implements Ticket, Persistable<TicketDto> {

	@Override
	public TicketDto saveDataInDTO() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isTheSame(Ticket ticketToAdd) {
		// TODO Auto-generated method stub
		return false;
	}

}
