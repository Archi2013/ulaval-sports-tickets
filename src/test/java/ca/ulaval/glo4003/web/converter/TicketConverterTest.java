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
import ca.ulaval.glo4003.dto.TicketDto;
import ca.ulaval.glo4003.web.viewmodel.TicketViewModel;

@RunWith(MockitoJUnitRunner.class)
public class TicketConverterTest {
	
	@Mock
	GameDto gameDto;
	
	@InjectMocks
	TicketConverter ticketConverter;
	
	TicketDto ticketDto1;
	TicketDto ticketDto2;
	List<TicketDto> ticketDtos;
	
	@Before
	public void setUp() {
		ticketDto1 = new TicketDto(gameDto, 123456789, 26.95, "Général", "Ocre");
		ticketDto2 = new TicketDto(gameDto, 987654321, 59.62, "VIP", "Champagne");
		
		ticketDtos = newArrayList();
		ticketDtos.add(ticketDto1);
		ticketDtos.add(ticketDto2);
		
		when(gameDto.getOpponents()).thenReturn("Blanc Astral");
		when(gameDto.getGameDate()).thenReturn(DateTime.now().plusDays(7));
	}
	
	@Test
	public void given_a_TicketDto_convert_should_return_a_TicketViewModel() {
		TicketViewModel ticketVM = ticketConverter.convert(ticketDto1);
		
		assertEquals(ticketVM.id, new Long(ticketDto1.getTicketId()));
	}
	
	@Test
	public void given_TicketDtoList_convert_should_return_a_TicketViewModelList() {
		List<TicketViewModel> ticketVMs = ticketConverter.convert(ticketDtos);
		
		for(int i = 0 ; i < ticketDtos.size() ; i++) {
			assertEquals(new Long(ticketDtos.get(i).getTicketId()), ticketVMs.get(i).id);
		}
	}

}
