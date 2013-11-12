package ca.ulaval.glo4003.web.converters;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.domain.dtos.GameDto;
import ca.ulaval.glo4003.domain.utilities.Constants;
import ca.ulaval.glo4003.web.viewmodels.GameViewModel;

@Component
public class GameConverter extends AbstractConverter<GameDto, GameViewModel> {
	
	@Inject
	Constants constants;

	public GameViewModel convert(GameDto gameDto) {
		return new GameViewModel(gameDto.getId(), gameDto.getOpponents(),
				gameDto.getLocation(),
				constants.toLongDateTimeFormatFR(gameDto.getGameDate()),
				gameDto.getNumberOfTickets());
	}
}
