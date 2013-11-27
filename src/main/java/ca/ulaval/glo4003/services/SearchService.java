package ca.ulaval.glo4003.services;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import ca.ulaval.glo4003.constants.DisplayedPeriod;
import ca.ulaval.glo4003.constants.TicketKind;
import ca.ulaval.glo4003.domain.sections.SectionForSearchDao;
import ca.ulaval.glo4003.domain.sections.SectionForSearchDto;
import ca.ulaval.glo4003.domain.tickets.TicketSearchPreferenceDto;
import ca.ulaval.glo4003.presentation.viewmodels.SectionForSearchViewModel;
import ca.ulaval.glo4003.presentation.viewmodels.TicketSearchViewModel;
import ca.ulaval.glo4003.presentation.viewmodels.factories.SectionForSearchViewModelFactory;
import ca.ulaval.glo4003.presentation.viewmodels.factories.TicketSearchPreferenceFactory;
import ca.ulaval.glo4003.utilities.Constants;

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
		mav.addObject("sportList", constants.getSportList());
		mav.addObject("displayedPeriods", DisplayedPeriod.getDisplayedPeriods());
		mav.addObject("ticketKinds", TicketKind.getTicketKinds());
	}

	public List<SectionForSearchViewModel> getSections(TicketSearchViewModel ticketSearchVM) {
		TicketSearchPreferenceDto preferenceDto = ticketSearchPreferenceFactory.createPreferenceDto(ticketSearchVM);
		List<SectionForSearchDto> sectionForSearchDtos = sectionForSearchDao.getSections(preferenceDto);
		return sectionForSearchViewModelFactory.createViewModels(sectionForSearchDtos);
	}
}
