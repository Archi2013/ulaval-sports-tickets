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

import ca.ulaval.glo4003.domain.search.SectionForSearchDao;
import ca.ulaval.glo4003.domain.search.SectionForSearchDto;
import ca.ulaval.glo4003.domain.search.TicketSearchPreferenceDto;
import ca.ulaval.glo4003.services.SearchService;

@RunWith(MockitoJUnitRunner.class)
public class SearchServiceTest {

	@Mock
	private SectionForSearchDao sectionForSearchDao;

	@InjectMocks
	private SearchService service;

	@Before
	public void setUp() {
	}
	
	@Test
	public void given_a_ticketSearchViewModel_getTickets_should_return_a_ticket_list() {
		TicketSearchPreferenceDto ticketSPDto = mock(TicketSearchPreferenceDto.class);
		List<SectionForSearchDto> sectionDtos = new ArrayList<>();
		
		when(sectionForSearchDao.getSections(ticketSPDto)).thenReturn(sectionDtos);

		List<SectionForSearchDto> actual = service.getSections(ticketSPDto);

		assertSame(sectionDtos, actual);
	}
}
