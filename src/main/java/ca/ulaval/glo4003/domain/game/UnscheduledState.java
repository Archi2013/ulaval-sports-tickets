package ca.ulaval.glo4003.domain.game;

import org.joda.time.DateTime;

import ca.ulaval.glo4003.domain.dtos.GameDto;
import ca.ulaval.glo4003.domain.tickets.Ticket;

public class UnscheduledState implements GameScheduleState {

	@Override
	public boolean isSchedulable() {
		return true;
	}

	@Override
	public GameScheduleState assign(String sport, DateTime gameDate) {
		return new ScheduledState(sport, gameDate);
	}

	@Override
	public void assignThisTicketToSchedule(Ticket ticket, int ticketNumber) {

	}

	@Override
	public void saveTheScheduleInThisDto(GameDto dto) {

	}

}
