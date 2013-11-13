package ca.ulaval.glo4003.persistence.daos;

import org.springframework.stereotype.Repository;

import ca.ulaval.glo4003.domain.dtos.TicketSearchPreferenceDto;
import ca.ulaval.glo4003.domain.utilities.user.User;

@Repository
public interface UserPreferencesDao {

	public TicketSearchPreferenceDto get(String username);

	void add(User username, TicketSearchPreferenceDto userPreferences);
	
}
