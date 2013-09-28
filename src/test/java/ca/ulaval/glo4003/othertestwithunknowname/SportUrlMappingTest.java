package ca.ulaval.glo4003.othertestwithunknowname;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4003.utility.SportDoesntExistInPropertieFileException;
import ca.ulaval.glo4003.utility.SportUrlMapper;

@RunWith(MockitoJUnitRunner.class)
public class SportUrlMappingTest {
	
	private static final String SPORT_NAME = "Basketball";
	private static final String SPORT_URL = "basketball";
	private static final String SPORT_NAME_SPACES = "Tennis de table";
	private static final String SPORT_URL_SPACES = "tennis-de-table";
	private static final String INVALID_SPORT_NAME = "Sport fictif";
	private static final String INVALID_SPORT_URL = "sport-interdit-a-la-pratique";
	private static final String SPORT_NAME_ACCENTS = "Athlétisme";
	private static final String SPORT_URL_ACCENTS = "athletisme";

	@InjectMocks
	SportUrlMapper sportUrlMapper;

	@Before
	public void setUp() {
		sportUrlMapper.setPropertiesFileName("sport-url-for-test.properties");
	}
	
	@Test
	public void with_a_existing_sport_getSportUrl_should_return_the_url() throws RuntimeException, SportDoesntExistInPropertieFileException {
		String sportUrl = sportUrlMapper.getSportUrl(SPORT_NAME);
		assertEquals(sportUrl, SPORT_URL);
	}
	
	@Test
	public void with_a_existing_sport_getSportName_should_return_the_name() throws RuntimeException, SportDoesntExistInPropertieFileException {
		String sportName = sportUrlMapper.getSportName(SPORT_URL);
		assertEquals(sportName, SPORT_NAME);
	}
	
	@Test
	public void with_a_existing_sport_containing_spaces_getSportUrl_should_return_the_url() throws RuntimeException, SportDoesntExistInPropertieFileException {
		String sportUrl = sportUrlMapper.getSportUrl(SPORT_NAME_SPACES);
		assertEquals(sportUrl, SPORT_URL_SPACES);
	}
	
	@Test
	public void with_a_existing_sport_containing_spaces_getSportName_should_return_the_name() throws RuntimeException, SportDoesntExistInPropertieFileException {
		String sportName = sportUrlMapper.getSportName(SPORT_URL_SPACES);
		assertEquals(sportName, SPORT_NAME_SPACES);
	}
	
	@Test
	public void with_a_existing_sport_containing_accents_getSportUrl_should_return_the_url() throws RuntimeException, SportDoesntExistInPropertieFileException {
		String sportUrl = sportUrlMapper.getSportUrl(SPORT_NAME_ACCENTS);
		assertEquals(sportUrl, SPORT_URL_ACCENTS);
	}
	
	@Test
	public void with_a_existing_sport_containing_accents_getSportName_should_return_the_name() throws RuntimeException, SportDoesntExistInPropertieFileException {
		String sportName = sportUrlMapper.getSportName(SPORT_URL_ACCENTS);
		assertEquals(sportName, SPORT_NAME_ACCENTS);
	}
	
	@Test(expected = SportDoesntExistInPropertieFileException.class)
	public void with_an_invalid_sport_name_getSportUrl_shoudl_raise_a_RuntimeException() throws RuntimeException, SportDoesntExistInPropertieFileException {
		sportUrlMapper.getSportUrl(INVALID_SPORT_NAME);
	}
	
	@Test(expected = SportDoesntExistInPropertieFileException.class)
	public void with_an_invalid_sport_url_getSportName_raise_a_RuntimeException() throws RuntimeException, SportDoesntExistInPropertieFileException {
		sportUrlMapper.getSportName(INVALID_SPORT_URL);
	}
}
