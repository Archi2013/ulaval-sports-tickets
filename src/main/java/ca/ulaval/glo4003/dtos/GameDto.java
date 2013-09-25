package ca.ulaval.glo4003.dtos;

import java.util.ArrayList;
import java.util.List;

public class GameDto {
	private long id;
	private List<TicketDto> tickets;

	public GameDto(long id) {
		this.id = id;
		this.tickets = new ArrayList<TicketDto>();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<TicketDto> getTickets() {
		return tickets;
	}

	public int getNumberOfTickets() {
		return tickets.size();
	}
}
