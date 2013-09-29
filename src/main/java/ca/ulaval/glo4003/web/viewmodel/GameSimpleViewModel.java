package ca.ulaval.glo4003.web.viewmodel;


public class GameSimpleViewModel {
	public Long id;
	public String opponents;
	public String date;
	public Integer numberOfTickets;
	
	public GameSimpleViewModel(Long id, String opponents, String date,
			Integer numberOfTickets) {
		this.id = id;
		this.opponents = opponents;
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
