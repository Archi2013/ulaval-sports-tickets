package ca.ulaval.glo4003.domain.services;

import org.joda.time.DateTime;

import ca.ulaval.glo4003.factories.IGameFactory;
import ca.ulaval.glo4003.pocos.Game;
import ca.ulaval.glo4003.pocos.Sport;
import ca.ulaval.glo4003.repositories.ISportRepository;

public class CommandGameService {

	private IGameFactory gameFactory;
	private ISportRepository sportRepository;

	public CommandGameService(IGameFactory gameFactory, ISportRepository sportRepository) {
		this.gameFactory = gameFactory;
		this.sportRepository = sportRepository;
	}

	public void createNewGame(String sportName, String opponent, DateTime date) {
		Game game = gameFactory.createNewGame(opponent, date);
		Sport sport = sportRepository.getSportByName(sportName);
		sport.addGameToCalendar(game);
	}

}
