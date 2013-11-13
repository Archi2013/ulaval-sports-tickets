package ca.ulaval.glo4003.domain.services;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.ulaval.glo4003.domain.utilities.user.User;
import ca.ulaval.glo4003.domain.utilities.user.UserPreferences;
import ca.ulaval.glo4003.persistence.daos.GameDao;
import ca.ulaval.glo4003.persistence.daos.UserDao;
import ca.ulaval.glo4003.persistence.daos.UserPreferencesDao;

@Service
public class UserPreferencesService {

	@Inject
	UserPreferencesDao userPreferencesDao;
	
	public String getUserPreferencesForUser(User currentUser){
		
		userPreferencesDao.get(currentUser.getUsername());
		
		// get UserPreference with currentUser info
		// transform userPreferenceDto, return viewModel
		return currentUser.getUsername();
	}
	
	public void saveUserPreference(){
		
		//userPreferencesDao.save();
	}
	
	
}
