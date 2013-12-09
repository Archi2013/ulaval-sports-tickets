package ca.ulaval.glo4003.services;

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

import ca.ulaval.glo4003.presentation.viewmodels.SectionForSearchViewModel;
import ca.ulaval.glo4003.presentation.viewmodels.factories.SectionForSearchViewModelFactory;
import ca.ulaval.glo4003.utilities.search.SectionForSearchDao;
import ca.ulaval.glo4003.utilities.search.dto.SectionForSearchDto;
import ca.ulaval.glo4003.utilities.search.dto.UserSearchPreferenceDto;

@RunWith(MockitoJUnitRunner.class)
public class SearchViewServiceTest {

	@Mock
	private SectionForSearchDao sectionForSearchDao;

	@Mock
	private SectionForSearchViewModelFactory sectionForSearchViewModelFactory;
	
	@InjectMocks
	private SearchViewService service;

	@Before
	public void setUp() {
	}
	
	@Test
	public void given_a_ticketSearchViewModel_getTickets_should_return_a_ticket_list() {
		UserSearchPreferenceDto ticketSPDto = mock(UserSearchPreferenceDto.class);
		List<SectionForSearchDto> sectionDtos = new ArrayList<>();
		List<SectionForSearchViewModel> sectionForSearchVMs = new ArrayList<>();
		
		when(sectionForSearchDao.getSections(ticketSPDto)).thenReturn(sectionDtos);
		when(sectionForSearchViewModelFactory.createViewModels(sectionDtos)).thenReturn(sectionForSearchVMs);

		List<SectionForSearchViewModel> actual = service.getSections(ticketSPDto);

		assertSame(sectionForSearchVMs, actual);
	}
}
