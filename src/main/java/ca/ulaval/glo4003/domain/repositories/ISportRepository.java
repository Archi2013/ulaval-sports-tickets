package ca.ulaval.glo4003.domain.repositories;

import ca.ulaval.glo4003.domain.pojos.Sport;

public interface ISportRepository {

	Sport getSportByName(String sportName);
}
