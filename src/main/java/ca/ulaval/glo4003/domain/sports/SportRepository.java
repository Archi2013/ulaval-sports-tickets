package ca.ulaval.glo4003.domain.sports;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Repository;

import ca.ulaval.glo4003.domain.game.Game;
import ca.ulaval.glo4003.domain.game.IGameRepository;
import ca.ulaval.glo4003.exceptions.GameAlreadyExistException;
import ca.ulaval.glo4003.exceptions.GameDoesntExistException;
import ca.ulaval.glo4003.exceptions.SportAlreadyExistException;
import ca.ulaval.glo4003.exceptions.SportDoesntExistException;
import ca.ulaval.glo4003.exceptions.TicketAlreadyExistsException;
import ca.ulaval.glo4003.exceptions.TicketDoesntExistException;
import ca.ulaval.glo4003.sports.dto.SportDto;
import ca.ulaval.glo4003.utilities.persistence.Persistable;

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
	public Sport get(String sportName) throws SportDoesntExistException, GameDoesntExistException {
		SportDto dto = sportDao.get(sportName);
		List<Game> gameList = gameRepository.getAll(sportName);
		PersistableSport sport = sportFactory.instantiateSport(dto.getName(), gameList);

		activeSports.add(sport);
		return sport;
	}

	@Override
	public void commit() throws SportDoesntExistException, GameDoesntExistException, GameAlreadyExistException,
			TicketAlreadyExistsException, TicketDoesntExistException {

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
