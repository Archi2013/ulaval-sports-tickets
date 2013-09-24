package ca.ulaval.glo4003.data_access.fake;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import ca.ulaval.glo4003.data_access.GameDao;
import ca.ulaval.glo4003.dtos.GameDto;
import ca.ulaval.glo4003.dtos.TicketDto;

@Repository
public class FakeDataGameDao implements GameDao {

	@Override
	public List<GameDto> getGamesForSport(String sportName) {
		GameDto firstGame = new GameDto(1);
		firstGame.getTickets().addAll(createFakeTickets(3));
		
		GameDto secondGame = new GameDto(2);
		secondGame.getTickets().addAll(createFakeTickets(4));
		
		List<GameDto> games = new ArrayList<GameDto>();
		games.add(firstGame);
		games.add(secondGame);
		
		return games;
	}
	
	private List<TicketDto> createFakeTickets(int number){
		List<TicketDto> tickets = new ArrayList<TicketDto>();
		
		for (int i = 0; i < number; i++) {
			TicketDto fakeTicket = new TicketDto(number, 1200, "opponents", DateTime.now(), "General", "Section");
		    tickets.add(fakeTicket);
		}
		return tickets;
	}

}
