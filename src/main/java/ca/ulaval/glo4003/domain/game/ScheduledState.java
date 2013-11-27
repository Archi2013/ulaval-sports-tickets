package ca.ulaval.glo4003.domain.game;

import org.joda.time.DateTime;

import ca.ulaval.glo4003.domain.tickets.Ticket;

public class ScheduledState implements GameScheduleState {

	private String sport;
	private DateTime gameDate;

	public ScheduledState(String sport, DateTime gameDate) {
		this.sport = sport;
		this.gameDate = gameDate;
	}

	@Override
	public boolean isSchedulable() {
		return false;
	}

	@Override
	public GameScheduleState assign(String sport, DateTime gameDate) {
		return this;
	}

	@Override
	public void assignThisTicketToSchedule(Ticket ticket, long ticketNumber) {
		ticket.assign(sport, gameDate, ticketNumber);

	}

	@Override
	public void saveTheScheduleInThisDto(GameDto dto) {
		dto.setSportName(sport);
		dto.setGameDate(gameDate);

	}

}
