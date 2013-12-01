package ca.ulaval.glo4003.services;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4003.domain.game.GameDao;
import ca.ulaval.glo4003.domain.game.GameDto;
import ca.ulaval.glo4003.domain.sections.SectionDao;
import ca.ulaval.glo4003.domain.sports.SportDao;
import ca.ulaval.glo4003.domain.sports.SportDto;
import ca.ulaval.glo4003.domain.sports.SportUrlMapper;
import ca.ulaval.glo4003.exceptions.GameDoesntExistException;
import ca.ulaval.glo4003.exceptions.NoSportForUrlException;
import ca.ulaval.glo4003.exceptions.SportDoesntExistException;
import ca.ulaval.glo4003.presentation.viewmodels.GamesViewModel;
import ca.ulaval.glo4003.presentation.viewmodels.factories.GamesViewModelFactory;
import ca.ulaval.glo4003.utilities.datafilters.GameIsInFutureFilter;

@Ignore
@RunWith(MockitoJUnitRunner.class)
public class QueryGameServiceTest {

	private static final String SPORT_NAME = "Baseball Masculin";
	private static final String SPORT_URL = "baseball-masculin";
	private static final DateTime GAME_DATE = DateTime.now();

	@Mock
	private SportDao sportDaoMock;

	@Mock
	private GameDao gameDaoMock;

	@Mock
	private SectionDao sectionDaoMock;

	@Mock
	private GamesViewModelFactory gamesViewModelFactoryMock;

	@Mock
	private SportUrlMapper sportUrlMapperMock;

	@Mock
	private GameIsInFutureFilter filterMock;

	@InjectMocks
	private QueryGameService service;

	private List<SportDto> sports;
	private List<GameDto> games;

	@Before
	public void setup() throws GameDoesntExistException, SportDoesntExistException, NoSportForUrlException {
		sports = new ArrayList();
		when(sportDaoMock.getAll()).thenReturn(sports);

		games = new ArrayList();
		when(gameDaoMock.getGamesForSport(SPORT_NAME)).thenReturn(games);

		when(sportUrlMapperMock.getSportName(SPORT_URL)).thenReturn(SPORT_NAME);

	}

	@Test
	public void getGamesForSport_should_map_sport_url_to_sport_name() throws Exception {
		service.getGamesForSport(SPORT_URL);

		verify(sportUrlMapperMock).getSportName(SPORT_URL);
	}

	@Test
	public void getGamesForSport_should_get_games_for_sport_from_dao() throws Exception {
		service.getGamesForSport(SPORT_URL);

		verify(gameDaoMock).getGamesForSport(SPORT_NAME);
	}

	@Test
	public void getGamesForSport_should_apply_filter_on_sport_games() throws Exception {
		service.getGamesForSport(SPORT_URL);

		verify(filterMock).applyFilterOnList(games);
	}

	@Test
	public void getGamesForSport_should_create_view_model() throws Exception {
		service.getGamesForSport(SPORT_URL);

		verify(gamesViewModelFactoryMock).createViewModel(SPORT_NAME, games);
	}

	@Test
	public void getGamesForSport_should_return_created_view_model() throws Exception {
		GamesViewModel viewModel = new GamesViewModel();
		when(gamesViewModelFactoryMock.createViewModel(SPORT_NAME, games)).thenReturn(viewModel);

		GamesViewModel response = service.getGamesForSport(SPORT_URL);

		Assert.assertSame(viewModel, response);
	}

	@SuppressWarnings("unchecked")
	@Test(expected = SportDoesntExistException.class)
	public void getGamesForSport_should_throw_sport_doesnt_exist_exception_if_sport_doesnt_exist_in_properties_file()
			throws Exception {
		when(sportUrlMapperMock.getSportName(SPORT_URL)).thenThrow(NoSportForUrlException.class);

		service.getGamesForSport(SPORT_URL);
	}
}
