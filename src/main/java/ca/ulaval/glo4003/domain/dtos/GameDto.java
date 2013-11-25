package ca.ulaval.glo4003.domain.dtos;

import org.joda.time.DateTime;

public class GameDto {

	private String sportName;
	private String opponents;
	private DateTime gameDate;
	private String location;
	private int numberOfTickets;
	private long nextTicketNumber;

	public GameDto(String opponents, DateTime gameDate, String sportName, String location) {
		this.sportName = sportName;
		this.opponents = opponents;
		this.gameDate = gameDate;
		this.location = location;
	}

	public GameDto(String opponents, DateTime gameDate, String sportName, String location,
			long nextTicketNumber) {
		this(opponents, gameDate, sportName, location);
		this.nextTicketNumber = nextTicketNumber;
	}

	public String getSportName() {
		return sportName;
	}

	public void setSportName(String sportName) {
		this.sportName = sportName;
	}

	public int getNumberOfTickets() {
		return numberOfTickets;
	}

	public void setNumberOfTickets(int numberOfTickets) {
		this.numberOfTickets = numberOfTickets;
	}

	public String getOpponents() {
		return opponents;
	}

	public void setOpponents(String opponents) {
		this.opponents = opponents;
	}

	public DateTime getGameDate() {
		return gameDate;
	}

	public void setGameDate(DateTime gameDate) {
		this.gameDate = gameDate;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public long getNextTicketNumber() {
		return nextTicketNumber;
	}

	public void setNextTicketNumber(long nextTicketNumber) {
		this.nextTicketNumber = nextTicketNumber;
	}

}
