package ca.ulaval.glo4003.web.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ca.ulaval.glo4003.domain.dtos.SportDto;
import ca.ulaval.glo4003.persistence.daos.SportDao;
import ca.ulaval.glo4003.web.viewmodels.TicketSearchViewModel;

@Controller
@RequestMapping(value = "/recherche", method = RequestMethod.GET)
public class SearchController {
	private static final Logger logger = LoggerFactory.getLogger(SearchController.class);
	
	@Inject
	SportDao sportDao;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView home() {
		logger.info("Search : Home");
		
		ModelAndView mav = new ModelAndView("search/home");
		
		// à remplacer par un qui contient les préférences usager.
		TicketSearchViewModel ticketSearchVM = new TicketSearchViewModel();
		ticketSearchVM.setSelectedSports(new String [] {getSportsList().get(2)});
		List<String> periods = getDisplayedPeriods();
		ticketSearchVM.setDisplayedPeriod(periods.get(periods.size() - 1));
		ticketSearchVM.setLocalGame(true);
		ticketSearchVM.setSelectedTicketTypes(new String [] {getTicketTypes().get(0)});
		
		mav.addObject("ticketSearchForm", ticketSearchVM);
		
		initSearchCriterions(mav);
		
		return mav;
	}
	
	@RequestMapping(value = "/execution", method = RequestMethod.POST)
	public ModelAndView search(@ModelAttribute("SpringWeb") TicketSearchViewModel ticketSearchVM, Model model) {
		logger.info("Search : search tickets");

		ModelAndView mav = new ModelAndView("search/home");
		mav.addObject("ticketSearchForm", ticketSearchVM);

		initSearchCriterions(mav);
		
		return mav;
	}

	private void initSearchCriterions(ModelAndView mav) {
		mav.addObject("sportsList", getSportsList());
		mav.addObject("displayedPeriods", getDisplayedPeriods());
		mav.addObject("ticketTypes", getTicketTypes());
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
		List<String> displayedPeriods = new ArrayList<>();
		displayedPeriods.add("aujourd'hui");
		displayedPeriods.add("une semaine");
		displayedPeriods.add("un mois");
		displayedPeriods.add("trois mois");
		displayedPeriods.add("six mois");
		displayedPeriods.add("tout");
		return displayedPeriods;
	}
	
	private List<String> getTicketTypes() {
		List<String> ticketTypes = new ArrayList<>();
		ticketTypes.add("admission générale");
		ticketTypes.add("avec siège");
		return ticketTypes;
	}
}
