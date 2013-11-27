package ca.ulaval.glo4003.presentation.converters;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.domain.game.GameDto;
import ca.ulaval.glo4003.presentation.viewmodels.GameViewModel;
import ca.ulaval.glo4003.utilities.Constants;

@Component
public class GameConverter extends AbstractConverter<GameDto, GameViewModel> {
	
	@Inject
	Constants constants;

	public GameViewModel convert(GameDto gameDto) {
		return new GameViewModel(
				gameDto.getGameDate(),
				gameDto.getOpponents(),
				gameDto.getLocation(),
				constants.toLongDateTimeFormatFR(gameDto.getGameDate()),
				gameDto.getNumberOfTickets());
	}
}
