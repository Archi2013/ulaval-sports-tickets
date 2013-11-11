package ca.ulaval.glo4003.domain.repositories;

import ca.ulaval.glo4003.domain.pojos.Sport;
import ca.ulaval.glo4003.persistence.daos.GameAlreadyExistException;
import ca.ulaval.glo4003.persistence.daos.GameDoesntExistException;
import ca.ulaval.glo4003.persistence.daos.SportDoesntExistException;
import ca.ulaval.glo4003.persistence.daos.TicketAlreadyExistException;

public interface ISportRepository {

	Sport getSportByName(String sportName) throws SportDoesntExistException;

	void commit() throws SportDoesntExistException, GameDoesntExistException, GameAlreadyExistException,
			TicketAlreadyExistException;
}
