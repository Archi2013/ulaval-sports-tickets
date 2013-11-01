package ca.ulaval.glo4003.web.converters;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.domain.dtos.SportDto;
import ca.ulaval.glo4003.domain.utilities.SportUrlMapper;
import ca.ulaval.glo4003.web.viewmodels.SportViewModel;

@Component
public class SportConverter extends AbstractConverter<SportDto, SportViewModel> {

	@Inject
	SportUrlMapper sportUrlMapper;

	public SportViewModel convert(SportDto sportDto) {
		String url = sportUrlMapper.getSportUrl(sportDto.getName());
		return new SportViewModel(sportDto.getName(), url);
	}
}
