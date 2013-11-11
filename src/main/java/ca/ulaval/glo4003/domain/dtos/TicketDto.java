package ca.ulaval.glo4003.domain.dtos;

import org.joda.time.DateTime;

public class TicketDto {

	public long gameId;
	public int ticketId;
	public double price;
	public String section;
	public String seat;
	public String sportName;
	public DateTime gameDate;
	public int ticketNumber;

	public TicketDto(String sportName, DateTime gameDate, int ticketNumber, double price) {
		this.sportName = sportName;

		this.gameDate = gameDate;
		this.ticketNumber = ticketNumber;
		this.price = price;
	}
	
	public TicketDto(String sportName, DateTime gameDate, int ticketNumber, double price, String seat, String section) {
		this.sportName = sportName;

		this.gameDate = gameDate;
		this.ticketNumber = ticketNumber;
		this.price = price;
		this.section = section;
		this.seat = seat;
	}

	public TicketDto(long gameId, int ticketId, double price, String section, String seat) {
		this.ticketId = ticketId;
		this.price = price;
		this.section = section;
		this.gameId = gameId;
		this.seat = seat;
	}
	
	public TicketDto(long gameId, int ticketId, double price) {
		this.ticketId = ticketId;
		this.price = price;
		this.gameId = gameId;
	}

	public int getTicketId() {
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

	public int getTicketNumber() {
		return ticketNumber;
	}
}
