package ca.ulaval.glo4003.presentation.controllers;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

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
import ca.ulaval.glo4003.domain.users.User;
import ca.ulaval.glo4003.exceptions.UserDoesntHaveSavedPreferences;
import ca.ulaval.glo4003.presentation.controllers.errorhandler.SearchErrorHandler;
import ca.ulaval.glo4003.presentation.viewmodels.SectionForSearchViewModel;
import ca.ulaval.glo4003.presentation.viewmodels.TicketSearchViewModel;
import ca.ulaval.glo4003.presentation.viewmodels.factories.TicketSearchPreferenceFactory;
import ca.ulaval.glo4003.services.CommandUserPreferencesService;
import ca.ulaval.glo4003.services.SearchViewService;
import ca.ulaval.glo4003.services.UserPreferencesViewService;
import ca.ulaval.glo4003.services.exceptions.UserPreferencesNotSaved;
import ca.ulaval.glo4003.utilities.Constants;
import ca.ulaval.glo4003.utilities.search.TicketSearchPreferenceDto;

@RunWith(MockitoJUnitRunner.class)
public class SearchControllerTest {

	private static final String USERNAME = "Sora";

	private static final String SEARCH_LIST_SUBVIEW = "search/list";

	private static final String SEARCH_HOME_PAGE = "search/home";
	
	@Mock
	private Constants constants;
	
	@Mock
	private SearchViewService searchService;
	
	@Mock
	private UserPreferencesViewService userPreferencesViewService;
	
	@Mock
	private CommandUserPreferencesService commandUserPreferencesService;
	
	@Mock
	private User currentUser;
	
	@Mock
	private SearchErrorHandler searchErrorHandler;
	
	@Mock
	private TicketSearchPreferenceFactory ticketSearchPreferenceFactory;
	
	@InjectMocks
	private SearchController controller;

	@Before
	public void setUp() {	
	}

	@Test
	public void home_should_return_the_good_search_home_page() {
		TicketSearchViewModel ticketSearchVM = getTicketSearchViewModel();
		
		when(ticketSearchPreferenceFactory.createInitialViewModel()).thenReturn(ticketSearchVM);
		when(currentUser.isLogged()).thenReturn(false);
		
		ModelAndView mav = controller.home();

		assertEquals(SEARCH_HOME_PAGE, mav.getViewName());
	}
	
	@Test
	public void home_should_add_the_currency_to_the_page() {
		TicketSearchViewModel ticketSearchVM = getTicketSearchViewModel();
		
		when(ticketSearchPreferenceFactory.createInitialViewModel()).thenReturn(ticketSearchVM);
		when(currentUser.isLogged()).thenReturn(false);
		
		ModelAndView mav = controller.home();
		ModelMap modelMap = mav.getModelMap();

		assertTrue(modelMap.containsAttribute("currency"));
		assertSame(Constants.CURRENCY, modelMap.get("currency"));
	}
	
	@Test
	public void home_should_add_a_ticketSearchForm_to_model() {
		TicketSearchViewModel ticketSearchVM = getTicketSearchViewModel();
		
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
		TicketSearchViewModel ticketSearchVM = getTicketSearchViewModel();
		List<SectionForSearchViewModel> sectionVMs = new ArrayList<>();
		
		when(ticketSearchPreferenceFactory.createInitialViewModel()).thenReturn(ticketSearchVM);
		when(currentUser.isLogged()).thenReturn(false);
		when(ticketSearchPreferenceFactory.createPreferenceDto(ticketSearchVM)).thenReturn(ticketSPDto);
		when(searchService.getSections(ticketSPDto)).thenReturn(sectionVMs);
		
		ModelAndView mav = controller.home();
		ModelMap modelMap = mav.getModelMap();

		assertTrue(modelMap.containsAttribute("sections"));
		assertSame(sectionVMs, modelMap.get("sections"));
	}
	
	@Test
	public void when_currentUser_isLogged_home_should_add_a_ticketSearchViewModel_from_userPreferencesService() throws UserDoesntHaveSavedPreferences {
		TicketSearchViewModel ticketSearchVM = getTicketSearchViewModel();
		
		when(currentUser.isLogged()).thenReturn(true);
		when(currentUser.getUsername()).thenReturn(USERNAME);
		when(userPreferencesViewService.getUserPreferencesForUser(USERNAME)).thenReturn(ticketSearchVM);
		
		ModelAndView mav = controller.home();
		ModelMap modelMap = mav.getModelMap();

		assertTrue(modelMap.containsAttribute("ticketSearchForm"));
		assertSame(ticketSearchVM, modelMap.get("ticketSearchForm"));
	}
	
	@Test
	public void when_currentUser_isLogged_and_UserDoesntHaveSavedPreferences_home_should_add_a_initial_ticketSearchViewModel() throws UserDoesntHaveSavedPreferences {
		TicketSearchViewModel ticketSearchVM = getTicketSearchViewModel();
		
		when(currentUser.isLogged()).thenReturn(true);
		when(currentUser.getUsername()).thenReturn(USERNAME);
		when(userPreferencesViewService.getUserPreferencesForUser(USERNAME)).thenThrow(new UserDoesntHaveSavedPreferences());
		when(ticketSearchPreferenceFactory.createInitialViewModel()).thenReturn(ticketSearchVM);
		
		ModelAndView mav = controller.home();
		ModelMap modelMap = mav.getModelMap();

		assertTrue(modelMap.containsAttribute("ticketSearchForm"));
		assertSame(ticketSearchVM, modelMap.get("ticketSearchForm"));
	}
	
	@Test
	public void savePreferences_should_set_preferencesSaved_to_true() {
		TicketSearchViewModel ticketSearchVM = getTicketSearchViewModel();
		
		when(ticketSearchPreferenceFactory.createInitialViewModel()).thenReturn(ticketSearchVM);
		when(currentUser.isLogged()).thenReturn(false);

		
		ModelAndView mav = controller.savePreferences(ticketSearchVM);
		ModelMap modelMap = mav.getModelMap();
		
		assertTrue(modelMap.containsAttribute("preferencesSaved"));
		assertTrue((boolean) modelMap.get("preferencesSaved"));
	}
	
	@Test
	public void savePreferences_should_return_the_good_search_home_page() {
		TicketSearchViewModel ticketSearchVM = getTicketSearchViewModel();
		
		when(ticketSearchPreferenceFactory.createInitialViewModel()).thenReturn(ticketSearchVM);
		when(currentUser.isLogged()).thenReturn(false);

		
		ModelAndView mav = controller.savePreferences(ticketSearchVM);

		assertEquals(SEARCH_HOME_PAGE, mav.getViewName());
	}
	
	@Test
	public void when_UserPreferencesNotSaved_should_set_preferencesSaved_to_false() throws UserPreferencesNotSaved {
		TicketSearchViewModel ticketSearchVM = getTicketSearchViewModel();
		TicketSearchPreferenceDto ticketSPDto = mock(TicketSearchPreferenceDto.class);
		
		when(ticketSearchPreferenceFactory.createInitialViewModel()).thenReturn(ticketSearchVM);
		when(currentUser.isLogged()).thenReturn(false);
		when(currentUser.getUsername()).thenReturn(USERNAME);
		when(ticketSearchPreferenceFactory.createPreferenceDto(ticketSearchVM)).thenReturn(ticketSPDto);
		doThrow(new UserPreferencesNotSaved()).when(commandUserPreferencesService).saveUserPreference(USERNAME, ticketSPDto);

		
		ModelAndView mav = controller.savePreferences(ticketSearchVM);
		ModelMap modelMap = mav.getModelMap();
		
		assertTrue(modelMap.containsAttribute("preferencesSaved"));
		assertFalse((boolean) modelMap.get("preferencesSaved"));
	}
	
	@Test
	public void when_UserPreferencesNotSaved_should_use_searchErrorHandler() throws UserPreferencesNotSaved {
		TicketSearchViewModel ticketSearchVM = getTicketSearchViewModel();
		TicketSearchPreferenceDto ticketSPDto = mock(TicketSearchPreferenceDto.class);
		
		when(ticketSearchPreferenceFactory.createInitialViewModel()).thenReturn(ticketSearchVM);
		when(currentUser.isLogged()).thenReturn(false);
		when(currentUser.getUsername()).thenReturn(USERNAME);
		when(ticketSearchPreferenceFactory.createPreferenceDto(ticketSearchVM)).thenReturn(ticketSPDto);
		doThrow(new UserPreferencesNotSaved()).when(commandUserPreferencesService).saveUserPreference(USERNAME, ticketSPDto);

		
		ModelAndView mav = controller.savePreferences(ticketSearchVM);
		
		verify(searchErrorHandler).addErrorMessageUserPreferencesNotSaved(mav);
	}
	
	@Test
	public void getList_should_return_the_good_subview() {
		TicketSearchViewModel ticketSearchVM = mock(TicketSearchViewModel.class);
		
		ModelAndView mav = controller.getList(ticketSearchVM);

		assertEquals(SEARCH_LIST_SUBVIEW, mav.getViewName());
	}
	
	@Test
	public void getList_should_add_preferencesSaved_to_false_to_delete_succes_message() {
		TicketSearchViewModel ticketSearchVM = mock(TicketSearchViewModel.class);
		
		ModelAndView mav = controller.getList(ticketSearchVM);
		ModelMap modelMap = mav.getModelMap();
		
		assertTrue(modelMap.containsAttribute("preferencesSaved"));
		assertFalse((boolean) modelMap.get("preferencesSaved"));
	}
	
	private TicketSearchViewModel getTicketSearchViewModel() {
		TicketSearchViewModel ticketSearchVM = new TicketSearchViewModel();
		List<String> selectedSports = new ArrayList<>();
		List<TicketKind> ticketKinds = new ArrayList<>();
		ticketSearchVM.selectedSports = selectedSports;
		ticketSearchVM.selectedTicketKinds = ticketKinds;
		ticketSearchVM.setLocalGameOnly(true);
		ticketSearchVM.setDisplayedPeriod(DisplayedPeriod.ALL);
		return ticketSearchVM;
	}
}
