package ca.ulaval.glo4003.web.controllers;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ui.Model;

import ca.ulaval.glo4003.domain.services.GameService;
import ca.ulaval.glo4003.persistence.dao.GameDoesntExistException;
import ca.ulaval.glo4003.web.viewmodel.GameViewModel;

@RunWith(MockitoJUnitRunner.class)
public class GameControllerTest {

	public static final int GAME_ID = 123;
	public static final String A_SPORT_NAME = "SportName";

	@Mock
	private Model model;

	@Mock
	private GameService gameService;

	@InjectMocks
	private GameController gameController;

	@Before
	public void setUp() {
	}

	@Test
	public void getTicketsForGame_should_get_games() throws GameDoesntExistException {
		gameController.getTicketsForGame(GAME_ID, A_SPORT_NAME, model);

		verify(gameService).getGame(GAME_ID);
	}

	@Test
	public void getTicketsForGame_should_add_the_specified_game_to_model() throws GameDoesntExistException {
		GameViewModel gameViewModel = mock(GameViewModel.class);
		when(gameService.getGame(GAME_ID)).thenReturn(gameViewModel);

		gameController.getTicketsForGame(GAME_ID, A_SPORT_NAME, model);

		verify(model).addAttribute("game", gameViewModel);
	}

	@Test
	public void getTicketsForGame_should_return_correct_view_path() {
		String path = gameController.getTicketsForGame(GAME_ID, A_SPORT_NAME, model);

		assertEquals("game/sections", path);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void getTicketsForGame_should_redirect_to_404_page_when_game_id_doesnt_exist() throws GameDoesntExistException {
		when(gameService.getGame(GAME_ID)).thenThrow(GameDoesntExistException.class);

		String path = gameController.getTicketsForGame(GAME_ID, A_SPORT_NAME, model);

		assertEquals("error/404", path);
	}
}
