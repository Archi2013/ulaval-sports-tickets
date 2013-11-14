package ca.ulaval.glo4003.domain.repositories;

import java.util.List;

import org.joda.time.DateTime;

import ca.ulaval.glo4003.domain.tickets.Ticket;
import ca.ulaval.glo4003.persistence.daos.GameDoesntExistException;
import ca.ulaval.glo4003.persistence.daos.TicketAlreadyExistException;
import ca.ulaval.glo4003.persistence.daos.TicketDoesntExistException;

public interface ITicketRepository {

	Ticket instantiateNewTicket();

	Ticket instantiateNewTicket(String section, String seat, boolean available);

	Ticket recoverTicket(String sport, DateTime Date, int ticketNumber) throws TicketDoesntExistException;

	Ticket recoverTicket(String sportName, DateTime gameDate, String seat);

	List<Ticket> recoverAllTicketsForGame(String sport, DateTime Date) throws GameDoesntExistException;

	void commit() throws TicketAlreadyExistException, TicketDoesntExistException, GameDoesntExistException;

	List<Ticket> recoverNGeneralTickets(Long id, int numberOfSeats) throws GameDoesntExistException;

}
