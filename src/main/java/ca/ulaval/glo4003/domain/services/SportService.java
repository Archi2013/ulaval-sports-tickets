package ca.ulaval.glo4003.domain.services;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import ca.ulaval.glo4003.domain.datafilters.GameIsInFutureFilter;
import ca.ulaval.glo4003.domain.dtos.GameDto;
import ca.ulaval.glo4003.domain.dtos.SportDto;
import ca.ulaval.glo4003.domain.utilities.NoSportForUrlException;
import ca.ulaval.glo4003.domain.utilities.SportUrlMapper;
import ca.ulaval.glo4003.persistence.daos.GameDao;
import ca.ulaval.glo4003.persistence.daos.GameDoesntExistException;
import ca.ulaval.glo4003.persistence.daos.SportDao;
import ca.ulaval.glo4003.persistence.daos.SportDoesntExistException;
import ca.ulaval.glo4003.persistence.daos.TicketDao;
import ca.ulaval.glo4003.presentation.viewmodels.GamesViewModel;
import ca.ulaval.glo4003.presentation.viewmodels.SportsViewModel;
import ca.ulaval.glo4003.presentation.viewmodels.factories.GamesViewModelFactory;
import ca.ulaval.glo4003.presentation.viewmodels.factories.SportsViewModelFactory;

@Service
public class SportService {
	@Inject
	private SportDao sportDao;

	@Inject
	private SportUrlMapper sportUrlMapper;

	@Inject
	private GameDao gameDao;

	@Inject
	private TicketDao ticketDao;

	@Inject
	private GameIsInFutureFilter filter;

	@Inject
	private SportsViewModelFactory sportsViewModelFactory;

	@Inject
	private GamesViewModelFactory gamesViewModelFactory;

	public SportsViewModel getSports() {
		List<SportDto> sports = sportDao.getAll();
		return sportsViewModelFactory.createViewModel(sports);
	}

	public GamesViewModel getGamesForSport(String sportUrl) throws SportDoesntExistException, GameDoesntExistException {
		try {
			String sportName = sportUrlMapper.getSportName(sportUrl);
			List<GameDto> games = gameDao.getGamesForSport(sportName);
			countNumberOfTickets(games);
			filter.applyFilterOnList(games);
			return gamesViewModelFactory.createViewModel(sportName, games);
		} catch (NoSportForUrlException e) {
			throw new SportDoesntExistException();
		}
	}

	private void countNumberOfTickets(List<GameDto> games) throws GameDoesntExistException {
		for (GameDto game : games) {
			try {
				game.setNumberOfTickets(ticketDao.getAllAvailable(game.getSportName(), game.getGameDate()).size());
			} catch (Exception e) {
				game.setNumberOfTickets(0);
			}
		}

	}
}
