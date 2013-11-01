package ca.ulaval.glo4003.web.viewmodels.factories;

import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.domain.dtos.TicketSearchPreferenceDto;
import ca.ulaval.glo4003.web.viewmodels.TicketSearchViewModel;

@Component
public class TicketSearchPreferenceFactory {

	public TicketSearchViewModel createViewModel(TicketSearchPreferenceDto ticketSPDto) {
		TicketSearchViewModel ticketSearchVM = new TicketSearchViewModel();
		ticketSearchVM.setSelectedSports(ticketSPDto.getSelectedSports());
		ticketSearchVM.setDisplayedPeriod(ticketSPDto.getDisplayedPeriod());
		ticketSearchVM.setLocalGame(ticketSPDto.isLocalGame());
		ticketSearchVM.setSelectedTicketTypes(ticketSPDto.getSelectedTicketTypes());
		return ticketSearchVM;
	}
	
	public TicketSearchPreferenceDto createPreferenceDto(TicketSearchViewModel ticketSVM) {
		TicketSearchPreferenceDto ticketSPDto = new TicketSearchPreferenceDto(ticketSVM.getSelectedSports(), ticketSVM.getDisplayedPeriod(),
				ticketSVM.isLocalGame(), ticketSVM.getSelectedTicketTypes());
		return ticketSPDto;
	}
}
