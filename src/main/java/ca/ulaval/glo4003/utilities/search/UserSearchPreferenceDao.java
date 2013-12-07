package ca.ulaval.glo4003.utilities.search;

import org.springframework.stereotype.Repository;

import ca.ulaval.glo4003.exceptions.UserDoesntHaveSavedSearchPreference;

@Repository
public interface UserSearchPreferenceDao {

	
	
	public UserSearchPreferenceDto get(String username) throws UserDoesntHaveSavedSearchPreference;
	public void save(String username, UserSearchPreferenceDto userPreferences) throws UserSearchPreferenceDoesntExistException;
	public void commit();
}
