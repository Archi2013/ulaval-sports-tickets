package ca.ulaval.glo4003.presentation.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import ca.ulaval.glo4003.domain.sports.SportUrlMapper;
import ca.ulaval.glo4003.domain.users.User;
import ca.ulaval.glo4003.exceptions.GameDoesntExistException;
import ca.ulaval.glo4003.exceptions.NoSportForUrlException;
import ca.ulaval.glo4003.exceptions.SportDoesntExistException;
import ca.ulaval.glo4003.presentation.viewmodels.SportsViewModel;
import ca.ulaval.glo4003.services.SportViewService;

@RunWith(MockitoJUnitRunner.class)
public class SportControllerTest {

	private static final String SPORT_NAME = "Basketball Féminin";
	private static final String SPORT_URL = "basketball-feminin";

	@Mock
	private SportUrlMapper sportUrlMapper;

	@Mock
	private SportViewService sportService;

	@Mock
	private User currentUser;

	@InjectMocks
	private SportController controller;

	@Before
	public void setUp() throws SportDoesntExistException, GameDoesntExistException, NoSportForUrlException {
		when(sportUrlMapper.getSportName(SPORT_URL)).thenReturn(SPORT_NAME);
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
}
