package ca.ulaval.glo4003.presentation.viewmodels.factories;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.transform;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.constants.DisplayedPeriod;
import ca.ulaval.glo4003.constants.TicketKind;
import ca.ulaval.glo4003.presentation.viewmodels.TicketSearchViewModel;
import ca.ulaval.glo4003.utilities.Constants;
import ca.ulaval.glo4003.utilities.search.TicketSearchPreferenceDto;

import com.google.common.base.Function;

@Component
public class TicketSearchPreferenceFactory {

	@Inject
	private Constants constants;

	public TicketSearchPreferenceDto createPreferenceDto(TicketSearchViewModel ticketSearchVM) {
		List<String> selectedTicketKindsString = newArrayList();
		List<TicketKind> selectedTicketKinds = ticketSearchVM.getSelectedTicketKinds();
		if (selectedTicketKinds != null) {
			selectedTicketKindsString = transform(selectedTicketKinds, new Function<TicketKind, String>() {
				@Override
				public String apply(TicketKind ticketKind) {
					return ticketKind.name();
				}
			});
		}
		TicketSearchPreferenceDto ticketSPDto = new TicketSearchPreferenceDto(
				ticketSearchVM.getSelectedSports(), ticketSearchVM.getDisplayedPeriod().name(),
				ticketSearchVM.isLocalGameOnly(), selectedTicketKindsString);
		return ticketSPDto;
	}
	
	public TicketSearchViewModel createViewModel(TicketSearchPreferenceDto ticketSPDto) {
		DisplayedPeriod displayPeriod = DisplayedPeriod.valueOf(ticketSPDto.getDisplayedPeriod());
		List<TicketKind> selectedTicketKinds = transform(ticketSPDto.getSelectedTicketKinds(),
				new Function<String, TicketKind>() {
					@Override
					public TicketKind apply(String ticketKind) {
						return TicketKind.valueOf(ticketKind);
					}
				});
		return createTicketSearchViewModel(ticketSPDto.getSelectedSports(), displayPeriod,
				ticketSPDto.isLocalGameOnly(), selectedTicketKinds);
	}

	public TicketSearchViewModel createInitialViewModel() {
		List<DisplayedPeriod> displayedPeriods = DisplayedPeriod.getDisplayedPeriods();
		return createTicketSearchViewModel(constants.getSportList(), displayedPeriods.get(displayedPeriods.size() - 1),
				false, TicketKind.getTicketKinds());
	}

	private TicketSearchViewModel createTicketSearchViewModel(List<String> selectedSports,
			DisplayedPeriod displayPeriod, Boolean isLocalGameOnly, List<TicketKind> selectedTicketKinds) {
		TicketSearchViewModel ticketSearchVM = new TicketSearchViewModel();
		ticketSearchVM.setSelectedSports(selectedSports);
		ticketSearchVM.setDisplayedPeriod(displayPeriod);
		ticketSearchVM.setLocalGameOnly(isLocalGameOnly);
		ticketSearchVM.setSelectedTicketKinds(selectedTicketKinds);
		return ticketSearchVM;
	}

}
