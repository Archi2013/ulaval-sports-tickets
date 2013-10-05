package ca.ulaval.glo4003.domain.services;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4003.domain.dtos.GameDto;
import ca.ulaval.glo4003.persistence.dao.GameDao;
import ca.ulaval.glo4003.persistence.dao.GameDoesntExistException;
import ca.ulaval.glo4003.web.viewmodel.SectionsViewModel;
import ca.ulaval.glo4003.web.viewmodel.factories.SectionsViewModelFactory;

@RunWith(MockitoJUnitRunner.class)
public class QueryGameServiceTest {

	private static final long GAME_ID = 12;

	@Mock
	private GameDao gameDaoMock;

	@Mock
	private SectionsViewModelFactory gameViewModelFactory;

	@InjectMocks
	private QueryGameService service;

	@Test
	public void getGame_should_get_game_from_dao() throws GameDoesntExistException {
		service.getGame(GAME_ID);

		verify(gameDaoMock).get(GAME_ID);
	}

	@Test
	public void getGame_should_create_view_model_from_game() throws GameDoesntExistException {
		GameDto gameDto = mock(GameDto.class);
		when(gameDaoMock.get(GAME_ID)).thenReturn(gameDto);

		service.getGame(GAME_ID);

		verify(gameViewModelFactory).createViewModel(gameDto);
	}

	@Test
	public void getGame_should_return_view_model() throws GameDoesntExistException {
		GameDto gameDto = mock(GameDto.class);
		when(gameDaoMock.get(GAME_ID)).thenReturn(gameDto);
		SectionsViewModel expectedViewModel = mock(SectionsViewModel.class);
		when(gameViewModelFactory.createViewModel(gameDto)).thenReturn(expectedViewModel);

		SectionsViewModel gameViewModel = service.getGame(GAME_ID);

		assertEquals(expectedViewModel, gameViewModel);
	}
}
