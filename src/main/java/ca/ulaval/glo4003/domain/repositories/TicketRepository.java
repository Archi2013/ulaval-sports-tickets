package ca.ulaval.glo4003.domain.repositories;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import ca.ulaval.glo4003.domain.dtos.TicketDto;
import ca.ulaval.glo4003.domain.factories.TicketFactory;
import ca.ulaval.glo4003.domain.pojos.persistable.Persistable;
import ca.ulaval.glo4003.domain.tickets.PersistableTicket;
import ca.ulaval.glo4003.domain.tickets.Ticket;
import ca.ulaval.glo4003.persistence.daos.TicketAlreadyExistException;
import ca.ulaval.glo4003.persistence.daos.TicketDao;

@Repository
public class TicketRepository implements ITicketRepository {

	private TicketFactory factory;
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
	public Ticket instantiateNewTicket(String seat, String section) {
		PersistableTicket newTicket = factory.instantiateTicket(seat, section);
		newTickets.add(newTicket);
		return newTicket;
	}

	@Override
	public Ticket recoverTicket(String sport, DateTime date, int ticketNumber) {
		TicketDto data = dao.get(sport, date, ticketNumber);
		PersistableTicket oldTicket = factory.instantiateTicket(data);
		oldTickets.add(oldTicket);
		return oldTicket;
	}

	@Override
	public List<Ticket> recoverAllTicketsForGame(String sport, DateTime Date) {
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
	public void commit() throws TicketAlreadyExistException {
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

	private void saveChangesToOldTickets() {
		for (Persistable<TicketDto> ticket : oldTickets) {
			dao.saveChanges(ticket.saveDataInDTO());
		}
	}

}
