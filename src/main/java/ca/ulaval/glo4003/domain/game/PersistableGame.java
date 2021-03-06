package ca.ulaval.glo4003.domain.game;

import java.util.List;

import org.joda.time.DateTime;

import ca.ulaval.glo4003.domain.tickets.Ticket;
import ca.ulaval.glo4003.game.dto.GameDto;
import ca.ulaval.glo4003.utilities.persistence.Persistable;

public class PersistableGame implements Game, Persistable<GameDto> {
	public static final String NO_SPORT_SET = "The sport has not yet been set in this new game";
	public static final String NO_LOCATION_SET = "The location has not yet been set in this new game";
	private String opponents;
	private String location;
	private List<Ticket> tickets;
	private GameScheduleState assignationState;
	private long nextTicketNumber;

	public PersistableGame(String opponents, String location, long nextTicketNumber,
			GameScheduleState assignationState, List<Ticket> tickets) {
		this.opponents = opponents;
		this.location = location;
		this.assignationState = assignationState;
		this.nextTicketNumber = nextTicketNumber;
		this.tickets = tickets;
	}

	@Override
	public GameDto saveDataInDTO() {
		GameDto dto = new GameDto(opponents, null, null, location);
		assignationState.saveTheScheduleInThisDto(dto);
		return dto;
	}

	@Override
	public boolean acceptsToBeScheduled() {
		return assignationState.isSchedulable();
	}

	@Override
	public void beScheduledToThisSport(String newSportName, DateTime gameDate) {
		if (acceptsToBeScheduled()) {
			assignationState = assignationState.assign(newSportName, gameDate);
		}

	}

	@Override
	public void addTicket(Ticket ticketToAdd) {
		if (!alreadyInTicketList(ticketToAdd) && ticketToAdd.isAssignable()) {
			assignationState.assignThisTicketToSchedule(ticketToAdd, nextTicketNumber);
			nextTicketNumber++;
			tickets.add(ticketToAdd);
		}
	}

	private boolean alreadyInTicketList(Ticket ticketToAdd) {
		for (Ticket ticket : tickets) {
			if (ticket.isSame(ticketToAdd)) {
				return true;
			}
		}
		return false;
	}

}
