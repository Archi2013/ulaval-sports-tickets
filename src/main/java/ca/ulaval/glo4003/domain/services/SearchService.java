package ca.ulaval.glo4003.domain.services;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import ca.ulaval.glo4003.domain.dtos.SectionForSearchDto;
import ca.ulaval.glo4003.domain.dtos.TicketSearchPreferenceDto;
import ca.ulaval.glo4003.domain.utilities.Constants;
import ca.ulaval.glo4003.persistence.daos.SectionForSearchDao;
import ca.ulaval.glo4003.web.viewmodels.SectionForSearchViewModel;
import ca.ulaval.glo4003.web.viewmodels.TicketSearchViewModel;
import ca.ulaval.glo4003.web.viewmodels.factories.SectionForSearchViewModelFactory;
import ca.ulaval.glo4003.web.viewmodels.factories.TicketSearchPreferenceFactory;

@Service
public class SearchService {
	
	@Inject
	private Constants constants;
	
	@Inject
	private SectionForSearchDao sectionForSearchDao;

	@Inject
	private SectionForSearchViewModelFactory sectionForSearchViewModelFactory;
	
	@Inject
	private TicketSearchPreferenceFactory ticketSearchPreferenceFactory;

	public TicketSearchViewModel getInitialisedTicketSearchViewModel() {
		return ticketSearchPreferenceFactory.createInitialViewModel();
	}

	public void initSearchCriterions(ModelAndView mav) {
		mav.addObject("sportList", constants.getSportsList());
		mav.addObject("displayedPeriods", constants.getDisplayedPeriods());
		mav.addObject("ticketKinds", constants.getTicketKinds());
	}
	
	public List<SectionForSearchViewModel> getSections(TicketSearchViewModel ticketSearchVM) {
		TicketSearchPreferenceDto preferenceDto = ticketSearchPreferenceFactory.createPreferenceDto(ticketSearchVM);
		List<SectionForSearchDto> sectionForSearchDtos = sectionForSearchDao.getSections(preferenceDto);
		return sectionForSearchViewModelFactory.createViewModels(sectionForSearchDtos);
	}
}
