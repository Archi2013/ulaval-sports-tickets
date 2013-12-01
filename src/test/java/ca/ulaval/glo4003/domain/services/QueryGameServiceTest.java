package ca.ulaval.glo4003.domain.services;

import static com.google.common.collect.Lists.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4003.domain.game.GameDao;
import ca.ulaval.glo4003.domain.game.GameDto;
import ca.ulaval.glo4003.domain.sections.SectionDao;
import ca.ulaval.glo4003.domain.sections.SectionDto;
import ca.ulaval.glo4003.exceptions.GameDoesntExistException;
import ca.ulaval.glo4003.presentation.viewmodels.SectionsViewModel;
import ca.ulaval.glo4003.presentation.viewmodels.factories.SectionsViewModelFactory;
import ca.ulaval.glo4003.services.QueryGameService;

@Ignore
@RunWith(MockitoJUnitRunner.class)
public class QueryGameServiceTest {

	private static final String SPORT_NAME = "Football";
	private static final DateTime GAME_DATE = DateTime.now();

	@Mock
	private GameDao gameDaoMock;

	@Mock
	private SectionDao sectionDaoMock;

	@Mock
	private SectionsViewModelFactory gameViewModelFactory;

	@InjectMocks
	private QueryGameService service;

	private GameDto gameDto;
	private List<SectionDto> sectionDtos;

	@Before
	public void setup() throws GameDoesntExistException {
		gameDto = mock(GameDto.class);
		when(gameDaoMock.get(SPORT_NAME, GAME_DATE)).thenReturn(gameDto);

		sectionDtos = newArrayList();
		when(sectionDaoMock.getAll(SPORT_NAME, GAME_DATE)).thenReturn(sectionDtos);
		

	}

	@Test
	public void getSectionsForGame_should_get_game_from_dao() throws Exception {
		service.getAvailableSectionsForGame(SPORT_NAME, GAME_DATE);

		verify(gameDaoMock).get(SPORT_NAME, GAME_DATE);
	}

	@Test
	public void getSectionsForGame_should_get_sections_for_game_from_dao() throws Exception {
		service.getAvailableSectionsForGame(SPORT_NAME, GAME_DATE);

		verify(sectionDaoMock).getAllAvailable(SPORT_NAME, GAME_DATE);
	}

	@Test
	public void getSectionsForGame_should_create_view_model_from_game() throws Exception {

		service.getAvailableSectionsForGame(SPORT_NAME, GAME_DATE);

		verify(gameViewModelFactory).createViewModel(gameDto, sectionDtos);
	}

	@Test
	public void getSectionsForGame_should_return_view_model() throws Exception {
		SectionsViewModel expectedViewModel = mock(SectionsViewModel.class);
		when(gameViewModelFactory.createViewModel(gameDto, sectionDtos)).thenReturn(expectedViewModel);

		SectionsViewModel gameViewModel = service.getAvailableSectionsForGame(SPORT_NAME, GAME_DATE);

		assertEquals(expectedViewModel, gameViewModel);
	}
}
