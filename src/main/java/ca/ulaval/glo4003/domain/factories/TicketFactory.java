package ca.ulaval.glo4003.domain.factories;

import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.domain.tickets.PersistableTicket;

@Component
public class TicketFactory {

	public PersistableTicket instantiateTicket() {
		return null;
	}

	public PersistableTicket instantiateTicket(String seat, String section) {
		return null;
	}

}
