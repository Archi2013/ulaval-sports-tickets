package ca.ulaval.glo4003.presentation.viewmodels.factories;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.domain.dtos.GameDto;
import ca.ulaval.glo4003.domain.dtos.SectionDto;
import ca.ulaval.glo4003.presentation.viewmodels.ChooseTicketsViewModel;

@Component
public class ChooseTicketsViewModelFactory {

	public ChooseTicketsViewModel createViewModel(GameDto gameDto, SectionDto sectionDto) {
		ChooseTicketsViewModel chooseTicketsVM = new ChooseTicketsViewModel();
		chooseTicketsVM.setNumberOfTicketsToBuy(1);
		chooseTicketsVM.setSelectedSeats(new ArrayList<String>());
		chooseTicketsVM.setSectionName(sectionDto.getSectionName());
		chooseTicketsVM.setGameId(gameDto.getId());
		
		return chooseTicketsVM;
	}
}
