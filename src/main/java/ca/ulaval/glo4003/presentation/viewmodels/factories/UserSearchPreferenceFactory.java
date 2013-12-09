package ca.ulaval.glo4003.presentation.viewmodels.factories;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.transform;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.constants.DisplayedPeriod;
import ca.ulaval.glo4003.constants.TicketKind;
import ca.ulaval.glo4003.presentation.viewmodels.SearchViewModel;
import ca.ulaval.glo4003.utilities.Constants;
import ca.ulaval.glo4003.utilities.search.dto.UserSearchPreferenceDto;

import com.google.common.base.Function;

@Component
public class UserSearchPreferenceFactory {

	@Inject
	private Constants constants;

	public UserSearchPreferenceDto createPreferenceDto(SearchViewModel searchVM) {
		List<String> selectedTicketKindsString = newArrayList();
		List<TicketKind> selectedTicketKinds = searchVM.getSelectedTicketKinds();
		if (selectedTicketKinds != null) {
			selectedTicketKindsString = transform(selectedTicketKinds, new Function<TicketKind, String>() {
				@Override
				public String apply(TicketKind ticketKind) {
					return ticketKind.name();
				}
			});
		}
		UserSearchPreferenceDto userSearchPreferenceDto = new UserSearchPreferenceDto(
				searchVM.getSelectedSports(), searchVM.getDisplayedPeriod().name(),
				searchVM.isLocalGameOnly(), selectedTicketKindsString);
		return userSearchPreferenceDto;
	}
	
	public SearchViewModel createViewModel(UserSearchPreferenceDto userSearchPreferenceDto) {
		DisplayedPeriod displayPeriod = DisplayedPeriod.valueOf(userSearchPreferenceDto.getDisplayedPeriod());
		List<TicketKind> selectedTicketKinds = transform(userSearchPreferenceDto.getSelectedTicketKinds(),
				new Function<String, TicketKind>() {
					@Override
					public TicketKind apply(String ticketKind) {
						return TicketKind.valueOf(ticketKind);
					}
				});
		return createTicketSearchViewModel(userSearchPreferenceDto.getSelectedSports(), displayPeriod,
				userSearchPreferenceDto.isLocalGameOnly(), selectedTicketKinds);
	}

	public SearchViewModel createInitialViewModel() {
		List<DisplayedPeriod> displayedPeriods = DisplayedPeriod.getDisplayedPeriods();
		return createTicketSearchViewModel(constants.getSportList(), displayedPeriods.get(displayedPeriods.size() - 1),
				false, TicketKind.getTicketKinds());
	}

	private SearchViewModel createTicketSearchViewModel(List<String> selectedSports,
			DisplayedPeriod displayPeriod, Boolean isLocalGameOnly, List<TicketKind> selectedTicketKinds) {
		SearchViewModel searchVM = new SearchViewModel();
		searchVM.setSelectedSports(selectedSports);
		searchVM.setDisplayedPeriod(displayPeriod);
		searchVM.setLocalGameOnly(isLocalGameOnly);
		searchVM.setSelectedTicketKinds(selectedTicketKinds);
		return searchVM;
	}

}
