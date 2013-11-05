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

import ca.ulaval.glo4003.domain.services.SectionService;
import ca.ulaval.glo4003.persistence.daos.SectionDoesntExistException;
import ca.ulaval.glo4003.web.viewmodels.ChooseTicketsViewModel;
import ca.ulaval.glo4003.web.viewmodels.SectionViewModel;

@RunWith(MockitoJUnitRunner.class)
public class SectionControllerTest {

	public static final Long GAME_ID = 123L;
	private static final String TICKET_TYPE = "GÉNÉRAL:BLEUS";

	@Mock
	private SectionService sectionService;

	@InjectMocks
	private SectionController sectionController;

	@Before
	public void setUp() {
	}

	@Test
	public void getSectionForGame_should_get_sections_from_service() throws SectionDoesntExistException {
		sectionController.getSectionForGame(GAME_ID, TICKET_TYPE);

		verify(sectionService).getSection(GAME_ID, TICKET_TYPE);
	}

	@Test
	public void getSectionForGame_should_add_the_specified_section_to_model() throws SectionDoesntExistException {
		SectionViewModel sectionViewModel = mock(SectionViewModel.class);
		when(sectionService.getSection(GAME_ID, TICKET_TYPE)).thenReturn(sectionViewModel);

		ModelAndView mav = sectionController.getSectionForGame(GAME_ID, TICKET_TYPE);
		ModelMap modelMap = mav.getModelMap();

		assertEquals(sectionViewModel, modelMap.get("section"));
	}
	
	@Test
	public void getSectionForGame_should_add_a_chooseTicketsForm_to_model() throws SectionDoesntExistException {
		ChooseTicketsViewModel chooseTicketsVM = mock(ChooseTicketsViewModel.class);
		when(sectionService.getChooseTicketsViewModel(GAME_ID, TICKET_TYPE)).thenReturn(chooseTicketsVM);

		ModelAndView mav = sectionController.getSectionForGame(GAME_ID, TICKET_TYPE);
		ModelMap modelMap = mav.getModelMap();

		assertEquals(chooseTicketsVM, modelMap.get("chooseTicketsForm"));
	}

	@Test
	public void getSectionForGame_should_return_correct_view_path() {
		ModelAndView mav = sectionController.getSectionForGame(GAME_ID, TICKET_TYPE);

		assertEquals("section/details", mav.getViewName());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void getTicketsForGame_should_redirect_to_404_page_when_section_doesnt_exist() throws SectionDoesntExistException {
		when(sectionService.getSection(GAME_ID, TICKET_TYPE)).thenThrow(SectionDoesntExistException.class);

		ModelAndView mav = sectionController.getSectionForGame(GAME_ID, TICKET_TYPE);

		assertEquals("error/404", mav.getViewName());
	}
}
