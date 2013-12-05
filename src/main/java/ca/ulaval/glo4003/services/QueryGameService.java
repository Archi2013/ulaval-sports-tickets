package ca.ulaval.glo4003.services;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import ca.ulaval.glo4003.domain.game.GameDao;
import ca.ulaval.glo4003.domain.game.GameDto;
import ca.ulaval.glo4003.domain.tickets.TicketDao;
import ca.ulaval.glo4003.exceptions.GameDoesntExistException;
import ca.ulaval.glo4003.exceptions.SportDoesntExistException;
import ca.ulaval.glo4003.presentation.viewmodels.GamesViewModel;
import ca.ulaval.glo4003.presentation.viewmodels.factories.GamesViewModelFactory;
import ca.ulaval.glo4003.utilities.datafilters.GameIsInFutureFilter;

@Service
public class QueryGameService {

	@Inject
	private GameIsInFutureFilter filter;

	@Inject
	private GameDao gameDao;

	@Inject
	private TicketDao ticketDao;

	@Inject
	private GamesViewModelFactory gamesViewModelFactory;

	public GamesViewModel getGamesForSport(String sportName)
			throws SportDoesntExistException, GameDoesntExistException {
		List<GameDto> games = gameDao.getGamesForSport(sportName);
		countNumberOfTickets(games);
		filter.applyFilterOnList(games);
		return gamesViewModelFactory.createViewModel(sportName, games);
	}

	private void countNumberOfTickets(List<GameDto> games)
			throws GameDoesntExistException {
		for (GameDto game : games) {
			game.setNumberOfTickets(ticketDao.getAllAvailable(
					game.getSportName(), game.getGameDate()).size());
		}
	}
}
