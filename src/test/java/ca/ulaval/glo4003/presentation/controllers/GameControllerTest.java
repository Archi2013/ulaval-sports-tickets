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

import ca.ulaval.glo4003.exceptions.GameDoesntExistException;
import ca.ulaval.glo4003.exceptions.NoSportForUrlException;
import ca.ulaval.glo4003.exceptions.SportDoesntExistException;
import ca.ulaval.glo4003.presentation.viewmodels.GamesViewModel;
import ca.ulaval.glo4003.services.GameViewService;
import ca.ulaval.glo4003.utilities.urlmapper.SportUrlMapper;

@RunWith(MockitoJUnitRunner.class)
public class GameControllerTest {

	private static final String SPORT_NAME = "Basketball féminin";
	private static final String SPORT_URL = "basketball-feminin";

	@Mock
	private GameViewService gameService;

	@Mock
	private SportUrlMapper sportUrlMapper;
	
	@InjectMocks
	private GameController gameController;

	private GamesViewModel gamesViewModel;

	@Before
	public void setUp() throws SportDoesntExistException, GameDoesntExistException, NoSportForUrlException {
		when(sportUrlMapper.getSportName(SPORT_URL)).thenReturn(SPORT_NAME);
		gamesViewModel = mock(GamesViewModel.class);
		when(gamesViewModel.hasGames()).thenReturn(true);
		when(gameService.getGamesForSport(SPORT_NAME)).thenReturn(gamesViewModel);
	}

	@Test
	public void getSportGames_should_get_games_from_service() throws Exception {
		gameController.getGamesForSport(SPORT_URL);

		verify(gameService).getGamesForSport(SPORT_NAME);
	}

	@Test
	public void getSportsGames_should_return_no_games_path_when_sport_doesnt_have_any_game() throws Exception {
		when(gamesViewModel.hasGames()).thenReturn(false);

		ModelAndView mav = gameController.getGamesForSport(SPORT_URL);

		assertEquals("game/no-games", mav.getViewName());
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

		assertEquals("game/list", mav.getViewName());
	}

	@Test
	public void getSportGames_should_add_sport_to_model_when_games_exist() throws Exception {
		when(gamesViewModel.hasGames()).thenReturn(true);

		ModelAndView mav = gameController.getGamesForSport(SPORT_URL);
		ModelMap modelMap = mav.getModelMap();

		assertTrue(modelMap.containsAttribute("games"));
		assertSame(gamesViewModel, modelMap.get("games"));
	}

	@Test
	public void getSportGames_should_redirect_to_404_path_when_NoSportForUrlException() throws Exception {
		when(sportUrlMapper.getSportName(SPORT_URL)).thenThrow(new NoSportForUrlException());

		ModelAndView mav = gameController.getGamesForSport(SPORT_URL);

		assertEquals("error/404", mav.getViewName());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void getSportGames_should_redirect_to_404_path_when_SportDoesntExistException() throws Exception {
		when(gameService.getGamesForSport(SPORT_NAME)).thenThrow(SportDoesntExistException.class);

		ModelAndView mav = gameController.getGamesForSport(SPORT_URL);

		assertEquals("error/404", mav.getViewName());
	}
}
