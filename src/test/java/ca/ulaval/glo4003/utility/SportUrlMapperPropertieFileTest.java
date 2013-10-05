package ca.ulaval.glo4003.utility;

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

import ca.ulaval.glo4003.domain.utilities.SportDoesntExistInPropertiesFileException;
import ca.ulaval.glo4003.domain.utilities.SportUrlMapperPropertiesFile;

@RunWith(MockitoJUnitRunner.class)
public class SportUrlMapperPropertieFileTest {
	private static final String SPORT_NAME = "Basketball";
	private static final String SPORT_URL = "basketball";
	private static final String SPORT_NAME_SPACES = "Tennis de table";
	private static final String SPORT_URL_SPACES = "tennis-de-table";
	private static final String INVALID_SPORT_NAME = "Sport fictif";
	private static final String INVALID_SPORT_URL = "sport-interdit-a-la-pratique";
	private static final String SPORT_NAME_ACCENTS = "Athlétisme";
	private static final String SPORT_URL_ACCENTS = "athletisme";

	@Mock
	Properties properties;
	
	@InjectMocks
	SportUrlMapperPropertiesFile sportUrlMapper;
	
	Set<Object> keySet;
	
	@Before
	public void setUp() {
		keySet = new HashSet<>();
		keySet.add(SPORT_URL);
		keySet.add(SPORT_URL_ACCENTS);
		keySet.add(SPORT_URL_SPACES);
	}
	
	@Test
	public void with_a_existing_sport_getSportUrl_should_return_the_url() throws RuntimeException, SportDoesntExistInPropertiesFileException {
		when(properties.getProperty(SPORT_URL)).thenReturn(SPORT_NAME);
		when(properties.keySet()).thenReturn(keySet);
		when(properties.isEmpty()).thenReturn(true);
		
		String sportUrl = sportUrlMapper.getSportUrl(SPORT_NAME);
		
		assertEquals(sportUrl, SPORT_URL);
	}
	
	@Test
	public void with_a_existing_sport_getSportName_should_return_the_name() throws RuntimeException, SportDoesntExistInPropertiesFileException {
		when(properties.getProperty(SPORT_URL)).thenReturn(SPORT_NAME);
		when(properties.isEmpty()).thenReturn(true);
		
		String sportName = sportUrlMapper.getSportName(SPORT_URL);
		
		assertEquals(sportName, SPORT_NAME);
	}
	
	@Test(expected = SportDoesntExistInPropertiesFileException.class)
	public void given_an_invalid_sport_name_getSportUrl_shoudl_raise_a_SportDoesntExistInPropertieFileException() throws RuntimeException, SportDoesntExistInPropertiesFileException {
		when(properties.getProperty(SPORT_URL)).thenReturn(SPORT_NAME);
		when(properties.getProperty(SPORT_URL_ACCENTS)).thenReturn(SPORT_NAME_ACCENTS);
		when(properties.getProperty(SPORT_URL_SPACES)).thenReturn(SPORT_NAME_SPACES);
		when(properties.keySet()).thenReturn(keySet);
		when(properties.isEmpty()).thenReturn(true);
		
		sportUrlMapper.getSportUrl(INVALID_SPORT_NAME);
	}
	
	@Test(expected = SportDoesntExistInPropertiesFileException.class)
	public void given_an_invalid_sport_url_getSportName_should_raise_a_SportDoesntExistInPropertieFileException() throws RuntimeException, SportDoesntExistInPropertiesFileException {
		when(properties.getProperty(SPORT_URL)).thenReturn(SPORT_NAME);
		when(properties.getProperty(SPORT_URL_ACCENTS)).thenReturn(SPORT_NAME_ACCENTS);
		when(properties.getProperty(SPORT_URL_SPACES)).thenReturn(SPORT_NAME_SPACES);
		when(properties.getProperty(INVALID_SPORT_URL)).thenReturn(null);
		when(properties.isEmpty()).thenReturn(true);
		
		sportUrlMapper.getSportName(INVALID_SPORT_URL);
	}
	
	@Test
	public void when_second_use_getSportUrl_should_return_the_url() throws RuntimeException, SportDoesntExistInPropertiesFileException {
		when(properties.getProperty(SPORT_URL)).thenReturn(SPORT_NAME);
		when(properties.keySet()).thenReturn(keySet);
		when(properties.isEmpty()).thenReturn(false);
		
		String sportUrl = sportUrlMapper.getSportUrl(SPORT_NAME);
		assertEquals(sportUrl, SPORT_URL);
	}
	
	@Test
	public void when_second_use_getSportName_should_return_the_name() throws RuntimeException, SportDoesntExistInPropertiesFileException {
		when(properties.getProperty(SPORT_URL)).thenReturn(SPORT_NAME);
		when(properties.isEmpty()).thenReturn(false);
		
		String sportName = sportUrlMapper.getSportName(SPORT_URL);
		assertEquals(sportName, SPORT_NAME);
	}
	
	@Test(expected = RuntimeException.class)
	public void when_propertie_file_doesnt_exist_getSportUrl_shoudl_raise_a_RuntimeException() throws RuntimeException, SportDoesntExistInPropertiesFileException, IOException {
		when(properties.isEmpty()).thenReturn(true);
		doThrow(IOException.class).when(properties).load((InputStream) any());
		
		sportUrlMapper.getSportUrl(SPORT_NAME);
	}
	
	@Test(expected = RuntimeException.class)
	public void when_propertie_file_doesnt_exist_getSportName_should_raise_a_RuntimeException() throws RuntimeException, SportDoesntExistInPropertiesFileException, IOException {
		when(properties.isEmpty()).thenReturn(true);
		doThrow(IOException.class).when(properties).load((InputStream) any());
		
		sportUrlMapper.getSportName(SPORT_URL);
	}
}
