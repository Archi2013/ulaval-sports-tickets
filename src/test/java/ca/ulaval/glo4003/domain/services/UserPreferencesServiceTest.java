package ca.ulaval.glo4003.domain.services;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4003.domain.search.TicketSearchPreferenceDto;
import ca.ulaval.glo4003.domain.users.User;
import ca.ulaval.glo4003.domain.users.UserPreferencesDao;
import ca.ulaval.glo4003.domain.users.UserPreferencesDto;
import ca.ulaval.glo4003.exceptions.UserDoesntHaveSavedPreferences;
import ca.ulaval.glo4003.presentation.viewmodels.TicketSearchViewModel;
import ca.ulaval.glo4003.presentation.viewmodels.factories.TicketSearchPreferenceFactory;
import ca.ulaval.glo4003.services.UserPreferencesService;

@Ignore
@RunWith(MockitoJUnitRunner.class)
public class UserPreferencesServiceTest {
	
	private static final String USERNAME = "mo";

	@Mock
	private User currentUser;
	
	@Mock
	private UserPreferencesDao userPreferencesDaoMock;
	
	@Mock
	UserPreferencesDto userPrefDto;
	
	@Mock
	TicketSearchPreferenceFactory ticketSearchFactoryMock;
	
	@InjectMocks
	private UserPreferencesService userPreferencesService;
	
	private TicketSearchPreferenceDto ticketSPDto;
	private TicketSearchViewModel ticketSearchViewModel;
	
	@Before
	public void setup() throws UserDoesntHaveSavedPreferences{
		
		when(currentUser.getUsername()).thenReturn(USERNAME);
		
		ticketSPDto = mock(TicketSearchPreferenceDto.class);
		when(userPreferencesDaoMock.get(USERNAME)).thenReturn(ticketSPDto);
		
		ticketSearchViewModel = new TicketSearchViewModel();
		when(ticketSearchFactoryMock.createViewModel(ticketSPDto)).thenReturn(ticketSearchViewModel);
	
		when(ticketSearchFactoryMock.createPreferenceDto(ticketSearchViewModel)).thenReturn(ticketSPDto);
	}
	
	@Test
	public void getUserPreferencesForUser_should_get_ticketSearchPrefDto() throws UserDoesntHaveSavedPreferences {
		userPreferencesService.getUserPreferencesForUser(currentUser);
		verify(userPreferencesDaoMock).get(USERNAME);
	}
	
	@Test
	public void saveUserPreference_should_create_ticketSearchPrefDto() throws UserDoesntHaveSavedPreferences {
		userPreferencesService.saveUserPreference(currentUser, ticketSearchViewModel);
		verify(userPreferencesDaoMock).save(currentUser,ticketSPDto);
	}
}
