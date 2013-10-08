package ca.ulaval.glo4003.domain.dtos;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

public class GameDto {

	private long id;
	private String opponents;
	private DateTime gameDate;
	private List<TicketDto> tickets;

	public GameDto(long id, String opponents, DateTime gameDate) {
		this.id = id;
		this.opponents = opponents;
		this.gameDate = gameDate;
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
