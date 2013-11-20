package ca.ulaval.glo4003.domain.repositories;

import java.util.List;

import org.joda.time.DateTime;

import ca.ulaval.glo4003.domain.tickets.Ticket;
import ca.ulaval.glo4003.persistence.daos.GameDoesntExistException;
import ca.ulaval.glo4003.persistence.daos.TicketAlreadyExistException;
import ca.ulaval.glo4003.persistence.daos.TicketDoesntExistException;

public interface ITicketRepository {

	@Deprecated
	Ticket instantiateNewTicket(double price);

	@Deprecated
	Ticket instantiateNewTicket(String section, String seat, double price, boolean available);

	Ticket createGeneralTicket(double price, boolean available);

	Ticket createSeatedTicket(String section, String seat, double price, boolean available);

	Ticket getWithId(String sport, DateTime Date, int ticketNumber) throws TicketDoesntExistException;

	Ticket getWithSeat(String sportName, DateTime gameDate, String seat);

	List<Ticket> getAll(String sportName, DateTime gameDate) throws GameDoesntExistException;

	void commit() throws TicketAlreadyExistException, TicketDoesntExistException, GameDoesntExistException;

	void clearCache();

	@Deprecated
	List<Ticket> recoverNGeneralTickets(String sportName, DateTime gameDate, int numberOfSeats) throws GameDoesntExistException;

}
