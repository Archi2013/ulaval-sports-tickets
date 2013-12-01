package ca.ulaval.glo4003.services;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import ca.ulaval.glo4003.domain.search.TicketSearchPreferenceDto;
import ca.ulaval.glo4003.domain.users.User;
import ca.ulaval.glo4003.domain.users.XmlUserPreferencesDao;
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
	
	@Inject
	XmlUserPreferencesDao xmlDao;
	
	
	public TicketSearchViewModel getUserPreferencesForUser(User currentUser) throws UserDoesntHaveSavedPreferences{
		System.out.println("TEST TEST TEST");
		TicketSearchPreferenceDto ticketSPDto= xmlDao.get(currentUser.getUsername());	
		System.out.print("ticketSPDTO"+ticketSPDto.getDisplayedPeriod());
		TicketSearchViewModel ticketSearchVModel=ticketSearchFactory.createViewModel(ticketSPDto);
		System.out.println("WTF!!!!!!!!!!");
		return ticketSearchVModel;
				
	}
	
	public void saveUserPreference(User currentUser, TicketSearchViewModel userPreferences){
		
		TicketSearchPreferenceDto ticketSearchDto = ticketSearchFactory.createPreferenceDto(
				userPreferences.getSelectedSports(), userPreferences.getDisplayedPeriod(),
				userPreferences.isLocalGameOnly(), userPreferences.getSelectedTicketKinds());	
		userPreferencesDao.save(currentUser,ticketSearchDto);
		xmlDao.save(currentUser, ticketSearchDto);
	}
	
	
}
