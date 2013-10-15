package ca.ulaval.glo4003.domain.repositories;

import java.util.List;

import ca.ulaval.glo4003.domain.pojos.persistable.PersistableGame;
import ca.ulaval.glo4003.persistence.daos.SportDoesntExistException;

public interface IGameRepository {

	List<PersistableGame> getGamesScheduledForSport(String sportName) throws SportDoesntExistException;

}
