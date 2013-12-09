package ca.ulaval.glo4003.domain.game;

import java.util.List;

import ca.ulaval.glo4003.domain.tickets.Ticket;
import ca.ulaval.glo4003.game.dto.GameDto;

public interface IGameFactory {

	PersistableGame instantiateGame(String opponents, String location);
	PersistableGame instantiateGame(GameDto data, List<Ticket> tickets);
}
