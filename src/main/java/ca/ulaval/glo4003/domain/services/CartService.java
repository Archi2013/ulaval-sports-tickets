package ca.ulaval.glo4003.domain.services;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import ca.ulaval.glo4003.domain.dtos.GameDto;
import ca.ulaval.glo4003.domain.dtos.SectionDto;
import ca.ulaval.glo4003.domain.utilities.payment.Cart;
import ca.ulaval.glo4003.persistence.daos.GameAlreadyExistException;
import ca.ulaval.glo4003.persistence.daos.GameDoesntExistException;
import ca.ulaval.glo4003.persistence.daos.TicketAlreadyExistException;
import ca.ulaval.glo4003.persistence.daos.TicketDoesntExistException;

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

		} catch (GameDoesntExistException | GameAlreadyExistException | TicketAlreadyExistException | TicketDoesntExistException e) {
			throw new CartException();
		}
	}
}
