package ca.ulaval.glo4003.web.converter;

import static com.google.common.collect.Lists.*;

import java.util.List;

import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.dto.SportDto;
import ca.ulaval.glo4003.utility.SportDoesntExistInPropertieFileException;
import ca.ulaval.glo4003.utility.SportUrlMapper;
import ca.ulaval.glo4003.web.viewmodel.SportSimpleViewModel;

import com.google.common.base.Function;

@Component
public class SportSimpleConverter {
	SportUrlMapper sportUrlMapper = new SportUrlMapper();
	
	public List<SportSimpleViewModel> convert(List<SportDto> sportDtos) {
		return transform(sportDtos, new Function<SportDto, SportSimpleViewModel>() {
			public SportSimpleViewModel apply(SportDto sportDto) {
				return convert(sportDto);
			}
		});
	}
	
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
