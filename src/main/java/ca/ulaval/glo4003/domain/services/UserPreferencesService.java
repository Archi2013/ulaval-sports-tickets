package ca.ulaval.glo4003.domain.services;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.ulaval.glo4003.domain.dtos.TicketSearchPreferenceDto;
import ca.ulaval.glo4003.domain.dtos.UserPreferencesDto;
import ca.ulaval.glo4003.domain.utilities.user.User;
import ca.ulaval.glo4003.domain.utilities.user.UserPreferences;
import ca.ulaval.glo4003.persistence.daos.GameDao;
import ca.ulaval.glo4003.persistence.daos.UserDao;
import ca.ulaval.glo4003.persistence.daos.UserPreferencesDao;
import ca.ulaval.glo4003.persistence.daos.fakes.FakeDataUserPreferencesDao;
import ca.ulaval.glo4003.presentation.viewmodels.TicketSearchViewModel;
import ca.ulaval.glo4003.presentation.viewmodels.factories.TicketSearchPreferenceFactory;

@Service
public class UserPreferencesService {

	@Inject
	FakeDataUserPreferencesDao userPreferencesDao;
	
	@Inject
	TicketSearchPreferenceFactory ticketSearchFactory;
	
	
	public TicketSearchViewModel getUserPreferencesForUser(User currentUser){
		
		UserPreferencesDto userPref= userPreferencesDao.get(currentUser.getUsername());	
		TicketSearchViewModel ticketSearchVModel=ticketSearchFactory.createViewModelFromUserPreferencesDto(userPref);
				
		// get UserPreference with currentUser info
		// transform userPreferenceDto, return viewModel
		return ticketSearchVModel;
	}
	
	public void saveUserPreference(User currentUser, TicketSearchViewModel userPreferences){
		
		
		TicketSearchPreferenceDto ticketSearchDto = ticketSearchFactory.createPreferenceDto(userPreferences);
		
		userPreferencesDao.save(currentUser,ticketSearchDto);
	}
	
	
}
