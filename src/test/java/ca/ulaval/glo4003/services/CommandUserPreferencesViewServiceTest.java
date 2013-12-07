package ca.ulaval.glo4003.services;

import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4003.domain.users.UserPreferencesDao;
import ca.ulaval.glo4003.domain.users.UserPreferencesDoesntExistException;
import ca.ulaval.glo4003.services.exceptions.UserPreferencesNotSaved;
import ca.ulaval.glo4003.utilities.search.TicketSearchPreferenceDto;

@RunWith(MockitoJUnitRunner.class)
public class CommandUserPreferencesViewServiceTest {

	private static final String USERNAME = "north";
	
	@Mock
	UserPreferencesDao userPreferencesDao;

	@InjectMocks
	private CommandUserPreferencesService commandUserPreferencesService;

	@Before
	public void setUp() {
	}
	
	@Test
	public void saveUserPreference_should_save_preferences() throws UserPreferencesNotSaved, UserPreferencesDoesntExistException {
		TicketSearchPreferenceDto ticketSearchPreferenceDto = mock(TicketSearchPreferenceDto.class);
		
		commandUserPreferencesService.saveUserPreference(USERNAME, ticketSearchPreferenceDto);
		
		verify(userPreferencesDao).save(USERNAME, ticketSearchPreferenceDto);
		verify(userPreferencesDao).commit();
	}
	
	@Test(expected=UserPreferencesNotSaved.class)
	public void when_UserPreferencesDoesntExistException_saveUserPreference_should_raise_UserPreferencesNotSaved() throws UserPreferencesDoesntExistException, UserPreferencesNotSaved {
		TicketSearchPreferenceDto ticketSearchPreferenceDto = mock(TicketSearchPreferenceDto.class);
		
		doThrow(new UserPreferencesDoesntExistException()).when(userPreferencesDao).save(USERNAME, ticketSearchPreferenceDto);
		
		commandUserPreferencesService.saveUserPreference(USERNAME, ticketSearchPreferenceDto);
	}
}
