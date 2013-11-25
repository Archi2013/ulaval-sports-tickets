package ca.ulaval.glo4003.domain.services;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import ca.ulaval.glo4003.domain.dtos.GameDto;
import ca.ulaval.glo4003.domain.dtos.SectionDto;
import ca.ulaval.glo4003.domain.pojos.Section;
import ca.ulaval.glo4003.domain.repositories.ISectionRepository;
import ca.ulaval.glo4003.domain.utilities.Calculator;
import ca.ulaval.glo4003.domain.utilities.Constants;
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

	private static final String ERROR_PAGE = "payment/error-page";

	private static final String ERROR_MESSAGE_TRAFFICKED_PAGE = "error-message.payment.trafficked-page";

	private static final String ERROR_MESSAGE_NOT_FOUND_TICKET = "error-message.payment.not-found-ticket";

	private static final String ERROR_MESSAGE_INVALID_CHOOSE_TICKETS_VIEW_MODEL = "error-message.payment.invalid-choose-tickets-view-model";

	private static final String ERROR_MESSAGE_NO_TICKETS = "error-message.payment.no-tickets";

	private static final String ERROR_MESSAGE_INVALID_CREDIT_CARD = "error-message.payment.invalid-credit-card";

	private static final String MODE_OF_PAYMENT_PAGE = "payment/mode-of-payment";

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
	private ISectionRepository sectionRepository;

	@Inject
	private MessageSource messageSource;

	public void prepareCartViewAndCart(ModelAndView mav, BindingResult result, ChooseTicketsViewModel chooseTicketsVM) {
		if (result.hasErrors()) {
			prepareErrorPage(mav, ERROR_MESSAGE_TRAFFICKED_PAGE);
			return;
		}

		mav.addObject("currency", Constants.CURRENCY);

		try {
			if (!isValidChooseTicketsViewModel(chooseTicketsVM)) {
				prepareErrorPage(mav, ERROR_MESSAGE_INVALID_CHOOSE_TICKETS_VIEW_MODEL);
				return;
			}
			mav.addObject("payableItems", getPayableItemsViewModel(chooseTicketsVM));
			saveToCart(chooseTicketsVM);
		} catch (GameDoesntExistException | SectionDoesntExistException e) {
			prepareErrorPage(mav, ERROR_MESSAGE_NOT_FOUND_TICKET);
		}
	}

	private void prepareErrorPage(ModelAndView mav, String message) {
		mav.setViewName(ERROR_PAGE);
		addErrorMessageToModel(mav, message);
	}

	private void addErrorMessageToModel(ModelAndView mav, String message) {
		String errorMessage = this.messageSource.getMessage(message, new Object[] {}, null);
		mav.addObject("errorMessage", errorMessage);
	}

	private Boolean isValidChooseTicketsViewModel(ChooseTicketsViewModel chooseTicketsVM) throws GameDoesntExistException,
	        SectionDoesntExistException {
		Section section = sectionRepository.getAvailable(chooseTicketsVM.getSportName(), chooseTicketsVM.getGameDate(),
		        chooseTicketsVM.getSectionName());
		return section.isValidElements(chooseTicketsVM.getNumberOfTicketsToBuy(), chooseTicketsVM.getSelectedSeats());
	}

	private PayableItemsViewModel getPayableItemsViewModel(ChooseTicketsViewModel chooseTicketsVM) throws GameDoesntExistException,
	        SectionDoesntExistException {
		GameDto gameDto = gameDao.get(chooseTicketsVM.getSportName(), chooseTicketsVM.getGameDate());
		SectionDto sectionDto = sectionDao.getAvailable(chooseTicketsVM.getSportName(), chooseTicketsVM.getGameDate(),
		        chooseTicketsVM.getSectionName());

		return payableItemsViewModelFactory.createViewModel(chooseTicketsVM, gameDto, sectionDto);
	}

	private void saveToCart(ChooseTicketsViewModel chooseTicketsVM) throws GameDoesntExistException, SectionDoesntExistException {
		GameDto gameDto = gameDao.get(chooseTicketsVM.getSportName(), chooseTicketsVM.getGameDate());
		SectionDto sectionDto = sectionDao.getAvailable(chooseTicketsVM.getSportName(), chooseTicketsVM.getGameDate(),
		        chooseTicketsVM.getSectionName());

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

	public void prepareModeOfPaymentView(ModelAndView mav) {
		mav.addObject("currency", Constants.CURRENCY);

		mav.addObject("paymentForm", new PaymentViewModel());
		mav.addObject("creditCardTypes", constants.getCreditCardTypes());
		try {
			mav.addObject("cumulativePrice", getCumulativePriceFR());
		} catch (NoTicketsInCartException e) {
			prepareErrorPage(mav, ERROR_MESSAGE_NO_TICKETS);
		}
	}

	private String getCumulativePriceFR() throws NoTicketsInCartException {
		if (currentCart.containTickets()) {
			return calculator.toPriceFR(currentCart.getCumulativePrice());
		} else {
			throw new NoTicketsInCartException();
		}
	}

	public void prepareValidationViewAndCart(ModelAndView mav, BindingResult result, PaymentViewModel paymentVM) {
		mav.addObject("currency", Constants.CURRENCY);

		if (result.hasErrors()) {
			modifyModelAndViewToRetryModeOfPayment(paymentVM, mav);
			return;
		}

		try {
			mav.addObject("cumulativePrice", getCumulativePriceFR());
			buyTicketsInCart(paymentVM);
		} catch (InvalidCreditCardException e) {
			modifyModelAndViewToRetryModeOfPayment(paymentVM, mav);
			addErrorMessageToModel(mav, ERROR_MESSAGE_INVALID_CREDIT_CARD);
			return;
		} catch (NoTicketsInCartException e) {
			mav.setViewName(ERROR_PAGE);
			prepareErrorPage(mav, ERROR_MESSAGE_NO_TICKETS);
		}

		emptyCart();
	}

	private void buyTicketsInCart(PaymentViewModel paymentVM) throws InvalidCreditCardException, NoTicketsInCartException {
		if (currentCart.containTickets()) {
			CreditCard creditCard = creditCardFactory.createCreditCard(paymentVM);
			creditCard.pay(currentCart.getCumulativePrice());
			makeTicketsUnavailable();
		} else {
			throw new NoTicketsInCartException();
		}
	}

	private void makeTicketsUnavailable() {
		cartService.makeTicketsUnavailableToOtherPeople(currentCart);
	}

	private void emptyCart() {
		currentCart.empty();
	}

	private void modifyModelAndViewToRetryModeOfPayment(PaymentViewModel paymentVM, ModelAndView mav) {
		mav.setViewName(MODE_OF_PAYMENT_PAGE);
		mav.addObject("paymentForm", paymentVM);
		mav.addObject("creditCardTypes", constants.getCreditCardTypes());
	}

}
