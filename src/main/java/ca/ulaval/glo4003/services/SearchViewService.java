package ca.ulaval.glo4003.services;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import ca.ulaval.glo4003.presentation.viewmodels.SectionForSearchViewModel;
import ca.ulaval.glo4003.presentation.viewmodels.factories.SectionForSearchViewModelFactory;
import ca.ulaval.glo4003.utilities.search.SectionForSearchDao;
import ca.ulaval.glo4003.utilities.search.dto.SectionForSearchDto;
import ca.ulaval.glo4003.utilities.search.dto.UserSearchPreferenceDto;

@Service
public class SearchViewService {

	@Inject
	private SectionForSearchDao sectionForSearchDao;
	
	@Inject
	private SectionForSearchViewModelFactory sectionForSearchViewModelFactory;
	
	public List<SectionForSearchViewModel> getSections(UserSearchPreferenceDto preferenceDto) {
		List<SectionForSearchDto> sectionDtos = sectionForSearchDao.getSections(preferenceDto);
		return sectionForSearchViewModelFactory.createViewModels(sectionDtos);
	}
}
