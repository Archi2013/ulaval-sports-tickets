package ca.ulaval.glo4003.presentation.viewmodels;

import org.joda.time.DateTime;

import ca.ulaval.glo4003.utilities.time.UrlDateTime;

public class GameViewModel {
	public DateTime gameDate;
	public String opponents;
	public String location;
	public String date;
	public Integer numberOfTickets;
	public UrlDateTime urlDateTime;

	public GameViewModel(DateTime gameDate, String opponents, String location,
			String date, UrlDateTime urlDateTime, Integer numberOfTickets) {
		this.gameDate = gameDate;
		this.opponents = opponents;
		this.location = location;
		this.date = date;
		this.numberOfTickets = numberOfTickets;
		this.urlDateTime = urlDateTime;
	}

	public DateTime getGameDate() {
		return gameDate;
	}

	public void setGameDate(DateTime gameDate) {
		this.gameDate = gameDate;
	}
	
	public String getOpponents() {
		return opponents;
	}

	public void setOpponents(String opponents) {
		this.opponents = opponents;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Integer getNumberOfTickets() {
		return numberOfTickets;
	}

	public void setNumberOfTickets(Integer numberOfTickets) {
		this.numberOfTickets = numberOfTickets;
	}

	public UrlDateTime getUrlDateTime() {
		return urlDateTime;
	}

	public void setUrlDateTime(UrlDateTime urlDateTime) {
		this.urlDateTime = urlDateTime;
	}

}
