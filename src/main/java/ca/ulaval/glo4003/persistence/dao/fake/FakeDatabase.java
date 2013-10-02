package ca.ulaval.glo4003.persistence.dao.fake;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Singleton;

import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import ca.ulaval.glo4003.dto.GameDto;
import ca.ulaval.glo4003.dto.SportDto;
import ca.ulaval.glo4003.dto.TicketDto;
import ca.ulaval.glo4003.persistence.dao.GameDoesntExistException;

@Service
@Singleton
public class FakeDatabase {

	private static int ticketid = 1;

	private Map<String, SportDto> sports;

	public FakeDatabase() {
		this.sports = createSports();
	}

	public GameDto getGame(long id) throws GameDoesntExistException {
		for (Entry<String, SportDto> entry : sports.entrySet()) {
			for (GameDto game : entry.getValue().getGames()) {
				if (game.getId() == id) {
					return game;
				}
			}
		}
		throw new GameDoesntExistException();
	}
	
	public List<SportDto> getSports() {
		return new ArrayList<SportDto>(sports.values());
	}

	public SportDto getSport(String sportName) {
		return sports.get(sportName);
	}

	private Map<String, SportDto> createSports() {
		Map<String, SportDto> sports = new HashMap<String, SportDto>();
		sports.put("Basketball Masculin", createBasketball());
		sports.put("Baseball Masculin", createBaseballWithoutGames());
		sports.put("Volleyball Féminin", createVolleyballFeminin());
		return sports;
	}

	private SportDto createBaseballWithoutGames() {
		SportDto baseball = new SportDto("Baseball Masculin");
		return baseball;
	}

	private SportDto createBasketball() {
		SportDto basketball = new SportDto("Basketball Masculin");
		basketball.getGames().addAll(createBasketballGames());
		return basketball;
	}

	private List<GameDto> createBasketballGames() {
		GameDto firstGame = new GameDto(1, "Carabins", DateTime.now().plusDays(1));
		firstGame.getTickets().addAll(createGeneralAdmissionTickets(firstGame, 15.50, "Bleus", 2));
		firstGame.getTickets().addAll(createGeneralAdmissionTickets(firstGame, 20.00, "Rouges", 5));
		firstGame.getTickets().add(new TicketDto(1, ticketid++, 35.00, "VIP", "Front Row"));
		firstGame.getTickets().add(new TicketDto(1, ticketid++, 35.00, "VIP", "Front Row"));

		GameDto secondGame = new GameDto(2, "Redmen", DateTime.now().plusDays(1));
		secondGame.getTickets().addAll(createGeneralAdmissionTickets(secondGame, 15.50, "Bleus", 0));
		secondGame.getTickets().addAll(createGeneralAdmissionTickets(secondGame, 20.00, "Rouges", 3));
		secondGame.getTickets().add(new TicketDto(2, ticketid++, 35.00, "VIP", "Front Row"));
		secondGame.getTickets().add(new TicketDto(2, ticketid++, 35.00, "VIP", "Front Row"));
		secondGame.getTickets().add(new TicketDto(2, ticketid++, 35.00, "VIP", "Front Row"));

		List<GameDto> games = new ArrayList<GameDto>();
		games.add(firstGame);
		games.add(secondGame);

		return games;
	}

	private SportDto createVolleyballFeminin() {
		SportDto volley = new SportDto("Volleyball Féminin");
		volley.getGames().addAll(createVolleyballFemininGames());
		return volley;
	}

	private List<GameDto> createVolleyballFemininGames() {
		GameDto firstGame = new GameDto(40, "Blue Dragoon", DateTime.now().plusDays(1));
		firstGame.getTickets().addAll(createGeneralAdmissionTickets(firstGame, 5.95, "Indigo", 12));
		firstGame.getTickets().addAll(createGeneralAdmissionTickets(firstGame, 6.95, "Cyan", 52));
		firstGame.getTickets().add(new TicketDto(1, ticketid++, 15.50, "VIP", "Loge Sud-Est"));
		firstGame.getTickets().add(new TicketDto(1, ticketid++, 15.50, "VIP", "Loge Nord-Est"));

		GameDto secondGame = new GameDto(41, "Phoenix rouge", DateTime.now().plusDays(1));
		secondGame.getTickets().addAll(createGeneralAdmissionTickets(secondGame, 25.95, "Pourpres", 10));
		secondGame.getTickets().addAll(createGeneralAdmissionTickets(secondGame, 20.05, "Bordeaux", 8));
		secondGame.getTickets().addAll(createGeneralAdmissionTickets(secondGame, 15.45, "Mauves", 12));
		secondGame.getTickets().add(new TicketDto(2, ticketid++, 30.70, "VIP", "Loge A"));
		secondGame.getTickets().add(new TicketDto(2, ticketid++, 30.70, "VIP", "Loge A"));
		secondGame.getTickets().add(new TicketDto(2, ticketid++, 30.70, "VIP", "Loge B"));
		secondGame.getTickets().add(new TicketDto(2, ticketid++, 30.70, "VIP", "Loge C"));
		secondGame.getTickets().add(new TicketDto(2, ticketid++, 30.70, "VIP", "Loge E"));

		GameDto thirdGame = new GameDto(42, "Emeraude vif", DateTime.now().plusDays(1));
		thirdGame.getTickets().addAll(createGeneralAdmissionTickets(secondGame, 15.95, "Olive", 22));
		thirdGame.getTickets().addAll(createGeneralAdmissionTickets(secondGame, 20.05, "Turquoise", 17));
		thirdGame.getTickets().addAll(createGeneralAdmissionTickets(secondGame, 5.45, "Basilic", 2));
		thirdGame.getTickets().add(new TicketDto(3, ticketid++, 30.70, "VIP", "Loge 3C"));
		thirdGame.getTickets().add(new TicketDto(3, ticketid++, 30.70, "VIP", "Loge 1A"));
		thirdGame.getTickets().add(new TicketDto(3, ticketid++, 30.70, "VIP", "Loge 4D"));
		thirdGame.getTickets().add(new TicketDto(3, ticketid++, 30.70, "VIP", "Loge 1A"));
		thirdGame.getTickets().add(new TicketDto(3, ticketid++, 30.70, "VIP", "Loge 5E"));

		List<GameDto> games = new ArrayList<GameDto>();
		games.add(firstGame);
		games.add(secondGame);
		games.add(thirdGame);

		return games;
	}

	private List<TicketDto> createGeneralAdmissionTickets(GameDto game, double price, String section, int number) {
		List<TicketDto> tickets = new ArrayList<TicketDto>();
		for (int i = 0; i < number; i++) {
			TicketDto fakeTicket = new TicketDto(game.getId(), ticketid, price, "Général", section);
			tickets.add(fakeTicket);
			ticketid++;
		}
		return tickets;
	}
}