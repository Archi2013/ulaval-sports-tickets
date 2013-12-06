package ca.ulaval.glo4003.services;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import ca.ulaval.glo4003.presentation.viewmodels.SectionForSearchViewModel;
import ca.ulaval.glo4003.presentation.viewmodels.factories.SectionForSearchViewModelFactory;
import ca.ulaval.glo4003.utilities.search.SectionForSearchDao;
import ca.ulaval.glo4003.utilities.search.SectionForSearchDto;
import ca.ulaval.glo4003.utilities.search.TicketSearchPreferenceDto;

@Service
public class SearchViewService {

	@Inject
	private SectionForSearchDao sectionForSearchDao;
	
	@Inject
	private SectionForSearchViewModelFactory sectionForSearchViewModelFactory;
	
	public List<SectionForSearchViewModel> getSections(TicketSearchPreferenceDto preferenceDto) {
		List<SectionForSearchDto> sectionDtos = sectionForSearchDao.getSections(preferenceDto);
		return sectionForSearchViewModelFactory.createViewModels(sectionDtos);
	}
}
