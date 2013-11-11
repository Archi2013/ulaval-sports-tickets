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

@Service
public class CartService {

	@Inject
	private CommandTicketService ticketService;

	public void makeTicketsUnavailableToOtherPeople(Cart cart) {
		try {
			GameDto game = cart.getGameDto();
			SectionDto section = cart.getSectionDto();
			List<String> seats = cart.getSelectedSeats();

			ticketService.makeTicketsUnavailable(game, section, seats);

		} catch (GameDoesntExistException | GameAlreadyExistException | TicketAlreadyExistException e) {
			// TODO Do nothing for now
		}
	}
}
