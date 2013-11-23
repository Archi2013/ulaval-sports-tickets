package ca.ulaval.glo4003.presentation.viewmodels;

import ca.ulaval.glo4003.utilities.time.DisplayDate;

public class GeneralTicketsToAddViewModel {
	int numberOfTickets;
	DisplayDate gameDate;
	String sportName;
	String price;

	public int getNumberOfTickets() {
		return numberOfTickets;
	}

	public void setNumberOfTickets(int numberOfTickets) {
		this.numberOfTickets = numberOfTickets;
	}

	public DisplayDate getGameDate() {
		return gameDate;
	}

	public void setGameDate(DisplayDate gameDate) {
		this.gameDate = gameDate;
	}

	public String getSportName() {
		return sportName;
	}

	public void setSportName(String sportname) {
		this.sportName = sportname;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

}
