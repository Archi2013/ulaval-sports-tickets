package ca.ulaval.glo4003.web.viewmodels.factories;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.domain.dtos.TicketSearchPreferenceDto;
import ca.ulaval.glo4003.domain.utilities.Constants;
import ca.ulaval.glo4003.domain.utilities.Constants.DisplayedPeriod;
import ca.ulaval.glo4003.web.viewmodels.TicketSearchViewModel;

@Component
public class TicketSearchPreferenceFactory {
	
	@Inject
	private Constants constants;
	
	public TicketSearchPreferenceDto createPreferenceDto(TicketSearchViewModel ticketSVM) {
		TicketSearchPreferenceDto ticketSPDto = new TicketSearchPreferenceDto(ticketSVM.getSelectedSports(), ticketSVM.getDisplayedPeriod(),
				ticketSVM.isLocalGameOnly(), ticketSVM.getSelectedTicketTypes());
		return ticketSPDto;
	}
	
	public TicketSearchViewModel createViewModel(TicketSearchPreferenceDto ticketSPDto) {
		TicketSearchViewModel ticketSearchVM = new TicketSearchViewModel();
		ticketSearchVM.setSelectedSports(ticketSPDto.getSelectedSports());
		ticketSearchVM.setDisplayedPeriod(ticketSPDto.getDisplayedPeriod());
		ticketSearchVM.setLocalGameOnly(ticketSPDto.isLocalGameOnly());
		ticketSearchVM.setSelectedTicketTypes(ticketSPDto.getSelectedTicketTypes());
		return ticketSearchVM;
	}
	
	public TicketSearchViewModel createInitialViewModel() {
		TicketSearchViewModel ticketSearchVM = new TicketSearchViewModel();
		ticketSearchVM.setSelectedSports(constants.getSportsList());
		List<DisplayedPeriod> displayedPeriods = constants.getDisplayedPeriods();
		ticketSearchVM.setDisplayedPeriod(displayedPeriods.get(displayedPeriods.size() - 1));
		ticketSearchVM.setLocalGameOnly(false);
		ticketSearchVM.setSelectedTicketTypes(constants.getTicketTypes());
		return ticketSearchVM;
	}
}
