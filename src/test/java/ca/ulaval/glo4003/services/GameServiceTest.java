package ca.ulaval.glo4003.services;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4003.factories.IGameFactory;
import ca.ulaval.glo4003.pocos.Game;
import ca.ulaval.glo4003.pocos.Sport;
import ca.ulaval.glo4003.repositories.ISportRepository;

@RunWith(MockitoJUnitRunner.class)
public class GameServiceTest {
	private static final String A_OPPONENT = "Oppenent";
	private static final String A_SPORT_NAME = "Sport";
	private static final DateTime A_DATE = new DateTime(100);
	@Mock
	private Sport sport;

	@Mock
	private Game game;

	@Mock
	private IGameFactory gameFactoryMock;

	@Mock
	private ISportRepository sportRepositoryMock;

	@InjectMocks
	private GameService gameService;

	@Before
	public void setUp() {
		when(sportRepositoryMock.getSportByName(A_SPORT_NAME)).thenReturn(sport);
		when(gameFactoryMock.createNewGame(A_OPPONENT, A_DATE)).thenReturn(game);
	}

	@Test
	public void addGameToSportCalendar_ask_factory_for_a_new_Game() {
		gameService.createNewGame(A_SPORT_NAME, A_OPPONENT, A_DATE);

		verify(gameFactoryMock).createNewGame(A_OPPONENT, A_DATE);
	}

	@Test
	public void addGameToSportCalendar_ask_for_related_sport_to_repository() {
		gameService.createNewGame(A_SPORT_NAME, A_OPPONENT, A_DATE);

		verify(sportRepositoryMock).getSportByName(A_SPORT_NAME);
	}

	@Test
	public void addGameToCalendar_add_the_game_to_the_sport() {
		gameService.createNewGame(A_SPORT_NAME, A_OPPONENT, A_DATE);

		verify(sport).addGameToCalendar(game);

	}
}
