package ca.ulaval.glo4003.domain.users;

import org.springframework.stereotype.Repository;

import ca.ulaval.glo4003.exceptions.UserDoesntHaveSavedPreferences;
import ca.ulaval.glo4003.utilities.search.TicketSearchPreferenceDto;

@Repository
public interface UserPreferencesDao {

	
	
	public TicketSearchPreferenceDto get(String username) throws UserDoesntHaveSavedPreferences;
	public void save(User username, TicketSearchPreferenceDto userPreferences) throws UserPreferencesDoesntExistEcception;
	public void commit();
}
