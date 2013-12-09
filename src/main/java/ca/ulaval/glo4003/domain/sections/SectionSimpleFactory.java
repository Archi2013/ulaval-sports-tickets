package ca.ulaval.glo4003.domain.sections;

import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.sections.dto.SectionDto;

@Component
public class SectionSimpleFactory implements SectionFactory {

	@Override
	public Section createSection(SectionDto sectionDto) {
		return new Section(sectionDto);
	}

}
