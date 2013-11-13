package ca.ulaval.glo4003.presentation.viewmodels;

public class GeneralTicketsToAddViewModel {
	int numberOfTickets;
	String gameDate;
	String sportName;

	public int getNumberOfTickets() {
		return numberOfTickets;
	}

	public void setNumberOfTickets(int numberOfTickets) {
		this.numberOfTickets = numberOfTickets;
	}

	public String getGameDate() {
		return gameDate;
	}

	public void setGameDate(String gameDate) {
		this.gameDate = gameDate;
	}

	public String getSportName() {
		return sportName;
	}

	public void setSportName(String sportname) {
		this.sportName = sportname;
	}

}
