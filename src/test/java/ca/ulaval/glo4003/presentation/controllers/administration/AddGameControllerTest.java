package ca.ulaval.glo4003.presentation.controllers.administration;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import javax.servlet.http.HttpSession;

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

import ca.ulaval.glo4003.domain.users.User;
import ca.ulaval.glo4003.exceptions.SportDoesntExistException;
import ca.ulaval.glo4003.presentation.viewmodels.GameToAddViewModel;
import ca.ulaval.glo4003.services.CommandGameService;
import ca.ulaval.glo4003.services.CommandTicketService;
import ca.ulaval.glo4003.services.SportViewService;
import ca.ulaval.glo4003.utilities.time.InputDate;

@RunWith(MockitoJUnitRunner.class)
public class AddGameControllerTest {

	@Mock
	private InputDate AN_INPUT_DATE;

	@Mock
	CommandTicketService ticketService;

	@Mock
	private CommandGameService gameService;

	@Mock
	private SportViewService sportService;
	
	@Mock
	private User currentUser;
	
	@Mock
	private HttpSession session;

	private GameToAddViewModel gameToAddVM = new GameToAddViewModel();

	@InjectMocks
	private AddGameController controller;

	@Before
	public void setUp() {
		gameToAddVM.setDate(AN_INPUT_DATE);
		when(session.getAttribute("currentUser")).thenReturn(currentUser);
	}
	
	@Test
	public void game_returns_the_form_to_add_a_game() {
		when(currentUser.isAdmin()).thenReturn(true);
		when(currentUser.isLogged()).thenReturn(true);
		
		ModelAndView mav = controller.game(session);

		Assert.assertEquals("admin/game", mav.getViewName());
	}

	@Test
	public void game_adds_a_sportsVM_to_model() {
		when(currentUser.isAdmin()).thenReturn(true);
		when(currentUser.isLogged()).thenReturn(true);
		
		ModelAndView model = controller.game(session);
		ModelMap modelMap = model.getModelMap();

		Assert.assertTrue(modelMap.containsAttribute("sportsVM"));
	}

	@Test
	public void addGame_adds_game_to_add_to_model() {
		when(currentUser.isAdmin()).thenReturn(true);
		when(currentUser.isLogged()).thenReturn(true);
		
		ModelAndView mav = controller.addGame(session, gameToAddVM);
		ModelMap modelMap = mav.getModelMap();

		assertTrue(modelMap.containsAttribute("game"));
		assertSame(gameToAddVM, modelMap.get("game"));
	}

	@Test
	public void addGame_returns_confirmation_view_if_service_throws_nothing() {
		when(currentUser.isAdmin()).thenReturn(true);
		when(currentUser.isLogged()).thenReturn(true);
		
		ModelAndView mav = controller.addGame(session, gameToAddVM);

		Assert.assertEquals("admin/game-added", mav.getViewName());
	}

	@Test
	public void addGame_returns_error_view_if_service_throws_exception() throws Exception {
		when(currentUser.isAdmin()).thenReturn(true);
		when(currentUser.isLogged()).thenReturn(true);
		
		doThrow(new SportDoesntExistException()).when(gameService).createNewGame(any(String.class), any(String.class),
				any(String.class), any(DateTime.class));
		ModelAndView mav = controller.addGame(session, gameToAddVM);

		Assert.assertEquals("admin/game-added-data-error", mav.getViewName());
	}
}
