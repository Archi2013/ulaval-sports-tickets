package ca.ulaval.glo4003.web.converter;

import static com.google.common.collect.Lists.*;

import java.util.List;

import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.dto.GameDto;
import ca.ulaval.glo4003.dto.SportDto;
import ca.ulaval.glo4003.utility.SportUrlMapper;
import ca.ulaval.glo4003.web.viewmodel.GameSimpleViewModel;
import ca.ulaval.glo4003.web.viewmodel.SportViewModel;

import com.google.common.base.Function;

@Component
public class SportConverter {
	SportUrlMapper sportUrlMapper = new SportUrlMapper();
	GameSimpleConverter gameSimpleConverter = new GameSimpleConverter();
	
	public List<SportViewModel> convert(List<SportDto> sportDtos) {
		return transform(sportDtos, new Function<SportDto, SportViewModel>() {
			public SportViewModel apply(SportDto sportDto) {
				return convert(sportDto);
			}
		});
	}
	
	public SportViewModel convert(SportDto sportDto) {
		return new SportViewModel(sportDto.getName(), convertListGameDto(sportDto.getGames()));
	}

	private List<GameSimpleViewModel> convertListGameDto(List<GameDto> gameDtos) {
		return gameSimpleConverter.convert(gameDtos);
	}
}
