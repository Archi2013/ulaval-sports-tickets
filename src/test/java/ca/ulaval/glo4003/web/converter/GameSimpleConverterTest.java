package ca.ulaval.glo4003.web.converter;

import static com.google.common.collect.Lists.*;
import static org.junit.Assert.*;

import java.util.List;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4003.domain.dtos.GameDto;
import ca.ulaval.glo4003.web.converters.GameConverter;
import ca.ulaval.glo4003.web.viewmodels.GameViewModel;

@RunWith(MockitoJUnitRunner.class)
public class GameSimpleConverterTest {

	@InjectMocks
	GameConverter gameSimpleConverter;

	List<GameDto> gameDtos;
	GameDto gameDto1;
	GameDto gameDto2;

	@Before
	public void setUp() {
		gameDto1 = new GameDto(1, "", DateTime.now().plusDays(6));
		gameDto2 = new GameDto(2, "", DateTime.now().plusDays(11));

		gameDtos = newArrayList();
		gameDtos.add(gameDto1);
		gameDtos.add(gameDto2);
	}

	@Test
	public void given_a_GameDto_convert_should_return_a_GameSimpleViewModel() {
		GameViewModel gameSVM = gameSimpleConverter.convert(gameDto1);

		assertEquals(gameSVM.id, new Long(gameDto1.getId()));
		assertEquals(gameSVM.opponents, gameDto1.getOpponents());
	}

	@Test
	public void given_GameDtoList_convert_should_return_a_GameSimpleViewModelList() {
		List<GameViewModel> gameSVMs = gameSimpleConverter.convert(gameDtos);

		for (int i = 0; i < gameDtos.size(); i++) {
			assertEquals(new Long(gameDtos.get(i).getId()), gameSVMs.get(i).id);
			assertEquals(gameDtos.get(i).getOpponents(), gameSVMs.get(i).opponents);
		}
	}
}
