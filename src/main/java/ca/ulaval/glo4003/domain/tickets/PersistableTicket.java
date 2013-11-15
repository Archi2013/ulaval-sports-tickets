package ca.ulaval.glo4003.domain.tickets;

import org.joda.time.DateTime;

import ca.ulaval.glo4003.domain.dtos.TicketDto;
import ca.ulaval.glo4003.domain.pojos.persistable.Persistable;
import ca.ulaval.glo4003.domain.tickets.state.TicketAssignationState;

public abstract class PersistableTicket extends Ticket implements Persistable<TicketDto> {

	private TicketAssignationState associationState;
	public double price;
	public String section;
	public String seat;
	public String sportName;
	public DateTime gameDate;
	public int ticketNumber;
	public long gameId;

	public long getGameId() {
		return gameId;
	}

	public void setGameId(long gameId) {
		this.gameId = gameId;
	}

	public PersistableTicket(TicketAssignationState associationState) {
		this.associationState = associationState;
		this.available = true;
	}

	@Override
	public boolean isAssignable() {
		return associationState.isAssignable();
	}

	@Override
	public void assign(String sport, DateTime date, int ticketNumber) {
		associationState = associationState.assign(sport, date, ticketNumber);
		this.sportName = sport;
		this.gameDate = date;
		this.ticketNumber = ticketNumber;
	}

	@Override
	public TicketDto saveDataInDTO() {
		TicketDto dto = new TicketDto(sportName, gameDate, ticketNumber, price, seat, section, available);
		dto.gameId = gameId;
		return dto;
	}
}
