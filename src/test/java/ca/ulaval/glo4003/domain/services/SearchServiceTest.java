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

import ca.ulaval.glo4003.constants.DisplayedPeriod;
import ca.ulaval.glo4003.constants.TicketKind;
import ca.ulaval.glo4003.domain.search.SectionForSearchDao;
import ca.ulaval.glo4003.domain.search.SectionForSearchDto;
import ca.ulaval.glo4003.domain.search.TicketSearchPreferenceDto;
import ca.ulaval.glo4003.presentation.viewmodels.SectionForSearchViewModel;
import ca.ulaval.glo4003.presentation.viewmodels.TicketSearchViewModel;
import ca.ulaval.glo4003.presentation.viewmodels.factories.SectionForSearchViewModelFactory;
import ca.ulaval.glo4003.presentation.viewmodels.factories.TicketSearchPreferenceFactory;
import ca.ulaval.glo4003.services.SearchService;
import ca.ulaval.glo4003.utilities.Constants;

@RunWith(MockitoJUnitRunner.class)
public class SearchServiceTest {

	@Mock
	private Constants constants;

	@Mock
	private SectionForSearchDao sectionForSearchDao;

	@Mock
	private SectionForSearchViewModelFactory ticketForSearchViewModelFactory;

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

		when(constants.getSportList()).thenReturn(sportList);

		service.initSearchCriterions(mav);
		ModelMap modelMap = mav.getModelMap();

		assertTrue(modelMap.containsAttribute("sportList"));
		assertSame(sportList, modelMap.get("sportList"));
	}

	@Test
	public void initSearchCriterions_should_add_all_displayedPeriods_to_model() {
		ModelAndView mav = new ModelAndView();
		List<DisplayedPeriod> displayedPeriods = new ArrayList<>();

		service.initSearchCriterions(mav);
		ModelMap modelMap = mav.getModelMap();

		assertTrue(modelMap.containsAttribute("displayedPeriods"));
	}

	@Test
	public void initSearchCriterions_should_add_ticketTypes_to_model() {
		ModelAndView mav = new ModelAndView();
		List<TicketKind> ticketKinds = new ArrayList<>();

		when(TicketKind.getTicketKinds()).thenReturn(ticketKinds);

		service.initSearchCriterions(mav);
		ModelMap modelMap = mav.getModelMap();

		assertTrue(modelMap.containsAttribute("ticketKinds"));
	}

	@Test
	public void given_a_ticketSearchViewModel_getTickets_should_return_a_ticket_list() {
		TicketSearchViewModel ticketSearchVM = new TicketSearchViewModel();
		List<SectionForSearchDto> sectionDtos = new ArrayList<>();
		TicketSearchPreferenceDto preferenceDto = mock(TicketSearchPreferenceDto.class);
		List<SectionForSearchViewModel> ticketVMs = new ArrayList<>();

		when(ticketSearchPreferenceFactory.createPreferenceDto(ticketSearchVM)).thenReturn(preferenceDto);
		when(sectionForSearchDao.getSections(preferenceDto)).thenReturn(sectionDtos);
		when(ticketForSearchViewModelFactory.createViewModels(sectionDtos)).thenReturn(ticketVMs);

		List<SectionForSearchViewModel> actual = service.getSections(ticketSearchVM);

		assertSame(ticketVMs, actual);
	}
}
