package ca.ulaval.glo4003.services;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
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
import ca.ulaval.glo4003.domain.tickets.TicketDao;
import ca.ulaval.glo4003.exceptions.GameDoesntExistException;
import ca.ulaval.glo4003.exceptions.NoSportForUrlException;
import ca.ulaval.glo4003.exceptions.SportDoesntExistException;
import ca.ulaval.glo4003.presentation.viewmodels.factories.GamesViewModelFactory;
import ca.ulaval.glo4003.utilities.datafilters.GameIsInFutureFilter;

@RunWith(MockitoJUnitRunner.class)
public class QueryGameServiceTest {
	@Mock
	private GameIsInFutureFilter filter;

	@Mock
	private GameDao gameDao;

	@Mock
	private GamesViewModelFactory gamesViewModelFactory;

	@Mock
	private TicketDao ticketDao;

	@InjectMocks
	private QueryGameService queryGameService;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGetGamesForSport() throws Exception {
		queryGameService.getGamesForSport(anyString());

		verify(gamesViewModelFactory, atLeastOnce()).createViewModel(
				anyString(), anyListOf(GameDto.class));
	}

	@Test(expected = SportDoesntExistException.class)
	public void testGetGamesForSport_withSportDoesntExist() throws Exception {
		when(gameDao.getGamesForSport(anyString())).thenThrow(
				new SportDoesntExistException());
		
		queryGameService.getGamesForSport(anyString());
	}

	@Test(expected = GameDoesntExistException.class)
	public void testGetGamesForSport_withGameThatDoesntExist() throws Exception {
		List<GameDto> listWithOneElement = new ArrayList<GameDto>();
		listWithOneElement.add(mock(GameDto.class));

		when(gameDao.getGamesForSport(anyString())).thenReturn(
				listWithOneElement);
		when(ticketDao.getAllAvailable(anyString(), any(DateTime.class)))
				.thenThrow(new GameDoesntExistException());

		queryGameService.getGamesForSport(anyString());

		verify(gamesViewModelFactory, atLeastOnce()).createViewModel(
				anyString(), anyListOf(GameDto.class));

	}

	@Test
	public void testGetGamesForSport_with2GamesThatExist() throws Exception {
		List<GameDto> listWithOneElement = new ArrayList<GameDto>();
		listWithOneElement.add(mock(GameDto.class));
		listWithOneElement.add(mock(GameDto.class));

		when(gameDao.getGamesForSport(anyString())).thenReturn(
				listWithOneElement);

		queryGameService.getGamesForSport(anyString());

		verify(gamesViewModelFactory, atLeastOnce()).createViewModel(
				anyString(), anyListOf(GameDto.class));

	}
}
