package ca.ulaval.glo4003.domain.services;

import org.joda.time.DateTime;

import ca.ulaval.glo4003.domain.pojos.Game;
import ca.ulaval.glo4003.domain.pojos.Sport;
import ca.ulaval.glo4003.domain.repositories.IGameRepository;
import ca.ulaval.glo4003.domain.repositories.ISportRepository;
import ca.ulaval.glo4003.persistence.daos.GameAlreadyExistException;
import ca.ulaval.glo4003.persistence.daos.GameDoesntExistException;
import ca.ulaval.glo4003.persistence.daos.SportDoesntExistException;

public class CommandGameService {

	private IGameRepository gameRepository;
	private ISportRepository sportRepository;

	public CommandGameService(IGameRepository gameFactory, ISportRepository sportRepository) {
		this.gameRepository = gameFactory;
		this.sportRepository = sportRepository;
	}

	public void createNewGame(String sportName, String opponent, DateTime date) throws SportDoesntExistException,
			GameDoesntExistException, GameAlreadyExistException {
		Game game = gameRepository.createNewGameInRepository(opponent, date);
		Sport sport = sportRepository.getSportByName(sportName);

		sport.addGameToCalendar(game);

		gameRepository.commit();
		sportRepository.commit();
	}

}
