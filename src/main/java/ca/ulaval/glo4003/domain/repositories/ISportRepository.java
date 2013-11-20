package ca.ulaval.glo4003.domain.repositories;

import ca.ulaval.glo4003.domain.pojos.Sport;
import ca.ulaval.glo4003.persistence.daos.SportDoesntExistException;

public interface ISportRepository extends Repository {

	Sport get(String sportName) throws SportDoesntExistException;
}
