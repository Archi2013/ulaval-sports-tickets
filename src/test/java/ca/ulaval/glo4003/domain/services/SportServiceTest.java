package ca.ulaval.glo4003.domain.services;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import ca.ulaval.glo4003.persistence.dao.GameDao;
import ca.ulaval.glo4003.persistence.dao.SportDao;
import ca.ulaval.glo4003.persistence.dao.SportDoesntExistException;
import ca.ulaval.glo4003.web.converter.GameSimpleConverter;
import ca.ulaval.glo4003.web.converter.SportConverter;
import ca.ulaval.glo4003.web.converter.SportSimpleConverter;
import ca.ulaval.glo4003.web.viewmodel.GamesViewModel;
import ca.ulaval.glo4003.web.viewmodel.SportsViewModel;
import ca.ulaval.glo4003.web.viewmodel.factories.GamesViewModelFactory;
import ca.ulaval.glo4003.web.viewmodel.factories.SportsViewModelFactory;

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
	private SportConverter sportConverterMock;

	@Mock
	private SportSimpleConverter simpleSportConverterMock;

	@Mock
	private GameSimpleConverter gameConverterMock;

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
