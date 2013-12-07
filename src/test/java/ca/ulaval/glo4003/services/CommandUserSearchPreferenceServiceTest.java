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
public class CommandUserSearchPreferenceServiceTest {

	private static final String USERNAME = "north";
	
	@Mock
	UserSearchPreferenceDao userSearchPreferenceDao;

	@InjectMocks
	private CommandUserSearchPreferenceService commandUserSearchPreferenceService;

	@Before
	public void setUp() {
	}
	
	@Test
	public void saveUserPreference_should_save_preferences() throws UserSearchPreferenceNotSaved, UserSearchPreferenceDoesntExistException {
		UserSearchPreferenceDto userSearchPreferenceDto = mock(UserSearchPreferenceDto.class);
		
		commandUserSearchPreferenceService.saveUserSearchPreference(USERNAME, userSearchPreferenceDto);
		
		verify(userSearchPreferenceDao).save(USERNAME, userSearchPreferenceDto);
		verify(userSearchPreferenceDao).commit();
	}
	
	@Test(expected=UserSearchPreferenceNotSaved.class)
	public void when_UserSearchPreferenceDoesntExistException_saveUserPreference_should_raise_UserPreferencesNotSaved() throws UserSearchPreferenceDoesntExistException, UserSearchPreferenceNotSaved {
		UserSearchPreferenceDto userSearchPreferenceDto = mock(UserSearchPreferenceDto.class);
		
		doThrow(new UserSearchPreferenceDoesntExistException()).when(userSearchPreferenceDao).save(USERNAME, userSearchPreferenceDto);
		
		commandUserSearchPreferenceService.saveUserSearchPreference(USERNAME, userSearchPreferenceDto);
	}
}
