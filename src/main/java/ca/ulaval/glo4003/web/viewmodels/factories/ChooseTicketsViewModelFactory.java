package ca.ulaval.glo4003.web.viewmodels.factories;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.domain.dtos.GameDto;
import ca.ulaval.glo4003.domain.dtos.SectionDto;
import ca.ulaval.glo4003.domain.utilities.Constants.TicketKind;
import ca.ulaval.glo4003.web.viewmodels.ChooseTicketsViewModel;

@Component
public class ChooseTicketsViewModelFactory {

	public ChooseTicketsViewModel createViewModel(GameDto gameDto, SectionDto sectionDto) {
		ChooseTicketsViewModel chooseTicketsVM = new ChooseTicketsViewModel();
		chooseTicketsVM.setNumberOfTicketsToBuy(1);
		chooseTicketsVM.setSelectedSeats(new ArrayList<String>());
		if (sectionDto.isGeneralAdmission()) {
			chooseTicketsVM.setTicketKind(TicketKind.GENERAL_ADMISSION);
		} else {
			chooseTicketsVM.setTicketKind(TicketKind.WITH_SEAT);
		}
		chooseTicketsVM.setAdmissionType(sectionDto.getAdmissionType());
		chooseTicketsVM.setSectionName(sectionDto.getSectionName());
		chooseTicketsVM.setDate(gameDto.getGameDate());
		chooseTicketsVM.setOpponents(gameDto.getOpponents());
		chooseTicketsVM.setSport(gameDto.getSportName());
		
		return chooseTicketsVM;
	}
}
