package ca.ulaval.glo4003.utilities.search;

import java.util.List;

import ca.ulaval.glo4003.utilities.search.dto.SectionForSearchDto;
import ca.ulaval.glo4003.utilities.search.dto.UserSearchPreferenceDto;

public interface SectionForSearchDao {

	public List<SectionForSearchDto> getSections(UserSearchPreferenceDto ticketSearchPreferenceDto);

}
