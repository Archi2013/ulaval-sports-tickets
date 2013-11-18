package ca.ulaval.glo4003.presentation.controllers;

import javax.inject.Inject;

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
import ca.ulaval.glo4003.persistence.daos.fakes.UserDoesntHaveSavedPreferences;
import ca.ulaval.glo4003.presentation.viewmodels.TicketSearchViewModel;

@Controller
@RequestMapping(value = "/recherche", method = RequestMethod.GET)
public class SearchController {
	
	@Inject
	SearchService searchService;
	
	@Inject
	UserPreferencesService userPreferencesService;
	
	
	@Autowired
	private User currentUser;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView home() {
		ModelAndView mav = new ModelAndView("search/home");
		mav.addObject("currency", Constants.CURRENCY);
		
		Boolean connectedUser = currentUser.isLogged();
		
		TicketSearchViewModel ticketSearchVM = searchService.getInitialisedTicketSearchViewModel();
		
		addConnectedUserToModelAndView(mav, connectedUser);
		
		if (connectedUser) {
			try{
			ticketSearchVM = userPreferencesService.getUserPreferencesForUser(currentUser);
			}catch (UserDoesntHaveSavedPreferences e){
			}
		}
		
		mav.addObject("ticketSearchForm", ticketSearchVM);
		
		mav.addObject("sections", searchService.getSections(ticketSearchVM));
		
		searchService.initSearchCriterions(mav);
		
		return mav;
	}
	
	@RequestMapping(value="sauvegarde-preferences", method=RequestMethod.POST)
	public ModelAndView savePreferences(@ModelAttribute("ticketSearchForm") TicketSearchViewModel ticketSearchVM) {
		userPreferencesService.saveUserPreference(currentUser,ticketSearchVM);
		ModelAndView mav = home();
		mav.addObject("preferencesSaved", true);
		
		return mav;
	}
	
	@RequestMapping(value="list", method=RequestMethod.POST)
    public ModelAndView getList(@ModelAttribute("ticketSearchForm") TicketSearchViewModel ticketSearchVM) {
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
}
