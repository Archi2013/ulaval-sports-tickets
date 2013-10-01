package ca.ulaval.glo4003.web.converter;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.dto.SportDto;
import ca.ulaval.glo4003.utility.SportDoesntExistInPropertieFileException;
import ca.ulaval.glo4003.utility.SportUrlMapper;
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
