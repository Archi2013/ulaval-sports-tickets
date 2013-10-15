package ca.ulaval.glo4003.domain.repositories;

import java.util.ArrayList;
import java.util.List;

import ca.ulaval.glo4003.domain.dtos.GameDto;
import ca.ulaval.glo4003.domain.factories.IGameFactory;
import ca.ulaval.glo4003.domain.pojos.Game;
import ca.ulaval.glo4003.persistence.daos.GameDao;
import ca.ulaval.glo4003.persistence.daos.SportDoesntExistException;

public class GameRepository implements IGameRepository {

	private GameDao gameDao;
	private IGameFactory gameFactory;

	public GameRepository(GameDao gameDao, IGameFactory gameFactory) {
		this.gameDao = gameDao;
		this.gameFactory = gameFactory;
	}

	@Override
	public List<Game> getGamesScheduledForSport(String sportName) throws SportDoesntExistException {
		List<GameDto> gameDtos = gameDao.getGamesForSport(sportName);
		List<Game> games = new ArrayList<>();
		for (GameDto dto : gameDtos) {
			Game newGame = gameFactory.instantiateGame(dto.getOpponents(), dto.getGameDate());
			games.add(newGame);
		}
		return games;
	}

}
