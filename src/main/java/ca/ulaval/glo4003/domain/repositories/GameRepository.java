package ca.ulaval.glo4003.domain.repositories;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import ca.ulaval.glo4003.domain.dtos.GameDto;
import ca.ulaval.glo4003.domain.factories.IGameFactory;
import ca.ulaval.glo4003.domain.pojos.Game;
import ca.ulaval.glo4003.persistence.daos.GameAlreadyExistException;
import ca.ulaval.glo4003.persistence.daos.GameDao;
import ca.ulaval.glo4003.persistence.daos.GameDoesntExistException;
import ca.ulaval.glo4003.persistence.daos.SportDoesntExistException;

public class GameRepository implements IGameRepository {

	private GameDao gameDao;
	private IGameFactory gameFactory;
	private List<Game> existingActiveGames;
	private List<Game> newActiveGames;

	public GameRepository(GameDao gameDao, IGameFactory gameFactory) {
		this.gameDao = gameDao;
		this.gameFactory = gameFactory;
		this.existingActiveGames = new ArrayList<>();
		this.newActiveGames = new ArrayList<>();
	}

	@Override
	public List<Game> getGamesScheduledForSport(String sportName) throws SportDoesntExistException {
		List<GameDto> gameDtos = gameDao.getGamesForSport(sportName);
		List<Game> games = new ArrayList<>();
		for (GameDto dto : gameDtos) {
			Game newGame = gameFactory.instantiateGame(dto.getOpponents(), dto.getGameDate());
			games.add(newGame);
		}
		existingActiveGames.addAll(games);
		return games;
	}

	public Game createNewGameInRepository(String opponents, DateTime date) {
		Game newGame = gameFactory.instantiateGame(opponents, date);
		newActiveGames.add(newGame);
		return newGame;

	}

	public void commit() throws GameDoesntExistException, GameAlreadyExistException {
		for (Game game : existingActiveGames) {
			GameDto dto = game.saveDataInDTO();
			gameDao.saveChanges(dto);
		}

		for (Game game : newActiveGames) {
			GameDto dto = game.saveDataInDTO();
			gameDao.add(dto);
		}
	}

}
