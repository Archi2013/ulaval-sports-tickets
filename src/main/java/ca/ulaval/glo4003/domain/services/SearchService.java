package ca.ulaval.glo4003.domain.services;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import ca.ulaval.glo4003.domain.dtos.SportDto;
import ca.ulaval.glo4003.domain.utilities.DisplayedPeriodMapper;
import ca.ulaval.glo4003.persistence.daos.SportDao;
import ca.ulaval.glo4003.web.viewmodels.TicketForSearchViewModel;
import ca.ulaval.glo4003.web.viewmodels.TicketSearchViewModel;

@Service
public class SearchService {
	
	@Inject
	private SportDao sportDao;
	
	@Inject
	private DisplayedPeriodMapper displayedPeriodMapper;

	public TicketSearchViewModel getInitialisedTicketSearchViewModel() {
		TicketSearchViewModel ticketSearchVM = new TicketSearchViewModel();
		ticketSearchVM.setSelectedSports(new String [] {getSportsList().get(2)});
		List<String> periods = getDisplayedPeriods();
		ticketSearchVM.setDisplayedPeriod(periods.get(periods.size() - 1));
		ticketSearchVM.setLocalGame(true);
		ticketSearchVM.setSelectedTicketTypes(new String [] {getTicketTypes().get(0)});
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
	
	private List<String> getDisplayedPeriods() {
		return displayedPeriodMapper.getAllNames();
	}
	
	private List<String> getTicketTypes() {
		List<String> ticketTypes = new ArrayList<>();
		ticketTypes.add("admission générale");
		ticketTypes.add("avec siège");
		return ticketTypes;
	}
}
