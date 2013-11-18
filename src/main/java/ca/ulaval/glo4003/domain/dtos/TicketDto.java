package ca.ulaval.glo4003.domain.dtos;

import org.joda.time.DateTime;

public class TicketDto {

	public long gameId;
	public long ticketId;
	public double price;
	public String section;
	public String seat;
	public String sportName;
	public DateTime gameDate;
	public boolean available;
	
	public TicketDto(String sportName, DateTime gameDate, String section, String seat, double price,
            boolean available) {
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

	@Deprecated
	public TicketDto(String sportName, String section, DateTime gameDate, long ticketId, double price, boolean available) {
		this.sportName = sportName;
		this.section = section;
		this.gameDate = gameDate;
		this.ticketId = ticketId;
		this.price = price;
		this.available = available;
	}

	@Deprecated
	public TicketDto(String sportName, DateTime gameDate, long ticketId, double price, String seat, String section,
			boolean available) {
		this.sportName = sportName;

		this.gameDate = gameDate;
		this.ticketId = ticketId;
		this.price = price;
		this.section = section;
		this.seat = seat;
		this.available = available;
	}

	@Deprecated
	public TicketDto(long gameId, int ticketId, double price, String section, String seat, boolean available) {
		this.ticketId = ticketId;
		this.price = price;
		this.section = section;
		this.gameId = gameId;
		this.seat = seat;
		this.available = available;
	}

	@Deprecated
	public TicketDto(long gameId, int ticketId, double price, boolean available) {
		this.ticketId = ticketId;
		this.price = price;
		this.gameId = gameId;
		this.available = available;
	}

	public long getTicketId() {
		return ticketId;
	}

	public void setID(int ticketID) {
		this.ticketId = ticketID;
	}

	public double getPrice() {
		return price;
	}

	public String getSection() {
		return section;
	}

	public long getGameId() {
		return gameId;
	}

	public String getSeat() {
		return seat;
	}

	public String getSportName() {
		return sportName;
	}

	public DateTime getGameDate() {
		return gameDate;
	}

	public boolean isAvailable() {
		return available;
	}

	@Deprecated
	public void setAvailable(boolean available) {
		this.available = available;
	}
	
	public boolean isGeneralSection() {
	    return "Générale".equals(section);
    }
}
