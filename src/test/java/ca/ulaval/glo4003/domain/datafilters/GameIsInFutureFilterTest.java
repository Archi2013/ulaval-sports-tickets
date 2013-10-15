package ca.ulaval.glo4003.domain.datafilters;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4003.domain.datafilters.GameIsInFutureFilter;
import ca.ulaval.glo4003.domain.dtos.GameDto;

@RunWith(MockitoJUnitRunner.class)
public class GameIsInFutureFilterTest {

	private static final DateTime A_DATE = new DateTime(100);
	private static final DateTime A_DATE_BEFORE = new DateTime(75);
	private static final DateTime A_DATE_AFTER = new DateTime(125);
	private static final int AN_ID = 123;
	private static final int ANOTHER_ID = 234;
	private static final String AN_OPPONENT = "Opponent";
	private static final String A_SPORT_NAME = "BaseketBall";
	private static final GameDto A_GAME_AFTER = new GameDto(AN_ID, AN_OPPONENT, A_DATE_AFTER, A_SPORT_NAME);
	private static final GameDto A_GAME_BEFORE = new GameDto(ANOTHER_ID, AN_OPPONENT, A_DATE_BEFORE, A_SPORT_NAME);
	private GameIsInFutureFilter filter;

	@Before
	public void SetUp() {
		filter = new GameIsInFutureFilter(A_DATE);
	}

	@Test
	public void if_list_empty_FilterOnDate_return_empty_list() {
		List<GameDto> list = new ArrayList<GameDto>();

		filter.applyFilterOnList(list);

		Assert.assertEquals(0, list.size());
	}

	@Test
	public void if_list_contains_one_game_before_FilterOnDate_return_empty_list() {
		List<GameDto> list = new ArrayList<GameDto>();
		list.add(A_GAME_BEFORE);

		filter.applyFilterOnList(list);

		Assert.assertEquals(0, list.size());
	}

	@Test
	public void if_list_contains_one_game_after_FilterOnDate_return_list_with_the_game() {
		List<GameDto> list = new ArrayList<GameDto>();
		list.add(A_GAME_AFTER);

		filter.applyFilterOnList(list);

		Assert.assertEquals(1, list.size());
		Assert.assertSame(A_GAME_AFTER, list.get(0));
	}

	@Test
	public void if_list_contains_many_games_applyFilter_return_only_the_games_after_the_specified_date() {
		List<GameDto> list = new ArrayList<GameDto>();
		list.add(A_GAME_BEFORE);
		list.add(A_GAME_AFTER);

		filter.applyFilterOnList(list);

		Assert.assertEquals(1, list.size());
		Assert.assertSame(A_GAME_AFTER, list.get(0));
	}
}
