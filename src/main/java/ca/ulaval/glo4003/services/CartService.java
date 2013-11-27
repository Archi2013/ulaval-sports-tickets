package ca.ulaval.glo4003.services;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import ca.ulaval.glo4003.domain.game.GameDto;
import ca.ulaval.glo4003.domain.payment.Cart;
import ca.ulaval.glo4003.domain.sections.SectionDto;
import ca.ulaval.glo4003.exceptions.GameAlreadyExistException;
import ca.ulaval.glo4003.exceptions.GameDoesntExistException;
import ca.ulaval.glo4003.exceptions.SportDoesntExistException;
import ca.ulaval.glo4003.exceptions.TicketAlreadyExistsException;
import ca.ulaval.glo4003.exceptions.TicketDoesntExistException;

@Service
public class CartService {

	@Inject
	private CommandTicketService ticketService;

	public void makeTicketsUnavailableToOtherPeople(Cart cart) {
		try {
			GameDto game = cart.getGameDto();
			SectionDto section = cart.getSectionDto();
			List<String> seats = cart.getSelectedSeats();
			int numberOfSeats = cart.getNumberOfTicketsToBuy();

			ticketService.makeTicketsUnavailable(game, section, numberOfSeats, seats);

		} catch (GameDoesntExistException | GameAlreadyExistException | TicketAlreadyExistsException | TicketDoesntExistException
				| SportDoesntExistException e) {
			throw new CartException();
		}
	}
}
