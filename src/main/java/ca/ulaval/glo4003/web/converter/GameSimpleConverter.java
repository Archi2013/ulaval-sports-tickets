package ca.ulaval.glo4003.web.converter;

import static com.google.common.collect.Lists.*;

import java.util.List;

import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.dto.GameDto;
import ca.ulaval.glo4003.web.viewmodel.GameSimpleViewModel;

import com.google.common.base.Function;

@Component
public class GameSimpleConverter {
	
	public List<GameSimpleViewModel> convert(List<GameDto> gameDtos) {
		return transform(gameDtos, new Function<GameDto, GameSimpleViewModel>() {
			@Override
			public GameSimpleViewModel apply(GameDto gameDto) {
				return convert(gameDto);
			}
		});
	}
	
	public GameSimpleViewModel convert(GameDto gameDto) {
		return new GameSimpleViewModel(gameDto.getId(), gameDto.getOpponents(),
				gameDto.getGameDate().toString("dd MMMM yyyy à HH'h'mm z"),
				gameDto.getNumberOfTickets());
	}
}
