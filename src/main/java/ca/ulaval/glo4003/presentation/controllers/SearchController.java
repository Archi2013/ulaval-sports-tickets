package ca.ulaval.glo4003.presentation.controllers;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import ca.ulaval.glo4003.domain.users.User;
import ca.ulaval.glo4003.exceptions.UserDoesntHaveSavedPreferences;
import ca.ulaval.glo4003.presentation.viewmodels.TicketSearchViewModel;
import ca.ulaval.glo4003.services.SearchService;
import ca.ulaval.glo4003.services.UserPreferencesService;
import ca.ulaval.glo4003.utilities.Constants;

@Controller
@SessionAttributes({ "currentUser" })
@RequestMapping(value = "/recherche", method = RequestMethod.GET)
public class SearchController {
	
	@Inject
	SearchService searchService;
	
	@Inject
	UserPreferencesService userPreferencesService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView home(@ModelAttribute("currentUser") User currentUser) {
		ModelAndView mav = new ModelAndView("search/home");
		
		mav.addObject("currency", Constants.CURRENCY);
		
		TicketSearchViewModel ticketSearchVM = searchService.getInitialisedTicketSearchViewModel();
		
		if (currentUser.isLogged()) {
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
	public ModelAndView savePreferences(@ModelAttribute("currentUser") User currentUser,
			@ModelAttribute("ticketSearchForm") TicketSearchViewModel ticketSearchVM) {
		userPreferencesService.saveUserPreference(currentUser,ticketSearchVM);
		ModelAndView mav = home(currentUser);
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
}
