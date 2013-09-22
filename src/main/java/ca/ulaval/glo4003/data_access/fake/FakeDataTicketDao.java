package ca.ulaval.glo4003.data_access.fake;

import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import ca.ulaval.glo4003.data_access.TicketDao;
import ca.ulaval.glo4003.dtos.TicketDto;

public class FakeDataTicketDao implements TicketDao {

	@Override
	public List<TicketDto> getTicketsForGame(int gameID) {
		TicketDto ticket1 = new TicketDto(1, 10, "Les Pros", new GregorianCalendar(113, 3, 12), "General", "Bleus");
		TicketDto ticket2 = new TicketDto(1, 10, "Les N00bz", new GregorianCalendar(113, 3, 12), "General", "Rouges");

		List<TicketDto> toReturn = new LinkedList<TicketDto>();
		toReturn.add(ticket1);
		toReturn.add(ticket2);

		return toReturn;
	}

}
