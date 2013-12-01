package ca.ulaval.glo4003.presentation.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import ca.ulaval.glo4003.exceptions.SportDoesntExistException;
import ca.ulaval.glo4003.presentation.viewmodels.GamesViewModel;
import ca.ulaval.glo4003.services.QueryGameService;

@RunWith(MockitoJUnitRunner.class)
public class GameControllerTest {

	private static final String SPORT_NAME = "Football";
	private static final String SPORT_URL = "basketball-feminin";
	private static final String GAME_DATE_STR = "201408241300EDT";
	private static final DateTime GAME_DATE = DateTime.parse(GAME_DATE_STR, DateTimeFormat.forPattern("yyyyMMddHHmmz"));

	@Mock
	private QueryGameService gameService;

	@Mock
	private User currentUser;

	@InjectMocks
	private GameController gameController;

	private GamesViewModel gamesViewModel;

	@Before
	public void setUp() throws SportDoesntExistException, GameDoesntExistException {
		gamesViewModel = mock(GamesViewModel.class);
		when(gamesViewModel.hasGames()).thenReturn(true);
		when(gameService.getGamesForSport(SPORT_URL)).thenReturn(gamesViewModel);
	}

	@Test
	public void getSportGames_should_get_games_from_service() throws Exception {

		gameController.getGamesForSport(SPORT_URL);

		verify(gameService).getGamesForSport(SPORT_URL);
	}

	@Test
	public void getSportsGames_should_return_no_games_path_when_sport_doesnt_have_any_game() throws Exception {
		when(gamesViewModel.hasGames()).thenReturn(false);

		ModelAndView mav = gameController.getGamesForSport(SPORT_URL);

		assertEquals("sport/no-games", mav.getViewName());
	}

	@Test
	public void getSportsGames_should_add_view_model_to_model_when_game_doesnt_have_any_game() throws Exception {
		when(gamesViewModel.hasGames()).thenReturn(false);

		ModelAndView mav = gameController.getGamesForSport(SPORT_URL);
		ModelMap modelMap = mav.getModelMap();

		assertTrue(modelMap.containsAttribute("games"));
		assertSame(gamesViewModel, modelMap.get("games"));
	}

	@Test
	public void getSportGames_should_return_correct_path_when_games_exist() throws Exception {
		when(gamesViewModel.hasGames()).thenReturn(true);

		ModelAndView mav = gameController.getGamesForSport(SPORT_URL);

		assertEquals("sport/games", mav.getViewName());
	}

	@Test
	public void getSportGames_should_add_sport_to_model_when_games_exist() throws Exception {
		when(gamesViewModel.hasGames()).thenReturn(true);

		ModelAndView mav = gameController.getGamesForSport(SPORT_URL);
		ModelMap modelMap = mav.getModelMap();

		assertTrue(modelMap.containsAttribute("games"));
		assertSame(gamesViewModel, modelMap.get("games"));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void getSportGames_should_redirect_to_404_path_when_sport_doesnt_exist() throws Exception {
		when(gameService.getGamesForSport(SPORT_URL)).thenThrow(SportDoesntExistException.class);

		ModelAndView mav = gameController.getGamesForSport(SPORT_URL);

		assertEquals("error/404", mav.getViewName());
	}

	@Test
	public void when_user_is_logged_getSportGames_should_add_connectedUser_at_true() throws GameDoesntExistException {
		when(currentUser.isLogged()).thenReturn(true);

		ModelAndView mav = gameController.getGamesForSport(SPORT_URL);
		ModelMap modelMap = mav.getModelMap();

		assertTrue(modelMap.containsAttribute("connectedUser"));
		assertTrue((Boolean) modelMap.get("connectedUser"));
	}

	@Test
	public void when_user_isnt_logged_getSportGames_should_add_connectedUser_at_false() {
		when(currentUser.isLogged()).thenReturn(false);

		ModelAndView mav = gameController.getGamesForSport(SPORT_URL);
		ModelMap modelMap = mav.getModelMap();

		assertTrue(modelMap.containsAttribute("connectedUser"));
		assertFalse((Boolean) modelMap.get("connectedUser"));
	}
}
