package ca.ulaval.glo4003.persistence.daos;

import org.springframework.stereotype.Repository;

import ca.ulaval.glo4003.domain.dtos.TicketSearchPreferenceDto;
import ca.ulaval.glo4003.domain.users.User;

@Repository
public interface UserPreferencesDao {

	
	
	public TicketSearchPreferenceDto get(String username) throws UserDoesntHaveSavedPreferences;
	public void save(User username, TicketSearchPreferenceDto userPreferences);
	public void commit();
}
