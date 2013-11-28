package ca.ulaval.glo4003.services;

import javax.inject.Inject;

import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import ca.ulaval.glo4003.domain.game.Game;
import ca.ulaval.glo4003.domain.game.IGameRepository;
import ca.ulaval.glo4003.domain.sports.ISportRepository;
import ca.ulaval.glo4003.domain.sports.Sport;
import ca.ulaval.glo4003.exceptions.GameAlreadyExistException;
import ca.ulaval.glo4003.exceptions.GameDoesntExistException;
import ca.ulaval.glo4003.exceptions.NoSportForUrlException;
import ca.ulaval.glo4003.exceptions.SportDoesntExistException;
import ca.ulaval.glo4003.exceptions.TicketAlreadyExistsException;
import ca.ulaval.glo4003.exceptions.TicketDoesntExistException;

@Service
public class CommandGameService {

	@Inject
	private IGameRepository gameRepository;
	@Inject
	private ISportRepository sportRepository;

	public void createNewGame(String sportName, String opponent, String location, DateTime date)
			throws SportDoesntExistException, GameDoesntExistException, GameAlreadyExistException,
			NoSportForUrlException, TicketAlreadyExistsException, TicketDoesntExistException {

		Game game = gameRepository.create(opponent, location);
		Sport sport = sportRepository.get(sportName);

		sport.addGameToCalendar(game, date);

		// gameRepository.commit();
		sportRepository.commit();
		sportRepository.clearCache();
	}

}
