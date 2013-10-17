package ca.ulaval.glo4003.domain.dtos;

import org.joda.time.DateTime;

public class GameDto {

	private String sportName;
	private long id;
	private String opponents;
	private DateTime gameDate;
	private int numberOfTickets;

	public GameDto(long id, String opponents, DateTime gameDate, String sportName) {
		this.sportName = sportName;
		this.id = id;
		this.opponents = opponents;
		this.gameDate = gameDate;
	}

	public String getSportName() {
		return sportName;
	}

	public void setSportName(String sportName) {
		this.sportName = sportName;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
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
}
