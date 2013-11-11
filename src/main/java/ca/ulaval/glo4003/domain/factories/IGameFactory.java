package ca.ulaval.glo4003.domain.factories;

import java.util.List;

import org.joda.time.DateTime;

import ca.ulaval.glo4003.domain.pojos.persistable.PersistableGame;
import ca.ulaval.glo4003.domain.tickets.Ticket;

public interface IGameFactory {

	PersistableGame instantiateGame(String opponents, DateTime gameDate);

	PersistableGame instantiateGame(String opponents, DateTime gameDate, List<Ticket> tickets);
}
