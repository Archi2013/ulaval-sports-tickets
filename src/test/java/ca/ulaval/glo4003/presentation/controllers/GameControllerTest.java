package ca.ulaval.glo4003.presentation.controllers;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import ca.ulaval.glo4003.domain.services.QueryGameService;
import ca.ulaval.glo4003.domain.utilities.user.User;
import ca.ulaval.glo4003.persistence.daos.GameDoesntExistException;
import ca.ulaval.glo4003.presentation.controllers.GameController;
import ca.ulaval.glo4003.presentation.viewmodels.SectionsViewModel;

@RunWith(MockitoJUnitRunner.class)
public class GameControllerTest {

	public static final Long GAME_ID = 123L;
	public static final String A_SPORT_NAME = "SportName";

	@Mock
	private QueryGameService gameService;

	@Mock
	private User currentUser;
	
	@InjectMocks
	private GameController gameController;

	@Before
	public void setUp() {
	}

	@Test
	public void getTicketsForGame_should_get_games() throws GameDoesntExistException {
		gameController.getTicketsForGame(GAME_ID, A_SPORT_NAME);

		verify(gameService).getAvailableSectionsForGame(GAME_ID);
	}

	@Test
	public void getTicketsForGame_should_add_the_specified_sections_to_model() throws GameDoesntExistException {
		SectionsViewModel sectionsViewModel = mock(SectionsViewModel.class);
		when(gameService.getAvailableSectionsForGame(GAME_ID)).thenReturn(sectionsViewModel);

		ModelAndView mav = gameController.getTicketsForGame(GAME_ID, A_SPORT_NAME);
		ModelMap modelMap = mav.getModelMap();

		assertTrue(modelMap.containsAttribute("gameSections"));
		assertSame(sectionsViewModel, modelMap.get("gameSections"));
	}

	@Test
	public void getTicketsForGame_should_return_correct_view_path() {
		ModelAndView mav = gameController.getTicketsForGame(GAME_ID, A_SPORT_NAME);

		assertEquals("game/sections", mav.getViewName());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void getTicketsForGame_should_redirect_to_404_page_when_game_id_doesnt_exist() throws GameDoesntExistException {
		when(gameService.getAvailableSectionsForGame(GAME_ID)).thenThrow(GameDoesntExistException.class);

		ModelAndView mav = gameController.getTicketsForGame(GAME_ID, A_SPORT_NAME);

		assertEquals("error/404", mav.getViewName());
	}
	
	@Test
	public void when_user_is_logged_home_should_add_connectedUser_at_true() {
		when(currentUser.isLogged()).thenReturn(true);
		
		ModelAndView mav = gameController.getTicketsForGame(GAME_ID, A_SPORT_NAME);
		ModelMap modelMap = mav.getModelMap();
		
		assertTrue(modelMap.containsAttribute("connectedUser"));
		assertTrue((Boolean) modelMap.get("connectedUser"));
	}
	
	@Test
	public void when_user_isnt_logged_home_should_add_connectedUser_at_false() {
		when(currentUser.isLogged()).thenReturn(false);
		
		ModelAndView mav = gameController.getTicketsForGame(GAME_ID, A_SPORT_NAME);
		ModelMap modelMap = mav.getModelMap();
		
		assertTrue(modelMap.containsAttribute("connectedUser"));
		assertFalse((Boolean) modelMap.get("connectedUser"));
	}
}
