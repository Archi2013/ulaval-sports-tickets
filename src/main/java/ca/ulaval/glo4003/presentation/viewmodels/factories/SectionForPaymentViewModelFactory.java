package ca.ulaval.glo4003.presentation.viewmodels.factories;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.domain.cart.SectionForCart;
import ca.ulaval.glo4003.presentation.viewmodels.SectionForPaymentViewModel;
import ca.ulaval.glo4003.utilities.Calculator;
import ca.ulaval.glo4003.utilities.Constants;

@Component
public class SectionForPaymentViewModelFactory {

	@Inject
	private Calculator calculator;

	@Inject
	private Constants constants;
	
	public SectionForPaymentViewModel createViewModel(SectionForCart sectionFC) {
		SectionForPaymentViewModel sectionForPaymentVM = new SectionForPaymentViewModel();
		
		sectionForPaymentVM.setGeneralAdmission(sectionFC.getGeneralAdmission());
		
		sectionForPaymentVM.setNumberOfTicketsToBuy(sectionFC.getNumberOfTicketsToBuy());
		sectionForPaymentVM.setSelectedSeats(toString(sectionFC.getSelectedSeats()));
		
		sectionForPaymentVM.setSportName(sectionFC.getSportName());
		sectionForPaymentVM.setGameDate(constants.toLongDateTimeFormatFR(sectionFC.getGameDate()));
		sectionForPaymentVM.setSectionName(sectionFC.getSectionName());
		
		sectionForPaymentVM.setOpponents(sectionFC.getOpponents());
		sectionForPaymentVM.setLocation(sectionFC.getLocation());
		sectionForPaymentVM.setSubtotal(calculator.toPriceFR(sectionFC.getSubtotal()));
		
		return null;
	}

	private String toString(Set<String> selectedSeats) {
		String result = "";
		List<String> seats = new ArrayList<>(selectedSeats);
		if (seats.size() == 0) {
			return "Erreur : la liste de sièges ne doit pas être vide";
		} else if (seats.size() == 1) {
			return seats.get(0);
		} else {
			for (int i = 0; i < (seats.size() - 1); i++) {
				result += seats.get(i);
				if (i < seats.size() - 2) {
					result += ", ";
				}
			}
			result += " et ";
			result += seats.get(seats.size() - 1);
			return result;
		}
	}
}
