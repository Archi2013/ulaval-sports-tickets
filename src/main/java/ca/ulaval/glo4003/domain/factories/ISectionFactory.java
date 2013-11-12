package ca.ulaval.glo4003.domain.factories;

import ca.ulaval.glo4003.domain.dtos.SectionDto;
import ca.ulaval.glo4003.domain.pojos.Section;

public interface ISectionFactory {

	public Section createSection(SectionDto sectionDto);

}