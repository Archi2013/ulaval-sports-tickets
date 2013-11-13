package ca.ulaval.glo4003.domain.repositories;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import ca.ulaval.glo4003.domain.dtos.TicketDto;
import ca.ulaval.glo4003.domain.factories.TicketFactory;
import ca.ulaval.glo4003.domain.pojos.persistable.Persistable;
import ca.ulaval.glo4003.domain.tickets.PersistableTicket;
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
	List<Persistable<TicketDto>> oldTickets = new ArrayList<>();

	// TODO règle le bogue. À supprimer plus tard.
	public TicketRepository() {

	}

	public TicketRepository(TicketFactory factory, TicketDao dao) {
		this.factory = factory;
		this.dao = dao;
	}

	@Override
	public Ticket instantiateNewTicket() {
		PersistableTicket newTicket = factory.instantiateTicket();
		newTickets.add(newTicket);
		return newTicket;
	}

	@Override
	public Ticket instantiateNewTicket(String seat, String section, boolean available) {
		PersistableTicket newTicket = factory.instantiateTicket(seat, section, available);
		newTickets.add(newTicket);
		return newTicket;
	}

	@Override
	public Ticket recoverTicket(String sport, DateTime date, int ticketNumber) throws TicketDoesntExistException {
		TicketDto data = dao.get(sport, date, ticketNumber);
		PersistableTicket oldTicket = factory.instantiateTicket(data);
		oldTickets.add(oldTicket);
		return oldTicket;
	}

	@Override
	public Ticket recoverTicket(String sport, DateTime date, String seat) {
		TicketDto data = dao.get(sport, date, seat);
		PersistableTicket oldTicket = factory.instantiateTicket(data.getSeat(), data.getSection(), true);
		oldTickets.add(oldTicket);
		return oldTicket;
	}

	@Override
	public List<Ticket> recoverAllTicketsForGame(String sport, DateTime Date) throws GameDoesntExistException {
		List<Ticket> ticketsToReturn = new ArrayList<>();

		List<TicketDto> datas = dao.getTicketsForGame(sport, Date);
		for (TicketDto data : datas) {
			PersistableTicket newTicket = factory.instantiateTicket(data);
			oldTickets.add(newTicket);

			ticketsToReturn.add(newTicket);
		}
		return ticketsToReturn;
	}

	@Override
	public void commit() throws TicketAlreadyExistException, TicketDoesntExistException {
		saveChangesToOldTickets();
		persistsNewTickets();
		dao.endTransaction();
	}

	private void persistsNewTickets() throws TicketAlreadyExistException {
		for (Persistable<TicketDto> ticket : newTickets) {
			dao.add(ticket.saveDataInDTO());
			oldTickets.add(ticket);
		}
		newTickets.clear();
	}

	private void saveChangesToOldTickets() throws TicketDoesntExistException {
		for (Persistable<TicketDto> ticket : oldTickets) {
			dao.update(ticket.saveDataInDTO());
		}
		dao.commit();
	}

	@Override
	public List<Ticket> recoverNGeneralTickets(Long gameId, int numberOfSeats) throws GameDoesntExistException {
		List<TicketDto> availableTickets = dao.getAvailableTicketsForGame(gameId);

		List<Ticket> ticketsToReturn = new ArrayList<>();
		for (TicketDto ticket : availableTickets) {
			PersistableTicket newTicket = factory.instantiateTicket(ticket);
			oldTickets.add(newTicket);
			ticketsToReturn.add(newTicket);
		}
		return ticketsToReturn.subList(0, numberOfSeats);
	}

}
