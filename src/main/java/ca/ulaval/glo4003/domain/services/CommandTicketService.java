package ca.ulaval.glo4003.domain.services;

import java.util.List;

import javax.inject.Inject;

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
import ca.ulaval.glo4003.persistence.daos.TicketAlreadyExistException;
import ca.ulaval.glo4003.persistence.daos.TicketDoesntExistException;

@Service
public class CommandTicketService {
	@Inject
	private ITicketRepository ticketRepository;

	@Inject
	private IGameRepository gameRepository;

	public void addGeneralTickets(String sportName, DateTime gameDate, int numberOfTickets) throws GameDoesntExistException,
			GameAlreadyExistException, TicketAlreadyExistException, TicketDoesntExistException {
		Game game = gameRepository.recoverGame(sportName, gameDate);

		for (int i = 0; i < numberOfTickets; i++) {
			game.addTicket(ticketRepository.instantiateNewTicket());
		}

		gameRepository.commit();
	}

	public void addSeatedTicket(String sport, DateTime date, String section, String seat)
			throws GameDoesntExistException, GameAlreadyExistException, TicketAlreadyExistException,
			TicketDoesntExistException {
		Game gameToUse = gameRepository.recoverGame(sport, date);
		gameToUse.addTicket(ticketRepository.instantiateNewTicket(section, seat, true));
		gameRepository.commit();
	}

	public void makeTicketsUnavailable(GameDto game, SectionDto section, int numberOfSeats, List<String> seats)
			throws GameDoesntExistException, GameAlreadyExistException, TicketAlreadyExistException,
			TicketDoesntExistException {

		if (section.isGeneralAdmission()) {
			List<Ticket> tickets = ticketRepository.recoverNGeneralTickets(game.getId(), numberOfSeats);
			for (Ticket ticket : tickets) {
				ticket.makeUnavailable();
			}
		} else {
			for (String seat : seats) {
				Ticket ticket = ticketRepository.recoverTicket(game.getSportName(), game.getGameDate(), seat);
				ticket.makeUnavailable();
			}
		}

		ticketRepository.commit();

	}

}
