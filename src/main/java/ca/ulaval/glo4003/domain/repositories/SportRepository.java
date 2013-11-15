package ca.ulaval.glo4003.domain.repositories;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Repository;

import ca.ulaval.glo4003.domain.dtos.SportDto;
import ca.ulaval.glo4003.domain.factories.SportFactory;
import ca.ulaval.glo4003.domain.pojos.Game;
import ca.ulaval.glo4003.domain.pojos.Sport;
import ca.ulaval.glo4003.domain.pojos.persistable.Persistable;
import ca.ulaval.glo4003.domain.pojos.persistable.PersistableSport;
import ca.ulaval.glo4003.persistence.daos.GameAlreadyExistException;
import ca.ulaval.glo4003.persistence.daos.GameDoesntExistException;
import ca.ulaval.glo4003.persistence.daos.SportAlreadyExistException;
import ca.ulaval.glo4003.persistence.daos.SportDao;
import ca.ulaval.glo4003.persistence.daos.SportDoesntExistException;
import ca.ulaval.glo4003.persistence.daos.TicketAlreadyExistException;
import ca.ulaval.glo4003.persistence.daos.TicketDoesntExistException;

@Repository
public class SportRepository implements ISportRepository {

	@Inject
	private SportDao sportDao;
	@Inject
	private SportFactory sportFactory;
	@Inject
	private IGameRepository gameRepository;

	private List<Persistable<SportDto>> activeSports = new ArrayList<Persistable<SportDto>>();

	@Override
	public Sport getSportByName(String sportName) throws SportDoesntExistException {
		SportDto dto = sportDao.get(sportName);
		List<Game> gameList = gameRepository.recoverAllGamesForSport(sportName);
		PersistableSport sport = sportFactory.instantiateSport(dto.getName(), gameList);

		activeSports.add(sport);
		return sport;
	}

	@Override
	public void commit() throws SportDoesntExistException, GameDoesntExistException, GameAlreadyExistException,
			TicketAlreadyExistException, TicketDoesntExistException {

		for (Persistable<SportDto> sport : activeSports) {
			try {
				sportDao.add(sport.saveDataInDTO());
			} catch (SportAlreadyExistException e) {
				// Sport Already Exist
			}
		}
		sportDao.commit();
		gameRepository.commit();
	}

	@Override
	public void clearCache() {
		activeSports.clear();
		gameRepository.clearCache();

	}

}
