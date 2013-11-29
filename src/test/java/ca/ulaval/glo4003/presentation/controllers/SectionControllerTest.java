package ca.ulaval.glo4003.presentation.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
import ca.ulaval.glo4003.exceptions.SectionDoesntExistException;
import ca.ulaval.glo4003.presentation.viewmodels.ChosenTicketsViewModel;
import ca.ulaval.glo4003.presentation.viewmodels.SectionViewModel;
import ca.ulaval.glo4003.services.SectionService;

@RunWith(MockitoJUnitRunner.class)
public class SectionControllerTest {

	private static final String SPORT_NAME = "Football";
	private static final String GAME_DATE_STR = "201408241300EDT";
	private static final DateTime GAME_DATE = DateTime.parse(GAME_DATE_STR, DateTimeFormat.forPattern("yyyyMMddHHmmz"));
	private static final String TICKET_TYPE = "GÉNÉRAL:BLEUS";

	@Mock
	private SectionService sectionService;

	@Mock
	private User currentUser;

	@InjectMocks
	private SectionController sectionController;

	@Before
	public void setUp() {
	}

	@Test
	public void getSectionForGame_should_get_sections_from_service() throws SectionDoesntExistException {
		sectionController.getSectionForGame(GAME_DATE_STR, SPORT_NAME, TICKET_TYPE);

		verify(sectionService).getAvailableSection(SPORT_NAME, GAME_DATE, TICKET_TYPE);
	}

	@Test
	public void getSectionForGame_should_add_the_specified_section_to_model() throws SectionDoesntExistException {
		SectionViewModel sectionViewModel = mock(SectionViewModel.class);
		when(sectionService.getAvailableSection(SPORT_NAME, GAME_DATE, TICKET_TYPE)).thenReturn(sectionViewModel);

		ModelAndView mav = sectionController.getSectionForGame(GAME_DATE_STR, SPORT_NAME, TICKET_TYPE);
		ModelMap modelMap = mav.getModelMap();

		assertEquals(sectionViewModel, modelMap.get("section"));
	}

	@Test
	public void getSectionForGame_should_add_a_chooseTicketsForm_to_model() throws SectionDoesntExistException {
		ChosenTicketsViewModel chooseTicketsVM = mock(ChosenTicketsViewModel.class);
		when(sectionService.getChooseTicketsViewModel(SPORT_NAME, GAME_DATE, TICKET_TYPE)).thenReturn(chooseTicketsVM);

		ModelAndView mav = sectionController.getSectionForGame(GAME_DATE_STR, SPORT_NAME, TICKET_TYPE);
		ModelMap modelMap = mav.getModelMap();

		assertEquals(chooseTicketsVM, modelMap.get("chooseTicketsForm"));
	}

	@Test
	public void getSectionForGame_should_return_correct_view_path() {
		ModelAndView mav = sectionController.getSectionForGame(GAME_DATE_STR, SPORT_NAME, TICKET_TYPE);

		assertEquals("section/details", mav.getViewName());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void getSectionForGame_should_redirect_to_404_page_when_section_doesnt_exist() throws SectionDoesntExistException {
		when(sectionService.getAvailableSection(SPORT_NAME, GAME_DATE, TICKET_TYPE)).thenThrow(SectionDoesntExistException.class);

		ModelAndView mav = sectionController.getSectionForGame(GAME_DATE_STR, SPORT_NAME, TICKET_TYPE);

		assertEquals("error/404", mav.getViewName());
	}

	@Test
	public void when_user_is_logged_getSectionForGame_should_add_connectedUser_at_true() {
		when(currentUser.isLogged()).thenReturn(true);

		ModelAndView mav = sectionController.getSectionForGame(GAME_DATE_STR, SPORT_NAME, TICKET_TYPE);
		ModelMap modelMap = mav.getModelMap();

		assertTrue(modelMap.containsAttribute("connectedUser"));
		assertTrue((Boolean) modelMap.get("connectedUser"));
	}

	@Test
	public void when_user_isnt_logged_getSectionForGame_should_add_connectedUser_at_false() {
		when(currentUser.isLogged()).thenReturn(false);

		ModelAndView mav = sectionController.getSectionForGame(GAME_DATE_STR, SPORT_NAME, TICKET_TYPE);
		ModelMap modelMap = mav.getModelMap();

		assertTrue(modelMap.containsAttribute("connectedUser"));
		assertFalse((Boolean) modelMap.get("connectedUser"));
	}
}
