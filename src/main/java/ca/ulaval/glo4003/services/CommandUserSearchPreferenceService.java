package ca.ulaval.glo4003.services;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import ca.ulaval.glo4003.services.exceptions.UserSearchPreferenceNotSaved;
import ca.ulaval.glo4003.utilities.search.UserSearchPreferenceDto;
import ca.ulaval.glo4003.utilities.search.UserSearchPreferenceDoesntExistException;
import ca.ulaval.glo4003.utilities.search.UserSearchPreferenceDao;

@Service
public class CommandUserSearchPreferenceService {
	
	@Inject
	UserSearchPreferenceDao userPreferencesDao;
	
	public void saveUserSearchPreference(String username, UserSearchPreferenceDto ticketSearchPreferenceDto) throws UserSearchPreferenceNotSaved {	
		try {
			userPreferencesDao.save(username, ticketSearchPreferenceDto);
			userPreferencesDao.commit();
		} catch (UserSearchPreferenceDoesntExistException e) {
			throw new UserSearchPreferenceNotSaved();
		}
	}
	
	
}
