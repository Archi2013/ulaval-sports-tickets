package ca.ulaval.glo4003.presentation.converters;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4003.presentation.viewmodels.SportViewModel;
import ca.ulaval.glo4003.sports.dto.SportDto;
import ca.ulaval.glo4003.utilities.urlmapper.SportUrlMapper;

@RunWith(MockitoJUnitRunner.class)
public class SportConverterTest {
	private static final String SPORT_URL1 = "handball";
	private static final String SPORT_NAME1 = "Handball";
	private static final String SPORT_URL2 = "escrime";
	private static final String SPORT_NAME2 = "Escrime";

	@Mock
	SportUrlMapper sportUrlMapper;

	@InjectMocks
	SportConverter sportConverter;

	SportDto sportDto1;
	SportDto sportDto2;
	List<SportDto> sportDtos;

	@Before
	public void setUp() {
		sportDto1 = new SportDto(SPORT_NAME1);
		// sportDto1.getGames().add(new GameDto("", DateTime.now(), "", ""));
		// sportDto1.getGames().add(new GameDto("", DateTime.now().plusDays(3),
		// "", ""));

		sportDto2 = new SportDto(SPORT_NAME2);
		// sportDto2.getGames().add(new GameDto("", DateTime.now().plusDays(10),
		// "", ""));
		// sportDto2.getGames().add(new GameDto("", DateTime.now().plusDays(1),
		// "", ""));

		sportDtos = newArrayList();
		sportDtos.add(sportDto1);
		sportDtos.add(sportDto2);
	}

	@Test
	public void given_a_SportDto_convert_should_return_a_SportViewModel() {
		when(sportUrlMapper.getUrl(SPORT_NAME1)).thenReturn(SPORT_URL1);

		SportViewModel sportSVM = sportConverter.convert(sportDto1);

		assertEquals(sportSVM.name, SPORT_NAME1);
		assertEquals(sportSVM.url, SPORT_URL1);
	}

	@Test
	public void given_sportDtoList_convert_should_return_a_SportViewList() throws RuntimeException {
		when(sportUrlMapper.getUrl(SPORT_NAME1)).thenReturn(SPORT_URL1);
		when(sportUrlMapper.getUrl(SPORT_NAME2)).thenReturn(SPORT_URL2);

		List<SportViewModel> sportSVMs = sportConverter.convert(sportDtos);

		for (int i = 0; i < sportDtos.size(); i++) {
			assertEquals(sportDtos.get(i).getName(), sportSVMs.get(i).name);
		}
	}
}
