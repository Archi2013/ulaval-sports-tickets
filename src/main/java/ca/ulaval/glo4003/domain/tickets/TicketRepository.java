package ca.ulaval.glo4003.domain.tickets;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import ca.ulaval.glo4003.exceptions.GameDoesntExistException;
import ca.ulaval.glo4003.exceptions.TicketAlreadyExistsException;
import ca.ulaval.glo4003.exceptions.TicketDoesntExistException;
import ca.ulaval.glo4003.utilities.persistence.Persistable;

@Repository
public class TicketRepository implements ITicketRepository {

	@Inject
	private TicketFactory factory;

	@Inject
	private TicketDao dao;

	private List<Persistable<TicketDto>> newTickets = new ArrayList<>();
	private List<Persistable<TicketDto>> ticketsInDao = new ArrayList<>();

	@Override
	public List<Ticket> getAll(String sportName, DateTime gameDate) {
		List<Ticket> ticketsToReturn = new ArrayList<>();
		List<TicketDto> datas = dao.getAll(sportName, gameDate);
		for (TicketDto data : datas) {
			Ticket recoveredTicket = factory.createTicket(data);
			ticketsInDao.add(recoveredTicket);

			ticketsToReturn.add(recoveredTicket);
		}
		return ticketsToReturn;
	}

	@Override
	public Ticket get(String sportName, DateTime gameDate, String section, String seat) throws TicketDoesntExistException {
		TicketDto data = dao.get(sportName, gameDate, section, seat);
		Ticket recoveredTicket = factory.createTicket(data);
		ticketsInDao.add(recoveredTicket);
		return recoveredTicket;
	}

	@Override
	public Ticket createGeneralTicket(double price, boolean available) {
		Ticket newTicket = factory.createGeneralTicket(price, available);
		newTickets.add(newTicket);
		return newTicket;
	}

	@Override
	public Ticket createSeatedTicket(String seat, String section, double price, boolean available) {
		Ticket newTicket = factory.createSeatedTicket(section, seat, price, available);
		newTickets.add(newTicket);
		return newTicket;
	}

	@Override
	public void commit() throws TicketAlreadyExistsException, TicketDoesntExistException, GameDoesntExistException {
		saveChangesToOldTickets();
		persistsNewTickets();
		dao.commit();
	}

	private void saveChangesToOldTickets() throws TicketDoesntExistException {
		for (Persistable<TicketDto> ticket : ticketsInDao) {
			TicketDto dto = ticket.saveDataInDTO();
			dao.update(dto);
		}
	}

	private void persistsNewTickets() throws TicketAlreadyExistsException, GameDoesntExistException {
		for (Persistable<TicketDto> ticket : newTickets) {
			dao.add(ticket.saveDataInDTO());
			ticketsInDao.add(ticket);
		}
		newTickets.clear();
	}

	@Override
	public void clearCache() {
		ticketsInDao.clear();
		newTickets.clear();

	}

	@Override
	public List<Ticket> getMultipleGeneralTickets(String sportName, DateTime gameDate, int numberOfSeats)
			throws GameDoesntExistException {
		List<TicketDto> availableTickets = dao.getAllAvailable(sportName, gameDate);

		List<Ticket> ticketsToReturn = new ArrayList<>();
		for (TicketDto ticket : availableTickets) {
			Ticket newTicket = factory.createTicket(ticket);
			ticketsInDao.add(newTicket);
			ticketsToReturn.add(newTicket);
		}
		return ticketsToReturn.subList(0, numberOfSeats);
	}

	// Tests only
	public List<Persistable<TicketDto>> getNewTickets() {
		return newTickets;
	}

	public List<Persistable<TicketDto>> getTicketsInDao() {
		return ticketsInDao;
	}

}
