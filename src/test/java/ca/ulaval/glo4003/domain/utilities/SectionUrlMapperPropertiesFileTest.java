/*package ca.ulaval.glo4003.domain.utilities;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SectionUrlMapperPropertiesFileTest {
	private static final String ADMISSION_TYPE = "VIP";
	private static final String SECTION_NAME = "Bleus";
	private static final String SECTION_URL = "vip-bleus";
	private static final TicketType TYPE = new TicketType(ADMISSION_TYPE, SECTION_NAME);

	private static final String INVALID_SECTION_NAME = "Section fictive";
	private static final String INVALID_SECTION_URL = "section-fictive";

	@Mock
	Properties properties;

	@InjectMocks
	SectionUrlMapperPropertiesFile sectionUrlMapper;

	Set<Object> keySet;

	@Before
	public void setUp() {
		keySet = new HashSet<>();
		keySet.add(SECTION_URL);
		keySet.add("OTHER URL");
		keySet.add("OTHER OTHER URL");

		when(properties.keySet()).thenReturn(keySet);
		when(properties.isEmpty()).thenReturn(false);

		when(properties.getProperty(anyString())).thenReturn("");
		when(properties.getProperty(SECTION_URL)).thenReturn(ADMISSION_TYPE + ":" + SECTION_NAME);
	}

	@Test
	public void with_an_existing_section_getSectionUrl_should_return_the_url() throws SectionDoesntExistInPropertiesFileException {
		String sectionUrl = sectionUrlMapper.getUrl(ADMISSION_TYPE, SECTION_NAME);

		assertEquals(sectionUrl, SECTION_URL);
	}

	@Test
	public void with_an_existing_section_getTicketType_should_return_the_ticket_type()
			throws SectionDoesntExistInPropertiesFileException {
		TicketType type = sectionUrlMapper.getTicketType(SECTION_URL);

		assertEquals(type.admissionType, ADMISSION_TYPE);
		assertEquals(type.sectionName, SECTION_NAME);
	}

	@Test(expected = SectionDoesntExistInPropertiesFileException.class)
	public void given_an_invalid_section_name_getSectionUrl_shoudl_raise_a_SectionDoesntExistInPropertiesFileException()
			throws SectionDoesntExistInPropertiesFileException {

		sectionUrlMapper.getUrl(ADMISSION_TYPE, INVALID_SECTION_NAME);
	}

	@Test(expected = SectionDoesntExistInPropertiesFileException.class)
	public void given_an_invalid_section_url_getTicketType_should_raise_a_SectionDoesntExistInPropertiesFileException()
			throws SectionDoesntExistInPropertiesFileException {
		when(properties.getProperty(INVALID_SECTION_URL)).thenReturn(null);

		sectionUrlMapper.getTicketType(INVALID_SECTION_URL);
	}

	@Test
	public void when_second_use_getSectionUrl_should_return_the_url() throws SectionDoesntExistInPropertiesFileException {
		String section = sectionUrlMapper.getUrl(ADMISSION_TYPE, SECTION_NAME);

		assertEquals(section, SECTION_URL);
	}

	@Test
	public void when_second_use_getTicketType_should_return_the_ticket_type() throws SectionDoesntExistInPropertiesFileException {
		TicketType type = sectionUrlMapper.getTicketType(SECTION_URL);

		assertEquals(type.admissionType, ADMISSION_TYPE);
		assertEquals(type.sectionName, SECTION_NAME);
	}

	@Test(expected = RuntimeException.class)
	public void when_properties_file_doesnt_exist_getSectionUrl_shoudl_raise_a_RuntimeException()
			throws SectionDoesntExistInPropertiesFileException, IOException {
		when(properties.isEmpty()).thenReturn(true);
		doThrow(IOException.class).when(properties).load((InputStream) any());

		sectionUrlMapper.getUrl(ADMISSION_TYPE, SECTION_NAME);
	}

	@Test(expected = RuntimeException.class)
	public void when_properties_file_doesnt_exist_getTicketType_should_raise_a_RuntimeException()
			throws SectionDoesntExistInPropertiesFileException, IOException {
		when(properties.isEmpty()).thenReturn(true);
		doThrow(IOException.class).when(properties).load((InputStream) any());

		sectionUrlMapper.getTicketType(SECTION_URL);
	}
}
*/
