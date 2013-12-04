package ca.ulaval.glo4003.presentation.viewmodels.factories;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.domain.cart.SectionForCart;
import ca.ulaval.glo4003.presentation.viewmodels.SectionForCartViewModel;
import ca.ulaval.glo4003.utilities.Calculator;
import ca.ulaval.glo4003.utilities.Constants;

@Component
public class SectionForCartViewModelFactory {
	
	@Inject
	private Constants constants;
	
	public SectionForCartViewModel createViewModel(SectionForCart sectionFC) {
		SectionForCartViewModel sectionForCartVM = new SectionForCartViewModel();
		
		sectionForCartVM.setGeneralAdmission(sectionFC.getGeneralAdmission());
		
		sectionForCartVM.setNumberOfTicketsToBuy(sectionFC.getNumberOfTicketsToBuy());
		sectionForCartVM.setSelectedSeats(toString(sectionFC.getSelectedSeats()));
		
		sectionForCartVM.setSportName(sectionFC.getSportName());
		sectionForCartVM.setGameDate(constants.toLongDateTimeFormatFR(sectionFC.getGameDate()));
		sectionForCartVM.setSectionName(sectionFC.getSectionName());
		
		sectionForCartVM.setOpponents(sectionFC.getOpponents());
		sectionForCartVM.setLocation(sectionFC.getLocation());
		sectionForCartVM.setSubtotal(Calculator.toPriceFR(sectionFC.getSubtotal()));
		
		return sectionForCartVM;
	}

	private String toString(Set<String> selectedSeats) {
		String result = "";
		List<String> seats = new ArrayList<>(selectedSeats);
		if (seats.size() == 0) {
			return "Liste de sièges vide";
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
