package ca.ulaval.glo4003.domain.search;

import java.util.List;

public interface SectionForSearchDao {

	public List<SectionForSearchDto> getSections(TicketSearchPreferenceDto ticketSearchPreferenceDto);

}