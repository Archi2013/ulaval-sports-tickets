package ca.ulaval.glo4003.data_access.fake;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Repository;

import ca.ulaval.glo4003.data_access.GameDoesntExistException;
import ca.ulaval.glo4003.data_access.TicketDao;
import ca.ulaval.glo4003.data_access.TicketDoesntExistException;
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
			for (GameDto game : sport.getGames()) {
				if (gameId == game.getId()) {
					return game.getTickets();
				}
			}
		}
		throw new GameDoesntExistException();
	}

	@Override
	public TicketDto getTicket(int ticketId) throws TicketDoesntExistException {
		List<SportDto> sports = database.getSports();
		for (SportDto sport : sports) {
			for (GameDto game : sport.getGames()) {
				for (TicketDto ticket : game.getTickets()) {
					if (ticketId == ticket.getTicketId()) {
						return ticket;
					}
				}
			}
		}
		throw new TicketDoesntExistException();
	}
}
