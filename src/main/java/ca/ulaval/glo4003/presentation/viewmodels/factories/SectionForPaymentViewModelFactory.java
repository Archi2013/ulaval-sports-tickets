package ca.ulaval.glo4003.presentation.viewmodels.factories;

import java.util.Set;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.constants.TicketKind;
import ca.ulaval.glo4003.domain.game.GameDto;
import ca.ulaval.glo4003.domain.sections.SectionDto;
import ca.ulaval.glo4003.presentation.viewmodels.ChosenTicketsViewModel;
import ca.ulaval.glo4003.presentation.viewmodels.SectionForPaymentViewModel;
import ca.ulaval.glo4003.utilities.Calculator;
import ca.ulaval.glo4003.utilities.Constants;

@Component
public class SectionForPaymentViewModelFactory {

	@Inject
	private Calculator calculator;

	@Inject
	private Constants constants;

	public SectionForPaymentViewModel createViewModel(ChosenTicketsViewModel chosenTicketsVM, GameDto gameDto,
			SectionDto sectionDto) {
		SectionForPaymentViewModel sectionForPaymentVM = new SectionForPaymentViewModel();
		sectionForPaymentVM.setNumberOfTicketsToBuy(chosenTicketsVM.getNumberOfTicketsToBuy());
		sectionForPaymentVM.setSelectedSeats(toString(chosenTicketsVM.getSelectedSeats()));
		if (sectionDto.isGeneralAdmission()) {
			sectionForPaymentVM.setTicketKind(TicketKind.GENERAL_ADMISSION);
		} else {
			sectionForPaymentVM.setTicketKind(TicketKind.WITH_SEAT);
		}
		sectionForPaymentVM.setSectionName(sectionDto.getSectionName());
		sectionForPaymentVM.setDate(constants.toLongDateTimeFormatFR(gameDto.getGameDate()));
		sectionForPaymentVM.setOpponents(gameDto.getOpponents());
		sectionForPaymentVM.setSport(gameDto.getSportName());

		Double subtotal = 0.0;

		if (sectionDto.isGeneralAdmission()) {
			subtotal = chosenTicketsVM.getNumberOfTicketsToBuy() * sectionDto.getPrice();
		} else {
			subtotal = chosenTicketsVM.getSelectedSeats().size() * sectionDto.getPrice();
		}

		String subtotalFR = calculator.toPriceFR(subtotal);

		sectionForPaymentVM.setSubtotal(subtotalFR);

		return sectionForPaymentVM;
	}

	private String toString(Set<String> selectedSeats) {
		String result = "";
		if (selectedSeats.size() == 0) {
			return "Erreur : la liste de sièges ne doit pas être vide";
		} else if (selectedSeats.size() == 1) {
			return selectedSeats.get(0);
		} else {
			for (int i = 0; i < (selectedSeats.size() - 1); i++) {
				result += selectedSeats.get(i);
				if (i < selectedSeats.size() - 2) {
					result += ", ";
				}
			}
			result += " et ";
			result += selectedSeats.get(selectedSeats.size() - 1);
			return result;
		}
	}

}
