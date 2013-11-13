package ca.ulaval.glo4003.presentation.controllers;

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
import ca.ulaval.glo4003.domain.services.UserPreferencesService;
import ca.ulaval.glo4003.domain.utilities.Constants;
import ca.ulaval.glo4003.domain.utilities.user.User;
import ca.ulaval.glo4003.presentation.viewmodels.TicketSearchViewModel;

@Controller
@RequestMapping(value = "/recherche", method = RequestMethod.GET)
public class SearchController {
	private static final Logger logger = LoggerFactory.getLogger(SearchController.class);
	
	@Inject
	SearchService searchService;
	
	@Inject
	UserPreferencesService userPreferencesService;
	
	
	@Autowired
	private User currentUser;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView home() {
		logger.info("Recherche : accueil");
		
		ModelAndView mav = new ModelAndView("search/home");
		mav.addObject("currency", Constants.CURRENCY);
		
		Boolean connectedUser = currentUser.isLogged();
		
		TicketSearchViewModel ticketSearchVM = searchService.getInitialisedTicketSearchViewModel();
		
		addConnectedUserToModelAndView(mav, connectedUser);
		addLogOfUserConnection(connectedUser);
		
		if (connectedUser) {
			// mettre les pr��f��rences de l'usager
			// TicketSearchViewModel ticketSearchVM = userPreferenceService.getUserPreference(currentUser)
			logger.info("Preference SAVE :"+ userPreferencesService.getUserPreferencesForUser(currentUser));
		}
		
		mav.addObject("ticketSearchForm", ticketSearchVM);
		
		mav.addObject("sections", searchService.getSections(ticketSearchVM));
		
		searchService.initSearchCriterions(mav);
		
		return mav;
	}
	
	@RequestMapping(value="sauvegarde-preferences", method=RequestMethod.POST)
	public ModelAndView savePreferences(@ModelAttribute("ticketSearchForm") TicketSearchViewModel ticketSearchVM) {
		logger.info("Recherche : enregistre les pr��f��rences de recherche");
		
		// Enregistrement de ticketSearchVM | il faut le transformer en TicketSearchPreferenceDto
		
		ModelAndView mav = home();
		
		mav.addObject("preferencesSaved", true);
		
		return mav;
	}
	
	@RequestMapping(value="list", method=RequestMethod.POST)
    public ModelAndView getList(@ModelAttribute("ticketSearchForm") TicketSearchViewModel ticketSearchVM) {
		logger.info("Recherche : recherche des billets...");

		ModelAndView mav = new ModelAndView("search/list");
		
		mav.addObject("currency", Constants.CURRENCY);
		
		mav.addObject("sections", searchService.getSections(ticketSearchVM));
		
		mav.addObject("preferencesSaved", false);
		
		return mav;
    }
	
	private void addConnectedUserToModelAndView(ModelAndView mav,
			Boolean connectedUser) {
		if (connectedUser) {
			mav.addObject("connectedUser", true);
		} else {
			mav.addObject("connectedUser", false);
		}
	}
	
	private void addLogOfUserConnection(Boolean connectedUser) {
		if (connectedUser) {
			logger.info("usag�� connect��");
		} else {
			logger.info("usag�� non connect��");
		}
	}
}
