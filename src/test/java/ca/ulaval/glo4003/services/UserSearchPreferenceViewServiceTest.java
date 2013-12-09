package ca.ulaval.glo4003.services;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4003.exceptions.UserDoesntHaveSavedSearchPreference;
import ca.ulaval.glo4003.presentation.viewmodels.SearchViewModel;
import ca.ulaval.glo4003.presentation.viewmodels.factories.UserSearchPreferenceFactory;
import ca.ulaval.glo4003.utilities.search.UserSearchPreferenceDao;
import ca.ulaval.glo4003.utilities.search.dto.UserSearchPreferenceDto;

@RunWith(MockitoJUnitRunner.class)
public class UserSearchPreferenceViewServiceTest {

	private static final String USERNAME = "Xéha";

	@Mock
	UserSearchPreferenceFactory userSearchPreferenceFactory;
	
	@Mock
	UserSearchPreferenceDao userSearchPreferenceDao;

	@InjectMocks
	private UserSearchPreferenceViewService userSearchPreferenceViewService;

	@Before
	public void setUp() {
	}

	@Test
	public void getUserPreferencesForUser_should_get_ticketSearchPrefDto() throws UserDoesntHaveSavedSearchPreference {
		SearchViewModel searchVM = mock(SearchViewModel.class);
		UserSearchPreferenceDto userSearchPreferenceDto = mock(UserSearchPreferenceDto.class);
		
		when(userSearchPreferenceDao.get(USERNAME)).thenReturn(userSearchPreferenceDto);
		when(userSearchPreferenceFactory.createViewModel(userSearchPreferenceDto)).thenReturn(searchVM);
		
		SearchViewModel actual = userSearchPreferenceViewService.getSearchViewModelForUser(USERNAME);
		
		assertEquals(searchVM, actual);
	}
}
