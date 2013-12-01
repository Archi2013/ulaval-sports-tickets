package ca.ulaval.glo4003.services;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4003.domain.game.GameDao;
import ca.ulaval.glo4003.domain.game.GameDto;
import ca.ulaval.glo4003.exceptions.SportDoesntExistException;
import ca.ulaval.glo4003.presentation.viewmodels.GameSelectionViewModel;
import ca.ulaval.glo4003.services.AdministrationViewService;

@RunWith(MockitoJUnitRunner.class)
public class AdministrationViewServiceTest {
	private static final String A_SPORT = "sport";
	private static final String AN_OPPONENT = "opponent";
	private static final String ANOTHER_OPPONENT = "opponent2";
	private static DateTime A_DATE = new DateTime(100);
	private static DateTime ANOTHER_DATE = new DateTime(200);

	private List<GameDto> games;

	@Mock
	private GameDao gameDao;

	@InjectMocks
	private AdministrationViewService service;

	@Before
	public void setUp() throws SportDoesntExistException {
		games = new ArrayList<GameDto>() {
            private static final long serialVersionUID = 1L;

			{
				add(new GameDto(AN_OPPONENT, A_DATE, A_SPORT, null));
				add(new GameDto(ANOTHER_OPPONENT, ANOTHER_DATE, A_SPORT, null));
			}
		};
		when(gameDao.getGamesForSport(A_SPORT)).thenReturn(games);
	}

	@Test
	public void getGameSelectionForSport_returns_viewModels_with_correct_opponents() throws SportDoesntExistException {
		List<GameSelectionViewModel> viewModels = service.getGameSelectionForSport(A_SPORT);

		Assert.assertEquals(AN_OPPONENT, viewModels.get(0).getOpponents());
		Assert.assertEquals(ANOTHER_OPPONENT, viewModels.get(1).getOpponents());
	}

	@Test
	public void getGameSelectionForSport_returns_viewModels_with_correct_dates() throws SportDoesntExistException {
		List<GameSelectionViewModel> viewModels = service.getGameSelectionForSport(A_SPORT);

		Assert.assertSame(A_DATE, viewModels.get(0).getGameDate().getDateTime());
		Assert.assertSame(ANOTHER_DATE, viewModels.get(1).getGameDate().getDateTime());
	}

}
