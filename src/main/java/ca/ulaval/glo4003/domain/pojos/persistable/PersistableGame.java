package ca.ulaval.glo4003.domain.pojos.persistable;

import java.util.List;

import org.joda.time.DateTime;

import ca.ulaval.glo4003.domain.dtos.GameDto;
import ca.ulaval.glo4003.domain.game.GameScheduleState;
import ca.ulaval.glo4003.domain.pojos.Game;
import ca.ulaval.glo4003.domain.tickets.Ticket;

public class PersistableGame implements Game, Persistable<GameDto> {
	public static final String NO_SPORT_SET = "The sport has not yet been set in this new game";
	public static final String NO_LOCATION_SET = "The location has not yet been set in this new game";
	private Long id;
	private String opponents;
	private String location;
	private List<Ticket> tickets;
	private GameScheduleState assignationState;

	public PersistableGame(Long id, String opponents, String location, GameScheduleState assignationState,
			List<Ticket> tickets) {
		this.id = id;
		this.opponents = opponents;
		this.location = location;
		this.assignationState = assignationState;
		this.tickets = tickets;
	}

	@Override
	public GameDto saveDataInDTO() {
		GameDto dto = new GameDto(id, opponents, null, null, location);
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
			assignationState.assignThisTicketToSchedule(ticketToAdd, tickets.size());
			tickets.add(ticketToAdd);
		}
	}

	@Override
	public void removeTicket(Ticket ticket) {
		tickets.remove(ticket);
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
