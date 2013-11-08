package ca.ulaval.glo4003.domain.repositories;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import ca.ulaval.glo4003.domain.dtos.TicketDto;
import ca.ulaval.glo4003.domain.factories.TicketFactory;
import ca.ulaval.glo4003.domain.tickets.Ticket;
import ca.ulaval.glo4003.persistence.daos.TicketDao;

@Repository
public class TicketRepository implements ITicketRepository {

	private TicketFactory factory;
	private TicketDao dao;

	public TicketRepository(TicketFactory factory, TicketDao dao) {
		this.factory = factory;
		this.dao = dao;
	}

	@Override
	public Ticket instantiateNewTicket() {
		return factory.instantiateTicket();
	}

	@Override
	public Ticket instantiateNewTicket(String seat, String section) {
		return factory.instantiateTicket(seat, section);
	}

	@Override
	public Ticket recoverTicket(String sport, DateTime date, int ticketNumber) {
		TicketDto data = dao.get(sport, date, ticketNumber);
		return factory.instantiateTicket(data.getSeat(), data.getSection());
	}

	@Override
	public List<Ticket> recoverAllTicketsForGame(String sport, DateTime Date) {
		List<TicketDto> datas = dao.getTicketsForGame(sport, Date);
		List<Ticket> ticketsToReturn = new ArrayList<>();
		for (TicketDto data : datas) {
			Ticket newTicket = factory.instantiateTicket(data.getSeat(), data.getSection());
			ticketsToReturn.add(newTicket);
		}
		return ticketsToReturn;
	}

	@Override
	public void commit() {
		// TODO Auto-generated method stub

	}

}
