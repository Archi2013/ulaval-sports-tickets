package ca.ulaval.glo4003.domain.sports;

import ca.ulaval.glo4003.exceptions.SportDoesntExistException;
import ca.ulaval.glo4003.utilities.repositories.Repository;

public interface ISportRepository extends Repository {

	Sport get(String sportName) throws SportDoesntExistException;
}
