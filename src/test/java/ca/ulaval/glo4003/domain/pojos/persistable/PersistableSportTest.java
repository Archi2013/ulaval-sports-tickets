package ca.ulaval.glo4003.domain.pojos.persistable;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4003.domain.dtos.SportDto;
import ca.ulaval.glo4003.domain.pojos.persistable.PersistableSport;

@RunWith(MockitoJUnitRunner.class)
public class PersistableSportTest {

	private static final String A_SPORT_NAME = "Sport";
	private List<PersistableGame> gameCalendar;

	@Mock
	private PersistableGame game;

	private PersistableSport sport;

	@Before
	public void setUp() {
		gameCalendar = new ArrayList<PersistableGame>();
		sport = new PersistableSport(A_SPORT_NAME, gameCalendar);
	}

	@Test
	public void saveDataInDto_returns_a_valid_dto() {
		SportDto dto = sport.saveDataInDTO();

		Assert.assertEquals(A_SPORT_NAME, dto.getName());
	}

	@Test
	public void addGameToCalendar_add_game_passed_to_sport_calendar() {
		sport.addGameToCalendar(game);

		Assert.assertEquals(1, gameCalendar.size());
	}
}
