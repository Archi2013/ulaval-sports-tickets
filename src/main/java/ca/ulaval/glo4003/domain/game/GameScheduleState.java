package ca.ulaval.glo4003.domain.game;

import org.joda.time.DateTime;

import ca.ulaval.glo4003.domain.dtos.GameDto;
import ca.ulaval.glo4003.domain.tickets.Ticket;

public interface GameScheduleState {

	boolean isSchedulable();

	GameScheduleState assign(String sport, DateTime gameDate);

	void assignThisTicketToSchedule(Ticket ticket, int ticketNumber);

	void saveTheScheduleInThisDto(GameDto dto);
}
