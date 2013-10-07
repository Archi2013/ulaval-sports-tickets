package ca.ulaval.glo4003.domain.pojos;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4003.domain.pojos.Game;
import ca.ulaval.glo4003.domain.pojos.Sport;

@RunWith(MockitoJUnitRunner.class)
public class SportTest {

	private static final String A_SPORT_NAME = "Sport";
	private List<Game> gameCalendar;

	@Mock
	private Game game;

	private Sport sport;

	@Before
	public void setUp() {
		gameCalendar = new ArrayList<Game>();
		sport = new Sport(A_SPORT_NAME, gameCalendar);
	}

	@Test
	public void addGameToCalendar_add_game_passed_to_sport_calendar() {
		sport.addGameToCalendar(game);

		Assert.assertEquals(1, gameCalendar.size());
	}
}
