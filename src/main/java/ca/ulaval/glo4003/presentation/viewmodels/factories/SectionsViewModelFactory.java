package ca.ulaval.glo4003.presentation.viewmodels.factories;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.game.dto.GameDto;
import ca.ulaval.glo4003.presentation.viewmodels.SectionsViewModel;
import ca.ulaval.glo4003.sections.dto.SectionDto;
import ca.ulaval.glo4003.utilities.Constants;

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
