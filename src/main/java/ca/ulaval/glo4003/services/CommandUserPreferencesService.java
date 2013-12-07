package ca.ulaval.glo4003.services;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import ca.ulaval.glo4003.domain.users.UserPreferencesDao;
import ca.ulaval.glo4003.domain.users.UserPreferencesDoesntExistException;
import ca.ulaval.glo4003.services.exceptions.UserPreferencesNotSaved;
import ca.ulaval.glo4003.utilities.search.TicketSearchPreferenceDto;

@Service
public class CommandUserPreferencesService {
	
	@Inject
	UserPreferencesDao userPreferencesDao;
	
	public void saveUserPreference(String username, TicketSearchPreferenceDto ticketSearchPreferenceDto) throws UserPreferencesNotSaved {	
		try {
			userPreferencesDao.save(username, ticketSearchPreferenceDto);
			userPreferencesDao.commit();
		} catch (UserPreferencesDoesntExistException e) {
			throw new UserPreferencesNotSaved();
		}
	}
	
	
}
