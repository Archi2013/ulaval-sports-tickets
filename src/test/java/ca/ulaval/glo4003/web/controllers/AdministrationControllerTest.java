package ca.ulaval.glo4003.web.controllers;

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
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import ca.ulaval.glo4003.domain.services.CommandGameService;
import ca.ulaval.glo4003.domain.services.SportService;
import ca.ulaval.glo4003.domain.utilities.DateParser;
import ca.ulaval.glo4003.domain.utilities.NoSportForUrlException;
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
	private Model model;

	@Mock
	private DateParser dateParser;

	@Mock
	private SportsViewModel sportsVM;
	
	private GameToAddViewModel gameToAddVM = new GameToAddViewModel();

	@InjectMocks
	private AdministrationController controller;

	@Before
	public void setUp() {
		when(dateParser.parseDate(any(String.class))).thenReturn(A_DATE);
	}

	@Test
	public void home_returns_the_home_administration_view() {
		String viewReturned = controller.home();

		Assert.assertEquals("admin/home", viewReturned);
	}

	@Test
	public void game_returns_the_form_to_add_a_game() {
		ModelAndView model = controller.game();

		Assert.assertEquals("admin/game", model.getViewName());
	}

	@Test
	public void game_adds_a_sportsVM_to_model() {
		ModelAndView model = controller.game();
		ModelMap modelMap = model.getModelMap();

		Assert.assertTrue(modelMap.containsAttribute("sportsVM"));
	}
	
	@Test
	public void addGame_adds_game_to_add_to_model() {
		controller.addGame(gameToAddVM, model);

		verify(model).addAttribute("game", gameToAddVM);
	}

	@Test
	public void addGame_returns_confirmation_view_if_service_throws_nothing() {
		String viewReturned = controller.addGame(gameToAddVM, model);

		Assert.assertEquals("admin/game-added", viewReturned);
	}

	@Test
	public void addGame_returns_error_view_if_service_throws_exception() throws SportDoesntExistException,
			GameDoesntExistException, GameAlreadyExistException, NoSportForUrlException {
		doThrow(new SportDoesntExistException()).when(gameService).createNewGame(any(String.class), any(String.class),
				any(DateTime.class));
		String viewReturned = controller.addGame(gameToAddVM, model);

		Assert.assertEquals("admin/game-added-data-error", viewReturned);
	}
}
