package ca.ulaval.glo4003.domain.services;

import javax.inject.Inject;

import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import ca.ulaval.glo4003.domain.pojos.Game;
import ca.ulaval.glo4003.domain.pojos.Sport;
import ca.ulaval.glo4003.domain.repositories.IGameRepository;
import ca.ulaval.glo4003.domain.repositories.ISportRepository;
import ca.ulaval.glo4003.domain.utilities.NoSportForUrlException;
import ca.ulaval.glo4003.domain.utilities.SportDoesntExistInPropertiesFileException;
import ca.ulaval.glo4003.domain.utilities.SportUrlMapper;
import ca.ulaval.glo4003.persistence.daos.GameAlreadyExistException;
import ca.ulaval.glo4003.persistence.daos.GameDoesntExistException;
import ca.ulaval.glo4003.persistence.daos.SportDoesntExistException;

@Service
public class CommandGameService {

	@Inject
	private IGameRepository gameRepository;
	@Inject
	private ISportRepository sportRepository;
	@Inject
	private SportUrlMapper sportUrlMapper;

	public void createNewGame(String sportName, String opponent, DateTime date) throws SportDoesntExistException,
			GameDoesntExistException, GameAlreadyExistException, SportDoesntExistInPropertiesFileException, NoSportForUrlException {

		String domainSportName = sportUrlMapper.getSportName(sportName);
		Game game = gameRepository.createNewGameInRepository(opponent, date);
		Sport sport = sportRepository.getSportByName(domainSportName);

		sport.addGameToCalendar(game);

		gameRepository.commit();
		sportRepository.commit();
	}

}
