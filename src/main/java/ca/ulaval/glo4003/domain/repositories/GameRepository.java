package ca.ulaval.glo4003.domain.repositories;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import ca.ulaval.glo4003.domain.dtos.GameDto;
import ca.ulaval.glo4003.domain.factories.IGameFactory;
import ca.ulaval.glo4003.domain.pojos.Game;
import ca.ulaval.glo4003.domain.pojos.persistable.Persistable;
import ca.ulaval.glo4003.domain.pojos.persistable.PersistableGame;
import ca.ulaval.glo4003.domain.tickets.Ticket;
import ca.ulaval.glo4003.persistence.daos.GameAlreadyExistException;
import ca.ulaval.glo4003.persistence.daos.GameDao;
import ca.ulaval.glo4003.persistence.daos.GameDoesntExistException;
import ca.ulaval.glo4003.persistence.daos.SportDoesntExistException;
import ca.ulaval.glo4003.persistence.daos.TicketAlreadyExistException;
import ca.ulaval.glo4003.persistence.daos.TicketDoesntExistException;

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

	int numberOfCallsToRepository = 0;

	@Override
	public Game recoverGame(String sport, DateTime date) throws GameDoesntExistException {
		numberOfCallsToRepository++;
		System.out.println("Le repository a ete appele: " + numberOfCallsToRepository);
		GameDto gameDto = gameDao.get(sport, date);
		System.out.println("Repository: location: " + gameDto.getLocation());
		System.out.println("Repository: nextTicketNumber: " + gameDto.getNextTicketNumber());
		System.out.println("Repository: opponents: " + gameDto.getOpponents());
		System.out.println("Repository: sportName" + gameDto.getSportName());
		System.out.println("Repository: id" + gameDto.getId());
		System.out.println("Repository: gameDate" + gameDto.getGameDate().toString());
		List<Ticket> tickets = getTicketsForGame(sport, date);
		PersistableGame game = gameFactory.instantiateGame(gameDto, tickets);

		existingActiveGames.add(game);

		return game;
	}

	@Override
	public List<Game> recoverAllGamesForSport(String sportName) throws SportDoesntExistException {
		numberOfCallsToRepository++;
		System.out.println("Le repository a ete appele: " + numberOfCallsToRepository);
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

	private List<Ticket> getTicketsForGame(String sport, DateTime date) {
		List<Ticket> tickets;
		try {
			tickets = ticketRepository.recoverAllTicketsForGame(sport, date);
		} catch (GameDoesntExistException e) {
			tickets = new ArrayList<>();
		}
		return tickets;
	}

	public Game instantiateNewGame(String opponents, String location) {
		PersistableGame newGame = gameFactory.instantiateGame(opponents, location);
		newActiveGames.add(newGame);
		return newGame;

	}

	public void commit() throws GameDoesntExistException, GameAlreadyExistException, TicketAlreadyExistException,
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
