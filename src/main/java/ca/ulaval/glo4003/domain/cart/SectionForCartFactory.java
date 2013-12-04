package ca.ulaval.glo4003.domain.cart;

import java.util.Set;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

@Component
public class SectionForCartFactory {

	public SectionForCart createSectionForGeneralTickets(String sportName,
			DateTime gameDate, String sectionName, String opponents,
			String location, Integer numberOfTicketsToBuy, double unitPrice) {
		return new SectionForCart(sportName, gameDate, sectionName,
				opponents, location,
				numberOfTicketsToBuy, unitPrice);
	}

	public SectionForCart createSectionForWithSeatTickets(String sportName,
			DateTime gameDate, String sectionName, String opponents,
			String location, Set<String> selectedSeats, double unitPrice) {
		return new SectionForCart(sportName, gameDate, sectionName,
				opponents, location,
				selectedSeats, unitPrice);
	}

}
