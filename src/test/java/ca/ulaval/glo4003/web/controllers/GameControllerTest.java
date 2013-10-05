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

import ca.ulaval.glo4003.domain.services.QueryGameService;
import ca.ulaval.glo4003.persistence.daos.GameDoesntExistException;
import ca.ulaval.glo4003.web.viewmodels.SectionsViewModel;

@RunWith(MockitoJUnitRunner.class)
public class GameControllerTest {

	public static final int GAME_ID = 123;
	public static final String A_SPORT_NAME = "SportName";

	@Mock
	private Model model;

	@Mock
	private QueryGameService gameService;

	@InjectMocks
	private GameController gameController;

	@Before
	public void setUp() {
	}

	@Test
	public void getTicketsForGame_should_get_games() throws GameDoesntExistException {
		gameController.getTicketsForGame(GAME_ID, A_SPORT_NAME, model);

		verify(gameService).getSectionsForGame(GAME_ID);
	}

	@Test
	public void getTicketsForGame_should_add_the_specified_sections_to_model() throws GameDoesntExistException {
		SectionsViewModel sectionsViewModel = mock(SectionsViewModel.class);
		when(gameService.getSectionsForGame(GAME_ID)).thenReturn(sectionsViewModel);

		gameController.getTicketsForGame(GAME_ID, A_SPORT_NAME, model);

		verify(model).addAttribute("gameSections", sectionsViewModel);
	}

	@Test
	public void getTicketsForGame_should_return_correct_view_path() {
		String path = gameController.getTicketsForGame(GAME_ID, A_SPORT_NAME, model);

		assertEquals("game/sections", path);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void getTicketsForGame_should_redirect_to_404_page_when_game_id_doesnt_exist() throws GameDoesntExistException {
		when(gameService.getSectionsForGame(GAME_ID)).thenThrow(GameDoesntExistException.class);

		String path = gameController.getTicketsForGame(GAME_ID, A_SPORT_NAME, model);

		assertEquals("error/404", path);
	}
}
