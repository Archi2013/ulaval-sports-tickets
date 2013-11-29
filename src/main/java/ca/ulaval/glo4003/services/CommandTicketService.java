package ca.ulaval.glo4003.services;

import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import ca.ulaval.glo4003.domain.cart.SectionForCart;
import ca.ulaval.glo4003.domain.game.Game;
import ca.ulaval.glo4003.domain.game.IGameRepository;
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

	public void makeTicketsUnavailable(Set<SectionForCart> sections) throws GameDoesntExistException, TicketDoesntExistException, SportDoesntExistException, GameAlreadyExistException, TicketAlreadyExistsException {
		for(SectionForCart sectionFC : sections) {
			if (sectionFC.getGeneralAdmission()) {
				List<Ticket> tickets = ticketRepository
						.recoverNGeneralTickets(sectionFC.getSportName(), sectionFC.getGameDate(), sectionFC.getNumberOfTicketsToBuy());
				for (Ticket ticket : tickets) {
					ticket.makeUnavailable();
				}
			} else {
				for (String seat : sectionFC.getSelectedSeats()) {
					Ticket ticket = ticketRepository.get(sectionFC.getSportName(), sectionFC.getGameDate(), sectionFC.getSectionName(), seat);
					ticket.makeUnavailable();
				}
			}
		}
		
		ticketRepository.commit();
		ticketRepository.clearCache();
	}

}
