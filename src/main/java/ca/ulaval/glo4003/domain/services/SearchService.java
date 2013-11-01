package ca.ulaval.glo4003.domain.services;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import ca.ulaval.glo4003.domain.dtos.SportDto;
import ca.ulaval.glo4003.domain.dtos.TicketForSearchDto;
import ca.ulaval.glo4003.domain.utilities.Constants.AdmissionType;
import ca.ulaval.glo4003.domain.utilities.Constants.DisplayedPeriod;
import ca.ulaval.glo4003.persistence.daos.SportDao;
import ca.ulaval.glo4003.persistence.daos.TicketForSearchDao;
import ca.ulaval.glo4003.web.viewmodels.TicketForSearchViewModel;
import ca.ulaval.glo4003.web.viewmodels.TicketSearchViewModel;
import ca.ulaval.glo4003.web.viewmodels.factories.TicketForSearchViewModelFactory;

@Service
public class SearchService {
	
	@Inject
	private SportDao sportDao;
	
	@Inject
	private TicketForSearchDao ticketForSearchDao;

	@Inject
	private TicketForSearchViewModelFactory ticketForSearchViewModelFactory;

	public TicketSearchViewModel getInitialisedTicketSearchViewModel() {
		TicketSearchViewModel ticketSearchVM = new TicketSearchViewModel();
		List<String> selectedSports = new ArrayList<>();
		selectedSports.add(getSportsList().get(2));
		ticketSearchVM.setSelectedSports(selectedSports);
		List<DisplayedPeriod> displayedPeriods = getDisplayedPeriods();
		ticketSearchVM.setDisplayedPeriod(displayedPeriods.get(displayedPeriods.size() - 1));
		ticketSearchVM.setLocalGame(true);
		List<AdmissionType> admissionTypes = new ArrayList<>();
		admissionTypes.add(getTicketTypes().get(0));
		ticketSearchVM.setSelectedTicketTypes(admissionTypes);
		return ticketSearchVM;
	}

	public void initSearchCriterions(ModelAndView mav) {
		mav.addObject("sportsList", getSportsList());
		mav.addObject("displayedPeriods", getDisplayedPeriods());
		mav.addObject("ticketTypes", getTicketTypes());
	}
	
	public List<TicketForSearchViewModel> getTickets(TicketSearchViewModel ticketSearchVM) {
		List<TicketForSearchDto> ticketForSearchDtos = ticketForSearchDao.getTickets(ticketSearchVM);
		return ticketForSearchViewModelFactory.createViewModels(ticketForSearchDtos);
	}
	
	private List<String> getSportsList() {
		List<SportDto> sportsDto = sportDao.getAll();
		
		List<String> sportsList = new ArrayList<>();
		
		for (SportDto sport : sportsDto) {
			sportsList.add(sport.getName());
		}
		return sportsList;
	}
	
	private List<DisplayedPeriod> getDisplayedPeriods() {
		List<DisplayedPeriod> list = new ArrayList<>();
		for (DisplayedPeriod period : DisplayedPeriod.values()) {
			list.add(period);
		}
		return list;
	}
	
	private List<AdmissionType> getTicketTypes() {
		List<AdmissionType> ticketTypes = new ArrayList<>();
		ticketTypes.add(AdmissionType.GENERAL_ADMISSION);
		ticketTypes.add(AdmissionType.WITH_SEAT);
		return ticketTypes;
	}
}
