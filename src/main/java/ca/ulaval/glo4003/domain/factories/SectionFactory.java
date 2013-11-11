package ca.ulaval.glo4003.domain.factories;

import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.domain.dtos.SectionDto;
import ca.ulaval.glo4003.domain.pojos.Section;

@Component
public class SectionFactory implements ISectionFactory {

	@Override
	public Section createSection(SectionDto sectionDto) {
		return new Section(sectionDto);
	}

}
