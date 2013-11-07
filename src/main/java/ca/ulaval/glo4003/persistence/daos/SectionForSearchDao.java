package ca.ulaval.glo4003.persistence.daos;

import java.util.List;

import ca.ulaval.glo4003.domain.dtos.SectionForSearchDto;
import ca.ulaval.glo4003.domain.dtos.TicketSearchPreferenceDto;

public interface SectionForSearchDao {

	public List<SectionForSearchDto> getSections(TicketSearchPreferenceDto ticketSearchPreferenceDto);

}
