package ca.ulaval.glo4003.services;

import java.util.List;

import javax.inject.Inject;

import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import ca.ulaval.glo4003.domain.game.Game;
import ca.ulaval.glo4003.domain.game.GameDto;
import ca.ulaval.glo4003.domain.game.IGameRepository;
import ca.ulaval.glo4003.domain.sections.SectionDto;
import ca.ulaval.glo4003.domain.tickets.ITicketRepository;
import ca.ulaval.glo4003.domain.tickets.Ticket;
import ca.ulaval.glo4003.exceptions.GameAlreadyExistException;
import ca.ulaval.glo4003.exceptions.GameDoesntExistException;
import ca.ulaval.glo4003.exceptions.SportDoesntExistException;
import ca.ulaval.glo4003.exceptions.TicketAlreadyExistsException;
import ca.ulaval.glo4003.exceptions.TicketDoesntExistException;

@Service
public class CommandTicketService {
	@Inject
	private ITicketRepository ticketRepository;

	@Inject
	private IGameRepository gameRepository;

	public void addGeneralTickets(String sportName, DateTime gameDate, int numberOfTickets, double price)
			throws GameDoesntExistException, GameAlreadyExistException, TicketAlreadyExistsException, TicketDoesntExistException,
			SportDoesntExistException {
		Game game = gameRepository.get(sportName, gameDate);

		for (int i = 0; i < numberOfTickets; i++) {
			game.addTicket(ticketRepository.createGeneralTicket(price, true));
		}

		gameRepository.commit();
		gameRepository.clearCache();
	}

	public void addSeatedTicket(String sport, DateTime date, String section, String seat, double price)
			throws GameDoesntExistException, GameAlreadyExistException, TicketAlreadyExistsException, TicketDoesntExistException,
			SportDoesntExistException {
		Game gameToUse = gameRepository.get(sport, date);
		gameToUse.addTicket(ticketRepository.createSeatedTicket(seat, section, price, true));
		gameRepository.commit();
		gameRepository.clearCache();
	}

	public void makeTicketsUnavailable(GameDto game, SectionDto section, int numberOfSeats, List<String> seats)
			throws GameDoesntExistException, GameAlreadyExistException, TicketAlreadyExistsException, TicketDoesntExistException,
			SportDoesntExistException {

		if (section.isGeneralAdmission()) {
			List<Ticket> tickets = ticketRepository
					.recoverNGeneralTickets(game.getSportName(), game.getGameDate(), numberOfSeats);
			for (Ticket ticket : tickets) {
				ticket.makeUnavailable();
			}
		} else {
			for (String seat : seats) {
				Ticket ticket = ticketRepository.get(game.getSportName(), game.getGameDate(), section.getSectionName(), seat);
				ticket.makeUnavailable();
			}
		}

		ticketRepository.commit();
		ticketRepository.clearCache();

	}

}
