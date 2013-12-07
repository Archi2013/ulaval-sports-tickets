package ca.ulaval.glo4003.services;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4003.domain.users.UserPreferencesDao;
import ca.ulaval.glo4003.exceptions.UserDoesntHaveSavedPreferences;
import ca.ulaval.glo4003.presentation.viewmodels.TicketSearchViewModel;
import ca.ulaval.glo4003.presentation.viewmodels.factories.TicketSearchPreferenceFactory;
import ca.ulaval.glo4003.utilities.search.TicketSearchPreferenceDto;

@RunWith(MockitoJUnitRunner.class)
public class UserPreferencesViewServiceTest {

	private static final String USERNAME = "Xéha";

	@Mock
	TicketSearchPreferenceFactory ticketSearchPreferenceFactory;
	
	@Mock
	UserPreferencesDao userPreferencesDao;

	@InjectMocks
	private UserPreferencesViewService userPreferencesViewService;

	@Before
	public void setUp() {
	}

	@Test
	public void getUserPreferencesForUser_should_get_ticketSearchPrefDto() throws UserDoesntHaveSavedPreferences {
		TicketSearchViewModel ticketSVM = mock(TicketSearchViewModel.class);
		TicketSearchPreferenceDto ticketSPDto = mock(TicketSearchPreferenceDto.class);
		
		when(userPreferencesDao.get(USERNAME)).thenReturn(ticketSPDto);
		when(ticketSearchPreferenceFactory.createViewModel(ticketSPDto)).thenReturn(ticketSVM);
		
		TicketSearchViewModel actual = userPreferencesViewService.getUserPreferencesForUser(USERNAME);
		
		assertEquals(ticketSVM, actual);
	}
}
