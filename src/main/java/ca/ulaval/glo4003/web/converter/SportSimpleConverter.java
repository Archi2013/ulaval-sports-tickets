package ca.ulaval.glo4003.web.converter;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.domain.dtos.SportDto;
import ca.ulaval.glo4003.domain.utilities.SportDoesntExistInPropertieFileException;
import ca.ulaval.glo4003.domain.utilities.SportUrlMapper;
import ca.ulaval.glo4003.web.viewmodel.SportSimpleViewModel;

@Component
public class SportSimpleConverter extends AbstractConverter<SportDto, SportSimpleViewModel> {

	@Inject
	SportUrlMapper sportUrlMapper;

	public SportSimpleViewModel convert(SportDto sportDto) {
		String url;
		try {
			url = sportUrlMapper.getSportUrl(sportDto.getName());
		} catch (RuntimeException | SportDoesntExistInPropertieFileException e) {
			url = "erreur";
		}
		return new SportSimpleViewModel(sportDto.getName(), url);
	}
}
