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

import ca.ulaval.glo4003.domain.datafilters.DataFilter;
import ca.ulaval.glo4003.domain.dtos.GameDto;
import ca.ulaval.glo4003.domain.dtos.SportDto;
import ca.ulaval.glo4003.persistence.daos.GameDao;
import ca.ulaval.glo4003.persistence.daos.SportDao;
import ca.ulaval.glo4003.persistence.daos.SportDoesntExistException;
import ca.ulaval.glo4003.web.converters.GameConverter;
import ca.ulaval.glo4003.web.converters.SportConverter;
import ca.ulaval.glo4003.web.viewmodels.GamesViewModel;
import ca.ulaval.glo4003.web.viewmodels.SportsViewModel;
import ca.ulaval.glo4003.web.viewmodels.factories.GamesViewModelFactory;
import ca.ulaval.glo4003.web.viewmodels.factories.SportsViewModelFactory;

@RunWith(MockitoJUnitRunner.class)
public class SportServiceTest {

	private static final String SPORT_NAME = "SPORT_NAME";

	@Mock
	private SportDao sportDaoMock;

	@Mock
	private GameDao gameDaoMock;

	@Mock
	private DataFilter<GameDto> filterMock;

	@Mock
	private SportConverter simpleSportConverterMock;

	@Mock
	private GameConverter gameConverterMock;

	@Mock
	private SportsViewModelFactory sportsViewModelFactoryMock;

	@Mock
	private GamesViewModelFactory gamesViewModelFactoryMock;

	@InjectMocks
	private SportService service = new SportService();

	private List<GameDto> games;
	private List<SportDto> sports;

	@Before
	public void setUp() throws SportDoesntExistException {
		sports = newArrayList();
		when(sportDaoMock.getAll()).thenReturn(sports);

		games = newArrayList();
		when(gameDaoMock.getGamesForSport(SPORT_NAME)).thenReturn(games);
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
	public void getGamesForSport_should_get_games_for_sport_from_dao() throws SportDoesntExistException {
		service.getGamesForSport(SPORT_NAME);

		verify(gameDaoMock).getGamesForSport(SPORT_NAME);
	}

	@Test
	public void getGamesForSport_should_apply_filter_on_sport_games() throws SportDoesntExistException {
		service.getGamesForSport(SPORT_NAME);

		verify(filterMock).applyFilterOnList(games);
	}

	@Test
	public void getGamesForSport_should_create_view_model() throws SportDoesntExistException {
		service.getGamesForSport(SPORT_NAME);

		verify(gamesViewModelFactoryMock).createViewModel(SPORT_NAME, games);
	}

	@Test
	public void getGamesForSport_should_return_created_view_model() throws SportDoesntExistException {
		GamesViewModel viewModel = new GamesViewModel();
		when(gamesViewModelFactoryMock.createViewModel(SPORT_NAME, games)).thenReturn(viewModel);
		GamesViewModel response = service.getGamesForSport(SPORT_NAME);

		assertSame(viewModel, response);
	}
}
