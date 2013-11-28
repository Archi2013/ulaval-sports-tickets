package ca.ulaval.glo4003.services;

import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.ulaval.glo4003.domain.game.GameDao;
import ca.ulaval.glo4003.domain.game.GameDto;
import ca.ulaval.glo4003.domain.payment.Cart;
import ca.ulaval.glo4003.domain.sections.ISectionRepository;
import ca.ulaval.glo4003.domain.sections.Section;
import ca.ulaval.glo4003.domain.sections.SectionDao;
import ca.ulaval.glo4003.domain.sections.SectionDto;
import ca.ulaval.glo4003.exceptions.GameAlreadyExistException;
import ca.ulaval.glo4003.exceptions.GameDoesntExistException;
import ca.ulaval.glo4003.exceptions.SectionDoesntExistException;
import ca.ulaval.glo4003.exceptions.SportDoesntExistException;
import ca.ulaval.glo4003.exceptions.TicketAlreadyExistsException;
import ca.ulaval.glo4003.exceptions.TicketDoesntExistException;
import ca.ulaval.glo4003.presentation.viewmodels.ChooseTicketsViewModel;
import ca.ulaval.glo4003.utilities.Calculator;

@Service
public class CartService {

	@Inject
	private GameDao gameDao;

	@Inject
	private SectionDao sectionDao;
	
	@Inject
	private Calculator calculator;
	
	@Inject
	private ISectionRepository sectionRepository;
	
	@Inject
	private CommandTicketService ticketService;
	
	@Autowired
	private Cart currentCart;

	public void saveToCart(ChooseTicketsViewModel chooseTicketsVM) throws InvalidTicketsException, TicketsNotFoundException {
		try {
			if (!isValidChooseTicketsViewModel(chooseTicketsVM)) {
				throw new InvalidTicketsException();
			}
			
			GameDto gameDto = gameDao.get(chooseTicketsVM.getSportName(),
					chooseTicketsVM.getGameDate());
			SectionDto sectionDto = sectionDao.getAvailable(
					chooseTicketsVM.getSportName(), chooseTicketsVM.getGameDate(),
					chooseTicketsVM.getSectionName());
			
			Double cumulativePrice = 0.0;

			if (sectionDto.isGeneralAdmission()) {
				cumulativePrice = calculator
						.calculateCumulativePriceForGeneralAdmission(
								chooseTicketsVM.getNumberOfTicketsToBuy(),
								sectionDto.getPrice());
			} else {
				cumulativePrice = calculator
						.calculateCumulativePriceForWithSeatAdmission(
								chooseTicketsVM.getSelectedSeats(),
								sectionDto.getPrice());
			}

			currentCart.setNumberOfTicketsToBuy(chooseTicketsVM
					.getNumberOfTicketsToBuy());
			currentCart.setSelectedSeats(chooseTicketsVM.getSelectedSeats());
			currentCart.setGameDto(gameDto);
			currentCart.setSectionDto(sectionDto);
			currentCart.setCumulativePrice(cumulativePrice);
		} catch (GameDoesntExistException | SectionDoesntExistException e) {
			throw new TicketsNotFoundException();
		}
	}
	
	private Boolean isValidChooseTicketsViewModel(ChooseTicketsViewModel chooseTicketsVM)
			throws GameDoesntExistException, SectionDoesntExistException {
		Section section = sectionRepository.getAvailable(chooseTicketsVM.getSportName(), chooseTicketsVM.getGameDate(),
				chooseTicketsVM.getSectionName());
		return section.isValidElements(chooseTicketsVM.getNumberOfTicketsToBuy(), chooseTicketsVM.getSelectedSeats());
	}
	
	public void makeTicketsUnavailableToOtherPeople() {
		try {
			GameDto game = currentCart.getGameDto();
			SectionDto section = currentCart.getSectionDto();
			List<String> seats = currentCart.getSelectedSeats();
			int numberOfSeats = currentCart.getNumberOfTicketsToBuy();

			ticketService.makeTicketsUnavailable(game, section, numberOfSeats, seats);

		} catch (GameDoesntExistException | GameAlreadyExistException | TicketAlreadyExistsException | TicketDoesntExistException
				| SportDoesntExistException e) {
			throw new CartException();
		}
	}
}
