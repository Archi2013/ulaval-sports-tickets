package ca.ulaval.glo4003.domain.repositories;

import java.util.List;

import org.joda.time.DateTime;

import ca.ulaval.glo4003.domain.tickets.Ticket;
import ca.ulaval.glo4003.persistence.daos.GameDoesntExistException;
import ca.ulaval.glo4003.persistence.daos.TicketDoesntExistException;

public interface ITicketRepository extends Repository {

	List<Ticket> getAll(String sportName, DateTime gameDate) throws GameDoesntExistException;

	Ticket getWithId(String sport, DateTime Date, int ticketNumber) throws TicketDoesntExistException;

	Ticket getWithSeat(String sportName, DateTime gameDate, String seat);

	Ticket createGeneralTicket(double price, boolean available);

	Ticket createSeatedTicket(String section, String seat, double price, boolean available);

	@Deprecated
	List<Ticket> recoverNGeneralTickets(String sportName, DateTime gameDate, int numberOfSeats) throws GameDoesntExistException;

}
