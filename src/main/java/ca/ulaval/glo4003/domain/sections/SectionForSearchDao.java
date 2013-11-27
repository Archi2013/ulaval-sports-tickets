package ca.ulaval.glo4003.domain.sections;

import java.util.List;

import ca.ulaval.glo4003.domain.tickets.TicketSearchPreferenceDto;

public interface SectionForSearchDao {

	public List<SectionForSearchDto> getSections(TicketSearchPreferenceDto ticketSearchPreferenceDto);

}
