package ca.ulaval.glo4003.domain.factories;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.domain.pojos.persistable.PersistableGame;
import ca.ulaval.glo4003.domain.tickets.Ticket;

@Component
public class GameFactory implements IGameFactory {

	@Override
	public PersistableGame instantiateGame(String opponents, DateTime gameDate) {
		return new PersistableGame(opponents, gameDate);
	}

	@Override
	public PersistableGame instantiateGame(String opponents, DateTime gameDate, List<Ticket> tickets) {
		// TODO Auto-generated method stub
		return null;
	}

}
