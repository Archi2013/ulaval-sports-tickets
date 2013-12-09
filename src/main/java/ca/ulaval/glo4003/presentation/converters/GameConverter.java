package ca.ulaval.glo4003.presentation.converters;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.game.dto.GameDto;
import ca.ulaval.glo4003.presentation.viewmodels.GameViewModel;
import ca.ulaval.glo4003.utilities.Constants;
import ca.ulaval.glo4003.utilities.time.UrlDateTime;

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
				new UrlDateTime(gameDto.getGameDate()),
				gameDto.getNumberOfTickets());
	}
}
