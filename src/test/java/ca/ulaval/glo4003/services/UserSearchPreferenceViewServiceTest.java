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
import ca.ulaval.glo4003.utilities.search.UserSearchPreferenceDto;
import ca.ulaval.glo4003.utilities.search.UserSearchPreferenceDao;

@RunWith(MockitoJUnitRunner.class)
public class UserSearchPreferenceViewServiceTest {

	private static final String USERNAME = "Xéha";

	@Mock
	UserSearchPreferenceFactory ticketSearchPreferenceFactory;
	
	@Mock
	UserSearchPreferenceDao userPreferencesDao;

	@InjectMocks
	private UserSearchPreferenceViewService userPreferencesViewService;

	@Before
	public void setUp() {
	}

	@Test
	public void getUserPreferencesForUser_should_get_ticketSearchPrefDto() throws UserDoesntHaveSavedSearchPreference {
		SearchViewModel ticketSVM = mock(SearchViewModel.class);
		UserSearchPreferenceDto ticketSPDto = mock(UserSearchPreferenceDto.class);
		
		when(userPreferencesDao.get(USERNAME)).thenReturn(ticketSPDto);
		when(ticketSearchPreferenceFactory.createViewModel(ticketSPDto)).thenReturn(ticketSVM);
		
		SearchViewModel actual = userPreferencesViewService.getSearchViewModelForUser(USERNAME);
		
		assertEquals(ticketSVM, actual);
	}
}
