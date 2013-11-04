package ca.ulaval.glo4003.web.controllers;

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

import ca.ulaval.glo4003.domain.services.SearchService;
import ca.ulaval.glo4003.web.viewmodels.TicketForSearchViewModel;
import ca.ulaval.glo4003.web.viewmodels.TicketSearchViewModel;

@RunWith(MockitoJUnitRunner.class)
public class SearchControllerTest {

	private static final String SEARCH_HOME_PAGE = "search/home";
	
	@Mock
	private SearchService searchService;
	
	@InjectMocks
	private SearchController controller;

	@Before
	public void setUp() {	
	}

	@Test
	public void home_should_return_the_good_search_home_page() {
		ModelAndView mav = controller.home();

		assertEquals(SEARCH_HOME_PAGE, mav.getViewName());
	}
	
	@Test
	public void home_should_add_a_ticketSearchForm_to_model() {
		TicketSearchViewModel ticketSearchVM = new TicketSearchViewModel();
		
		when(searchService.getInitialisedTicketSearchViewModel()).thenReturn(ticketSearchVM);
		
		ModelAndView mav = controller.home();
		ModelMap modelMap = mav.getModelMap();

		assertTrue(modelMap.containsAttribute("ticketSearchForm"));
		assertSame(ticketSearchVM, modelMap.get("ticketSearchForm"));
	}
	
	@Test
	public void home_should_initialise_the_ModelAndView_with_search_criterions() {
		ModelAndView mav = controller.home();
		
		verify(searchService, times(1)).initSearchCriterions(mav);
	}
	
	@Test
	public void home_should_add_a_ticket_list_to_model() {
		TicketSearchViewModel ticketSearchVM = new TicketSearchViewModel();
		List<TicketForSearchViewModel> tickets = new ArrayList<>();

		when(searchService.getInitialisedTicketSearchViewModel()).thenReturn(ticketSearchVM);
		when(searchService.getTickets(ticketSearchVM)).thenReturn(tickets);
		
		ModelAndView mav = controller.home();
		ModelMap modelMap = mav.getModelMap();

		assertTrue(modelMap.containsAttribute("tickets"));
		assertSame(tickets, modelMap.get("tickets"));
	}
	
	@Test
	public void savePreferences_should_set_preferencesSaved_to_true() {
		TicketSearchViewModel ticketSearchVM = new TicketSearchViewModel();
		
		ModelAndView mav = controller.savePreferences(ticketSearchVM);
		ModelMap modelMap = mav.getModelMap();
		
		assertTrue(modelMap.containsAttribute("preseferencesSaved"));
		assertTrue((boolean) modelMap.get("preferencesSaved"));
	}
	
	@Test
	public void getList_should_return_the_good_subview() {
		TicketSearchViewModel ticketSearchVM = new TicketSearchViewModel();
		
		ModelAndView mav = controller.getList(ticketSearchVM);

		assertEquals("search/list", mav.getViewName());
	}
	
	@Test
	public void getList_should_add_a_ticket_list_to_model() {
		TicketSearchViewModel ticketSearchVM = new TicketSearchViewModel();
		List<TicketForSearchViewModel> tickets = new ArrayList<>();
		
		when(searchService.getTickets(ticketSearchVM)).thenReturn(tickets);
		
		ModelAndView mav = controller.getList(ticketSearchVM);
		ModelMap modelMap = mav.getModelMap();

		assertTrue(modelMap.containsAttribute("tickets"));
		assertSame(tickets, modelMap.get("tickets"));
	}
}
