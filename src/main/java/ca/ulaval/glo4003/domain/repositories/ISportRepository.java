package ca.ulaval.glo4003.domain.repositories;

import ca.ulaval.glo4003.domain.pojos.persistable.PersistableSport;
import ca.ulaval.glo4003.persistence.daos.SportDoesntExistException;

public interface ISportRepository {

	PersistableSport getSportByName(String sportName) throws SportDoesntExistException;
}
