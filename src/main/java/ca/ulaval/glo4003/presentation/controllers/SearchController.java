package ca.ulaval.glo4003.presentation.controllers;

import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import ca.ulaval.glo4003.constants.DisplayedPeriod;
import ca.ulaval.glo4003.constants.TicketKind;
import ca.ulaval.glo4003.domain.users.User;
import ca.ulaval.glo4003.exceptions.UserDoesntHaveSavedPreferences;
import ca.ulaval.glo4003.presentation.controllers.errormanagers.SearchErrorManager;
import ca.ulaval.glo4003.presentation.viewmodels.SectionForSearchViewModel;
import ca.ulaval.glo4003.presentation.viewmodels.TicketSearchViewModel;
import ca.ulaval.glo4003.presentation.viewmodels.factories.SectionForSearchViewModelFactory;
import ca.ulaval.glo4003.presentation.viewmodels.factories.TicketSearchPreferenceFactory;
import ca.ulaval.glo4003.services.SearchService;
import ca.ulaval.glo4003.services.UserPreferencesService;
import ca.ulaval.glo4003.services.exceptions.UserPreferencesNotSaved;
import ca.ulaval.glo4003.utilities.Constants;
import ca.ulaval.glo4003.utilities.search.TicketSearchPreferenceDto;

@Controller
@SessionAttributes({ "currentUser" })
@RequestMapping(value = "/recherche", method = RequestMethod.GET)
public class SearchController {
	
	private static final String SEARCH_HOME_PAGE = "search/home";

	@Inject
	private Constants constants;
	
	@Inject
	private SearchService searchService;
	
	@Inject
	private UserPreferencesService userPreferencesService;

	@Inject
	private TicketSearchPreferenceFactory ticketSearchPreferenceFactory;
	
	@Inject
	private SectionForSearchViewModelFactory sectionForSearchViewModelFactory;
	
	@Inject
	private SearchErrorManager searchErrorManager;
	
	@Autowired
	User currentUser;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView home() {
		ModelAndView mav = new ModelAndView(SEARCH_HOME_PAGE);
		
		mav.addObject("currency", Constants.CURRENCY);
		
		TicketSearchViewModel ticketSearchVM = ticketSearchPreferenceFactory.createInitialViewModel();
		
		if (currentUser.isLogged()) {
			try {
				ticketSearchVM = userPreferencesService.getUserPreferencesForUser(currentUser);
			} catch (UserDoesntHaveSavedPreferences e){
				ticketSearchVM = ticketSearchPreferenceFactory.createInitialViewModel();
			}
		}
		
		mav.addObject("ticketSearchForm", ticketSearchVM);
		
		mav.addObject("sections", getSectionViewModels(ticketSearchVM));
		
		initSearchCriterions(mav);
		
		return mav;
	}
	
	@RequestMapping(value="sauvegarde-preferences", method=RequestMethod.POST)
	public ModelAndView savePreferences(@ModelAttribute("ticketSearchForm") TicketSearchViewModel ticketSearchVM) {
		ModelAndView mav = home();
		try {
			userPreferencesService.saveUserPreference(currentUser, ticketSearchVM);
			mav.addObject("preferencesSaved", true);
		} catch (UserPreferencesNotSaved e) {
			mav.addObject("preferencesSaved", false);
			searchErrorManager.addErrorMessageUserPreferencesNotSaved(mav);
		}
		return mav;
	}
	
	@RequestMapping(value="list", method=RequestMethod.POST)
    public ModelAndView getList(@ModelAttribute("ticketSearchForm") TicketSearchViewModel ticketSearchVM) {
		ModelAndView mav = new ModelAndView("search/list");
		
		mav.addObject("currency", Constants.CURRENCY);
		
		mav.addObject("sections", getSectionViewModels(ticketSearchVM));
		
		mav.addObject("preferencesSaved", false);
		
		return mav;
    }
	
	private void initSearchCriterions(ModelAndView mav) {
		mav.addObject("sportList", constants.getSportList());
		mav.addObject("displayedPeriods", DisplayedPeriod.getDisplayedPeriods());
		mav.addObject("ticketKinds", TicketKind.getTicketKinds());
	}
	
	private List<SectionForSearchViewModel> getSectionViewModels(TicketSearchViewModel ticketSearchVM) {
		TicketSearchPreferenceDto preferenceDto = ticketSearchPreferenceFactory.createPreferenceDto(
				ticketSearchVM.getSelectedSports(), ticketSearchVM.getDisplayedPeriod(),
				ticketSearchVM.isLocalGameOnly(), ticketSearchVM.getSelectedTicketKinds());
		return sectionForSearchViewModelFactory.createViewModels(searchService.getSections(preferenceDto));
	}
}
