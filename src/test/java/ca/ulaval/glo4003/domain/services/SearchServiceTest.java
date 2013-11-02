package ca.ulaval.glo4003.domain.services;

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

import ca.ulaval.glo4003.domain.dtos.TicketForSearchDto;
import ca.ulaval.glo4003.domain.dtos.TicketSearchPreferenceDto;
import ca.ulaval.glo4003.domain.utilities.Constants;
import ca.ulaval.glo4003.domain.utilities.Constants.DisplayedPeriod;
import ca.ulaval.glo4003.domain.utilities.Constants.TicketKind;
import ca.ulaval.glo4003.persistence.daos.TicketForSearchDao;
import ca.ulaval.glo4003.web.viewmodels.TicketForSearchViewModel;
import ca.ulaval.glo4003.web.viewmodels.TicketSearchViewModel;
import ca.ulaval.glo4003.web.viewmodels.factories.TicketForSearchViewModelFactory;
import ca.ulaval.glo4003.web.viewmodels.factories.TicketSearchPreferenceFactory;

@RunWith(MockitoJUnitRunner.class)
public class SearchServiceTest {
	
	@Mock
	private Constants constants;
	
	@Mock
	private TicketForSearchDao ticketForSearchDao;

	@Mock
	private TicketForSearchViewModelFactory ticketForSearchViewModelFactory;
	
	@Mock
	private TicketSearchPreferenceFactory ticketSearchPreferenceFactory;

	@InjectMocks
	private SearchService service;

	@Before
	public void setUp() {
	}

	@Test
	public void getInitialisedTicketSearchViewModel_should_return_a_initialised_TicketSearchViewModel() {
		TicketSearchViewModel ticketSearchVM = new TicketSearchViewModel();
		
		when(ticketSearchPreferenceFactory.createInitialViewModel()).thenReturn(ticketSearchVM);
		
		TicketSearchViewModel actual = service.getInitialisedTicketSearchViewModel();
		
		assertSame(ticketSearchVM, actual);
	}
	
	@Test
	public void initSearchCriterions_should_add_a_all_sportNames_to_model() {
		ModelAndView mav = new ModelAndView();
		List<String> sportList = new ArrayList<>();
		
		when(constants.getSportsList()).thenReturn(sportList);
		
		service.initSearchCriterions(mav);
		ModelMap modelMap = mav.getModelMap();
		
		assertTrue(modelMap.containsAttribute("sportList"));
		assertSame(sportList, modelMap.get("sportList"));
	}
	
	@Test
	public void initSearchCriterions_should_add_all_displayedPeriods_to_model() {
		ModelAndView mav = new ModelAndView();
		List<DisplayedPeriod> displayedPeriods = new ArrayList<>();
		
		when(constants.getDisplayedPeriods()).thenReturn(displayedPeriods);
		
		service.initSearchCriterions(mav);
		ModelMap modelMap = mav.getModelMap();
		
		assertTrue(modelMap.containsAttribute("displayedPeriods"));
		assertSame(displayedPeriods, modelMap.get("displayedPeriods"));
	}
	
	@Test
	public void initSearchCriterions_should_add_ticketTypes_to_model() {
		ModelAndView mav = new ModelAndView();
		List<TicketKind> ticketKinds = new ArrayList<>();
		
		when(constants.getTicketKinds()).thenReturn(ticketKinds);
		
		service.initSearchCriterions(mav);
		ModelMap modelMap = mav.getModelMap();
		
		assertTrue(modelMap.containsAttribute("ticketKinds"));
		assertSame(ticketKinds, modelMap.get("ticketKinds"));
	}
	
	@Test
	public void given_a_ticketSearchViewModel_getTickets_should_return_a_ticket_list() {
		TicketSearchViewModel ticketSearchVM = new TicketSearchViewModel();
		List<TicketForSearchDto> ticketDtos = new ArrayList<>();
		TicketSearchPreferenceDto preferenceDto = new TicketSearchPreferenceDto(null, null, true, null);
		List<TicketForSearchViewModel> ticketVMs = new ArrayList<>();
		
		when(ticketSearchPreferenceFactory.createPreferenceDto(ticketSearchVM)).thenReturn(preferenceDto);
		when(ticketForSearchDao.getTickets(preferenceDto)).thenReturn(ticketDtos);
		when(ticketForSearchViewModelFactory.createViewModels(ticketDtos)).thenReturn(ticketVMs);
		
		List<TicketForSearchViewModel> actual = service.getTickets(ticketSearchVM);
		
		assertSame(ticketVMs, actual);
	}
}
