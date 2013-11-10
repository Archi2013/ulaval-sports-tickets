package ca.ulaval.glo4003.domain.services;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import ca.ulaval.glo4003.domain.dtos.GameDto;
import ca.ulaval.glo4003.domain.dtos.SectionDto;
import ca.ulaval.glo4003.domain.pojos.Game;
import ca.ulaval.glo4003.domain.repositories.IGameRepository;
import ca.ulaval.glo4003.domain.repositories.ITicketRepository;
import ca.ulaval.glo4003.domain.tickets.Ticket;
import ca.ulaval.glo4003.persistence.daos.GameAlreadyExistException;
import ca.ulaval.glo4003.persistence.daos.GameDoesntExistException;

@Service
public class CommandTicketService {
	private ITicketRepository ticketRepository;
	private IGameRepository gameRepository;

	// TODO est là pour corriger le bogue
	public CommandTicketService() {

	}

	public CommandTicketService(ITicketRepository ticketRepository, IGameRepository gameRepository) {
		this.ticketRepository = ticketRepository;
		this.gameRepository = gameRepository;
	}

	public void addGeneralTickets(String sport, DateTime date, int numberOfTickets) throws GameDoesntExistException,
			GameAlreadyExistException {
		Game game = gameRepository.recoverGame(sport, date);

		for (int i = 0; i < numberOfTickets; i++) {
			game.addTicket(ticketRepository.instantiateNewTicket());
		}

		gameRepository.commit();
	}

	public void addSeatedTicket(String sport, DateTime date, String section, String seat) throws GameDoesntExistException,
			GameAlreadyExistException {
		Game gameToUse = gameRepository.recoverGame(sport, date);
		gameToUse.addTicket(ticketRepository.instantiateNewTicket(section, seat));
		gameRepository.commit();
	}

	public void makeTicketsUnavailable(GameDto game, SectionDto section, List<String> seats) throws GameDoesntExistException,
			GameAlreadyExistException {
		Game gameToUse = gameRepository.recoverGame(game.getSportName(), game.getGameDate());
		for (String seat : seats) {
			Ticket ticket = ticketRepository.recoverTicket(game.getSportName(), game.getGameDate(), seat);
			gameToUse.removeTicket(ticket);
		}
		gameRepository.commit();

	}
}
