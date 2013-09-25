package ca.ulaval.glo4003.data_access.fake;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Singleton;

import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import ca.ulaval.glo4003.dtos.GameDto;
import ca.ulaval.glo4003.dtos.SportDto;
import ca.ulaval.glo4003.dtos.TicketDto;

@Service
@Singleton
public class FakeDatabase {

	private static int ticketid = 1;

	private Map<String, SportDto> sports;

	public FakeDatabase() {
		this.sports = createSports();
	}

	public List<SportDto> getSports() {
		return new ArrayList<SportDto>(sports.values());
	}

	public SportDto getSport(String sportName) {
		return sports.get(sportName);
	}

	private Map<String, SportDto> createSports() {
		Map<String, SportDto> sports = new HashMap<String, SportDto>();
		sports.put("Hockey", createHockey());
		return sports;
	}

	private SportDto createHockey() {
		SportDto hockey = new SportDto("Hockey");
		hockey.getGames().addAll(createHockeyGames());
		return hockey;
	}

	private List<GameDto> createHockeyGames() {
		GameDto firstGame = new GameDto(1);
		String firstOpponents = "Carabins";
		DateTime firstDate = DateTime.now();
		firstGame.getTickets().addAll(createGeneralAdmissionTickets(15.50, firstOpponents, firstDate, "Bleus", 2));
		firstGame.getTickets().addAll(createGeneralAdmissionTickets(20.00, firstOpponents, firstDate, "Rouges", 5));
		firstGame.getTickets().add(new TicketDto(ticketid++, 35.00, firstOpponents, firstDate, "VIP", "Front Row"));
		firstGame.getTickets().add(new TicketDto(ticketid++, 35.00, firstOpponents, firstDate, "VIP", "Front Row"));

		GameDto secondGame = new GameDto(2);
		String secondOpponents = "Carabins";
		DateTime secondDate = DateTime.now();
		secondGame.getTickets().addAll(createGeneralAdmissionTickets(15.50, secondOpponents, secondDate, "Bleus", 0));
		secondGame.getTickets().addAll(createGeneralAdmissionTickets(20.00, secondOpponents, secondDate, "Rouges", 3));
		secondGame.getTickets().add(new TicketDto(ticketid++, 35.00, secondOpponents, secondDate, "VIP", "Front Row"));
		secondGame.getTickets().add(new TicketDto(ticketid++, 35.00, secondOpponents, secondDate, "VIP", "Front Row"));
		secondGame.getTickets().add(new TicketDto(ticketid++, 35.00, secondOpponents, secondDate, "VIP", "Front Row"));

		List<GameDto> games = new ArrayList<GameDto>();
		games.add(firstGame);
		games.add(secondGame);

		return games;
	}

	private List<TicketDto> createGeneralAdmissionTickets(double price, String opponents, DateTime date, String section, int number) {
		List<TicketDto> tickets = new ArrayList<TicketDto>();
		for (int i = 0; i < number; i++) {
			TicketDto fakeTicket = new TicketDto(ticketid, price, opponents, date, "General", section);
			tickets.add(fakeTicket);
			ticketid++;
		}
		return tickets;
	}
}
