package ca.ulaval.glo4003.domain.pojos.persistable;

import java.util.List;

import org.joda.time.DateTime;

import ca.ulaval.glo4003.domain.dtos.GameDto;
import ca.ulaval.glo4003.domain.pojos.Game;
import ca.ulaval.glo4003.domain.tickets.Ticket;

public class PersistableGame implements Game, Persistable<GameDto> {
	public static final String NO_SPORT_SET = "The sport has not yet been set in this new game";
	public static final String NO_LOCATION_SET = "The location has not yet been set in this new game";
	public static final Long DEFAULT_ID = null;
	private Long id;
	private String opponents;
	private DateTime gameDate;
	private String sportName;
	private String location;
	private List<Ticket> tickets;

	public PersistableGame(long id, String opponents, DateTime gameDate, String sportName, String location) {
		this.id = id;
		this.opponents = opponents;
		this.gameDate = gameDate;
		this.sportName = sportName;
		this.location = location;
	}

	public PersistableGame(String opponents, DateTime gameDate) {
		this.id = DEFAULT_ID;
		this.opponents = opponents;
		this.gameDate = gameDate;
		this.location = NO_LOCATION_SET;
		this.sportName = NO_SPORT_SET;
	}

	public PersistableGame(Long id, String opponents, DateTime gameDate, String sportName, String location, List<Ticket> tickets) {
		this.id = id;
		this.opponents = opponents;
		this.gameDate = gameDate;
		this.sportName = sportName;
		this.location = location;
		this.tickets = tickets;
	}

	@Override
	public GameDto saveDataInDTO() {
		return new GameDto(id, opponents, gameDate, sportName, location);
	}

	@Override
	public boolean acceptsToBeScheduled() {
		if (sportName.equals(NO_SPORT_SET)) {
			return true;
		}
		return false;

	}

	@Override
	public void beScheduledToThisSport(String newSportName) {
		if (acceptsToBeScheduled()) {
			sportName = newSportName;
		}

	}

	@Override
	public void addTicket(Ticket ticketToAdd) {
		if (!alreadyInTicketList(ticketToAdd) && ticketToAdd.isAssignable()) {
			ticketToAdd.assign(sportName, gameDate, tickets.size());
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
