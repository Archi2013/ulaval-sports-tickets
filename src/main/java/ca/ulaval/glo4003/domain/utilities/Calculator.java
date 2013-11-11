package ca.ulaval.glo4003.domain.utilities;

import java.util.List;

import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.domain.dtos.SectionDto;

@Component
public class Calculator {

	public Double calculateCumulativePriceForGeneralAdmission(Integer numberOfTicketsToBuy, Double price) {
		return numberOfTicketsToBuy * price;
	}

	public Double calculateCumulativePriceForWithSeatAdmission(List<String> selectedSeats, Double price) {
		return selectedSeats.size() * price;
	}
	
	public String toPriceFR(Double number) {
		return String.format("%10.2f", number).replace(".", ",");
	}
}
