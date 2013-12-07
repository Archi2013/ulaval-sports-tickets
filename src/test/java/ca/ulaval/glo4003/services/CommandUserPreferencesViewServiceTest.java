package ca.ulaval.glo4003.services;

import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4003.services.exceptions.UserSearchPreferenceNotSaved;
import ca.ulaval.glo4003.utilities.search.UserSearchPreferenceDto;
import ca.ulaval.glo4003.utilities.search.UserSearchPreferenceDoesntExistException;
import ca.ulaval.glo4003.utilities.search.UserSearchPreferenceDao;

@RunWith(MockitoJUnitRunner.class)
public class CommandUserPreferencesViewServiceTest {

	private static final String USERNAME = "north";
	
	@Mock
	UserSearchPreferenceDao userPreferencesDao;

	@InjectMocks
	private CommandUserSearchPreferenceService commandUserPreferencesService;

	@Before
	public void setUp() {
	}
	
	@Test
	public void saveUserPreference_should_save_preferences() throws UserSearchPreferenceNotSaved, UserSearchPreferenceDoesntExistException {
		UserSearchPreferenceDto ticketSearchPreferenceDto = mock(UserSearchPreferenceDto.class);
		
		commandUserPreferencesService.saveUserSearchPreference(USERNAME, ticketSearchPreferenceDto);
		
		verify(userPreferencesDao).save(USERNAME, ticketSearchPreferenceDto);
		verify(userPreferencesDao).commit();
	}
	
	@Test(expected=UserSearchPreferenceNotSaved.class)
	public void when_UserPreferencesDoesntExistException_saveUserPreference_should_raise_UserPreferencesNotSaved() throws UserSearchPreferenceDoesntExistException, UserSearchPreferenceNotSaved {
		UserSearchPreferenceDto ticketSearchPreferenceDto = mock(UserSearchPreferenceDto.class);
		
		doThrow(new UserSearchPreferenceDoesntExistException()).when(userPreferencesDao).save(USERNAME, ticketSearchPreferenceDto);
		
		commandUserPreferencesService.saveUserSearchPreference(USERNAME, ticketSearchPreferenceDto);
	}
}
