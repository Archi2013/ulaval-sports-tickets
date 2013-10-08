package ca.ulaval.glo4003.web.viewmodels.factories;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.domain.dtos.GameDto;
import ca.ulaval.glo4003.domain.dtos.SectionDto;
import ca.ulaval.glo4003.web.viewmodels.SectionsViewModel;

@Component
public class SectionsViewModelFactory {

	@Inject
	private SectionViewModelFactory sectionFactory;

	public SectionsViewModel createViewModel(GameDto gameDto, List<SectionDto> sections) {
		return new SectionsViewModel(gameDto.getOpponents(), gameDto.getGameDate().toString("d MMMM yyyy à HH'h'mm z"),
				sectionFactory.createViewModel(sections, gameDto));
	}
}
