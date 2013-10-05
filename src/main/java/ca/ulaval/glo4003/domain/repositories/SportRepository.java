package ca.ulaval.glo4003.domain.repositories;

import ca.ulaval.glo4003.domain.dtos.SportDto;
import ca.ulaval.glo4003.domain.factories.SportFactory;
import ca.ulaval.glo4003.domain.pojos.Sport;
import ca.ulaval.glo4003.persistence.dao.SportDao;

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
