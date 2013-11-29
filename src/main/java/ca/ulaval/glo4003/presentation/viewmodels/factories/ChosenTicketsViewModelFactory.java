package ca.ulaval.glo4003.presentation.viewmodels.factories;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.domain.game.GameDto;
import ca.ulaval.glo4003.domain.sections.SectionDto;
import ca.ulaval.glo4003.presentation.viewmodels.ChosenTicketsViewModel;

@Component
public class ChosenTicketsViewModelFactory {

	public ChosenTicketsViewModel createViewModel(GameDto gameDto, SectionDto sectionDto) {
		ChosenTicketsViewModel chosenTicketsVM = new ChosenTicketsViewModel();
		chosenTicketsVM.setNumberOfTicketsToBuy(1);
		chosenTicketsVM.setSelectedSeats(new ArrayList<String>());
		chosenTicketsVM.setSectionName(sectionDto.getSectionName());
		chosenTicketsVM.setSportName(gameDto.getSportName());
		chosenTicketsVM.setGameDate(gameDto.getGameDate());
		return chosenTicketsVM;
	}
}
