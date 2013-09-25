package ca.ulaval.glo4003.data_access.fake;

import java.util.List;

import javax.inject.Inject;

import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import ca.ulaval.glo4003.data_access.GameDoesntExistException;
import ca.ulaval.glo4003.data_access.TicketDao;
import ca.ulaval.glo4003.dtos.GameDto;
import ca.ulaval.glo4003.dtos.SportDto;
import ca.ulaval.glo4003.dtos.TicketDto;

@Repository
public class FakeDataTicketDao implements TicketDao {

	@Inject
	private FakeDatabase database;

	@Override
	public List<TicketDto> getTicketsForGame(int gameId) throws GameDoesntExistException {
		List<SportDto> sports = database.getSports();
		for (SportDto sport : sports) {
			List<GameDto> games = sport.getGames();
			for (GameDto game : games) {
				if (gameId == game.getId()) {
					return game.getTickets();
				}
			}
		}
		throw new GameDoesntExistException();
	}

	@Override
	public TicketDto getTicket(int ticketId) {
		TicketDto ticket = new TicketDto(1, 26.95, "Pharetra", new DateTime(2013, 9, 29, 18, 30), "Général", "Rouge");
		return ticket;
	}
}
