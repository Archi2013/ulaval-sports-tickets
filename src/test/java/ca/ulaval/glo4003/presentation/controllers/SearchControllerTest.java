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
import ca.ulaval.glo4003.exceptions.UserDoesntHaveSavedSearchPreference;
import ca.ulaval.glo4003.presentation.controllers.errorhandler.SearchErrorHandler;
import ca.ulaval.glo4003.presentation.viewmodels.SectionForSearchViewModel;
import ca.ulaval.glo4003.presentation.viewmodels.SearchViewModel;
import ca.ulaval.glo4003.presentation.viewmodels.factories.UserSearchPreferenceFactory;
import ca.ulaval.glo4003.services.CommandUserSearchPreferenceService;
import ca.ulaval.glo4003.services.SearchViewService;
import ca.ulaval.glo4003.services.UserSearchPreferenceViewService;
import ca.ulaval.glo4003.services.exceptions.UserSearchPreferenceNotSaved;
import ca.ulaval.glo4003.utilities.Constants;
import ca.ulaval.glo4003.utilities.search.UserSearchPreferenceDto;

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
	private UserSearchPreferenceViewService userSearchPreferenceViewService;
	
	@Mock
	private CommandUserSearchPreferenceService commandUserSearchPreferenceService;
	
	@Mock
	private User currentUser;
	
	@Mock
	private SearchErrorHandler searchErrorHandler;
	
	@Mock
	private UserSearchPreferenceFactory userSearchPreferenceFactory;
	
	@InjectMocks
	private SearchController controller;
	
	@Mock
	private SearchViewModel searchVM;

	@Before
	public void setUp() {	
	}

	@Test
	public void home_should_return_the_good_search_home_page() {
		when(userSearchPreferenceFactory.createInitialViewModel()).thenReturn(searchVM);
		when(currentUser.isLogged()).thenReturn(false);
		
		ModelAndView mav = controller.home();

		assertEquals(SEARCH_HOME_PAGE, mav.getViewName());
	}
	
	@Test
	public void home_should_add_the_currency_to_the_page() {
		when(userSearchPreferenceFactory.createInitialViewModel()).thenReturn(searchVM);
		when(currentUser.isLogged()).thenReturn(false);
		
		ModelAndView mav = controller.home();
		ModelMap modelMap = mav.getModelMap();

		assertTrue(modelMap.containsAttribute("currency"));
		assertSame(Constants.CURRENCY, modelMap.get("currency"));
	}
	
	@Test
	public void home_should_add_a_searchForm_to_model() {
		when(userSearchPreferenceFactory.createInitialViewModel()).thenReturn(searchVM);
		when(currentUser.isLogged()).thenReturn(false);
		
		ModelAndView mav = controller.home();
		ModelMap modelMap = mav.getModelMap();

		assertTrue(modelMap.containsAttribute("searchForm"));
		assertSame(searchVM, modelMap.get("searchForm"));
	}
	
	@Test
	public void home_should_add_a_ticket_list_to_model() {
		UserSearchPreferenceDto userSearchPreferenceDto = mock(UserSearchPreferenceDto.class);
		List<SectionForSearchViewModel> sectionVMs = new ArrayList<>();
		
		when(userSearchPreferenceFactory.createInitialViewModel()).thenReturn(searchVM);
		when(currentUser.isLogged()).thenReturn(false);
		when(userSearchPreferenceFactory.createPreferenceDto(searchVM)).thenReturn(userSearchPreferenceDto);
		when(searchService.getSections(userSearchPreferenceDto)).thenReturn(sectionVMs);
		
		ModelAndView mav = controller.home();
		ModelMap modelMap = mav.getModelMap();

		assertTrue(modelMap.containsAttribute("sections"));
		assertSame(sectionVMs, modelMap.get("sections"));
	}
	
	@Test
	public void when_currentUser_isLogged_home_should_add_a_ticketSearchViewModel_from_userPreferencesService() throws UserDoesntHaveSavedSearchPreference {
		when(currentUser.isLogged()).thenReturn(true);
		when(currentUser.getUsername()).thenReturn(USERNAME);
		when(userSearchPreferenceViewService.getSearchViewModelForUser(USERNAME)).thenReturn(searchVM);
		
		ModelAndView mav = controller.home();
		ModelMap modelMap = mav.getModelMap();

		assertTrue(modelMap.containsAttribute("searchForm"));
		assertSame(searchVM, modelMap.get("searchForm"));
	}
	
	@Test
	public void when_currentUser_isLogged_and_UserDoesntHaveSavedPreferences_home_should_add_a_initial_ticketSearchViewModel() throws UserDoesntHaveSavedSearchPreference {
		when(currentUser.isLogged()).thenReturn(true);
		when(currentUser.getUsername()).thenReturn(USERNAME);
		when(userSearchPreferenceViewService.getSearchViewModelForUser(USERNAME)).thenThrow(new UserDoesntHaveSavedSearchPreference());
		when(userSearchPreferenceFactory.createInitialViewModel()).thenReturn(searchVM);
		
		ModelAndView mav = controller.home();
		ModelMap modelMap = mav.getModelMap();

		assertTrue(modelMap.containsAttribute("searchForm"));
		assertSame(searchVM, modelMap.get("searchForm"));
	}
	
	@Test
	public void savePreference_should_set_preferenceSaved_to_true() {
		when(userSearchPreferenceFactory.createInitialViewModel()).thenReturn(searchVM);
		when(currentUser.isLogged()).thenReturn(false);

		
		ModelAndView mav = controller.savePreference(searchVM);
		ModelMap modelMap = mav.getModelMap();
		
		assertTrue(modelMap.containsAttribute("preferenceSaved"));
		assertTrue((boolean) modelMap.get("preferenceSaved"));
	}
	
	@Test
	public void savePreference_should_return_the_good_search_home_page() {
		when(userSearchPreferenceFactory.createInitialViewModel()).thenReturn(searchVM);
		when(currentUser.isLogged()).thenReturn(false);

		
		ModelAndView mav = controller.savePreference(searchVM);

		assertEquals(SEARCH_HOME_PAGE, mav.getViewName());
	}
	
	@Test
	public void when_UserSearchPreferenceNotSaved_should_set_preferenceSaved_to_false() throws UserSearchPreferenceNotSaved {
		UserSearchPreferenceDto userSearchPreferenceDto = mock(UserSearchPreferenceDto.class);
		
		when(userSearchPreferenceFactory.createInitialViewModel()).thenReturn(searchVM);
		when(currentUser.isLogged()).thenReturn(false);
		when(currentUser.getUsername()).thenReturn(USERNAME);
		when(userSearchPreferenceFactory.createPreferenceDto(searchVM)).thenReturn(userSearchPreferenceDto);
		doThrow(new UserSearchPreferenceNotSaved()).when(commandUserSearchPreferenceService).saveUserSearchPreference(USERNAME, userSearchPreferenceDto);

		
		ModelAndView mav = controller.savePreference(searchVM);
		ModelMap modelMap = mav.getModelMap();
		
		assertTrue(modelMap.containsAttribute("preferenceSaved"));
		assertFalse((boolean) modelMap.get("preferenceSaved"));
	}
	
	@Test
	public void when_UserSearchPreferenceNotSaved_savePreference_should_use_searchErrorHandler() throws UserSearchPreferenceNotSaved {
		UserSearchPreferenceDto userSearchPreferenceDto = mock(UserSearchPreferenceDto.class);
		
		when(userSearchPreferenceFactory.createInitialViewModel()).thenReturn(searchVM);
		when(currentUser.isLogged()).thenReturn(false);
		when(currentUser.getUsername()).thenReturn(USERNAME);
		when(userSearchPreferenceFactory.createPreferenceDto(searchVM)).thenReturn(userSearchPreferenceDto);
		doThrow(new UserSearchPreferenceNotSaved()).when(commandUserSearchPreferenceService).saveUserSearchPreference(USERNAME, userSearchPreferenceDto);

		
		ModelAndView mav = controller.savePreference(searchVM);
		
		verify(searchErrorHandler).addErrorMessageUserPreferencesNotSaved(mav);
	}
	
	@Test
	public void getList_should_return_the_good_subview() {
		ModelAndView mav = controller.getList(searchVM);

		assertEquals(SEARCH_LIST_SUBVIEW, mav.getViewName());
	}
	
	@Test
	public void getList_should_add_preferenceSaved_to_false_to_delete_succes_message() {
		ModelAndView mav = controller.getList(searchVM);
		ModelMap modelMap = mav.getModelMap();
		
		assertTrue(modelMap.containsAttribute("preferenceSaved"));
		assertFalse((boolean) modelMap.get("preferenceSaved"));
	}
}
