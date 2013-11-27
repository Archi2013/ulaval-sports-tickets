package ca.ulaval.glo4003.presentation.viewmodels.factories;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.domain.game.GameDto;
import ca.ulaval.glo4003.domain.sections.SectionDto;
import ca.ulaval.glo4003.presentation.viewmodels.ChooseTicketsViewModel;

@Component
public class ChooseTicketsViewModelFactory {

	public ChooseTicketsViewModel createViewModel(GameDto gameDto, SectionDto sectionDto) {
		ChooseTicketsViewModel chooseTicketsVM = new ChooseTicketsViewModel();
		chooseTicketsVM.setNumberOfTicketsToBuy(1);
		chooseTicketsVM.setSelectedSeats(new ArrayList<String>());
		chooseTicketsVM.setSectionName(sectionDto.getSectionName());
		chooseTicketsVM.setSportName(gameDto.getSportName());
		chooseTicketsVM.setGameDate(gameDto.getGameDate());
		return chooseTicketsVM;
	}
}
