package ca.ulaval.glo4003.domain.services;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4003.domain.dtos.TicketSearchPreferenceDto;
import ca.ulaval.glo4003.domain.dtos.UserDto;
import ca.ulaval.glo4003.domain.dtos.UserPreferencesDto;
import ca.ulaval.glo4003.domain.users.User;
import ca.ulaval.glo4003.persistence.daos.UserDoesntHaveSavedPreferences;
import ca.ulaval.glo4003.persistence.daos.UserPreferencesDao;
import ca.ulaval.glo4003.presentation.viewmodels.TicketSearchViewModel;
import ca.ulaval.glo4003.presentation.viewmodels.factories.TicketSearchPreferenceFactory;

@RunWith(MockitoJUnitRunner.class)
public class UserPreferencesServiceTest {
		
	@Mock
	private User currentUser;
	
	@Mock
	private UserPreferencesDao userPreferencesDao;
	
	@Mock
	UserPreferencesDto userPrefDto;
	
	@Mock
	TicketSearchPreferenceFactory ticketSearchFactory;
	
	@Mock
	TicketSearchPreferenceDto ticketSearchPreferencesDto;
	
	@InjectMocks
	private UserPreferencesService userPreferencesService;
	
	@Test
	public void save_should_add_new_preferences_when_new_users() {
		//TODO
		assertTrue(true);
	}
}
