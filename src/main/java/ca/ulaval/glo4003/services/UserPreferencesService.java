package ca.ulaval.glo4003.services;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import ca.ulaval.glo4003.domain.users.User;
import ca.ulaval.glo4003.domain.users.XmlUserPreferencesDao;
import ca.ulaval.glo4003.exceptions.UserDoesntHaveSavedPreferences;
import ca.ulaval.glo4003.presentation.viewmodels.TicketSearchViewModel;
import ca.ulaval.glo4003.presentation.viewmodels.factories.TicketSearchPreferenceFactory;
import ca.ulaval.glo4003.utilities.search.TicketSearchPreferenceDto;

@Service
public class UserPreferencesService {


	@Inject
	TicketSearchPreferenceFactory ticketSearchFactory;
	
	@Inject
	XmlUserPreferencesDao xmlUserPreferencesDao;
	
	
	public TicketSearchViewModel getUserPreferencesForUser(User currentUser) throws UserDoesntHaveSavedPreferences{
		TicketSearchPreferenceDto ticketSPDto= xmlUserPreferencesDao.get(currentUser.getUsername());	
		TicketSearchViewModel ticketSearchVModel=ticketSearchFactory.createViewModel(ticketSPDto);
		return ticketSearchVModel;
				
	}
	
	public void saveUserPreference(User currentUser, TicketSearchViewModel userPreferences){
		
		TicketSearchPreferenceDto ticketSearchDto = ticketSearchFactory.createPreferenceDto(
				userPreferences.getSelectedSports(), userPreferences.getDisplayedPeriod(),
				userPreferences.isLocalGameOnly(), userPreferences.getSelectedTicketKinds());	
		
		xmlUserPreferencesDao.save(currentUser, ticketSearchDto);
		xmlUserPreferencesDao.commit();
	}
	
	
}
