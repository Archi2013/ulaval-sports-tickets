package ca.ulaval.glo4003.domain.services;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import ca.ulaval.glo4003.domain.dtos.SportDto;
import ca.ulaval.glo4003.domain.dtos.TicketForSearchDto;
import ca.ulaval.glo4003.domain.utilities.Constants;
import ca.ulaval.glo4003.domain.utilities.Constants.AdmissionType;
import ca.ulaval.glo4003.domain.utilities.Constants.DisplayedPeriod;
import ca.ulaval.glo4003.persistence.daos.SportDao;
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
	
	@Inject
	private TicketSearchPreferenceFactory ticketSearchPreferenceViewModelFactory;

	public TicketSearchViewModel getInitialisedTicketSearchViewModel() {
		return ticketSearchPreferenceViewModelFactory.createInitialViewModel();
	}

	public void initSearchCriterions(ModelAndView mav) {
		mav.addObject("sportsList", constants.getSportsList());
		mav.addObject("displayedPeriods", constants.getDisplayedPeriods());
		mav.addObject("ticketTypes", constants.getTicketTypes());
	}
	
	public List<TicketForSearchViewModel> getTickets(TicketSearchViewModel ticketSearchVM) {
		List<TicketForSearchDto> ticketForSearchDtos = ticketForSearchDao.getTickets(
				ticketSearchPreferenceFactory.createPreferenceDto(ticketSearchVM));
		return ticketForSearchViewModelFactory.createViewModels(ticketForSearchDtos);
	}
}
