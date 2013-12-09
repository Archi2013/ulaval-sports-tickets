package ca.ulaval.glo4003.domain.sections;

import ca.ulaval.glo4003.sections.dto.SectionDto;


public interface SectionFactory {

	public Section createSection(SectionDto sectionDto);

}
