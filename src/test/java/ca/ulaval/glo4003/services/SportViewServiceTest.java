package ca.ulaval.glo4003.services;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4003.domain.game.GameDao;
import ca.ulaval.glo4003.domain.sports.SportDao;
import ca.ulaval.glo4003.exceptions.NoSportForUrlException;
import ca.ulaval.glo4003.exceptions.SportDoesntExistException;
import ca.ulaval.glo4003.game.dto.GameDto;
import ca.ulaval.glo4003.presentation.viewmodels.SportsViewModel;
import ca.ulaval.glo4003.presentation.viewmodels.factories.SportsViewModelFactory;
import ca.ulaval.glo4003.sports.dto.SportDto;

@RunWith(MockitoJUnitRunner.class)
public class SportViewServiceTest {

	private static final String SPORT_NAME = "Baseball Masculin";
	private static final String SPORT_URL = "baseball-masculin";

	@Mock
	private SportDao sportDaoMock;

	@Mock
	private GameDao gameDaoMock;

	@Mock
	private SportsViewModelFactory sportsViewModelFactoryMock;

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
}
