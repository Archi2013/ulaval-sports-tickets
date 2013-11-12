package ca.ulaval.glo4003.web.viewmodels.factories;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.domain.dtos.GameDto;
import ca.ulaval.glo4003.domain.dtos.SectionDto;
import ca.ulaval.glo4003.domain.utilities.Calculator;
import ca.ulaval.glo4003.domain.utilities.Constants;
import ca.ulaval.glo4003.domain.utilities.Constants.TicketKind;
import ca.ulaval.glo4003.web.viewmodels.ChooseTicketsViewModel;
import ca.ulaval.glo4003.web.viewmodels.SectionForPaymentViewModel;

@Component
public class SectionForPaymentViewModelFactory {
	
	@Inject
	private Calculator calculator;
	
	@Inject
	private Constants constants;

	public SectionForPaymentViewModel createViewModel(ChooseTicketsViewModel chooseTicketsVM,
			GameDto gameDto, SectionDto sectionDto) {
		SectionForPaymentViewModel sectionForPaymentVM = new SectionForPaymentViewModel();
		sectionForPaymentVM.setNumberOfTicketsToBuy(chooseTicketsVM.getNumberOfTicketsToBuy());
		sectionForPaymentVM.setSelectedSeats(toString(chooseTicketsVM.getSelectedSeats()));
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
			subtotal = chooseTicketsVM.getNumberOfTicketsToBuy() * sectionDto.getPrice();
		} else {
			subtotal = chooseTicketsVM.getSelectedSeats().size() * sectionDto.getPrice();
		}
		
		String subtotalFR = calculator.toPriceFR(subtotal);
		
		sectionForPaymentVM.setSubtotal(subtotalFR);
		
		return sectionForPaymentVM;
	}

	private String toString(List<String> selectedSeats) {
		String result = "";
		if (selectedSeats.size() == 0){
			return "Erreur : la liste de sièges ne doit pas être vide";
		} else if (selectedSeats.size() == 1) {
			return selectedSeats.get(0);
		} else {
			for (int i = 0 ; i < (selectedSeats.size() - 1) ; i++) {
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
