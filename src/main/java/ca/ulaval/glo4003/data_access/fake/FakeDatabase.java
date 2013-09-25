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
		GameDto firstGame = new GameDto(1, "Carabins", DateTime.now());
		firstGame.getTickets().addAll(createGeneralAdmissionTickets(firstGame, 15.50, "Bleus", 2));
		firstGame.getTickets().addAll(createGeneralAdmissionTickets(firstGame, 20.00, "Rouges", 5));
		firstGame.getTickets().add(new TicketDto(firstGame, ticketid++, 35.00, "VIP", "Front Row"));
		firstGame.getTickets().add(new TicketDto(firstGame, ticketid++, 35.00, "VIP", "Front Row"));

		GameDto secondGame = new GameDto(2, "Redmen", DateTime.now());
		secondGame.getTickets().addAll(createGeneralAdmissionTickets(secondGame, 15.50, "Bleus", 0));
		secondGame.getTickets().addAll(createGeneralAdmissionTickets(secondGame, 20.00, "Rouges", 3));
		secondGame.getTickets().add(new TicketDto(secondGame, ticketid++, 35.00, "VIP", "Front Row"));
		secondGame.getTickets().add(new TicketDto(secondGame, ticketid++, 35.00, "VIP", "Front Row"));
		secondGame.getTickets().add(new TicketDto(secondGame, ticketid++, 35.00, "VIP", "Front Row"));

		List<GameDto> games = new ArrayList<GameDto>();
		games.add(firstGame);
		games.add(secondGame);

		return games;
	}

	private List<TicketDto> createGeneralAdmissionTickets(GameDto game, double price, String section, int number) {
		List<TicketDto> tickets = new ArrayList<TicketDto>();
		for (int i = 0; i < number; i++) {
			TicketDto fakeTicket = new TicketDto(game, ticketid, price, "General", section);
			tickets.add(fakeTicket);
			ticketid++;
		}
		return tickets;
	}
}
