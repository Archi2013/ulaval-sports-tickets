package ca.ulaval.glo4003.domain.services;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import ca.ulaval.glo4003.domain.dtos.TicketForSearchDto;
import ca.ulaval.glo4003.domain.dtos.TicketSearchPreferenceDto;
import ca.ulaval.glo4003.domain.utilities.Constants;
import ca.ulaval.glo4003.persistence.daos.TicketForSearchDao;
import ca.ulaval.glo4003.web.viewmodels.TicketForSearchViewModel;
import ca.ulaval.glo4003.web.viewmodels.TicketSearchViewModel;
import ca.ulaval.glo4003.web.viewmodels.factories.TicketForSearchViewModelFactory;
import ca.ulaval.glo4003.web.viewmodels.factories.TicketSearchPreferenceFactory;

@Service
public class SearchService {
	
	@Inject
	private Constants constants;
	
	@Inject
	private TicketForSearchDao ticketForSearchDao;

	@Inject
	private TicketForSearchViewModelFactory ticketForSearchViewModelFactory;
	
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
	
	public List<TicketForSearchViewModel> getTickets(TicketSearchViewModel ticketSearchVM) {
		TicketSearchPreferenceDto preferenceDto = ticketSearchPreferenceFactory.createPreferenceDto(ticketSearchVM);
		List<TicketForSearchDto> ticketForSearchDtos = ticketForSearchDao.getTickets(preferenceDto);
		return ticketForSearchViewModelFactory.createViewModels(ticketForSearchDtos);
	}
}
