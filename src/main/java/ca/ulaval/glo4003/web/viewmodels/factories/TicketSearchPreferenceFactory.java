package ca.ulaval.glo4003.web.viewmodels.factories;

import static com.google.common.collect.Lists.*;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.google.common.base.Function;

import ca.ulaval.glo4003.domain.dtos.TicketSearchPreferenceDto;
import ca.ulaval.glo4003.domain.utilities.Constants;
import ca.ulaval.glo4003.domain.utilities.Constants.DisplayedPeriod;
import ca.ulaval.glo4003.domain.utilities.Constants.TicketKind;
import ca.ulaval.glo4003.web.viewmodels.TicketSearchViewModel;

@Component
public class TicketSearchPreferenceFactory {
	
	@Inject
	private Constants constants;
	
	public TicketSearchPreferenceDto createPreferenceDto(TicketSearchViewModel ticketSVM) {
		List<TicketKind> selectedTicketKinds = ticketSVM.getSelectedTicketKinds();
		List<String> selectedTicketKindsString = newArrayList();
		if (selectedTicketKinds != null) {
			selectedTicketKindsString = transform(selectedTicketKinds, new Function<TicketKind, String>() {
				@Override
				public String apply(TicketKind ticketKind) {
					return ticketKind.name();
				}
			});
		}
		TicketSearchPreferenceDto ticketSPDto = new TicketSearchPreferenceDto(ticketSVM.getSelectedSports(), ticketSVM.getDisplayedPeriod().name(),
				ticketSVM.isLocalGameOnly(), selectedTicketKindsString);
		return ticketSPDto;
	}
	
	public TicketSearchViewModel createViewModel(TicketSearchPreferenceDto ticketSPDto) {
		DisplayedPeriod displayPeriod = DisplayedPeriod.valueOf(ticketSPDto.getDisplayedPeriod());
		List<TicketKind> selectedTicketKinds = transform(ticketSPDto.getSelectedTicketKinds(), new Function<String, TicketKind>() {
			@Override
			public TicketKind apply(String ticketKind) {
				return TicketKind.valueOf(ticketKind);
			}
		});
		
		TicketSearchViewModel ticketSearchVM = new TicketSearchViewModel();
		ticketSearchVM.setSelectedSports(ticketSPDto.getSelectedSports());
		ticketSearchVM.setDisplayedPeriod(displayPeriod);
		ticketSearchVM.setLocalGameOnly(ticketSPDto.isLocalGameOnly());
		ticketSearchVM.setSelectedTicketKinds(selectedTicketKinds);
		return ticketSearchVM;
	}
	
	public TicketSearchViewModel createInitialViewModel() {
		TicketSearchViewModel ticketSearchVM = new TicketSearchViewModel();
		ticketSearchVM.setSelectedSports(constants.getSportList());
		List<DisplayedPeriod> displayedPeriods = constants.getDisplayedPeriods();
		ticketSearchVM.setDisplayedPeriod(displayedPeriods.get(displayedPeriods.size() - 1));
		ticketSearchVM.setLocalGameOnly(false);
		ticketSearchVM.setSelectedTicketKinds(constants.getTicketKinds());
		return ticketSearchVM;
	}
}
