package ca.ulaval.glo4003.domain.tickets;

import org.joda.time.DateTime;

public class SeatedTicketDto extends TicketDto {

	public SeatedTicketDto(Long ticketId, String sportName, DateTime gameDate, String section, String seat, double price,
			boolean available) {
		super(ticketId, sportName, gameDate, section, seat, price, available);
	}

	public SeatedTicketDto(String section, String seat, double price, boolean available) {
		super(null, null, section, seat, price, available);
	}

	@Override
	public boolean isGeneral() {
		return false;
	}
}
