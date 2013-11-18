package ca.ulaval.glo4003.domain.dtos;

import org.joda.time.DateTime;

public class SeatedTicketDto extends TicketDto{

	public SeatedTicketDto(Long ticketId, String sportName, DateTime gameDate, String section, String seat, double price,
	        boolean available) {
		super(ticketId, sportName, gameDate, section, seat, price, available);
	}

	public SeatedTicketDto(String sportName, DateTime gameDate, String section, String seat, double price, boolean available) {
		super(sportName, gameDate, section, seat, price, available);
	}

	@Override
    public boolean isGeneralSection() {
	    return false;
    }
}
