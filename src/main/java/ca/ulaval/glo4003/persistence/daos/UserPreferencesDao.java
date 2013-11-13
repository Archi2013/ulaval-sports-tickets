package ca.ulaval.glo4003.persistence.daos;

import org.springframework.stereotype.Repository;

import ca.ulaval.glo4003.domain.dtos.TicketSearchPreferenceDto;
import ca.ulaval.glo4003.domain.dtos.UserPreferencesDto;
import ca.ulaval.glo4003.domain.utilities.user.User;
import ca.ulaval.glo4003.persistence.daos.fakes.UserDoesntHaveSavedPreferences;

@Repository
public interface UserPreferencesDao {

	
	
	public UserPreferencesDto get(String username) throws UserDoesntHaveSavedPreferences;

	void save(User username, TicketSearchPreferenceDto userPreferences);
	
	public void commit();
}
