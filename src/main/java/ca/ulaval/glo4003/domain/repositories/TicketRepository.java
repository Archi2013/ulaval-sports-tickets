package ca.ulaval.glo4003.domain.repositories;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import ca.ulaval.glo4003.domain.dtos.TicketDto;
import ca.ulaval.glo4003.domain.factories.TicketFactory;
import ca.ulaval.glo4003.domain.pojos.persistable.Persistable;
import ca.ulaval.glo4003.domain.tickets.Ticket;
import ca.ulaval.glo4003.persistence.daos.GameDoesntExistException;
import ca.ulaval.glo4003.persistence.daos.TicketAlreadyExistException;
import ca.ulaval.glo4003.persistence.daos.TicketDao;
import ca.ulaval.glo4003.persistence.daos.TicketDoesntExistException;

@Repository
public class TicketRepository implements ITicketRepository {

	@Inject
	private TicketFactory factory;

	@Inject
	private TicketDao dao;

	List<Persistable<TicketDto>> newTickets = new ArrayList<>();
	List<Persistable<TicketDto>> ticketsInDao = new ArrayList<>();

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
	public Ticket getWithId(String sport, DateTime date, int ticketNumber) throws TicketDoesntExistException {
		TicketDto data = dao.get(sport, date, ticketNumber);
		Ticket recoveredTicket = factory.createTicket(data);
		ticketsInDao.add(recoveredTicket);
		return recoveredTicket;
	}

	@Override
	public Ticket getWithSeat(String sport, DateTime date, String seat) {
		TicketDto data = dao.get(sport, date, seat);
		Ticket recoveredTicket = factory.createSeatedTicket(data.section, data.seat, data.price, true);
		ticketsInDao.add(recoveredTicket);
		return recoveredTicket;
	}

	@Override
	public List<Ticket> getAll(String sport, DateTime Date) throws GameDoesntExistException {
		List<Ticket> ticketsToReturn = new ArrayList<>();
		List<TicketDto> datas = dao.getTicketsForGame(sport, Date);
		for (TicketDto data : datas) {
			Ticket recoveredTicket = factory.createTicket(data);
			ticketsInDao.add(recoveredTicket);

			ticketsToReturn.add(recoveredTicket);
		}
		return ticketsToReturn;
	}

	@Override
	public void commit() throws TicketAlreadyExistException, TicketDoesntExistException, GameDoesntExistException {
		saveChangesToOldTickets();
		persistsNewTickets();
		dao.commit();
	}

	private void saveChangesToOldTickets() throws TicketDoesntExistException {
		for (Persistable<TicketDto> ticket : ticketsInDao) {
			TicketDto dto = ticket.saveDataInDTO();
			// TODO hack de ticketId
			// dto.ticketId = dto.ticketNumber;
			dao.update(dto);
		}
	}

	private void persistsNewTickets() throws TicketAlreadyExistException, GameDoesntExistException {
		for (Persistable<TicketDto> ticket : newTickets) {
			dao.add(ticket.saveDataInDTO());
			ticketsInDao.add(ticket);
		}
		newTickets.clear();
	}

	@Override
	public List<Ticket> recoverNGeneralTickets(String sportName, DateTime gameDate, int numberOfSeats)
			throws GameDoesntExistException {
		List<TicketDto> availableTickets = dao.getAvailableTicketsForGame(sportName, gameDate);

		List<Ticket> ticketsToReturn = new ArrayList<>();
		for (TicketDto ticket : availableTickets) {
			// TODO ticketId à la place de ticketNumber...
			Ticket newTicket = factory.createTicket(ticket);
			newTicket.assign(ticket.sportName, ticket.gameDate, ticket.ticketId);
			ticketsInDao.add(newTicket);
			ticketsToReturn.add(newTicket);
		}
		return ticketsToReturn.subList(0, numberOfSeats);
	}

	@Override
	public void clearCache() {
		ticketsInDao.clear();
		newTickets.clear();

	}
}
