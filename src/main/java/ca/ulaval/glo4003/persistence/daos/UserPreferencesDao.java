package ca.ulaval.glo4003.persistence.daos;

import org.springframework.stereotype.Repository;

import ca.ulaval.glo4003.domain.dtos.TicketSearchPreferenceDto;
import ca.ulaval.glo4003.domain.dtos.UserPreferencesDto;
import ca.ulaval.glo4003.domain.utilities.user.User;

@Repository
public interface UserPreferencesDao {

	
	
	public UserPreferencesDto get(String username);

	void save(User username, TicketSearchPreferenceDto userPreferences);
	
}
