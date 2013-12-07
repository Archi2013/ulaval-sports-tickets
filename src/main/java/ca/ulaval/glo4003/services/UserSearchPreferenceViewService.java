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
	UserSearchPreferenceFactory ticketSearchPreferenceFactory;
	
	@Inject
	UserSearchPreferenceDao userPreferencesDao;
	
	public SearchViewModel getSearchViewModelForUser(String username) throws UserDoesntHaveSavedSearchPreference {
		UserSearchPreferenceDto ticketSPDto = userPreferencesDao.get(username);	
		SearchViewModel ticketSearchVModel = ticketSearchPreferenceFactory.createViewModel(ticketSPDto);
		return ticketSearchVModel;			
	}
}
