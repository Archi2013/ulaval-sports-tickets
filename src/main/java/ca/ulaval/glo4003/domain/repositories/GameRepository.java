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
import ca.ulaval.glo4003.persistence.daos.GameAlreadyExistException;
import ca.ulaval.glo4003.persistence.daos.GameDao;
import ca.ulaval.glo4003.persistence.daos.GameDoesntExistException;
import ca.ulaval.glo4003.persistence.daos.SportDoesntExistException;

@Repository
public class GameRepository implements IGameRepository {

	@Inject
	private GameDao gameDao;
	@Inject
	private IGameFactory gameFactory;

	private List<Persistable<GameDto>> existingActiveGames;
	private List<Persistable<GameDto>> newActiveGames;

	public GameRepository() {
		existingActiveGames = new ArrayList<>();
		newActiveGames = new ArrayList<>();
	}

	@Override
	public List<Game> getGamesScheduledForSport(String sportName) throws SportDoesntExistException {
		List<GameDto> gameDtos = gameDao.getGamesForSport(sportName);
		List<PersistableGame> games = new ArrayList<>();
		for (GameDto dto : gameDtos) {
			PersistableGame newGame = gameFactory.instantiateGame(dto.getOpponents(), dto.getGameDate());
			games.add(newGame);
		}
		existingActiveGames.addAll(games);
		List<Game> gameList = new ArrayList<>();
		gameList.addAll(games);
		return gameList;
	}

	public Game createNewGameInRepository(String opponents, DateTime date) {
		PersistableGame newGame = gameFactory.instantiateGame(opponents, date);
		newActiveGames.add(newGame);
		return newGame;

	}

	public void commit() throws GameDoesntExistException, GameAlreadyExistException {
		for (Persistable<GameDto> game : existingActiveGames) {
			GameDto dto = game.saveDataInDTO();
			gameDao.saveChanges(dto);
		}

		for (Persistable<GameDto> game : newActiveGames) {
			GameDto dto = game.saveDataInDTO();
			gameDao.add(dto);
		}
		existingActiveGames.addAll(newActiveGames);
		newActiveGames.clear();
	}

	@Override
	public Game getGame(String aSport, DateTime a_DATE) {
		// TODO Auto-generated method stub
		return null;
	}

}
