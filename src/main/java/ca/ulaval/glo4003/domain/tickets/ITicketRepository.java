package ca.ulaval.glo4003.domain.tickets;

import java.util.List;

import org.joda.time.DateTime;

import ca.ulaval.glo4003.exceptions.GameDoesntExistException;
import ca.ulaval.glo4003.exceptions.TicketAlreadyExistsException;
import ca.ulaval.glo4003.exceptions.TicketDoesntExistException;
import ca.ulaval.glo4003.utilities.repositories.Repository;

public interface ITicketRepository extends Repository {

	List<Ticket> getAll(String sportName, DateTime gameDate) throws GameDoesntExistException;

	Ticket get(String sportName, DateTime gameDate, String section, String seat) throws TicketDoesntExistException;

	Ticket createGeneralTicket(double price, boolean available);

	Ticket createSeatedTicket(String section, String seat, double price, boolean available) throws TicketAlreadyExistsException;

	List<Ticket> getMultipleGeneralTickets(String sportName, DateTime gameDate, int numberOfSeats)
			throws GameDoesntExistException;

}
