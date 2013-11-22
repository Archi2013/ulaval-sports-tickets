package ca.ulaval.glo4003.web.controllers;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;
import junit.framework.Assert;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import ca.ulaval.glo4003.domain.services.CommandGameService;
import ca.ulaval.glo4003.domain.services.SportService;
import ca.ulaval.glo4003.domain.utilities.DateParser;
import ca.ulaval.glo4003.domain.utilities.NoSportForUrlException;
import ca.ulaval.glo4003.domain.utilities.user.User;
import ca.ulaval.glo4003.persistence.daos.GameAlreadyExistException;
import ca.ulaval.glo4003.persistence.daos.GameDoesntExistException;
import ca.ulaval.glo4003.persistence.daos.SportDoesntExistException;
import ca.ulaval.glo4003.web.viewmodels.GameToAddViewModel;
import ca.ulaval.glo4003.web.viewmodels.SportsViewModel;

@RunWith(MockitoJUnitRunner.class)
public class AdministrationControllerTest {

	private static final DateTime A_DATE = new DateTime(100);

	@Mock
	private CommandGameService gameService;

	@Mock
	private SportService sportService;

	@Mock
	private DateParser dateParser;

	@Mock
	private SportsViewModel sportsVM;

	@Mock
	private User currentUser;

	private GameToAddViewModel gameToAddVM = new GameToAddViewModel();

	@InjectMocks
	private AdministrationController controller;

	@Before
	public void setUp() {
		when(dateParser.parseDate(any(String.class))).thenReturn(A_DATE);
	}

	@Test
	public void home_returns_the_home_administration_view_when_admin() {
		when(currentUser.isAdmin()).thenReturn(true);
		when(currentUser.isLogged()).thenReturn(true);

		ModelAndView mav = controller.home();

		Assert.assertEquals("admin/home", mav.getViewName());
	}

	@Test
	public void home_redirect_site_home_when_not_admin() {
		when(currentUser.isAdmin()).thenReturn(false);
		ModelAndView mav = controller.home();

		assertEquals("redirect:/", mav.getViewName());
	}

	@Test
	public void when_user_isnt_logged_home_should_add_connectedUser_at_false() {
		when(currentUser.isAdmin()).thenReturn(true);
		when(currentUser.isLogged()).thenReturn(false);

		ModelAndView mav = controller.home();

		assertEquals("redirect:/", mav.getViewName());
	}

	@Test
	public void game_returns_the_form_to_add_a_game() {
		when(currentUser.isAdmin()).thenReturn(true);
		when(currentUser.isLogged()).thenReturn(true);

		ModelAndView mav = controller.game();

		Assert.assertEquals("admin/game", mav.getViewName());
	}

	@Test
	public void game_adds_a_sportsVM_to_model() {
		when(currentUser.isAdmin()).thenReturn(true);
		when(currentUser.isLogged()).thenReturn(true);

		ModelAndView model = controller.game();
		ModelMap modelMap = model.getModelMap();

		Assert.assertTrue(modelMap.containsAttribute("sportsVM"));
	}

	@Test
	public void when_user_is_logged_game_should_add_connectedUser_at_true() {
		when(currentUser.isLogged()).thenReturn(true);
		when(currentUser.isAdmin()).thenReturn(true);

		ModelAndView mav = controller.game();
		ModelMap modelMap = mav.getModelMap();

		assertTrue(modelMap.containsAttribute("sportsVM"));
		assertEquals(modelMap.get("sportsVM"), sportService.getSports());
	}

	@Test
	public void when_user_isnt_logged_game_should_add_connectedUser_at_false() {
		when(currentUser.isLogged()).thenReturn(false);
		when(currentUser.isAdmin()).thenReturn(true);

		ModelAndView mav = controller.game();

		assertEquals("redirect:/", mav.getViewName());
	}

	@Test
	public void addGame_adds_game_to_add_to_model() {
		when(currentUser.isAdmin()).thenReturn(true);
		when(currentUser.isLogged()).thenReturn(true);

		ModelAndView mav = controller.addGame(gameToAddVM);
		ModelMap modelMap = mav.getModelMap();

		assertTrue(modelMap.containsAttribute("game"));
		assertSame(gameToAddVM, modelMap.get("game"));
	}

	@Test
	public void addGame_returns_confirmation_view_if_service_throws_nothing() {
		when(currentUser.isAdmin()).thenReturn(true);
		when(currentUser.isLogged()).thenReturn(true);

		ModelAndView mav = controller.addGame(gameToAddVM);

		Assert.assertEquals("admin/game-added", mav.getViewName());
	}

	@Test
	public void addGame_returns_error_view_if_service_throws_exception() throws SportDoesntExistException,
			GameDoesntExistException, GameAlreadyExistException, NoSportForUrlException {
		doThrow(new SportDoesntExistException()).when(gameService).createNewGame(any(String.class), any(String.class),
				any(DateTime.class));
		when(currentUser.isAdmin()).thenReturn(true);
		when(currentUser.isLogged()).thenReturn(true);

		ModelAndView mav = controller.addGame(gameToAddVM);

		Assert.assertEquals("admin/game-added-data-error", mav.getViewName());
	}

	@Test
	public void when_user_is_logged_addGame_should_add_connectedUser_at_true() {
		when(currentUser.isLogged()).thenReturn(true);
		when(currentUser.isAdmin()).thenReturn(true);

		ModelAndView mav = controller.addGame(gameToAddVM);
		ModelMap modelMap = mav.getModelMap();

		assertTrue(modelMap.containsAttribute("game"));
		assertEquals(modelMap.get("game"), gameToAddVM);
	}

	@Test
	public void when_user_isnt_logged_addGame_should_add_connectedUser_at_false() {
		when(currentUser.isLogged()).thenReturn(false);

		ModelAndView mav = controller.addGame(gameToAddVM);

		assertEquals("redirect:/", mav.getViewName());
	}
}