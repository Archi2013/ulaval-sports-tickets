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

import ca.ulaval.glo4003.domain.dtos.GameDto;
import ca.ulaval.glo4003.web.viewmodel.GameViewModel;

@RunWith(MockitoJUnitRunner.class)
public class GameConverterTest {
	
	@Mock
	TicketConverter ticketConverter;
	
	@InjectMocks
	GameConverter gameConverter;
	
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
	public void given_a_GameDto_convert_should_return_a_GameViewModel() {
		GameViewModel gameVM = gameConverter.convert(gameDto1);
		
		assertEquals(gameVM.id, new Long(gameDto1.getId()));
		assertEquals(gameVM.opponents, gameDto1.getOpponents());
	}
	
	@Test
	public void given_GameDtoList_convert_should_return_a_GameViewModelList() {
		List<GameViewModel> gameVMs = gameConverter.convert(gameDtos);
		
		for(int i = 0 ; i < gameDtos.size() ; i++) {
			assertEquals(new Long(gameDtos.get(i).getId()), gameVMs.get(i).id);
			assertEquals(gameDtos.get(i).getOpponents(), gameVMs.get(i).opponents);
		}
	}
}
