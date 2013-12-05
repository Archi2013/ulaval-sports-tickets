package ca.ulaval.glo4003.services;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
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
import ca.ulaval.glo4003.domain.tickets.TicketDao;
import ca.ulaval.glo4003.exceptions.GameDoesntExistException;
import ca.ulaval.glo4003.exceptions.NoSportForUrlException;
import ca.ulaval.glo4003.exceptions.SportDoesntExistException;
import ca.ulaval.glo4003.presentation.viewmodels.factories.GamesViewModelFactory;
import ca.ulaval.glo4003.presentation.viewmodels.factories.SportsViewModelFactory;
import ca.ulaval.glo4003.services.SportService;
import ca.ulaval.glo4003.utilities.datafilters.GameIsInFutureFilter;

@RunWith(MockitoJUnitRunner.class)
public class SportServiceTest {
	@Mock
	private GameIsInFutureFilter filter;

	@Mock
	private GameDao gameDao;

	@Mock
	private GamesViewModelFactory gamesViewModelFactory;

	@Mock
	private SportDao sportDao;

	@Mock
	private SportsViewModelFactory sportsViewModelFactory;

	@Mock
	private SportUrlMapper sportUrlMapper;

	@Mock
	private TicketDao ticketDao;

	@InjectMocks
	private SportService sportService;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGetSports() throws Exception {
		sportService.getSports();

		verify(sportsViewModelFactory, atLeastOnce()).createViewModel(
				anyListOf(SportDto.class));
	}

	@Test(expected = SportDoesntExistException.class)
	public void testGetSports_withSportThatDoesntExist() throws Exception {
		when(sportUrlMapper.getSportName(anyString())).thenThrow(
				new NoSportForUrlException());

		String sportUrl = anyString();
		sportService.getGamesForSport(sportUrl);
	}

	@Test
	public void testGetGamesForSport() throws Exception {
		String sportUrl = anyString();

		sportService.getGamesForSport(sportUrl);

		verify(gamesViewModelFactory, atLeastOnce()).createViewModel(
				anyString(), anyListOf(GameDto.class));
	}

	@Test(expected = GameDoesntExistException.class)
	public void testGetGamesForSport_withGameThatDoesntExist() throws Exception {
		List<GameDto> listWithOneElement = new ArrayList<GameDto>();
		listWithOneElement.add(mock(GameDto.class));

		when(gameDao.getGamesForSport(anyString())).thenReturn(
				listWithOneElement);
		when(ticketDao.getAllAvailable(anyString(), any(DateTime.class)))
				.thenThrow(new GameDoesntExistException());

		String sportUrl = anyString();
		sportService.getGamesForSport(sportUrl);
	}

	@Test
	public void testGetGamesForSport_with2GameList() throws Exception {
		List<GameDto> listWithOneElement = new ArrayList<GameDto>();
		listWithOneElement.add(mock(GameDto.class));
		listWithOneElement.add(mock(GameDto.class));

		when(gameDao.getGamesForSport(anyString())).thenReturn(
				listWithOneElement);

		String sportUrl = anyString();
		sportService.getGamesForSport(sportUrl);

		verify(gamesViewModelFactory, atLeastOnce()).createViewModel(
				anyString(), anyListOf(GameDto.class));
	}
}