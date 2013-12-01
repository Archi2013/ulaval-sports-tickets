package ca.ulaval.glo4003.presentation.controllers;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import ca.ulaval.glo4003.constants.DisplayedPeriod;
import ca.ulaval.glo4003.constants.TicketKind;
import ca.ulaval.glo4003.domain.search.SectionForSearchDto;
import ca.ulaval.glo4003.domain.search.TicketSearchPreferenceDto;
import ca.ulaval.glo4003.domain.users.User;
import ca.ulaval.glo4003.presentation.viewmodels.SectionForSearchViewModel;
import ca.ulaval.glo4003.presentation.viewmodels.TicketSearchViewModel;
import ca.ulaval.glo4003.presentation.viewmodels.factories.SectionForSearchViewModelFactory;
import ca.ulaval.glo4003.presentation.viewmodels.factories.TicketSearchPreferenceFactory;
import ca.ulaval.glo4003.services.SearchService;
import ca.ulaval.glo4003.services.UserPreferencesService;
import ca.ulaval.glo4003.utilities.Constants;

@RunWith(MockitoJUnitRunner.class)
public class SearchControllerTest {

	private static final String SEARCH_HOME_PAGE = "search/home";
	
	@Mock
	private Constants constants;
	
	@Mock
	private SearchService searchService;
	
	@Mock
	private UserPreferencesService userPreferencesService;
	
	@Mock
	private User currentUser;
	
	@Mock
	private TicketSearchPreferenceFactory ticketSearchPreferenceFactory;
	
	@Mock
	private SectionForSearchViewModelFactory sectionForSearchViewModelFactory;
	
	@InjectMocks
	private SearchController controller;

	@Before
	public void setUp() {	
	}

	@Test
	public void home_should_return_the_good_search_home_page() {
		TicketSearchViewModel ticketSearchVM = new TicketSearchViewModel();
		List<String> selectedSports = new ArrayList<>();
		List<TicketKind> ticketKinds = new ArrayList<>();
		ticketSearchVM.selectedSports = selectedSports;
		ticketSearchVM.selectedTicketKinds = ticketKinds;
		ticketSearchVM.setLocalGameOnly(true);
		ticketSearchVM.setDisplayedPeriod(DisplayedPeriod.ALL);
		
		when(ticketSearchPreferenceFactory.createInitialViewModel()).thenReturn(ticketSearchVM);
		when(currentUser.isLogged()).thenReturn(false);
		
		ModelAndView mav = controller.home();

		assertEquals(SEARCH_HOME_PAGE, mav.getViewName());
	}
	
	@Test
	public void home_should_add_a_ticketSearchForm_to_model() {
		TicketSearchViewModel ticketSearchVM = new TicketSearchViewModel();
		List<String> selectedSports = new ArrayList<>();
		List<TicketKind> ticketKinds = new ArrayList<>();
		ticketSearchVM.selectedSports = selectedSports;
		ticketSearchVM.selectedTicketKinds = ticketKinds;
		ticketSearchVM.setLocalGameOnly(true);
		ticketSearchVM.setDisplayedPeriod(DisplayedPeriod.ALL);
		
		when(ticketSearchPreferenceFactory.createInitialViewModel()).thenReturn(ticketSearchVM);
		when(currentUser.isLogged()).thenReturn(false);
		
		ModelAndView mav = controller.home();
		ModelMap modelMap = mav.getModelMap();

		assertTrue(modelMap.containsAttribute("ticketSearchForm"));
		assertSame(ticketSearchVM, modelMap.get("ticketSearchForm"));
	}
	
	@Test
	public void home_should_add_a_ticket_list_to_model() {
		TicketSearchPreferenceDto ticketSPDto = mock(TicketSearchPreferenceDto.class);
		TicketSearchViewModel ticketSearchVM = mock(TicketSearchViewModel.class);
		List<SectionForSearchDto> sectionDtos = new ArrayList<>();
		List<SectionForSearchViewModel> sectionVMs = new ArrayList<>();
		List<String> selectedSports = new ArrayList<>();
		List<TicketKind> ticketKinds = new ArrayList<>();
		ticketSearchVM.selectedSports = selectedSports;
		ticketSearchVM.selectedTicketKinds = ticketKinds;
		ticketSearchVM.setLocalGameOnly(true);
		ticketSearchVM.setDisplayedPeriod(DisplayedPeriod.ALL);
		
		when(ticketSearchPreferenceFactory.createInitialViewModel()).thenReturn(ticketSearchVM);
		when(currentUser.isLogged()).thenReturn(false);
		when(ticketSearchPreferenceFactory.createPreferenceDto(selectedSports, DisplayedPeriod.ALL, true, ticketKinds)).thenReturn(ticketSPDto);
		when(searchService.getSections(ticketSPDto)).thenReturn(sectionDtos);
		when(sectionForSearchViewModelFactory.createViewModels(sectionDtos)).thenReturn(sectionVMs);
		
		ModelAndView mav = controller.home();
		ModelMap modelMap = mav.getModelMap();

		assertTrue(modelMap.containsAttribute("sections"));
		assertSame(sectionVMs, modelMap.get("sections"));
	}
	
	@Test
	public void savePreferences_should_set_preferencesSaved_to_true() {
		TicketSearchViewModel ticketSearchVM = new TicketSearchViewModel();
		List<String> selectedSports = new ArrayList<>();
		List<TicketKind> ticketKinds = new ArrayList<>();
		ticketSearchVM.selectedSports = selectedSports;
		ticketSearchVM.selectedTicketKinds = ticketKinds;
		ticketSearchVM.setLocalGameOnly(true);
		ticketSearchVM.setDisplayedPeriod(DisplayedPeriod.ALL);
		
		when(ticketSearchPreferenceFactory.createInitialViewModel()).thenReturn(ticketSearchVM);
		when(currentUser.isLogged()).thenReturn(false);

		
		ModelAndView mav = controller.savePreferences(ticketSearchVM);
		ModelMap modelMap = mav.getModelMap();
		
		assertTrue(true);
		assertTrue(modelMap.containsAttribute("preferencesSaved"));
		assertTrue((boolean) modelMap.get("preferencesSaved"));
	}
	
	@Test
	public void getList_should_return_the_good_subview() {
		TicketSearchViewModel ticketSearchVM = new TicketSearchViewModel();
		
		ModelAndView mav = controller.getList(ticketSearchVM);

		assertEquals("search/list", mav.getViewName());
	}
}
