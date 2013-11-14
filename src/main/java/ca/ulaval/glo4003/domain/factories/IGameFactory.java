package ca.ulaval.glo4003.domain.factories;

import java.util.List;

import ca.ulaval.glo4003.domain.dtos.GameDto;
import ca.ulaval.glo4003.domain.pojos.persistable.PersistableGame;
import ca.ulaval.glo4003.domain.tickets.Ticket;

public interface IGameFactory {

	PersistableGame instantiateGame(String opponents, String location);

	// PersistableGame instantiateGame(String opponents, DateTime gameDate,
	// String location, List<Ticket> tickets);

	PersistableGame instantiateGame(GameDto data, List<Ticket> tickets);
}
