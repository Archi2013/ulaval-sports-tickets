package ca.ulaval.glo4003.repositories;

import ca.ulaval.glo4003.dto.SportDto;
import ca.ulaval.glo4003.factories.SportFactory;
import ca.ulaval.glo4003.persistence.dao.SportDao;
import ca.ulaval.glo4003.pojos.Sport;

public class SportRepository implements ISportRepository {

	private SportDao sportDao;
	private SportFactory sportFactory;

	public SportRepository(SportDao sportDao, SportFactory sportFactory) {
		this.sportDao = sportDao;
		this.sportFactory = sportFactory;
	}

	@Override
	public Sport getSportByName(String sportName) {
		SportDto dto = sportDao.get(sportName);

		return sportFactory.instantiateSport(dto.getName());
	}

}
