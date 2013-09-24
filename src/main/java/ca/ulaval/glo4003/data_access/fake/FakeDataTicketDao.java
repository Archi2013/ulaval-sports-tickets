package ca.ulaval.glo4003.data_access.fake;

import java.util.LinkedList;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import ca.ulaval.glo4003.data_access.TicketDao;
import ca.ulaval.glo4003.dtos.TicketDto;

@Repository
public class FakeDataTicketDao implements TicketDao {

	@Override
	public List<TicketDto> getTicketsForGame(int gameID) {
		TicketDto ticket1 = new TicketDto(1, 10, "Les Pros", new DateTime(2013, 9, 29, 18, 30), "General", "Bleus");
		TicketDto ticket2 = new TicketDto(2, 10, "Les N00bz", new DateTime(2013, 9, 30, 18, 30), "General", "Rouges");

		List<TicketDto> toReturn = new LinkedList<TicketDto>();
		toReturn.add(ticket1);
		toReturn.add(ticket2);

		return toReturn;
	}

	@Override
	public TicketDto getTicket(int ticketId) {
		TicketDto ticket = new TicketDto(1, 26.95, "Pharetra", new DateTime(2013, 9, 29, 18, 30), "Général", "Rouge");
		return ticket;
	}
}
