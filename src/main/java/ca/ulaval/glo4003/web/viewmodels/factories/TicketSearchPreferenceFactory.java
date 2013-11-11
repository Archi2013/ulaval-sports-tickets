package ca.ulaval.glo4003.web.viewmodels.factories;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

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
		List<String> selectedTicketKinds = new ArrayList<>();
		for (TicketKind ticketKind : ticketSVM.getSelectedTicketKinds()) {
			selectedTicketKinds.add(ticketKind.name());
		}
		TicketSearchPreferenceDto ticketSPDto = new TicketSearchPreferenceDto(ticketSVM.getSelectedSports(), ticketSVM.getDisplayedPeriod().name(),
				ticketSVM.isLocalGameOnly(), selectedTicketKinds);
		return ticketSPDto;
	}
	
	public TicketSearchViewModel createViewModel(TicketSearchPreferenceDto ticketSPDto) {
		DisplayedPeriod displayPeriod = DisplayedPeriod.valueOf(ticketSPDto.getDisplayedPeriod());
		List<TicketKind> selectedTicketKinds = new ArrayList<>();
		for (String ticketKind : ticketSPDto.getSelectedTicketKinds()) {
			selectedTicketKinds.add(TicketKind.valueOf(ticketKind));
		}
		
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
