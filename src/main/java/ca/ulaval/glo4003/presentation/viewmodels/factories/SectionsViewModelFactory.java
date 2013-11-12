package ca.ulaval.glo4003.presentation.viewmodels.factories;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.domain.dtos.GameDto;
import ca.ulaval.glo4003.domain.dtos.SectionDto;
import ca.ulaval.glo4003.domain.utilities.Constants;
import ca.ulaval.glo4003.presentation.viewmodels.SectionsViewModel;

@Component
public class SectionsViewModelFactory {

	@Inject
	private SectionViewModelFactory sectionFactory;
	
	@Inject
	private Constants constants;

	public SectionsViewModel createViewModel(GameDto gameDto, List<SectionDto> sections) {
		return new SectionsViewModel(gameDto.getOpponents(), constants.toLongDateTimeFormatFR(gameDto.getGameDate()),
				sectionFactory.createViewModel(sections, gameDto));
	}
}
