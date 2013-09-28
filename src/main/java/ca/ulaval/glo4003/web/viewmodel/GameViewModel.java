package ca.ulaval.glo4003.web.viewmodel;

import java.util.List;


public class GameViewModel {
	private Long id;
	private String opponents;
	private String date;
	private List<TicketViewModel> tickets;
	
	public GameViewModel(Long id, String opponents, String date, List<TicketViewModel> tickets) {
		this.id = id;
		this.opponents = opponents;
		this.date = date;
		this.tickets = tickets;
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

	public List<TicketViewModel> getTickets() {
		return tickets;
	}

	public void setTickets(List<TicketViewModel> tickets) {
		this.tickets = tickets;
	}
	
	
}
