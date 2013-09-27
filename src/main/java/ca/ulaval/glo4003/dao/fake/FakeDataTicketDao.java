package ca.ulaval.glo4003.dao.fake;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Repository;

import ca.ulaval.glo4003.dao.GameDoesntExistException;
import ca.ulaval.glo4003.dao.TicketDao;
import ca.ulaval.glo4003.dao.TicketDoesntExistException;
import ca.ulaval.glo4003.dto.GameDto;
import ca.ulaval.glo4003.dto.SportDto;
import ca.ulaval.glo4003.dto.TicketDto;

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
