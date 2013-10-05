package ca.ulaval.glo4003.web.controllers;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ui.Model;

import ca.ulaval.glo4003.domain.services.SectionService;
import ca.ulaval.glo4003.persistence.daos.SectionDoesntExistException;
import ca.ulaval.glo4003.web.viewmodels.SectionViewModel;

@RunWith(MockitoJUnitRunner.class)
public class SectionControllerTest {

	public static final int GAME_ID = 123;
	private static final String TICKET_TYPE = "GÉNÉRAL:BLEUS";

	@Mock
	private Model model;

	@Mock
	private SectionService sectionService;

	@InjectMocks
	private SectionController sectionController;

	@Before
	public void setUp() {
	}

	@Test
	public void getSectionForGame_should_get_sections_from_service() throws SectionDoesntExistException {
		sectionController.getSectionForGame(GAME_ID, TICKET_TYPE, model);

		verify(sectionService).getSection(GAME_ID, TICKET_TYPE);
	}

	@Test
	public void getSectionForGame_should_add_the_specified_section_to_model() throws SectionDoesntExistException {
		SectionViewModel sectionViewModel = mock(SectionViewModel.class);
		when(sectionService.getSection(GAME_ID, TICKET_TYPE)).thenReturn(sectionViewModel);

		sectionController.getSectionForGame(GAME_ID, TICKET_TYPE, model);

		verify(model).addAttribute("section", sectionViewModel);
	}

	@Test
	public void getSectionForGame_should_return_correct_view_path() {
		String path = sectionController.getSectionForGame(GAME_ID, TICKET_TYPE, model);

		assertEquals("section/details", path);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void getTicketsForGame_should_redirect_to_404_page_when_game_id_doesnt_exist() throws SectionDoesntExistException {
		when(sectionService.getSection(GAME_ID, TICKET_TYPE)).thenThrow(SectionDoesntExistException.class);

		String path = sectionController.getSectionForGame(GAME_ID, TICKET_TYPE, model);

		assertEquals("error/404", path);
	}
}
