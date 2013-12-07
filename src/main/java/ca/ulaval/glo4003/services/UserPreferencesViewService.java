package ca.ulaval.glo4003.services;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import ca.ulaval.glo4003.domain.users.UserPreferencesDao;
import ca.ulaval.glo4003.exceptions.UserDoesntHaveSavedPreferences;
import ca.ulaval.glo4003.presentation.viewmodels.TicketSearchViewModel;
import ca.ulaval.glo4003.presentation.viewmodels.factories.TicketSearchPreferenceFactory;
import ca.ulaval.glo4003.utilities.search.TicketSearchPreferenceDto;

@Service
public class UserPreferencesViewService {

	@Inject
	TicketSearchPreferenceFactory ticketSearchPreferenceFactory;
	
	@Inject
	UserPreferencesDao userPreferencesDao;
	
	public TicketSearchViewModel getUserPreferencesForUser(String username) throws UserDoesntHaveSavedPreferences {
		TicketSearchPreferenceDto ticketSPDto = userPreferencesDao.get(username);	
		TicketSearchViewModel ticketSearchVModel = ticketSearchPreferenceFactory.createViewModel(ticketSPDto);
		return ticketSearchVModel;			
	}
}
