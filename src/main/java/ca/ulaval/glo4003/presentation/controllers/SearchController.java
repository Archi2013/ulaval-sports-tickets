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
import ca.ulaval.glo4003.exceptions.UserDoesntHaveSavedSearchPreference;
import ca.ulaval.glo4003.presentation.controllers.errorhandler.SearchErrorHandler;
import ca.ulaval.glo4003.presentation.viewmodels.SectionForSearchViewModel;
import ca.ulaval.glo4003.presentation.viewmodels.SearchViewModel;
import ca.ulaval.glo4003.presentation.viewmodels.factories.UserSearchPreferenceFactory;
import ca.ulaval.glo4003.services.CommandUserSearchPreferenceService;
import ca.ulaval.glo4003.services.SearchViewService;
import ca.ulaval.glo4003.services.UserSearchPreferenceViewService;
import ca.ulaval.glo4003.services.exceptions.UserSearchPreferenceNotSaved;
import ca.ulaval.glo4003.utilities.Constants;
import ca.ulaval.glo4003.utilities.search.dto.UserSearchPreferenceDto;

@Controller
@SessionAttributes({ "currentUser" })
@RequestMapping(value = "/recherche", method = RequestMethod.GET)
public class SearchController {
	
	private static final String SEARCH_HOME_PAGE = "search/home";

	@Inject
	private Constants constants;
	
	@Inject
	private SearchViewService searchService;
	
	@Inject
	private UserSearchPreferenceViewService userSearchPreferenceViewService;
	
	@Inject
	private CommandUserSearchPreferenceService commandUserSearchPreferenceService;

	@Inject
	private UserSearchPreferenceFactory userSearchPreferenceFactory;
	
	@Inject
	private SearchErrorHandler searchErrorHandler;
	
	@Autowired
	User currentUser;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView home() {
		ModelAndView mav = new ModelAndView(SEARCH_HOME_PAGE);
		
		mav.addObject("currency", Constants.CURRENCY);
		
		SearchViewModel searchVM = userSearchPreferenceFactory.createInitialViewModel();
		
		if (currentUser.isLogged()) {
			try {
				searchVM = userSearchPreferenceViewService.getSearchViewModelForUser(currentUser.getUsername());
			} catch (UserDoesntHaveSavedSearchPreference e){
				searchVM = userSearchPreferenceFactory.createInitialViewModel();
			}
		}
		
		mav.addObject("searchForm", searchVM);
		
		mav.addObject("sections", getSectionViewModels(searchVM));
		
		initSearchCriterions(mav);
		
		return mav;
	}
	
	@RequestMapping(value="sauvegarde-preference", method=RequestMethod.POST)
	public ModelAndView savePreference(@ModelAttribute("searchForm") SearchViewModel searchVM) {
		ModelAndView mav;
		try {
			UserSearchPreferenceDto userSearchPreferenceDto = userSearchPreferenceFactory.createPreferenceDto(searchVM);
			commandUserSearchPreferenceService.saveUserSearchPreference(currentUser.getUsername(), userSearchPreferenceDto);
			mav = home();
			mav.addObject("preferenceSaved", true);
		} catch (UserSearchPreferenceNotSaved e) {
			mav = home();
			mav.addObject("preferenceSaved", false);
			searchErrorHandler.addErrorMessageUserPreferencesNotSaved(mav);
		}
		return mav;
	}
	
	@RequestMapping(value="list", method=RequestMethod.POST)
    public ModelAndView getList(@ModelAttribute("searchForm") SearchViewModel searchVM) {
		ModelAndView mav = new ModelAndView("search/list");
		
		mav.addObject("currency", Constants.CURRENCY);
		
		mav.addObject("sections", getSectionViewModels(searchVM));
		
		mav.addObject("preferenceSaved", false);
		
		return mav;
    }
	
	private void initSearchCriterions(ModelAndView mav) {
		mav.addObject("sportList", constants.getSportList());
		mav.addObject("displayedPeriods", DisplayedPeriod.getDisplayedPeriods());
		mav.addObject("ticketKinds", TicketKind.getTicketKinds());
	}
	
	private List<SectionForSearchViewModel> getSectionViewModels(SearchViewModel searchVM) {
		UserSearchPreferenceDto userSearchPreferenceDto = userSearchPreferenceFactory.createPreferenceDto(searchVM);
		return searchService.getSections(userSearchPreferenceDto);
	}
}
