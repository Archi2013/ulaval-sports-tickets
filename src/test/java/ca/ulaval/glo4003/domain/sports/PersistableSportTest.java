package ca.ulaval.glo4003.domain.sports;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4003.domain.game.Game;
import ca.ulaval.glo4003.domain.sports.PersistableSport;
import ca.ulaval.glo4003.sports.dto.SportDto;

@RunWith(MockitoJUnitRunner.class)
public class PersistableSportTest {

	private static final String A_SPORT_NAME = "Sport";
	private static final DateTime A_DATE = new DateTime(100);
	private List<Game> gameCalendar;

	@Mock
	private Game existingGame1;

	@Mock
	private Game existingGame2;

	@Mock
	private Game gameToAdd;

	private PersistableSport sport;

	@Before
	public void setUp() {
		gameCalendar = new ArrayList<Game>();
		gameCalendar.add(existingGame1);
		gameCalendar.add(existingGame2);
		sport = new PersistableSport(A_SPORT_NAME, gameCalendar);
	}

	@Test
	public void saveDataInDto_returns_a_valid_dto() {
		SportDto dto = sport.saveDataInDTO();

		Assert.assertEquals(A_SPORT_NAME, dto.getName());
	}

	@Test
	public void addGameToCalendar_schedules_game_if_game_accepts_to_be_scheduled() {
		when(gameToAdd.acceptsToBeScheduled()).thenReturn(true);

		sport.addGameToCalendar(gameToAdd, A_DATE);

		verify(gameToAdd).beScheduledToThisSport(A_SPORT_NAME, A_DATE);
	}

	@Test
	public void addGameToCalendar_add_game_to_sport_calendar_if_game_accepts_to_be_scheduled() {
		when(gameToAdd.acceptsToBeScheduled()).thenReturn(true);

		sport.addGameToCalendar(gameToAdd, A_DATE);

		Assert.assertEquals(3, gameCalendar.size());
		Assert.assertSame(gameToAdd, gameCalendar.get(2));
	}

	@Test
	public void addGameToCalendar_does_nothing_if_game_does_not_accept_to_be_scheduled() {
		when(gameToAdd.acceptsToBeScheduled()).thenReturn(false);

		sport.addGameToCalendar(gameToAdd, A_DATE);

		Assert.assertEquals(2, gameCalendar.size());
	}

}
