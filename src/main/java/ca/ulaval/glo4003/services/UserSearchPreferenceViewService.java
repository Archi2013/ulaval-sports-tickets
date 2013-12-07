package ca.ulaval.glo4003.services;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import ca.ulaval.glo4003.exceptions.UserDoesntHaveSavedSearchPreference;
import ca.ulaval.glo4003.presentation.viewmodels.SearchViewModel;
import ca.ulaval.glo4003.presentation.viewmodels.factories.UserSearchPreferenceFactory;
import ca.ulaval.glo4003.utilities.search.UserSearchPreferenceDto;
import ca.ulaval.glo4003.utilities.search.UserSearchPreferenceDao;

@Service
public class UserSearchPreferenceViewService {

	@Inject
	UserSearchPreferenceFactory userSearchPreferenceFactory;
	
	@Inject
	UserSearchPreferenceDao userSearchPreferenceDao;
	
	public SearchViewModel getSearchViewModelForUser(String username) throws UserDoesntHaveSavedSearchPreference {
		UserSearchPreferenceDto userSearchPreferenceDto = userSearchPreferenceDao.get(username);	
		SearchViewModel searchVM = userSearchPreferenceFactory.createViewModel(userSearchPreferenceDto);
		return searchVM;			
	}
}
