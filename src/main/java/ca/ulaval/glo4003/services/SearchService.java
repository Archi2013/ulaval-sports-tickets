package ca.ulaval.glo4003.services;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import ca.ulaval.glo4003.domain.search.SectionForSearchDao;
import ca.ulaval.glo4003.domain.search.SectionForSearchDto;
import ca.ulaval.glo4003.domain.search.TicketSearchPreferenceDto;

@Service
public class SearchService {

	@Inject
	private SectionForSearchDao sectionForSearchDao;
	
	public List<SectionForSearchDto> getSections(TicketSearchPreferenceDto preferenceDto) {
		return sectionForSearchDao.getSections(preferenceDto);
	}
}
