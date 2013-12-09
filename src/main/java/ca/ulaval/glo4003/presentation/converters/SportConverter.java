package ca.ulaval.glo4003.presentation.converters;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.presentation.viewmodels.SportViewModel;
import ca.ulaval.glo4003.sports.dto.SportDto;
import ca.ulaval.glo4003.utilities.urlmapper.SportUrlMapper;

@Component
public class SportConverter extends AbstractConverter<SportDto, SportViewModel> {

	@Inject
	SportUrlMapper sportUrlMapper;

	public SportViewModel convert(SportDto sportDto) {
		String url = sportUrlMapper.getUrl(sportDto.getName());
		return new SportViewModel(sportDto.getName(), url);
	}
}
