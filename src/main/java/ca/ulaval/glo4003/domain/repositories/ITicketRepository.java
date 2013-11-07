package ca.ulaval.glo4003.domain.repositories;

import ca.ulaval.glo4003.domain.tickets.Ticket;

public interface ITicketRepository {

	Ticket instantiateNewTicket();

	Ticket instantiateNewTicket(String section, String seat);

}
