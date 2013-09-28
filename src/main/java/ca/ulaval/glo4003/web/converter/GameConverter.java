package ca.ulaval.glo4003.web.converter;

import static com.google.common.collect.Lists.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.dto.GameDto;
import ca.ulaval.glo4003.dto.TicketDto;
import ca.ulaval.glo4003.web.viewmodel.GameViewModel;
import ca.ulaval.glo4003.web.viewmodel.TicketViewModel;

import com.google.common.base.Function;

@Component
public class GameConverter {
	TicketConverter ticketConverter = new TicketConverter();
	
	public List<GameViewModel> convert(List<GameDto> gameDtos) {
		return transform(gameDtos, new Function<GameDto, GameViewModel>() {
			public GameViewModel apply(GameDto gameDto) {
				return convert(gameDto);
			}
		});
	}
	
	public GameViewModel convert(GameDto gameDto) {
		return new GameViewModel(gameDto.getId(), gameDto.getOpponents(),
				gameDto.getGameDate().toString("dd MMMM yyyy à HH'h'mm z"), convertListTicketDto(gameDto.getTickets()));
	}
	
	private List<TicketViewModel> convertListTicketDto(List<TicketDto> ticketDtos) {
		return ticketConverter.convert(ticketDtos);
	}
}
