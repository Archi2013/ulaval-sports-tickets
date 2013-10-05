package ca.ulaval.glo4003.web.converters;

import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.domain.dtos.SectionDto;
import ca.ulaval.glo4003.web.viewmodels.SectionViewModel;

@Component
public class SectionConverter extends AbstractConverter<SectionDto, SectionViewModel> {

	@Override
	public SectionViewModel convert(SectionDto dto) {
		return new SectionViewModel(dto.getAdmissionType(), dto.getSectionName(), dto.getNumberOfTickets());
	}

}
