package ca.ulaval.glo4003.domain.repositories;

import java.util.List;

import org.joda.time.DateTime;

import ca.ulaval.glo4003.domain.pojos.Game;
import ca.ulaval.glo4003.persistence.daos.GameAlreadyExistException;
import ca.ulaval.glo4003.persistence.daos.GameDoesntExistException;
import ca.ulaval.glo4003.persistence.daos.SportDoesntExistException;

public interface IGameRepository {

	List<Game> getGamesScheduledForSport(String sportName) throws SportDoesntExistException;

	public Game createNewGameInRepository(String opponents, DateTime date);

	public void commit() throws GameDoesntExistException, GameAlreadyExistException;

	Game getGame(String aSport, DateTime a_DATE);

}
