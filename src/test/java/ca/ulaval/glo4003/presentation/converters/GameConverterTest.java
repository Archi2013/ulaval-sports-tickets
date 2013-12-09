package ca.ulaval.glo4003.presentation.converters;

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

import ca.ulaval.glo4003.game.dto.GameDto;
import ca.ulaval.glo4003.presentation.converters.GameConverter;
import ca.ulaval.glo4003.presentation.viewmodels.GameViewModel;
import ca.ulaval.glo4003.utilities.Constants;

@RunWith(MockitoJUnitRunner.class)
public class GameConverterTest {

	@Mock
	Constants constants;
	
	@InjectMocks
	GameConverter gameConverter;

	List<GameDto> gameDtos;
	GameDto gameDto1;
	GameDto gameDto2;

	@Before
	public void setUp() {
		gameDto1 = new GameDto("", DateTime.now().plusDays(6), "", "");
		gameDto2 = new GameDto("", DateTime.now().plusDays(11), "", "");

		gameDtos = newArrayList();
		gameDtos.add(gameDto1);
		gameDtos.add(gameDto2);
	}

	@Test
	public void given_a_GameDto_convert_should_return_a_GameSimpleViewModel() {
		GameViewModel gameSVM = gameConverter.convert(gameDto1);

		assertEquals(gameSVM.getGameDate(), gameDto1.getGameDate());
		assertEquals(gameSVM.opponents, gameDto1.getOpponents());
	}

	@Test
	public void given_GameDtoList_convert_should_return_a_GameSimpleViewModelList() {
		List<GameViewModel> gameSVMs = gameConverter.convert(gameDtos);

		for (int i = 0; i < gameDtos.size(); i++) {
			assertEquals(gameDtos.get(i).getGameDate(), gameSVMs.get(i).getGameDate());
			assertEquals(gameDtos.get(i).getOpponents(), gameSVMs.get(i).opponents);
		}
	}
}
