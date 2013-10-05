package ca.ulaval.glo4003.web.converter;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.domain.dtos.GameDto;
import ca.ulaval.glo4003.domain.dtos.SportDto;
import ca.ulaval.glo4003.web.viewmodel.GameSimpleViewModel;
import ca.ulaval.glo4003.web.viewmodel.SportViewModel;

@Component
public class SportConverter extends AbstractConverter<SportDto, SportViewModel> {
	@Inject
	GameSimpleConverter gameSimpleConverter;

	@Override
	public SportViewModel convert(SportDto sportDto) {
		return new SportViewModel(sportDto.getName(), convertListGameDto(sportDto.getGames()));
	}

	private List<GameSimpleViewModel> convertListGameDto(List<GameDto> gameDtos) {
		return gameSimpleConverter.convert(gameDtos);
	}
}
