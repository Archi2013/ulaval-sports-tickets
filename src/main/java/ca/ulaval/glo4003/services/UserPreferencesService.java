package ca.ulaval.glo4003.services;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import ca.ulaval.glo4003.domain.search.TicketSearchPreferenceDto;
import ca.ulaval.glo4003.domain.users.User;
import ca.ulaval.glo4003.exceptions.UserDoesntHaveSavedPreferences;
import ca.ulaval.glo4003.fakes.FakeDataUserPreferencesDao;
import ca.ulaval.glo4003.presentation.viewmodels.TicketSearchViewModel;
import ca.ulaval.glo4003.presentation.viewmodels.factories.TicketSearchPreferenceFactory;

@Service
public class UserPreferencesService {

	@Inject
	FakeDataUserPreferencesDao userPreferencesDao;
	
	@Inject
	TicketSearchPreferenceFactory ticketSearchFactory;
	
	
	public TicketSearchViewModel getUserPreferencesForUser(User currentUser) throws UserDoesntHaveSavedPreferences{
		
		TicketSearchPreferenceDto ticketSPDto= userPreferencesDao.get(currentUser.getUsername());	
		TicketSearchViewModel ticketSearchVModel=ticketSearchFactory.createViewModel(ticketSPDto);
		return ticketSearchVModel;
				
	}
	
	public void saveUserPreference(User currentUser, TicketSearchViewModel userPreferences){
		
		TicketSearchPreferenceDto ticketSearchDto = ticketSearchFactory.createPreferenceDto(userPreferences);	
		userPreferencesDao.save(currentUser,ticketSearchDto);
	}
	
	
}
