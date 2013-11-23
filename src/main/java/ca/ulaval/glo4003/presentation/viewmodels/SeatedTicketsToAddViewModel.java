package ca.ulaval.glo4003.presentation.viewmodels;

import ca.ulaval.glo4003.utilities.time.DisplayDate;

public class SeatedTicketsToAddViewModel {
	DisplayDate gameDate;
	String section;
	String seat;
	String sportName;
	String price;

	public DisplayDate getGameDate() {
		return gameDate;
	}

	public void setGameDate(DisplayDate gameDate) {
		this.gameDate = gameDate;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getSeat() {
		return seat;
	}

	public void setSeat(String seat) {
		this.seat = seat;
	}

	public String getSportName() {
		return sportName;
	}

	public void setSportName(String sportName) {
		this.sportName = sportName;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

}
