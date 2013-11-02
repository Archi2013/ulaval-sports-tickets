package ca.ulaval.glo4003.domain.utilities;

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

import ca.ulaval.glo4003.persistence.daos.SportDao;

@RunWith(MockitoJUnitRunner.class)
public class SportUrlMapperWithGenerationTest {

	private static final String SPORT_NAME = "Soccer mixte";
	private static final String SPORT_URL = "soccer-mixte";
	private static final String SPORT_NAME_WITH_ACCENT = "Baseball féminin";
	private static final String SPORT_URL_WITH_ACCENT = "baseball-feminin";
	private static final String SPORT_NAME_FIRST_LETTER_WITH_ACCENT = "Équitation";
	private static final String SPORT_URL_FIRST_LETTER_WITH_ACCENT = "equitation";
	private static final String SPORT_NAME_EXTREME = "Œuvre d'art de l'être-aimé : enfant";
	private static final String SPORT_URL_EXTREME = "oeuvre-d-art-de-l-etre-aime-enfant";
	private static final String INVALID_SPORT_URL = "une-url-ne-correspondant-a-aucun-sport";
	
	@Mock
	private SportDao sportDao;
	
	@InjectMocks
	private SportUrlMapperWithGeneration sportUrlMapper;
	
	@Before
	public void setUp() throws Exception {
		List<String> sports = new ArrayList<>();
		sports.add(SPORT_NAME);
		sports.add(SPORT_NAME_WITH_ACCENT);
		sports.add(SPORT_NAME_FIRST_LETTER_WITH_ACCENT);
		sports.add(SPORT_NAME_EXTREME);
		when(sportDao.getAllSportNames()).thenReturn(sports);
	}

	@Test
	public void given_a_valid_sportName_getSportUrl_should_return_the_url() {
		String url = sportUrlMapper.getSportUrl(SPORT_NAME);
		assertEquals(SPORT_URL, url);
	}
	
	@Test
	public void given_a_sportName_with_accent_getSportUrl_should_return_the_url() {
		String url = sportUrlMapper.getSportUrl(SPORT_NAME_WITH_ACCENT);
		assertEquals(SPORT_URL_WITH_ACCENT, url);
	}
	
	@Test
	public void given_a_sportName_first_letter_with_accent_getSportUrl_should_return_the_url() {
		String url = sportUrlMapper.getSportUrl(SPORT_NAME_FIRST_LETTER_WITH_ACCENT);
		assertEquals(SPORT_URL_FIRST_LETTER_WITH_ACCENT, url);
	}

	@Test
	public void given_a_sportName_extreme_getSportUrl_should_return_the_url() {
		String url = sportUrlMapper.getSportUrl(SPORT_NAME_EXTREME);
		assertEquals(SPORT_URL_EXTREME, url);
	}
	
	@Test
	public void given_a_sportUrl_getSportName_should_return_the_name() throws NoSportForUrlException {
		String name = sportUrlMapper.getSportName(SPORT_URL);
		assertEquals(SPORT_NAME, name);
	}
	
	@Test
	public void given_an_other_sportUrl_getSportName_should_return_the_name() throws NoSportForUrlException {
		String name = sportUrlMapper.getSportName(SPORT_URL_EXTREME);
		assertEquals(SPORT_NAME_EXTREME, name);
	}
	
	@Test(expected = NoSportForUrlException.class)
	public void given_an_invalid_sportUrl_getSportName_should_raise_NoSportForUrlException() throws NoSportForUrlException {
		sportUrlMapper.getSportName(INVALID_SPORT_URL);
	}
}
