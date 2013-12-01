package ca.ulaval.glo4003.presentation.viewmodels.factories;

import java.util.HashSet;

import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.domain.game.GameDto;
import ca.ulaval.glo4003.domain.sections.SectionDto;
import ca.ulaval.glo4003.presentation.viewmodels.ChosenGeneralTicketsViewModel;
import ca.ulaval.glo4003.presentation.viewmodels.ChosenTicketsViewModel;
import ca.ulaval.glo4003.presentation.viewmodels.ChosenWithSeatTicketsViewModel;

@Component
public class ChosenTicketsViewModelFactory {

	public ChosenGeneralTicketsViewModel createViewModelForGeneralTickets(GameDto gameDto, SectionDto sectionDto) {
		ChosenGeneralTicketsViewModel chosenGeneralTicketsVM = new ChosenGeneralTicketsViewModel();
		chosenGeneralTicketsVM.setNumberOfTicketsToBuy(1);
		modifyViewModel(chosenGeneralTicketsVM, gameDto, sectionDto);
		return chosenGeneralTicketsVM;
	}
	
	public ChosenWithSeatTicketsViewModel createViewModelForWithSeatTickets(GameDto gameDto, SectionDto sectionDto) {
		ChosenWithSeatTicketsViewModel chosenWithSeatTicketsVM = new ChosenWithSeatTicketsViewModel();
		chosenWithSeatTicketsVM.setSelectedSeats(new HashSet<String>());
		modifyViewModel(chosenWithSeatTicketsVM, gameDto, sectionDto);
		return chosenWithSeatTicketsVM;
	}
	
	private void modifyViewModel(ChosenTicketsViewModel chosenTicketsVM, GameDto gameDto, SectionDto sectionDto) {
		chosenTicketsVM.setSectionName(sectionDto.getSectionName());
		chosenTicketsVM.setSportName(gameDto.getSportName());
		chosenTicketsVM.setGameDate(gameDto.getGameDate());
	}
}
