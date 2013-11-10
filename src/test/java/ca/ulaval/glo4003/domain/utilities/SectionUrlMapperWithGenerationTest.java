package ca.ulaval.glo4003.domain.utilities;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

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
public class SectionUrlMapperWithGenerationTest {
	
	private static final String INVALID_TICKET_TYPE_URL = "invalid-ticket-type-url";
	private static final String URL_FROM_EXTREME = "oeuvre-de-l-amitie-confiance--coeur-penche-vigueur-eternelle";
	private static final String SECTION_NAME_EXTREME = "=Cœur penché, vigueur éternelle.";
	private static final String ADMISSION_TYPE_EXTREME = "Œuvre de l'amitié : confiance";
	private static final String URL_FROM_WITH_ACCENT = "specialissime--genialissime";
	private static final String SECTION_NAME_WITH_ACCENT = "Génialissime";
	private static final String ADMISSION_TYPE_WITH_ACCENT = "Spécialissime";
	private static final String SECTION_NAME = "Cramoisie";
	private static final String ADMISSION_TYPE = "VIP";
	private static final String URL = "vip--cramoisie";
	
	@Mock
	SectionDao sectionDao;
	
	@InjectMocks
	private SectionUrlMapperWithGeneration sectionUrlMapper;
	
	@Before
	public void setUp() throws Exception {
		Set<TicketType> ticketTypes = new HashSet<>();
		ticketTypes.add(new TicketType(ADMISSION_TYPE, SECTION_NAME));
		ticketTypes.add(new TicketType(ADMISSION_TYPE_WITH_ACCENT, SECTION_NAME_WITH_ACCENT));
		ticketTypes.add(new TicketType(ADMISSION_TYPE_EXTREME, SECTION_NAME_EXTREME));
		when(sectionDao.getAllTicketTypes()).thenReturn(ticketTypes);
	}
	
	@Test
	public void given_an_admissionType_and_a_sectionName_getSectionUrl_should_return_the_url() {
		String url = sectionUrlMapper.getSectionUrl(ADMISSION_TYPE, SECTION_NAME);
		assertEquals(URL, url);
	}
	
	@Test
	public void given_an_admissionType_with_accent_and_a_sectionName_with_accent_getSectionUrl_should_return_the_url() {
		String url = sectionUrlMapper.getSectionUrl(ADMISSION_TYPE_WITH_ACCENT, SECTION_NAME_WITH_ACCENT);
		assertEquals(URL_FROM_WITH_ACCENT, url);
	}
	
	@Test
	public void given_an_admissionType_extreme_and_a_sectionName_extreme_getSectionUrl_should_return_the_url() {
		String url = sectionUrlMapper.getSectionUrl(ADMISSION_TYPE_EXTREME, SECTION_NAME_EXTREME);
		assertEquals(URL_FROM_EXTREME, url);
	}
	
	@Test
	public void given_a_ticketTypeUrl_getTicketType_should_return_the_ticketType() throws NoTicketTypeForUrlException {
		TicketType actual = sectionUrlMapper.getTicketType(URL);
		TicketType expected = new TicketType(ADMISSION_TYPE, SECTION_NAME);
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void given_an_other_ticketTypeUrl_getTicketType_should_return_the_ticketType() throws NoTicketTypeForUrlException {
		TicketType actual = sectionUrlMapper.getTicketType(URL_FROM_EXTREME);
		TicketType expected = new TicketType(ADMISSION_TYPE_EXTREME, SECTION_NAME_EXTREME);
		
		assertEquals(expected, actual);
	}
	
	@Test(expected=NoTicketTypeForUrlException.class)
	public void given_an_invalid_ticketTypeUrl_getTicketType_should_raise_NoTicketTypeForUrlException() throws NoTicketTypeForUrlException {
		sectionUrlMapper.getTicketType(INVALID_TICKET_TYPE_URL);
	}
}
