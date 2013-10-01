package ca.ulaval.glo4003.domain.services;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
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
import ca.ulaval.glo4003.persistence.dao.GameDao;
import ca.ulaval.glo4003.persistence.dao.SportDao;
import ca.ulaval.glo4003.persistence.dao.SportDoesntExistException;
import ca.ulaval.glo4003.web.converter.GameSimpleConverter;
import ca.ulaval.glo4003.web.converter.SportConverter;
import ca.ulaval.glo4003.web.converter.SportSimpleConverter;
import ca.ulaval.glo4003.web.viewmodel.GameSimpleViewModel;
import ca.ulaval.glo4003.web.viewmodel.SportSimpleViewModel;

@RunWith(MockitoJUnitRunner.class)
public class SportServiceTest {

	private static final String SPORT_NAME = "SPORT_NAME";

	@Mock
	private SportDao sportDaoMock;

	@Mock
	private GameDao gameDaoMock;

	@Mock
	private DataFilter<GameDto> filterMock;

	@Mock
	private SportConverter sportConverterMock;

	@Mock
	private SportSimpleConverter simpleSportConverterMock;

	@Mock
	private GameSimpleConverter gameConverterMock;

	@InjectMocks
	private SportService service = new SportService();

	private List<GameDto> games;
	private List<SportDto> sports;

	@Before
	public void setUp() throws SportDoesntExistException {
		sports = newArrayList();
		when(sportDaoMock.getAll()).thenReturn(sports);

		games = newArrayList();
		when(gameDaoMock.getGamesForSport(SPORT_NAME)).thenReturn(games);
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
	public void getGamesForSport_should_get_games_for_sport_from_dao() throws SportDoesntExistException {
		service.getGamesForSport(SPORT_NAME);

		verify(gameDaoMock).getGamesForSport(SPORT_NAME);
	}

	@Test
	public void getGamesForSport_should_apply_filter_on_sport_games() throws SportDoesntExistException {
		service.getGamesForSport(SPORT_NAME);

		verify(filterMock).applyFilterOnList(games);
	}

	@Test
	public void getGamesForSport_should_convert_games_to_view_models() throws SportDoesntExistException {
		service.getGamesForSport(SPORT_NAME);

		verify(gameConverterMock).convert(games);
	}

	@Test
	public void getGamesForSport_should_return_converted_view_models() throws SportDoesntExistException {
		List<GameSimpleViewModel> viewModels = new ArrayList<GameSimpleViewModel>(5);
		when(gameConverterMock.convert(games)).thenReturn(viewModels);
		List<GameSimpleViewModel> response = service.getGamesForSport(SPORT_NAME);

		assertSame(viewModels, response);
	}
}
