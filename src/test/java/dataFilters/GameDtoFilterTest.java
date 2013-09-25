package dataFilters;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4003.dtos.GameDto;

@RunWith(MockitoJUnitRunner.class)
public class GameDtoFilterTest {

	private static final DateTime A_DATE = new DateTime(100);
	private static final DateTime A_DATE_BEFORE = new DateTime(75);
	private static final DateTime A_DATE_AFTER = new DateTime(125);
	private static final int AN_ID = 123;
	private static final int ANOTHER_ID = 234;
	private static final String AN_OPPONENT = "Opponent";
	private GameDtoFilter filter;

	@Before
	public void SetUp() {
		filter = new GameDtoFilter();
	}

	@Test
	public void if_list_empty_FilterOnDate_return_empty_list() {
		List<GameDto> list = new ArrayList<GameDto>();

		List<GameDto> listReturned = filter.FilterOnDate(A_DATE, list);

		Assert.assertEquals(0, listReturned.size());
	}

	@Test
	public void if_list_contains_one_passed_game_FilterOnDate_return_empty_list() {
		List<GameDto> list = new ArrayList<GameDto>();
		list.add(new GameDto(AN_ID, AN_OPPONENT, A_DATE_BEFORE));

		List<GameDto> listReturned = filter.FilterOnDate(A_DATE, list);

		Assert.assertEquals(0, listReturned.size());
	}
}
