package ca.ulaval.glo4003.services;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import ca.ulaval.glo4003.services.exceptions.UserSearchPreferenceNotSaved;
import ca.ulaval.glo4003.utilities.search.UserSearchPreferenceDoesntExistException;
import ca.ulaval.glo4003.utilities.search.UserSearchPreferenceDao;
import ca.ulaval.glo4003.utilities.search.dto.UserSearchPreferenceDto;

@Service
public class CommandUserSearchPreferenceService {
	
	@Inject
	UserSearchPreferenceDao userSearchPreferenceDao;
	
	public void saveUserSearchPreference(String username, UserSearchPreferenceDto userSearchPreferenceDto) throws UserSearchPreferenceNotSaved {	
		try {
			userSearchPreferenceDao.save(username, userSearchPreferenceDto);
			userSearchPreferenceDao.commit();
		} catch (UserSearchPreferenceDoesntExistException e) {
			throw new UserSearchPreferenceNotSaved();
		}
	}
	
	
}
