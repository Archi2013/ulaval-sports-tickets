package ca.ulaval.glo4003.repositories;

import ca.ulaval.glo4003.pojos.Sport;

public interface ISportRepository {

	Sport getSportByName(String sportName);
}
