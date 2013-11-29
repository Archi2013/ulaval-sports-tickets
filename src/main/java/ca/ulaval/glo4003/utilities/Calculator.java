package ca.ulaval.glo4003.utilities;

import java.util.Set;

import org.springframework.stereotype.Component;

@Component
public class Calculator {

	public Double calculateCumulativePriceForGeneralAdmission(Integer numberOfTicketsToBuy, Double price) {
		return numberOfTicketsToBuy * price;
	}

	public Double calculateCumulativePriceForWithSeatAdmission(Set<String> selectedSeats, Double price) {
		return selectedSeats.size() * price;
	}
	
	public String toPriceFR(Double number) {
		return String.format("%10.2f", number).replace(".", ",");
	}
}
