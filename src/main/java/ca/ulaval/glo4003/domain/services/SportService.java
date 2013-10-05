package ca.ulaval.glo4003.domain.services;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import ca.ulaval.glo4003.domain.datafilters.DataFilter;
import ca.ulaval.glo4003.domain.dtos.GameDto;
import ca.ulaval.glo4003.domain.dtos.SportDto;
import ca.ulaval.glo4003.persistence.daos.GameDao;
import ca.ulaval.glo4003.persistence.daos.SportDao;
import ca.ulaval.glo4003.persistence.daos.SportDoesntExistException;
import ca.ulaval.glo4003.web.viewmodels.GamesViewModel;
import ca.ulaval.glo4003.web.viewmodels.SportsViewModel;
import ca.ulaval.glo4003.web.viewmodels.factories.GamesViewModelFactory;
import ca.ulaval.glo4003.web.viewmodels.factories.SportsViewModelFactory;

@Service
public class SportService {
	@Inject
	private SportDao sportDao;

	@Inject
	private GameDao gameDao;

	@Inject
	private DataFilter<GameDto> filter;

	@Inject
	private SportsViewModelFactory sportsViewModelFactory;

	@Inject
	private GamesViewModelFactory gamesViewModelFactory;

	public SportsViewModel getSports() {
		List<SportDto> sports = sportDao.getAll();
		return sportsViewModelFactory.createViewModel(sports);
	}

	public GamesViewModel getGamesForSport(String sportName) throws SportDoesntExistException {
		List<GameDto> games = gameDao.getGamesForSport(sportName);
		filter.applyFilterOnList(games);
		return gamesViewModelFactory.createViewModel(sportName, games);
	}

}
