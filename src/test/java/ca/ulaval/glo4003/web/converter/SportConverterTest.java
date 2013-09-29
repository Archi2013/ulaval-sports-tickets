package ca.ulaval.glo4003.web.converter;

import static com.google.common.collect.Lists.*;
import static org.junit.Assert.*;

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
import ca.ulaval.glo4003.web.viewmodel.SportViewModel;

@RunWith(MockitoJUnitRunner.class)
public class SportConverterTest {
	private static final String SPORT_NAME1 = "Handball";
	private static final String SPORT_NAME2 = "Escrime";

	@Mock
	GameSimpleConverter gameSimpleConverter;
	
	@InjectMocks
	SportConverter sportConverter;
	
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
	public void given_a_SportDto_convert_should_return_a_SportViewModel() {
		SportViewModel sportVM = sportConverter.convert(sportDto1);
		
		assertEquals(sportVM.name, SPORT_NAME1);
	}
	
	@Test
	public void given_SportDtoList_convert_should_return_a_SportViewModelList() {
		List<SportViewModel> sportVMs = sportConverter.convert(sportDtos);
		
		for(int i = 0 ; i < sportDtos.size() ; i++) {
			assertEquals(sportDtos.get(i).getName(), sportVMs.get(i).name);
		}
	}

}
