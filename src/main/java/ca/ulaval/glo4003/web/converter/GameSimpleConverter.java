package ca.ulaval.glo4003.web.converter;

import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.domain.dtos.GameDto;
import ca.ulaval.glo4003.web.viewmodel.GameSimpleViewModel;

@Component
public class GameSimpleConverter extends AbstractConverter<GameDto, GameSimpleViewModel> {

	public GameSimpleViewModel convert(GameDto gameDto) {
		return new GameSimpleViewModel(gameDto.getId(), gameDto.getOpponents(), gameDto.getGameDate().toString(
				"dd MMMM yyyy à HH'h'mm z"), gameDto.getNumberOfTickets());
	}
}
