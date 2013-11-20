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
import ca.ulaval.glo4003.domain.utilities.SportUrlMapper;

@RunWith(MockitoJUnitRunner.class)
public class CommandGameServiceTest {
	private static final String A_OPPONENT = "Opponent";
	private static final String A_SPORT_NAME = "Sport";
	private static final String A_LOCATION = "La lune";
	private static final DateTime A_DATE = new DateTime(100);
	@Mock
	private PersistableSport sport;

	@Mock
	private PersistableGame game;
	@Mock
	private IGameRepository gameRepositoryMock;

	@Mock
	private ISportRepository sportRepositoryMock;

	@Mock
	private SportUrlMapper sportUrlMapper;

	@InjectMocks
	private CommandGameService gameService;

	@Before
	public void setUp() throws Exception {
		when(sportRepositoryMock.get(A_SPORT_NAME)).thenReturn(sport);
		when(gameRepositoryMock.create(A_OPPONENT, A_LOCATION)).thenReturn(game);
		when(sportUrlMapper.getSportName(A_SPORT_NAME)).thenReturn(A_SPORT_NAME);
	}

	@Test
	public void addGameToCalendar_add_the_game_to_the_sport() throws Exception {
		gameService.createNewGame(A_SPORT_NAME, A_OPPONENT, A_LOCATION, A_DATE);

		verify(sport).addGameToCalendar(game, A_DATE);

	}

	@Test
	public void addGameToCalendar_commits_its_changes() throws Exception {
		gameService.createNewGame(A_SPORT_NAME, A_OPPONENT, A_LOCATION, A_DATE);

		verify(sportRepositoryMock).commit();
	}
}
