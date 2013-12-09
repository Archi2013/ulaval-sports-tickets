package ca.ulaval.glo4003.services;

import java.util.Set;

import javax.inject.Inject;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.ulaval.glo4003.domain.cart.Cart;
import ca.ulaval.glo4003.domain.cart.SectionForCart;
import ca.ulaval.glo4003.domain.cart.SectionForCartFactory;
import ca.ulaval.glo4003.domain.game.GameDao;
import ca.ulaval.glo4003.domain.sections.Section;
import ca.ulaval.glo4003.domain.sections.SectionDao;
import ca.ulaval.glo4003.domain.sections.SectionRepository;
import ca.ulaval.glo4003.exceptions.GameAlreadyExistException;
import ca.ulaval.glo4003.exceptions.GameDoesntExistException;
import ca.ulaval.glo4003.exceptions.SectionDoesntExistException;
import ca.ulaval.glo4003.exceptions.SportDoesntExistException;
import ca.ulaval.glo4003.exceptions.TicketAlreadyExistsException;
import ca.ulaval.glo4003.exceptions.TicketDoesntExistException;
import ca.ulaval.glo4003.game.dto.GameDto;
import ca.ulaval.glo4003.sections.dto.SectionDto;
import ca.ulaval.glo4003.services.exceptions.CartException;
import ca.ulaval.glo4003.services.exceptions.InvalidTicketsException;
import ca.ulaval.glo4003.services.exceptions.NoTicketsInCartException;
import ca.ulaval.glo4003.services.exceptions.TicketsNotFoundException;

@Service
public class CommandCartService {

	@Inject
	private GameDao gameDao;

	@Inject
	private SectionDao sectionDao;
	
	@Inject
	private SectionRepository sectionRepository;
	
	@Inject
	private CommandTicketService ticketService;
	
	@Autowired
	private Cart currentCart;
	
	@Inject
	private SectionForCartFactory sectionForCartFactory;

	public void addGeneralTicketsToCart(String sportName, DateTime gameDate, String sectionName,
			Integer numberOfTicketsToBuy) throws InvalidTicketsException, TicketsNotFoundException {
		try {
			if (!isValidGeneralTickets(sportName, gameDate, sectionName, numberOfTicketsToBuy)) {
				throw new InvalidTicketsException();
			}
			
			GameDto gameDto = gameDao.get(sportName, gameDate);
			SectionDto sectionDto = sectionDao.getAvailable(sportName, gameDate, sectionName);
			
			SectionForCart sectionForCart = sectionForCartFactory
					.createSectionForGeneralTickets(sportName, gameDate, sectionName,
							gameDto.getOpponents(), gameDto.getLocation(),
							numberOfTicketsToBuy, sectionDto.getPrice());
			currentCart.addSection(sectionForCart);
		} catch (SectionDoesntExistException | GameDoesntExistException e) {
			throw new TicketsNotFoundException();
		}
	}
	
	public void addWithSeatTicketsToCart(String sportName, DateTime gameDate, String sectionName,
			Set<String> selectedSeats) throws InvalidTicketsException, TicketsNotFoundException {
		try {
			if (!isValidWithSeatTickets(sportName, gameDate, sectionName, selectedSeats)) {
				throw new InvalidTicketsException();
			}
			
			GameDto gameDto = gameDao.get(sportName, gameDate);
			SectionDto sectionDto = sectionDao.getAvailable(sportName, gameDate, sectionName);

			SectionForCart sectionForCart = sectionForCartFactory
					.createSectionForWithSeatTickets(sportName, gameDate, sectionName,
					gameDto.getOpponents(), gameDto.getLocation(),
					selectedSeats, sectionDto.getPrice());
			currentCart.addSection(sectionForCart);
		} catch (SectionDoesntExistException | GameDoesntExistException e) {
			throw new TicketsNotFoundException();
		}
	}
	
	public void makeTicketsUnavailableToOtherPeople() {
		try {
			ticketService.makeTicketsUnavailable(currentCart.getSections());
		} catch (GameDoesntExistException | TicketDoesntExistException | GameAlreadyExistException | TicketAlreadyExistsException | SportDoesntExistException | NoTicketsInCartException e) {
			throw new CartException();
		}
	}

	public void emptyCart() {
		currentCart.empty();
		
	}
	
	private boolean isValidGeneralTickets(String sportName, DateTime gameDate,
			String sectionName, Integer numberOfTicketsToBuy) throws SectionDoesntExistException {
		Section section = sectionRepository.getAvailable(sportName, gameDate, sectionName);
		if (section.isGeneralAdmission()) {
			return section.isValidNumberOfTicketsForGeneralTickets(numberOfTicketsToBuy);
		}
		return false;
	}
	
	private boolean isValidWithSeatTickets(String sportName, DateTime gameDate,
			String sectionName, Set<String> selectedSeats) throws SectionDoesntExistException {
		Section section = sectionRepository.getAvailable(sportName, gameDate, sectionName);
		if (!section.isGeneralAdmission()) {
			return section.isValidSelectedSeatsForWithSeatTickets(selectedSeats);
		}
		return false;
	}
}
