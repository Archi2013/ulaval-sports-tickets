package ca.ulaval.glo4003.domain.utilities;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4003.persistence.daos.SectionDao;

@RunWith(MockitoJUnitRunner.class)
public class TicketTypeUrlMapperWithGenerationTest {
	
	private static final String INVALID_TICKET_TYPE_URL = "invalid-ticket-type-url";
	private static final String URL_FROM_EXTREME = "coeur-penche-vigueur-eternelle";
	private static final String SECTION_NAME_EXTREME = "=Cœur penché, vigueur éternelle.";
	private static final String URL_FROM_WITH_ACCENT = "genialissime";
	private static final String SECTION_NAME_WITH_ACCENT = "Génialissime";
	private static final String SECTION_NAME = "Cramoisie";
	private static final String URL = "cramoisie";
	
	@Mock
	SectionDao sectionDao;
	
	@InjectMocks
	private TicketTypeUrlMapperWithGeneration sectionUrlMapper;
	
	@Before
	public void setUp() throws Exception {
		Set<String> sections = new HashSet<>();
		sections.add(SECTION_NAME);
		sections.add(SECTION_NAME_WITH_ACCENT);
		sections.add(SECTION_NAME_EXTREME);
		when(sectionDao.getAllSections()).thenReturn(sections);
	}
	
	@Test
	public void given_an_admissionType_and_a_sectionName_getSectionUrl_should_return_the_url() {
		String url = sectionUrlMapper.getUrl(SECTION_NAME);
		assertEquals(URL, url);
	}
	
	@Test
	public void given_an_admissionType_with_accent_and_a_sectionName_with_accent_getSectionUrl_should_return_the_url() {
		String url = sectionUrlMapper.getUrl(SECTION_NAME_WITH_ACCENT);
		assertEquals(URL_FROM_WITH_ACCENT, url);
	}
	
	@Test
	public void given_an_admissionType_extreme_and_a_sectionName_extreme_getSectionUrl_should_return_the_url() {
		String url = sectionUrlMapper.getUrl(SECTION_NAME_EXTREME);
		assertEquals(URL_FROM_EXTREME, url);
	}
	
	@Test
	public void given_a_ticketTypeUrl_getTicketType_should_return_the_ticketType() throws NoTicketTypeForUrlException {
		String actual = sectionUrlMapper.getTicketType(URL);
		String expected = SECTION_NAME;
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void given_an_other_ticketTypeUrl_getTicketType_should_return_the_ticketType() throws NoTicketTypeForUrlException {
		String actual = sectionUrlMapper.getTicketType(URL_FROM_EXTREME);
		String expected = SECTION_NAME_EXTREME;
		
		assertEquals(expected, actual);
	}
	
	@Test(expected=NoTicketTypeForUrlException.class)
	public void given_an_invalid_ticketTypeUrl_getTicketType_should_raise_NoTicketTypeForUrlException() throws NoTicketTypeForUrlException {
		sectionUrlMapper.getTicketType(INVALID_TICKET_TYPE_URL);
	}
}
