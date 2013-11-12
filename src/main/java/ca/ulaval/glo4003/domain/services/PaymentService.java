package ca.ulaval.glo4003.domain.services;

import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.ulaval.glo4003.domain.dtos.GameDto;
import ca.ulaval.glo4003.domain.dtos.SectionDto;
import ca.ulaval.glo4003.domain.pojos.Section;
import ca.ulaval.glo4003.domain.repositories.SectionRepository;
import ca.ulaval.glo4003.domain.utilities.Calculator;
import ca.ulaval.glo4003.domain.utilities.Constants;
import ca.ulaval.glo4003.domain.utilities.Constants.CreditCardType;
import ca.ulaval.glo4003.domain.utilities.payment.Cart;
import ca.ulaval.glo4003.domain.utilities.payment.CreditCard;
import ca.ulaval.glo4003.domain.utilities.payment.CreditCardFactory;
import ca.ulaval.glo4003.domain.utilities.payment.InvalidCreditCardException;
import ca.ulaval.glo4003.persistence.daos.GameDao;
import ca.ulaval.glo4003.persistence.daos.GameDoesntExistException;
import ca.ulaval.glo4003.persistence.daos.SectionDao;
import ca.ulaval.glo4003.persistence.daos.SectionDoesntExistException;
import ca.ulaval.glo4003.presentation.viewmodels.ChooseTicketsViewModel;
import ca.ulaval.glo4003.presentation.viewmodels.PayableItemsViewModel;
import ca.ulaval.glo4003.presentation.viewmodels.PaymentViewModel;
import ca.ulaval.glo4003.presentation.viewmodels.factories.PayableItemsViewModelFactory;

@Service
public class PaymentService {

	@Inject
	private GameDao gameDao;

	@Inject
	private SectionDao sectionDao;

	@Inject
	private PayableItemsViewModelFactory payableItemsViewModelFactory;

	@Inject
	private Calculator calculator;

	@Inject
	private Constants constants;

	@Inject
	private CreditCardFactory creditCardFactory;

	@Inject
	private CartService cartService;

	@Autowired
	private Cart currentCart;

	@Inject
	private SectionRepository sectionRepository;

	public Boolean isValidChooseTicketsViewModel(ChooseTicketsViewModel chooseTicketsVM) throws GameDoesntExistException,
			SectionDoesntExistException {
		Section section = sectionRepository.getAvailable(chooseTicketsVM.getGameId(), chooseTicketsVM.getSectionName());
		return section.isValidElements(chooseTicketsVM.getNumberOfTicketsToBuy(), chooseTicketsVM.getSelectedSeats());
	}

	public PayableItemsViewModel getPayableItemsViewModel(ChooseTicketsViewModel chooseTicketsVM)
			throws GameDoesntExistException, SectionDoesntExistException {
		GameDto gameDto = gameDao.get(chooseTicketsVM.getGameId());
		SectionDto sectionDto = sectionDao.getAvailable(chooseTicketsVM.getGameId(), chooseTicketsVM.getSectionName());

		return payableItemsViewModelFactory.createViewModel(chooseTicketsVM, gameDto, sectionDto);
	}

	public void saveToCart(ChooseTicketsViewModel chooseTicketsVM) throws GameDoesntExistException, SectionDoesntExistException {
		GameDto gameDto = gameDao.get(chooseTicketsVM.getGameId());
		SectionDto sectionDto = sectionDao.getAvailable(chooseTicketsVM.getGameId(), chooseTicketsVM.getSectionName());

		Double cumulativePrice = 0.0;

		if (sectionDto.isGeneralAdmission()) {
			cumulativePrice = calculator.calculateCumulativePriceForGeneralAdmission(chooseTicketsVM.getNumberOfTicketsToBuy(),
					sectionDto.getPrice());
		} else {
			cumulativePrice = calculator.calculateCumulativePriceForWithSeatAdmission(chooseTicketsVM.getSelectedSeats(),
					sectionDto.getPrice());
		}

		currentCart.setNumberOfTicketsToBuy(chooseTicketsVM.getNumberOfTicketsToBuy());
		currentCart.setSelectedSeats(chooseTicketsVM.getSelectedSeats());
		currentCart.setGameDto(gameDto);
		currentCart.setSectionDto(sectionDto);
		currentCart.setCumulativePrice(cumulativePrice);
	}

	public List<CreditCardType> getCreditCardTypes() {
		return constants.getCreditCardTypes();
	}

	public String getCumulativePriceFR() throws NoTicketsInCartException {
		if (currentCart.containTickets()) {
			return calculator.toPriceFR(currentCart.getCumulativePrice());
		} else {
			throw new NoTicketsInCartException();
		}
	}

	public void buyTicketsInCart(PaymentViewModel paymentVM) throws InvalidCreditCardException {
		CreditCard creditCard = creditCardFactory.createCreditCard(paymentVM);
		if (currentCart.containTickets()) {
			creditCard.pay(currentCart.getCumulativePrice());
			cartService.makeTicketsUnavailableToOtherPeople(currentCart);
		}
	}

	public void emptyCart() {
		currentCart.empty();
	}

}
