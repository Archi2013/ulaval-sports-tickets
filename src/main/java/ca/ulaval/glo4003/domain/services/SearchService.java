package ca.ulaval.glo4003.domain.services;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import ca.ulaval.glo4003.domain.dtos.SportDto;
import ca.ulaval.glo4003.domain.utilities.Constants.AdmissionType;
import ca.ulaval.glo4003.domain.utilities.Constants.DisplayedPeriod;
import ca.ulaval.glo4003.persistence.daos.SportDao;
import ca.ulaval.glo4003.web.viewmodels.TicketForSearchViewModel;
import ca.ulaval.glo4003.web.viewmodels.TicketSearchViewModel;

@Service
public class SearchService {
	
	@Inject
	private SportDao sportDao;

	public TicketSearchViewModel getInitialisedTicketSearchViewModel() {
		TicketSearchViewModel ticketSearchVM = new TicketSearchViewModel();
		ticketSearchVM.setSelectedSports(new String [] {getSportsList().get(2)});
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
		TicketForSearchViewModel t1 = new TicketForSearchViewModel("Baseball Masculin", "Radiants", "15/11/2013 à 16h45",
				"Générale", "Générale", new Integer(3), "16,95");
		TicketForSearchViewModel t2 = new TicketForSearchViewModel("Soccer Masculin", "Endormis", "16/11/2013 à 20h45",
				"Générale", "Générale", new Integer(22), "23,95");
		TicketForSearchViewModel t3 = new TicketForSearchViewModel("Volleyball Féminin", "Kira", "17/11/2013 à 10h30",
				"VIP", "Orange", new Integer(4), "16,95");
		TicketForSearchViewModel t4 = new TicketForSearchViewModel("Volleyball Féminin", "Kira", "17/11/2013 à 10h30",
				"VIP", "Rouge", new Integer(4), "17,95");
		List<TicketForSearchViewModel> tickets = new ArrayList<>();
		tickets.add(t1);
		tickets.add(t2);
		tickets.add(t3);
		tickets.add(t4);
		return tickets;
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
