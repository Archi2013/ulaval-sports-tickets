package ca.ulaval.glo4003.services;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4003.constants.DisplayedPeriod;
import ca.ulaval.glo4003.constants.TicketKind;
import ca.ulaval.glo4003.domain.users.User;
import ca.ulaval.glo4003.domain.users.UserPreferencesDoesntExistException;
import ca.ulaval.glo4003.domain.users.UserPreferencesDto;
import ca.ulaval.glo4003.domain.users.XmlUserPreferencesDao;
import ca.ulaval.glo4003.exceptions.UserDoesntHaveSavedPreferences;
import ca.ulaval.glo4003.presentation.viewmodels.TicketSearchViewModel;
import ca.ulaval.glo4003.presentation.viewmodels.factories.TicketSearchPreferenceFactory;
import ca.ulaval.glo4003.services.exceptions.UserPreferencesNotSaved;
import ca.ulaval.glo4003.utilities.search.TicketSearchPreferenceDto;

@RunWith(MockitoJUnitRunner.class)
public class UserPreferencesServiceTest {

	private static final String USERNAME = "mo";

	@Mock
	private User currentUser;

	@Mock
	private XmlUserPreferencesDao userPreferencesDaoMock;

	@Mock
	UserPreferencesDto userPrefDto;

	@Mock
	TicketSearchPreferenceFactory ticketSearchFactoryMock;

	@InjectMocks
	private UserPreferencesService userPreferencesService;

	private TicketSearchPreferenceDto ticketSPDto;
	private TicketSearchViewModel ticketSearchViewModel;

	@Before
	public void setUp() throws UserDoesntHaveSavedPreferences {

		when(currentUser.getUsername()).thenReturn(USERNAME);

		ticketSPDto = mock(TicketSearchPreferenceDto.class);
		when(userPreferencesDaoMock.get(USERNAME)).thenReturn(ticketSPDto);

		ticketSearchViewModel = new TicketSearchViewModel();
		List<String> selectedSports = new ArrayList<>();
		List<TicketKind> ticketKinds = new ArrayList<>();
		ticketSearchViewModel.selectedSports = selectedSports;
		ticketSearchViewModel.selectedTicketKinds = ticketKinds;
		ticketSearchViewModel.setLocalGameOnly(true);
		ticketSearchViewModel.setDisplayedPeriod(DisplayedPeriod.ALL);
		when(ticketSearchFactoryMock.createViewModel(ticketSPDto)).thenReturn(ticketSearchViewModel);

		when(
				ticketSearchFactoryMock.createPreferenceDto(ticketSearchViewModel.getSelectedSports(),
						ticketSearchViewModel.getDisplayedPeriod(), ticketSearchViewModel.isLocalGameOnly(),
						ticketSearchViewModel.getSelectedTicketKinds())).thenReturn(ticketSPDto);
	}

	@Test
	public void getUserPreferencesForUser_should_get_ticketSearchPrefDto() throws UserDoesntHaveSavedPreferences {
		userPreferencesService.getUserPreferencesForUser(currentUser);
		verify(userPreferencesDaoMock).get(USERNAME);
	}

	@Test
	public void saveUserPreference_should_create_ticketSearchPrefDto() throws UserPreferencesNotSaved, UserPreferencesDoesntExistException {
		userPreferencesService.saveUserPreference(currentUser, ticketSearchViewModel);
		verify(userPreferencesDaoMock).save(currentUser, ticketSPDto);
	}
}
