package ca.ulaval.glo4003.domain.services;

import static com.google.common.collect.Lists.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4003.domain.game.GameDao;
import ca.ulaval.glo4003.domain.game.GameDto;
import ca.ulaval.glo4003.domain.sports.SportDao;
import ca.ulaval.glo4003.domain.sports.SportDto;
import ca.ulaval.glo4003.domain.sports.SportUrlMapper;
import ca.ulaval.glo4003.exceptions.NoSportForUrlException;
import ca.ulaval.glo4003.exceptions.SportDoesntExistException;
import ca.ulaval.glo4003.presentation.viewmodels.GamesViewModel;
import ca.ulaval.glo4003.presentation.viewmodels.SportsViewModel;
import ca.ulaval.glo4003.presentation.viewmodels.factories.GamesViewModelFactory;
import ca.ulaval.glo4003.presentation.viewmodels.factories.SportsViewModelFactory;
import ca.ulaval.glo4003.services.SportViewService;
import ca.ulaval.glo4003.utilities.datafilters.GameIsInFutureFilter;

@RunWith(MockitoJUnitRunner.class)
public class SportViewServiceTest {

	private static final String SPORT_NAME = "Baseball Masculin";
	private static final String SPORT_URL = "baseball-masculin";

	@Mock
	private SportDao sportDaoMock;

	@Mock
	private GameDao gameDaoMock;

	@Mock
	private SportUrlMapper sportUrlMapperMock;

	@Mock
	private GameIsInFutureFilter filterMock;

	@Mock
	private SportsViewModelFactory sportsViewModelFactoryMock;

	@Mock
	private GamesViewModelFactory gamesViewModelFactoryMock;

	@InjectMocks
	private SportViewService service = new SportViewService();

	private List<SportDto> sports;
	private List<GameDto> games;

	@Before
	public void setUp() throws SportDoesntExistException, NoSportForUrlException {
		sports = newArrayList();
		when(sportDaoMock.getAll()).thenReturn(sports);

		games = newArrayList();
		when(gameDaoMock.getGamesForSport(SPORT_NAME)).thenReturn(games);

		when(sportUrlMapperMock.getSportName(SPORT_URL)).thenReturn(SPORT_NAME);
	}

	@Test
	public void getSports_should_get_sports_from_dao() {
		service.getSports();

		verify(sportDaoMock).getAll();
	}

	@Test
	public void getSports_should_create_view_model() {
		service.getSports();

		verify(sportsViewModelFactoryMock).createViewModel(sports);
	}

	@Test
	public void getSports_should_return_created_view_model() {
		SportsViewModel sportsViewModels = new SportsViewModel();
		when(sportsViewModelFactoryMock.createViewModel(sports)).thenReturn(sportsViewModels);
		SportsViewModel response = service.getSports();

		assertEquals(sportsViewModels, response);
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

		assertSame(viewModel, response);
	}

	@SuppressWarnings("unchecked")
	@Test(expected = SportDoesntExistException.class)
	public void getGamesForSport_should_throw_sport_doesnt_exist_exception_if_sport_doesnt_exist_in_properties_file()
			throws Exception {
		when(sportUrlMapperMock.getSportName(SPORT_URL)).thenThrow(NoSportForUrlException.class);

		service.getGamesForSport(SPORT_URL);
	}
}
