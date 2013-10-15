package ca.ulaval.glo4003.domain.repositories;

import java.util.List;

import ca.ulaval.glo4003.domain.dtos.SportDto;
import ca.ulaval.glo4003.domain.factories.SportFactory;
import ca.ulaval.glo4003.domain.pojos.persistable.PersistableGame;
import ca.ulaval.glo4003.domain.pojos.persistable.PersistableSport;
import ca.ulaval.glo4003.persistence.daos.SportDao;
import ca.ulaval.glo4003.persistence.daos.SportDoesntExistException;

public class SportRepository implements ISportRepository {

	private SportDao sportDao;
	private SportFactory sportFactory;
	private IGameRepository gameRepository;

	public SportRepository(SportDao sportDao, SportFactory sportFactory, IGameRepository gameRepository) {
		this.sportDao = sportDao;
		this.sportFactory = sportFactory;
		this.gameRepository = gameRepository;
	}

	@Override
	public PersistableSport getSportByName(String sportName) throws SportDoesntExistException {
		SportDto dto = sportDao.get(sportName);
		List<PersistableGame> gameList = gameRepository.getGamesScheduledForSport(sportName);

		return sportFactory.instantiateSport(dto.getName(), gameList);
	}

}
