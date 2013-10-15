package ca.ulaval.glo4003.domain.services;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4003.domain.pojos.persistable.PersistableGame;
import ca.ulaval.glo4003.domain.pojos.persistable.PersistableSport;
import ca.ulaval.glo4003.domain.repositories.IGameRepository;
import ca.ulaval.glo4003.domain.repositories.ISportRepository;

@RunWith(MockitoJUnitRunner.class)
public class CommandGameServiceTest {
	private static final String A_OPPONENT = "Oppenent";
	private static final String A_SPORT_NAME = "Sport";
	private static final DateTime A_DATE = new DateTime(100);
	@Mock
	private PersistableSport sport;

	@Mock
	private PersistableGame game;

	@Mock
	private IGameRepository gameRepositoryMock;

	@Mock
	private ISportRepository sportRepositoryMock;

	@InjectMocks
	private CommandGameService gameService;

	@Before
	public void setUp() throws Exception {
		when(sportRepositoryMock.getSportByName(A_SPORT_NAME)).thenReturn(sport);
		when(gameRepositoryMock.createNewGameInRepository(A_OPPONENT, A_DATE)).thenReturn(game);
	}

	@Test
	public void addGameToCalendar_add_the_game_to_the_sport() throws Exception {
		gameService.createNewGame(A_SPORT_NAME, A_OPPONENT, A_DATE);

		verify(sport).addGameToCalendar(game);

	}

	@Test
	public void addGameToCalendar_commits_its_changes() throws Exception {
		gameService.createNewGame(A_SPORT_NAME, A_OPPONENT, A_DATE);

		verify(sportRepositoryMock).commit();
		verify(gameRepositoryMock).commit();
	}
}
