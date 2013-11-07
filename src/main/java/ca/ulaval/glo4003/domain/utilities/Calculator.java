package ca.ulaval.glo4003.domain.utilities;

import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.domain.dtos.SectionDto;
import ca.ulaval.glo4003.web.viewmodels.ChooseTicketsViewModel;

@Component
public class Calculator {

	public Double calculateCumulativePrice(
			ChooseTicketsViewModel chooseTicketsVM, SectionDto sectionDto) {
		Double cumulatedPrice = 0.0;
		
		if (sectionDto.isGeneralAdmission()) {
			cumulatedPrice = chooseTicketsVM.getNumberOfTicketsToBuy() * sectionDto.getPrice();
		} else {
			cumulatedPrice = chooseTicketsVM.getSelectedSeats().size() * sectionDto.getPrice();
		}
		return cumulatedPrice;
	}

	public String toPriceFR(Double number) {
		return String.format("%10.2f", number).replace(".", ",");
	}
}
