package ca.ulaval.glo4003.domain.cart;

import java.util.Set;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

@Component
public class SectionForCartFactory {

	public SectionForCart createSectionForGeneralTickets(String sportName,
			DateTime gameDate, String sectionName, String opponents,
			String location, Integer numberOfTicketsToBuy, double price) {
		return new SectionForCart(sportName, gameDate, sectionName,
				opponents, location,
				numberOfTicketsToBuy, price);
	}

	public SectionForCart createSectionForWithSeatTickets(String sportName,
			DateTime gameDate, String sectionName, String opponents,
			String location, Set<String> selectedSeats, double price) {
		return new SectionForCart(sportName, gameDate, sectionName,
				opponents, location,
				selectedSeats, price);
	}

}
