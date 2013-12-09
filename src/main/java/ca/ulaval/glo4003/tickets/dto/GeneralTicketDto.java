package ca.ulaval.glo4003.tickets.dto;

import org.joda.time.DateTime;

public class GeneralTicketDto extends TicketDto {

	public static final String GENERAL_SECTION = "Générale";
	public static final String GENERAL_SEAT = "Générale";

	public GeneralTicketDto(Long ticketId, String sportName, DateTime gameDate, double price, boolean available) {
		super(ticketId, sportName, gameDate, GENERAL_SECTION, GENERAL_SEAT, price, available);
	}

	public GeneralTicketDto(double price, boolean available) {
		super(null, null, GENERAL_SECTION, GENERAL_SEAT, price, available);
	}

	@Override
	public boolean isGeneral() {
		return true;
	}
}
