package ca.ulaval.glo4003.web.controllers;

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

import ca.ulaval.glo4003.domain.services.SportService;
import ca.ulaval.glo4003.domain.utilities.NoSportForUrlException;
import ca.ulaval.glo4003.domain.utilities.SportUrlMapper;
import ca.ulaval.glo4003.domain.utilities.user.User;
import ca.ulaval.glo4003.persistence.daos.GameDoesntExistException;
import ca.ulaval.glo4003.persistence.daos.SportDoesntExistException;
import ca.ulaval.glo4003.web.viewmodels.GamesViewModel;
import ca.ulaval.glo4003.web.viewmodels.SportsViewModel;

@RunWith(MockitoJUnitRunner.class)
public class SportControllerTest {

	private static final String SPORT_NAME = "Basketball Féminin";
	private static final String SPORT_URL = "basketball-feminin";

	@Mock
	private SportUrlMapper sportUrlMapper;

	@Mock
	private SportService sportService;

	@Mock
	private User currentUser;
	
	@InjectMocks
	private SportController controller;

	private GamesViewModel gamesViewModel;

	@Before
	public void setUp() throws SportDoesntExistException, GameDoesntExistException, NoSportForUrlException {
		gamesViewModel = mock(GamesViewModel.class);
		when(sportUrlMapper.getSportName(SPORT_URL)).thenReturn(SPORT_NAME);
		when(gamesViewModel.hasGames()).thenReturn(true);
		when(sportService.getGamesForSport(SPORT_URL)).thenReturn(gamesViewModel);
	}

	@Test
	public void getSports_should_get_sports_from_service() {
		controller.getSports();

		verify(sportService).getSports();
	}

	@Test
	public void getSports_should_add_the_sports_to_model() {
		SportsViewModel viewModel = new SportsViewModel();
		when(sportService.getSports()).thenReturn(viewModel);

		ModelAndView mav = controller.getSports();
		ModelMap modelMap = mav.getModelMap();

		assertTrue(modelMap.containsAttribute("sports"));
		assertSame(viewModel, modelMap.get("sports"));
	}

	@Test
	public void getSports_should_return_right_path() {
		ModelAndView mav = controller.getSports();

		assertEquals("sport/list", mav.getViewName());
	}

	@Test
	public void getSportGames_should_get_games_from_service() throws Exception {

		controller.getSportGames(SPORT_URL);

		verify(sportService).getGamesForSport(SPORT_URL);
	}

	@Test
	public void getSportsGames_should_return_no_games_path_when_sport_doesnt_have_any_game() throws Exception {
		when(gamesViewModel.hasGames()).thenReturn(false);

		ModelAndView mav = controller.getSportGames(SPORT_URL);

		assertEquals("sport/no-games", mav.getViewName());
	}

	@Test
	public void getSportsGames_should_add_view_model_to_model_when_game_doesnt_have_any_game() throws Exception {
		when(gamesViewModel.hasGames()).thenReturn(false);

		ModelAndView mav = controller.getSportGames(SPORT_URL);
		ModelMap modelMap = mav.getModelMap();

		assertTrue(modelMap.containsAttribute("games"));
		assertSame(gamesViewModel, modelMap.get("games"));
	}

	@Test
	public void getSportGames_should_return_correct_path_when_games_exist() throws Exception {
		when(gamesViewModel.hasGames()).thenReturn(true);
		
		ModelAndView mav = controller.getSportGames(SPORT_URL);

		assertEquals("sport/games", mav.getViewName());
	}

	@Test
	public void getSportGames_should_add_sport_to_model_when_games_exist() throws Exception {
		when(gamesViewModel.hasGames()).thenReturn(true);
		
		ModelAndView mav = controller.getSportGames(SPORT_URL);
		ModelMap modelMap = mav.getModelMap();

		assertTrue(modelMap.containsAttribute("games"));
		assertSame(gamesViewModel, modelMap.get("games"));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void getSportGames_should_redirect_to_404_path_when_sport_doesnt_exist() throws Exception {
		when(sportService.getGamesForSport(SPORT_URL)).thenThrow(SportDoesntExistException.class);

		ModelAndView mav = controller.getSportGames(SPORT_URL);

		assertEquals("error/404", mav.getViewName());
	}
	
	@Test
	public void when_user_is_logged_getSportGames_should_add_connectedUser_at_true() throws GameDoesntExistException {
		when(currentUser.isLogged()).thenReturn(true);
		
		ModelAndView mav = controller.getSportGames(SPORT_URL);
		ModelMap modelMap = mav.getModelMap();
		
		assertTrue(modelMap.containsAttribute("connectedUser"));
		assertTrue((Boolean) modelMap.get("connectedUser"));
	}
	
	@Test
	public void when_user_isnt_logged_getSportGames_should_add_connectedUser_at_false() {
		when(currentUser.isLogged()).thenReturn(false);
		
		ModelAndView mav = controller.getSportGames(SPORT_URL);
		ModelMap modelMap = mav.getModelMap();
		
		assertTrue(modelMap.containsAttribute("connectedUser"));
		assertFalse((Boolean) modelMap.get("connectedUser"));
	}
}
