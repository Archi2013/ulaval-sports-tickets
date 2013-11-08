package ca.ulaval.glo4003.domain.repositories;

import java.util.List;

import org.joda.time.DateTime;

import ca.ulaval.glo4003.domain.tickets.Ticket;

public interface ITicketRepository {

	Ticket instantiateNewTicket();

	Ticket instantiateNewTicket(String section, String seat);

	Ticket recoverTicket(String sport, DateTime Date, int ticketNumber);

	List<Ticket> recoverAllTicketsForGame(String sport, DateTime Date);

	void commit();

}
