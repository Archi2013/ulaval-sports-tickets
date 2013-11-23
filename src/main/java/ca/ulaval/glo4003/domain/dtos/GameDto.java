package ca.ulaval.glo4003.domain.dtos;

import org.joda.time.DateTime;

public class GameDto {

	private String sportName;
	private Long id;
	private String opponents;
	private DateTime gameDate;
	private String location;
	private int numberOfTickets;
	private long nextTicketNumber;

	public GameDto(long id, String opponents, DateTime gameDate, String sportName, String location) {
		this(new Long(id), opponents, gameDate, sportName, location);
	}

	public GameDto(Long id, String opponents, DateTime gameDate, String sportName, String location) {
		this.sportName = sportName;
		this.id = id;
		this.opponents = opponents;
		this.gameDate = gameDate;
		this.location = location;
	}

	public GameDto(Long id, String opponents, DateTime gameDate, String sportName, String location,
			long nextTicketNumber) {
		this(id, opponents, gameDate, sportName, location);
		this.nextTicketNumber = nextTicketNumber;
	}

	public String getSportName() {
		return sportName;
	}

	public void setSportName(String sportName) {
		this.sportName = sportName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
