package ca.ulaval.glo4003.domain.services;

import javax.inject.Inject;

import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import ca.ulaval.glo4003.domain.pojos.Game;
import ca.ulaval.glo4003.domain.pojos.Sport;
import ca.ulaval.glo4003.domain.repositories.IGameRepository;
import ca.ulaval.glo4003.domain.repositories.ISportRepository;
import ca.ulaval.glo4003.domain.utilities.NoSportForUrlException;
import ca.ulaval.glo4003.domain.utilities.SportUrlMapper;
import ca.ulaval.glo4003.persistence.daos.GameAlreadyExistException;
import ca.ulaval.glo4003.persistence.daos.GameDoesntExistException;
import ca.ulaval.glo4003.persistence.daos.SportDoesntExistException;
import ca.ulaval.glo4003.persistence.daos.TicketAlreadyExistException;
import ca.ulaval.glo4003.persistence.daos.TicketDoesntExistException;

@Service
public class CommandGameService {

	@Inject
	private IGameRepository gameRepository;
	@Inject
	private ISportRepository sportRepository;
	@Inject
	private SportUrlMapper sportUrlMapper;

	public void createNewGame(String sportName, String opponent, String location, DateTime date)
			throws SportDoesntExistException, GameDoesntExistException, GameAlreadyExistException,
			NoSportForUrlException, TicketAlreadyExistException, TicketDoesntExistException {

		String domainSportName = sportUrlMapper.getSportName(sportName);
		Game game = gameRepository.instantiateNewGame(opponent, location);
		Sport sport = sportRepository.getSportByName(domainSportName);

		sport.addGameToCalendar(game, date);

		// gameRepository.commit();
		sportRepository.commit();
		sportRepository.clearCache();
	}

}
