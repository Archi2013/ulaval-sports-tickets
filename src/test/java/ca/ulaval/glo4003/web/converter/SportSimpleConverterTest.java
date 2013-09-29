package ca.ulaval.glo4003.web.converter;

import static com.google.common.collect.Lists.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4003.dto.GameDto;
import ca.ulaval.glo4003.dto.SportDto;
import ca.ulaval.glo4003.utility.SportDoesntExistInPropertieFileException;
import ca.ulaval.glo4003.utility.SportUrlMapper;
import ca.ulaval.glo4003.web.viewmodel.SportSimpleViewModel;

@RunWith(MockitoJUnitRunner.class)
public class SportSimpleConverterTest {
	private static final String ERROR_URL = "erreur";
	private static final String SPORT_URL1 = "handball";
	private static final String SPORT_NAME1 = "Handball";
	private static final String SPORT_URL2 = "escrime";
	private static final String SPORT_NAME2 = "Escrime";

	@Mock
	SportUrlMapper sportUrlMapper;
	
	@InjectMocks
	SportSimpleConverter sportSimpleConverter;
	
	SportDto sportDto1;
	SportDto sportDto2;
	List<SportDto> sportDtos;
	
	@Before
	public void setUp() {
		sportDto1 = new SportDto(SPORT_NAME1);
		sportDto1.getGames().add(new GameDto(1, "", DateTime.now()));
		sportDto1.getGames().add(new GameDto(2, "", DateTime.now().plusDays(3)));
		
		sportDto2 = new SportDto(SPORT_NAME2);
		sportDto2.getGames().add(new GameDto(1, "", DateTime.now().plusDays(10)));
		sportDto2.getGames().add(new GameDto(2, "", DateTime.now().plusDays(1)));
		
		sportDtos = newArrayList();
		sportDtos.add(sportDto1);
		sportDtos.add(sportDto2);
	}

	@Test
	public void given_a_SportDto_convert_should_return_a_SportSimpleViewModel() throws RuntimeException, SportDoesntExistInPropertieFileException {
		when(sportUrlMapper.getSportUrl(SPORT_NAME1)).thenReturn(SPORT_URL1);
		
		SportSimpleViewModel sportSVM = sportSimpleConverter.convert(sportDto1);
		
		assertEquals(sportSVM.name, SPORT_NAME1);
		assertEquals(sportSVM.url, SPORT_URL1);
	}
	
	@Test
	public void when_url_mapping_throw_SportDoesntExistInPropertieFileException_convert_should_return_a_SportSimpleViewModel_with_erreur_as_url() throws RuntimeException, SportDoesntExistInPropertieFileException {
		when(sportUrlMapper.getSportUrl(SPORT_NAME1)).thenThrow(SportDoesntExistInPropertieFileException.class);
		
		SportSimpleViewModel sportSVM = sportSimpleConverter.convert(sportDto1);
		
		assertEquals(sportSVM.name, SPORT_NAME1);
		assertEquals(sportSVM.url, ERROR_URL);
	}
	
	@Test
	public void when_url_mapping_throw_RuntimeException_convert_should_return_a_SportSimpleViewModel_with_erreur_as_url() throws RuntimeException, SportDoesntExistInPropertieFileException {
		when(sportUrlMapper.getSportUrl(SPORT_NAME1)).thenThrow(RuntimeException.class);
		
		SportSimpleViewModel sportSVM = sportSimpleConverter.convert(sportDto1);
		
		assertEquals(sportSVM.name, SPORT_NAME1);
		assertEquals(sportSVM.url, ERROR_URL);
	}

	@Test
	public void given_sportDtoList_convert_should_return_a_SportSimpleViewList() throws RuntimeException, SportDoesntExistInPropertieFileException {
		when(sportUrlMapper.getSportUrl(SPORT_NAME1)).thenReturn(SPORT_URL1);
		when(sportUrlMapper.getSportUrl(SPORT_NAME2)).thenReturn(SPORT_URL2);
		
		List<SportSimpleViewModel> sportSVMs = sportSimpleConverter.convert(sportDtos);
		
		for(int i = 0; i < sportDtos.size() ; i++) {
			assertEquals(sportDtos.get(i).getName(), sportSVMs.get(i).name);
		}
	}
}
