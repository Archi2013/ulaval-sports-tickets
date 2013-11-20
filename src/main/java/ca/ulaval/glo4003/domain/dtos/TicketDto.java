package ca.ulaval.glo4003.domain.dtos;

import org.joda.time.DateTime;

public class TicketDto {

	public Long ticketId;
	public double price;
	public String section;
	public String seat;
	public String sportName;
	public DateTime gameDate;
	public boolean available;

	public TicketDto(String sportName, DateTime gameDate, String section, String seat, double price, boolean available) {
		this(null, sportName, gameDate, section, seat, price, available);
	}

	public TicketDto(Long ticketId, String sportName, DateTime gameDate, String section, String seat, double price,
			boolean available) {
		this.ticketId = ticketId;
		this.sportName = sportName;
		this.gameDate = gameDate;
		this.section = section;
		this.seat = seat;
		this.price = price;
		this.available = available;
	}

	public boolean isGeneralSection() {
		return "Générale".equals(section);
	}
}
