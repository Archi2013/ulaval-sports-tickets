package ca.ulaval.glo4003.web.viewmodels;

public class GameViewModel {
	public Long id;
	public String opponents;
	public String location;
	public String date;
	public Integer numberOfTickets;

	public GameViewModel(Long id, String opponents, String location, String date, Integer numberOfTickets) {
		this.id = id;
		this.opponents = opponents;
		this.location = location;
		this.date = date;
		this.numberOfTickets = numberOfTickets;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

}
