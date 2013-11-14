package ca.ulaval.glo4003.domain.factories;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.domain.dtos.GameDto;
import ca.ulaval.glo4003.domain.game.GameScheduleState;
import ca.ulaval.glo4003.domain.game.ScheduledState;
import ca.ulaval.glo4003.domain.game.UnscheduledState;
import ca.ulaval.glo4003.domain.pojos.persistable.PersistableGame;
import ca.ulaval.glo4003.domain.tickets.Ticket;

@Component
public class GameFactory implements IGameFactory {

	@Override
	public PersistableGame instantiateGame(String opponents, String location) {
		return instantiateGame(new GameDto(0, opponents, null, null, location), new ArrayList<Ticket>());
	}

	// @Override
	// public PersistableGame instantiateGame(String opponents, DateTime
	// gameDate, String location, List<Ticket> tickets) {
	// TODO Auto-generated method stub
	// return null;
	// }

	@Override
	public PersistableGame instantiateGame(GameDto data, List<Ticket> tickets) {
		return new PersistableGame(data.getId(), data.getOpponents(), data.getLocation(), data.getNextTicketNumber(),
				createAssignationState(data), tickets);
	}

	private GameScheduleState createAssignationState(GameDto data) {
		if (data.getSportName() == null || data.getGameDate() == null) {
			return new UnscheduledState();
		}
		return new ScheduledState(data.getSportName(), data.getGameDate());
	}

}
