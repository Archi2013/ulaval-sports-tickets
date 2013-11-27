package ca.ulaval.glo4003.presentation.controllers;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import ca.ulaval.glo4003.domain.users.User;
import ca.ulaval.glo4003.exceptions.GameDoesntExistException;
import ca.ulaval.glo4003.presentation.controllers.GameController;
import ca.ulaval.glo4003.presentation.viewmodels.SectionsViewModel;
import ca.ulaval.glo4003.services.QueryGameService;

@RunWith(MockitoJUnitRunner.class)
public class GameControllerTest {

	private static final String SPORT_NAME = "Football";
	private static final String GAME_DATE_STR = "201408241300EDT";
	private static final DateTime GAME_DATE = DateTime.parse(GAME_DATE_STR, DateTimeFormat.forPattern("yyyyMMddHHmmz"));

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
	public void getTicketsForGame_should_get_games() throws Exception {
		gameController.getTicketsForGame(GAME_DATE_STR, SPORT_NAME);

		verify(gameService).getAvailableSectionsForGame(SPORT_NAME, GAME_DATE);
	}

	@Test
	public void getTicketsForGame_should_add_the_specified_sections_to_model() throws Exception {
		SectionsViewModel sectionsViewModel = mock(SectionsViewModel.class);
		when(gameService.getAvailableSectionsForGame(SPORT_NAME, GAME_DATE)).thenReturn(sectionsViewModel);

		ModelAndView mav = gameController.getTicketsForGame(GAME_DATE_STR, SPORT_NAME);
		ModelMap modelMap = mav.getModelMap();

		assertTrue(modelMap.containsAttribute("gameSections"));
		assertSame(sectionsViewModel, modelMap.get("gameSections"));
	}

	@Test
	public void getTicketsForGame_should_return_correct_view_path() {
		ModelAndView mav = gameController.getTicketsForGame(GAME_DATE_STR, SPORT_NAME);

		assertEquals("game/sections", mav.getViewName());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void getTicketsForGame_should_redirect_to_404_page_when_sport_name_game_date_doesnt_exist() throws Exception {
		when(gameService.getAvailableSectionsForGame(SPORT_NAME, GAME_DATE)).thenThrow(GameDoesntExistException.class);

		ModelAndView mav = gameController.getTicketsForGame(GAME_DATE_STR, SPORT_NAME);

		assertEquals("error/404", mav.getViewName());
	}
	
	@Test
	public void when_user_is_logged_home_should_add_connectedUser_at_true() {
		when(currentUser.isLogged()).thenReturn(true);
		
		ModelAndView mav = gameController.getTicketsForGame(GAME_DATE_STR, SPORT_NAME);
		ModelMap modelMap = mav.getModelMap();
		
		assertTrue(modelMap.containsAttribute("connectedUser"));
		assertTrue((Boolean) modelMap.get("connectedUser"));
	}
	
	@Test
	public void when_user_isnt_logged_home_should_add_connectedUser_at_false() {
		when(currentUser.isLogged()).thenReturn(false);
		
		ModelAndView mav = gameController.getTicketsForGame(GAME_DATE_STR, SPORT_NAME);
		ModelMap modelMap = mav.getModelMap();
		
		assertTrue(modelMap.containsAttribute("connectedUser"));
		assertFalse((Boolean) modelMap.get("connectedUser"));
	}
}
