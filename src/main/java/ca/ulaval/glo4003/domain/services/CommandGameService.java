package ca.ulaval.glo4003.domain.services;

import org.joda.time.DateTime;

import ca.ulaval.glo4003.domain.factories.IGameFactory;
import ca.ulaval.glo4003.domain.pojos.persistable.PersistableGame;
import ca.ulaval.glo4003.domain.pojos.persistable.PersistableSport;
import ca.ulaval.glo4003.domain.repositories.ISportRepository;
import ca.ulaval.glo4003.persistence.daos.SportDoesntExistException;

public class CommandGameService {

	private IGameFactory gameFactory;
	private ISportRepository sportRepository;

	public CommandGameService(IGameFactory gameFactory, ISportRepository sportRepository) {
		this.gameFactory = gameFactory;
		this.sportRepository = sportRepository;
	}

	public void createNewGame(String sportName, String opponent, DateTime date) {
		PersistableGame game = gameFactory.instantiateGame(opponent, date);
		PersistableSport sport;
		try {
			sport = sportRepository.getSportByName(sportName);
		} catch (SportDoesntExistException e) {
			throw new RuntimeException();
		}
		sport.addGameToCalendar(game);
	}

}
