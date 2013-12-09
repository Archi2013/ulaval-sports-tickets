package ca.ulaval.glo4003.services;

import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4003.domain.game.GameDao;
import ca.ulaval.glo4003.domain.game.GameDto;
import ca.ulaval.glo4003.domain.tickets.TicketDao;
import ca.ulaval.glo4003.exceptions.SportDoesntExistException;
import ca.ulaval.glo4003.presentation.viewmodels.factories.GamesViewModelFactory;
import ca.ulaval.glo4003.utilities.datafilters.GameIsInFutureFilter;

@RunWith(MockitoJUnitRunner.class)
public class GameViewServiceTest {

	@Mock
	private GameIsInFutureFilter filter;
	
	@Mock
	private TicketDao ticketDao;
	
	@Mock
	private GameDao gameDao;

	@Mock
	private GamesViewModelFactory gamesViewModelFactory;

	@InjectMocks
	private GameViewService queryGameService;

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

	@Test
	public void testGetGamesForSport_withGameThatDoesntExist() throws Exception {
		List<GameDto> listWithOneElement = new ArrayList<GameDto>();
		listWithOneElement.add(mock(GameDto.class));

		when(gameDao.getGamesForSport(anyString())).thenReturn(
				listWithOneElement);

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
