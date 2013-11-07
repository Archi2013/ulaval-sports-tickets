package ca.ulaval.glo4003.web.controllers;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ca.ulaval.glo4003.domain.services.SearchService;
import ca.ulaval.glo4003.domain.utilities.Constants;
import ca.ulaval.glo4003.domain.utilities.User;
import ca.ulaval.glo4003.web.viewmodels.TicketSearchViewModel;

@Controller
@RequestMapping(value = "/recherche", method = RequestMethod.GET)
public class SearchController {
	private static final Logger logger = LoggerFactory.getLogger(SearchController.class);
	
	@Inject
	SearchService searchService;
	
	@Autowired
	private User currentUser;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView home() {
		ModelAndView mav = new ModelAndView("search/home");
		mav.addObject("currency", Constants.CURRENCY);
		
		Boolean connectedUser = currentUser.isLogged();
		
		TicketSearchViewModel ticketSearchVM = searchService.getInitialisedTicketSearchViewModel();
		
		if (connectedUser) {
			mav.addObject("connectedUser", true);
			
			// mettre les préférences de l'usager
			// TicketSearchViewModel ticketSearchVM = 
			
			logger.info("usagé connecté");
		} else {
			mav.addObject("connectedUser", false);
			logger.info("usagé non connecté");
		}
		
		mav.addObject("ticketSearchForm", ticketSearchVM);
		
		mav.addObject("sections", searchService.getSections(ticketSearchVM));
		
		mav.addObject("searchForm", ticketSearchVM); // Pour les tests
		
		searchService.initSearchCriterions(mav);
		
		return mav;
	}
	
	@RequestMapping(value="sauvegarde-preferences", method=RequestMethod.POST)
	public ModelAndView savePreferences(@ModelAttribute("ticketSearchForm") TicketSearchViewModel ticketSearchVM) {
		logger.info("Search : save user search preferences");
		
		// Enregistrement de ticketSearchVM | il faut le transformer en TicketSearchPreferenceDto
		
		ModelAndView mav = home();
		
		mav.addObject("preferencesSaved", true);
		
		return mav;
	}
	
	@RequestMapping(value="list", method=RequestMethod.POST)
    public ModelAndView getList(@ModelAttribute("ticketSearchForm") TicketSearchViewModel ticketSearchVM) {
		logger.info("Search : search tickets");

		ModelAndView mav = new ModelAndView("search/list");
		
		mav.addObject("sections", searchService.getSections(ticketSearchVM));
		
		mav.addObject("searchForm", ticketSearchVM); // Pour les tests
		
		mav.addObject("preferencesSaved", false);
		
		return mav;
    }
}
