package ca.ulaval.glo4003.domain.game;

import java.util.List;

import org.joda.time.DateTime;

import ca.ulaval.glo4003.exceptions.GameDoesntExistException;
import ca.ulaval.glo4003.exceptions.SportDoesntExistException;
import ca.ulaval.glo4003.utilities.repositories.Repository;

public interface IGameRepository extends Repository {

	List<Game> getAll(String sportName) throws SportDoesntExistException;

	Game get(String sportName, DateTime gameDate) throws GameDoesntExistException;

	public Game create(String opponents, String location);

}
