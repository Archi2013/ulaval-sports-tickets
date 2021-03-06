package ca.ulaval.glo4003.presentation.controllers;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.joda.time.DateTime;
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
import ca.ulaval.glo4003.exceptions.SectionDoesntExistException;
import ca.ulaval.glo4003.presentation.viewmodels.ChosenGeneralTicketsViewModel;
import ca.ulaval.glo4003.presentation.viewmodels.ChosenWithSeatTicketsViewModel;
import ca.ulaval.glo4003.presentation.viewmodels.SectionViewModel;
import ca.ulaval.glo4003.presentation.viewmodels.SectionsViewModel;
import ca.ulaval.glo4003.services.SectionService;
import ca.ulaval.glo4003.utilities.time.UrlDateTime;
import ca.ulaval.glo4003.utilities.time.UrlDateTimeFactory;
import ca.ulaval.glo4003.utilities.urlmapper.SportUrlMapper;

@RunWith(MockitoJUnitRunner.class)
public class SectionControllerTest {

	private static final String SPORT_URL = "football";
	private static final String SPORT_NAME = "Football";
	private static final String GAME_DATE_STR = "1998-02-28--16-30-EST";
	private static final DateTime GAME_DATE = DateTime.parse("1998-02-28T16:30:00.000-05:00");
	private static final String TICKET_TYPE = "GÉNÉRAL:BLEUS";

	@Mock
	private SectionService sectionService;
	
	@Mock
	private SportUrlMapper sportUrlMapper;

	@Mock
	private UrlDateTimeFactory urlDateTimeFactory;
	
	@InjectMocks
	private SectionController sectionController;

	@Before
	public void setUp() {
		UrlDateTime urlDateTime = mock(UrlDateTime.class);
		when(urlDateTimeFactory.create(GAME_DATE_STR)).thenReturn(urlDateTime);
		when(urlDateTime.getDateTime()).thenReturn(GAME_DATE);
	}

	@Test
	public void getSectionDetails_should_add_the_specified_section_to_model() throws SectionDoesntExistException, NoSportForUrlException {
		SectionViewModel sectionViewModel = mock(SectionViewModel.class);
		when(sectionService.getAvailableSection(SPORT_NAME, GAME_DATE, TICKET_TYPE)).thenReturn(sectionViewModel);
		when(sectionViewModel.isGeneralAdmission()).thenReturn(true);
		when(sportUrlMapper.getSportName(SPORT_URL)).thenReturn(SPORT_NAME);
		
		ModelAndView mav = sectionController.getSectionDetails(GAME_DATE_STR, SPORT_URL, TICKET_TYPE);
		ModelMap modelMap = mav.getModelMap();

		assertEquals(sectionViewModel, modelMap.get("section"));
	}

	@Test
	public void getSectionDetails_should_add_a_chosenGeneralTicketsForm_to_model() throws SectionDoesntExistException, NoSportForUrlException {
		SectionViewModel sectionViewModel = mock(SectionViewModel.class);
		when(sectionService.getAvailableSection(SPORT_NAME, GAME_DATE, TICKET_TYPE)).thenReturn(sectionViewModel);
		when(sectionViewModel.isGeneralAdmission()).thenReturn(true);
		when(sportUrlMapper.getSportName(SPORT_URL)).thenReturn(SPORT_NAME);

		ChosenGeneralTicketsViewModel chosenGeneralTicketsVM = mock(ChosenGeneralTicketsViewModel.class);
		when(sectionService.getChosenGeneralTicketsViewModel(SPORT_NAME, GAME_DATE, TICKET_TYPE)).thenReturn(
				chosenGeneralTicketsVM);

		ModelAndView mav = sectionController.getSectionDetails(GAME_DATE_STR, SPORT_URL, TICKET_TYPE);
		ModelMap modelMap = mav.getModelMap();

		assertEquals(chosenGeneralTicketsVM, modelMap.get("chosenGeneralTicketsForm"));
	}

	@Test
	public void getSectionDetails_should_add_a_chosenWithSeatTicketsForm_to_model() throws SectionDoesntExistException, NoSportForUrlException {
		SectionViewModel sectionViewModel = mock(SectionViewModel.class);
		when(sectionService.getAvailableSection(SPORT_NAME, GAME_DATE, TICKET_TYPE)).thenReturn(sectionViewModel);
		when(sectionViewModel.isGeneralAdmission()).thenReturn(false);
		when(sportUrlMapper.getSportName(SPORT_URL)).thenReturn(SPORT_NAME);

		ChosenWithSeatTicketsViewModel chosenWithSeatTicketsVM = mock(ChosenWithSeatTicketsViewModel.class);
		when(sectionService.getChosenWithSeatTicketsViewModel(SPORT_NAME, GAME_DATE, TICKET_TYPE)).thenReturn(
				chosenWithSeatTicketsVM);

		ModelAndView mav = sectionController.getSectionDetails(GAME_DATE_STR, SPORT_URL, TICKET_TYPE);
		ModelMap modelMap = mav.getModelMap();

		assertEquals(chosenWithSeatTicketsVM, modelMap.get("chosenWithSeatTicketsForm"));
	}

	@Test
	public void getSectionDetails_should_return_correct_view_path() throws SectionDoesntExistException, NoSportForUrlException {
		SectionViewModel sectionViewModel = mock(SectionViewModel.class);
		when(sectionService.getAvailableSection(SPORT_NAME, GAME_DATE, TICKET_TYPE)).thenReturn(sectionViewModel);
		when(sectionViewModel.isGeneralAdmission()).thenReturn(true);
		when(sportUrlMapper.getSportName(SPORT_URL)).thenReturn(SPORT_NAME);

		ModelAndView mav = sectionController.getSectionDetails(GAME_DATE_STR, SPORT_URL, TICKET_TYPE);

		assertEquals("section/details", mav.getViewName());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void getSectionDetails_should_redirect_to_404_page_when_section_doesnt_exist()
			throws SectionDoesntExistException, NoSportForUrlException {
		when(sportUrlMapper.getSportName(SPORT_URL)).thenReturn(SPORT_NAME);
		
		when(sectionService.getAvailableSection(SPORT_NAME, GAME_DATE, TICKET_TYPE)).thenThrow(
				SectionDoesntExistException.class);

		ModelAndView mav = sectionController.getSectionDetails(GAME_DATE_STR, SPORT_URL, TICKET_TYPE);

		assertEquals("error/404", mav.getViewName());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void getSectionDetails_should_redirect_to_404_page_when_no_sport_for_url()
			throws SectionDoesntExistException, NoSportForUrlException {
		when(sportUrlMapper.getSportName(SPORT_URL)).thenThrow(NoSportForUrlException.class);

		ModelAndView mav = sectionController.getSectionDetails(GAME_DATE_STR, SPORT_URL, TICKET_TYPE);

		assertEquals("error/404", mav.getViewName());
	}

	@Test
	public void getSectionsForGame_should_get_games() throws Exception {
		when(sportUrlMapper.getSportName(SPORT_URL)).thenReturn(SPORT_NAME);
		
		sectionController.getSectionsForGame(GAME_DATE_STR, SPORT_URL);

		verify(sectionService).getAvailableSectionsForGame(SPORT_NAME, GAME_DATE);
	}

	@Test
	public void getSectionsForGame_should_add_the_specified_sections_to_model() throws Exception {
		SectionsViewModel sectionsViewModel = mock(SectionsViewModel.class);
		when(sportUrlMapper.getSportName(SPORT_URL)).thenReturn(SPORT_NAME);
		when(sectionService.getAvailableSectionsForGame(SPORT_NAME, GAME_DATE)).thenReturn(sectionsViewModel);

		ModelAndView mav = sectionController.getSectionsForGame(GAME_DATE_STR, SPORT_URL);
		ModelMap modelMap = mav.getModelMap();

		assertTrue(modelMap.containsAttribute("gameSections"));
		assertSame(sectionsViewModel, modelMap.get("gameSections"));
	}

	@Test
	public void getSectionsForGame_should_return_correct_view_path() throws NoSportForUrlException {
		ModelAndView mav = sectionController.getSectionsForGame(GAME_DATE_STR, SPORT_URL);

		assertEquals("section/list", mav.getViewName());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void getSectionsForGame_should_redirect_to_404_page_when_sport_name_game_date_doesnt_exist() throws NoSportForUrlException, GameDoesntExistException {
		when(sportUrlMapper.getSportName(SPORT_URL)).thenReturn(SPORT_NAME);
		when(sectionService.getAvailableSectionsForGame(SPORT_NAME, GAME_DATE)).thenThrow(
				GameDoesntExistException.class);

		ModelAndView mav = sectionController.getSectionsForGame(GAME_DATE_STR, SPORT_URL);

		assertEquals("error/404", mav.getViewName());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void getSectionsForGame_should_redirect_to_404_page_when_no_sport_for_url() throws NoSportForUrlException, GameDoesntExistException {
		when(sportUrlMapper.getSportName(SPORT_URL)).thenThrow(NoSportForUrlException.class);

		ModelAndView mav = sectionController.getSectionsForGame(GAME_DATE_STR, SPORT_URL);

		assertEquals("error/404", mav.getViewName());
	}
}
