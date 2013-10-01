package ca.ulaval.glo4003.repositories;

import ca.ulaval.glo4003.pocos.Sport;

public interface ISportRepository {

	Sport getSportByName(String sportName);
}
