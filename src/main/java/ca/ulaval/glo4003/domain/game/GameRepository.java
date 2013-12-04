package ca.ulaval.glo4003.domain.game;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import ca.ulaval.glo4003.domain.tickets.Ticket;
import ca.ulaval.glo4003.domain.tickets.TicketRepository;
import ca.ulaval.glo4003.exceptions.GameAlreadyExistException;
import ca.ulaval.glo4003.exceptions.GameDoesntExistException;
import ca.ulaval.glo4003.exceptions.SportDoesntExistException;
import ca.ulaval.glo4003.exceptions.TicketAlreadyExistsException;
import ca.ulaval.glo4003.exceptions.TicketDoesntExistException;
import ca.ulaval.glo4003.utilities.persistence.Persistable;

@Repository
public class GameRepository implements IGameRepository {

	@Inject
	private GameDao gameDao;
	@Inject
	private IGameFactory gameFactory;
	@Inject
	private TicketRepository ticketRepository;

	private List<Persistable<GameDto>> existingActiveGames = new ArrayList<>();
	private List<Persistable<GameDto>> newActiveGames = new ArrayList<>();

	@Override
	public Game get(String sport, DateTime date) throws GameDoesntExistException {
		GameDto gameDto = gameDao.get(sport, date);
		List<Ticket> tickets = getTicketsForGame(sport, date);
		PersistableGame game = gameFactory.instantiateGame(gameDto, tickets);

		existingActiveGames.add(game);

		return game;
	}

	@Override
	public List<Game> getAll(String sportName) throws SportDoesntExistException, GameDoesntExistException {
		List<GameDto> gameDtos = gameDao.getGamesForSport(sportName);
		List<PersistableGame> games = new ArrayList<>();
		for (GameDto dto : gameDtos) {
			List<Ticket> tickets = getTicketsForGame(sportName, dto.getGameDate());
			PersistableGame newGame = gameFactory.instantiateGame(dto, tickets);
			games.add(newGame);
		}
		existingActiveGames.addAll(games);
		List<Game> gameList = new ArrayList<>();
		gameList.addAll(games);
		return gameList;
	}

	private List<Ticket> getTicketsForGame(String sport, DateTime date) throws GameDoesntExistException {
		List<Ticket> tickets;
		tickets = ticketRepository.getAll(sport, date);
		return tickets;
	}

	public Game create(String opponents, String location) {
		PersistableGame newGame = gameFactory.instantiateGame(opponents, location);
		newActiveGames.add(newGame);
		return newGame;

	}

	public void commit() throws GameDoesntExistException, GameAlreadyExistException, TicketAlreadyExistsException,
			TicketDoesntExistException {
		for (Persistable<GameDto> game : existingActiveGames) {
			GameDto dto = game.saveDataInDTO();
			gameDao.update(dto);
		}

		for (Persistable<GameDto> game : newActiveGames) {
			GameDto dto = game.saveDataInDTO();
			gameDao.add(dto);
		}
		gameDao.commit();
		existingActiveGames.addAll(newActiveGames);
		newActiveGames.clear();
		ticketRepository.commit();
	}

	@Override
	public void clearCache() {
		existingActiveGames.clear();
		newActiveGames.clear();
		ticketRepository.clearCache();

	}

}
