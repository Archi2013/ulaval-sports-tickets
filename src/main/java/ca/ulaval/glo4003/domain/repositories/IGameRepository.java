package ca.ulaval.glo4003.domain.repositories;

import java.util.List;

import org.joda.time.DateTime;

import ca.ulaval.glo4003.domain.pojos.Game;
import ca.ulaval.glo4003.persistence.daos.GameAlreadyExistException;
import ca.ulaval.glo4003.persistence.daos.GameDoesntExistException;
import ca.ulaval.glo4003.persistence.daos.SportDoesntExistException;
import ca.ulaval.glo4003.persistence.daos.TicketAlreadyExistException;
import ca.ulaval.glo4003.persistence.daos.TicketDoesntExistException;

public interface IGameRepository {

	List<Game> getAll(String sportName) throws SportDoesntExistException;

	Game get(String sportName, DateTime gameDate) throws GameDoesntExistException;

	public Game create(String opponents, String location);

	public void commit() throws GameDoesntExistException, GameAlreadyExistException, TicketAlreadyExistException,
			TicketDoesntExistException;

	void clearCache();

}
