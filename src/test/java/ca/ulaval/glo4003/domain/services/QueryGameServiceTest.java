package ca.ulaval.glo4003.domain.services;

import static com.google.common.collect.Lists.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4003.domain.dtos.GameDto;
import ca.ulaval.glo4003.domain.dtos.SectionDto;
import ca.ulaval.glo4003.persistence.daos.GameDao;
import ca.ulaval.glo4003.persistence.daos.GameDoesntExistException;
import ca.ulaval.glo4003.persistence.daos.SectionDao;
import ca.ulaval.glo4003.presentation.viewmodels.SectionsViewModel;
import ca.ulaval.glo4003.presentation.viewmodels.factories.SectionsViewModelFactory;

@RunWith(MockitoJUnitRunner.class)
public class QueryGameServiceTest {

	private static final Long GAME_ID = 12L;

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
		when(gameDaoMock.get(GAME_ID)).thenReturn(gameDto);

		sectionDtos = newArrayList();
		when(sectionDaoMock.getAll(GAME_ID)).thenReturn(sectionDtos);

	}

	@Test
	public void getSectionsForGame_should_get_game_from_dao() throws GameDoesntExistException {
		service.getSectionsForGame(GAME_ID);

		verify(gameDaoMock).get(GAME_ID);
	}

	@Test
	public void getSectionsForGame_should_get_sections_for_game_from_dao() throws GameDoesntExistException {
		service.getSectionsForGame(GAME_ID);

		verify(sectionDaoMock).getAll(GAME_ID);
	}

	@Test
	public void getSectionsForGame_should_create_view_model_from_game() throws GameDoesntExistException {

		service.getSectionsForGame(GAME_ID);

		verify(gameViewModelFactory).createViewModel(gameDto, sectionDtos);
	}

	@Test
	public void getSectionsForGame_should_return_view_model() throws GameDoesntExistException {
		SectionsViewModel expectedViewModel = mock(SectionsViewModel.class);
		when(gameViewModelFactory.createViewModel(gameDto, sectionDtos)).thenReturn(expectedViewModel);

		SectionsViewModel gameViewModel = service.getSectionsForGame(GAME_ID);

		assertEquals(expectedViewModel, gameViewModel);
	}
}
