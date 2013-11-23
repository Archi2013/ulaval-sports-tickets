package ca.ulaval.glo4003.presentation.viewmodels;

import ca.ulaval.glo4003.utilities.time.InputDate;

public class GameToAddViewModel {
	String sport;
	String opponents;
	String location;
	InputDate date;

	public String getSport() {
		return sport;
	}

	public void setSport(String sport) {
		this.sport = sport;
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

	public InputDate getDate() {
		return date;
	}

	public void setDate(InputDate date) {
		this.date = date;
	}
}
