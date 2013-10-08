package ca.ulaval.glo4003.web.converters;

import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.domain.dtos.GameDto;
import ca.ulaval.glo4003.web.viewmodels.GameViewModel;

@Component
public class GameConverter extends AbstractConverter<GameDto, GameViewModel> {

	public GameViewModel convert(GameDto gameDto) {
		return new GameViewModel(gameDto.getId(), gameDto.getOpponents(), gameDto.getGameDate().toString(
				"d MMMM yyyy à HH'h'mm z"), gameDto.getNumberOfTickets());
	}
}
