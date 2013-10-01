package ca.ulaval.glo4003.domain.services;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4003.datafilter.DataFilter;
import ca.ulaval.glo4003.dto.GameDto;
import ca.ulaval.glo4003.dto.SportDto;
import ca.ulaval.glo4003.persistence.dao.SportDao;
import ca.ulaval.glo4003.web.converter.SportConverter;
import ca.ulaval.glo4003.web.converter.SportSimpleConverter;
import ca.ulaval.glo4003.web.viewmodel.SportSimpleViewModel;
import ca.ulaval.glo4003.web.viewmodel.SportViewModel;

@RunWith(MockitoJUnitRunner.class)
public class SportServiceTest {

	private static final String SPORT_NAME = "SPORT_NAME";

	@Mock
	private SportDao sportDaoMock;

	@Mock
	private DataFilter<GameDto> filterMock;

	@Mock
	private SportConverter sportConverterMock;

	@Mock
	private SportSimpleConverter simpleSportConverterMock;

	@InjectMocks
	private SportService service = new SportService();

	private List<GameDto> games;

	private SportDto sportDtoMock;

	private List<SportDto> sports;

	@Before
	public void setUp() {
		sports = newArrayList();
		when(sportDaoMock.getAll()).thenReturn(sports);

		sportDtoMock = mock(SportDto.class);
		games = new ArrayList<GameDto>();
		when(sportDtoMock.getGames()).thenReturn(games);
		when(sportDaoMock.get(SPORT_NAME)).thenReturn(sportDtoMock);
	}

	@Test
	public void getSports_should_get_sports_from_dao() {
		service.getSports();

		verify(sportDaoMock).getAll();
	}

	@Test
	public void getSports_should_convert_dtos_to_view_models() {
		service.getSports();

		verify(simpleSportConverterMock).convert(sports);
	}

	@Test
	public void getSports_should_return_converted_view_models() {
		List<SportSimpleViewModel> sportsViewModels = newArrayList();
		when(simpleSportConverterMock.convert(sports)).thenReturn(sportsViewModels);
		List<SportSimpleViewModel> response = service.getSports();

		assertEquals(sportsViewModels, response);
	}

	@Test
	public void getSport_should_get_sport_from_dao() {
		service.getSport(SPORT_NAME);

		verify(sportDaoMock).get(SPORT_NAME);
	}

	@Test
	public void getSport_should_apply_filter_on_sport_games() {
		service.getSport(SPORT_NAME);

		verify(filterMock).applyFilterOnList(games);
	}

	@Test
	public void getSport_should_convert_sports_to_view_models() {
		service.getSport(SPORT_NAME);

		verify(sportConverterMock).convert(sportDtoMock);
	}

	@Test
	public void getSport_should_return_converted_view_models() {
		SportViewModel viewModel = new SportViewModel("", null);
		when(sportConverterMock.convert(sportDtoMock)).thenReturn(viewModel);
		SportViewModel response = service.getSport(SPORT_NAME);

		assertSame(viewModel, response);
	}
}
