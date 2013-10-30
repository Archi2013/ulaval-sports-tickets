package ca.ulaval.glo4003.web.controllers;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ca.ulaval.glo4003.domain.services.SearchService;
import ca.ulaval.glo4003.persistence.daos.SportDao;
import ca.ulaval.glo4003.web.viewmodels.TicketSearchViewModel;

@Controller
@RequestMapping(value = "/recherche", method = RequestMethod.GET)
public class SearchController {
	private static final Logger logger = LoggerFactory.getLogger(SearchController.class);
	
	@Inject
	SportDao sportDao;
	
	@Inject
	SearchService searchService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView home() {
		logger.info("Search : Home");
		
		ModelAndView mav = new ModelAndView("search/home");
		
		mav.addObject("ticketSearchForm", searchService.getInitialisedTicketSearchViewModel());
		
		searchService.initSearchCriterions(mav);
		
		return mav;
	}
	
	@RequestMapping(value="list", method=RequestMethod.POST)
    public ModelAndView getTemp(@ModelAttribute("ticketSearchForm") TicketSearchViewModel ticketSearchVM, Model model) {
		logger.info("Search : search tickets");

		ModelAndView mav = new ModelAndView("search/list");
		
		mav.addObject("tickets", searchService.getTickets(ticketSearchVM));
		
		return mav;
    }
}
