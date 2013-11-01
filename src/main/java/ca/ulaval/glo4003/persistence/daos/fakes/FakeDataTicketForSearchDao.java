package ca.ulaval.glo4003.persistence.daos.fakes;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import ca.ulaval.glo4003.domain.dtos.TicketForSearchDto;
import ca.ulaval.glo4003.persistence.daos.TicketForSearchDao;
import ca.ulaval.glo4003.web.viewmodels.TicketSearchViewModel;


@Repository
public class FakeDataTicketForSearchDao implements TicketForSearchDao {
	
	@Override
	public List<TicketForSearchDto> getTickets(TicketSearchViewModel ticketSearchVM) {
		TicketForSearchDto t1 = new TicketForSearchDto("Baseball Masculin", "Radiants", DateTime.now(),
				"Générale", "Générale", new Integer(3), new Double(16.95), "/sport/volleyball-feminin/match/40/billets/generale-generale");
		TicketForSearchDto t2 = new TicketForSearchDto("Soccer Masculin", "Endormis", DateTime.now(),
				"Générale", "Générale", new Integer(22), new Double(23.95), "/sport/volleyball-feminin/match/40/billets/generale-generale");
		TicketForSearchDto t3 = new TicketForSearchDto("Volleyball Féminin", "Kira", DateTime.now(),
				"VIP", "Orange", new Integer(7), new Double(16.95), "/sport/volleyball-feminin/match/1/billets/vip-front-row");
		TicketForSearchDto t4 = new TicketForSearchDto("Volleyball Féminin", "Kira", DateTime.now(),
				"VIP", "Rouge", new Integer(4), new Double(17.95), "/sport/volleyball-feminin/match/1/billets/vip-front-row");
		List<TicketForSearchDto> tickets = new ArrayList<>();
		tickets.add(t1);
		tickets.add(t2);
		tickets.add(t3);
		tickets.add(t4);
		return tickets;
	}

}
