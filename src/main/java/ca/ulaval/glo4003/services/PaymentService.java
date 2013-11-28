package ca.ulaval.glo4003.services;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import ca.ulaval.glo4003.constants.CreditCardType;
import ca.ulaval.glo4003.domain.game.GameDao;
import ca.ulaval.glo4003.domain.game.GameDto;
import ca.ulaval.glo4003.domain.payment.Cart;
import ca.ulaval.glo4003.domain.payment.CreditCard;
import ca.ulaval.glo4003.domain.payment.CreditCardFactory;
import ca.ulaval.glo4003.domain.payment.InvalidCreditCardException;
import ca.ulaval.glo4003.domain.sections.ISectionRepository;
import ca.ulaval.glo4003.domain.sections.Section;
import ca.ulaval.glo4003.domain.sections.SectionDao;
import ca.ulaval.glo4003.domain.sections.SectionDto;
import ca.ulaval.glo4003.exceptions.GameDoesntExistException;
import ca.ulaval.glo4003.exceptions.SectionDoesntExistException;
import ca.ulaval.glo4003.presentation.viewmodels.ChooseTicketsViewModel;
import ca.ulaval.glo4003.presentation.viewmodels.PayableItemsViewModel;
import ca.ulaval.glo4003.presentation.viewmodels.PaymentViewModel;
import ca.ulaval.glo4003.presentation.viewmodels.factories.PayableItemsViewModelFactory;
import ca.ulaval.glo4003.utilities.Calculator;
import ca.ulaval.glo4003.utilities.Constants;

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
	private Calculator calculator;

	@Inject
	private CreditCardFactory creditCardFactory;

	@Inject
	private CartService cartService;

	@Autowired
	private Cart currentCart;

	@Inject
	private MessageSource messageSource;

	private void prepareErrorPage(ModelAndView mav, String message) {
		mav.setViewName(ERROR_PAGE);
		addErrorMessageToModel(mav, message);
	}

	private void addErrorMessageToModel(ModelAndView mav, String message) {
		String errorMessage = this.messageSource.getMessage(message, new Object[] {}, null);
		mav.addObject("errorMessage", errorMessage);
	}

	public void prepareModeOfPaymentView(ModelAndView mav) {
		mav.addObject("currency", Constants.CURRENCY);

		mav.addObject("paymentForm", new PaymentViewModel());
		mav.addObject("creditCardTypes", CreditCardType.getCreditCardTypes());
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

	private void buyTicketsInCart(PaymentViewModel paymentVM) throws InvalidCreditCardException,
			NoTicketsInCartException {
		if (currentCart.containTickets()) {
			CreditCard creditCard = creditCardFactory.createCreditCard(paymentVM);
			creditCard.pay(currentCart.getCumulativePrice());
			makeTicketsUnavailable();
		} else {
			throw new NoTicketsInCartException();
		}
	}

	private void makeTicketsUnavailable() {
		cartService.makeTicketsUnavailableToOtherPeople();
	}

	private void emptyCart() {
		currentCart.empty();
	}

	private void modifyModelAndViewToRetryModeOfPayment(PaymentViewModel paymentVM, ModelAndView mav) {
		mav.setViewName(MODE_OF_PAYMENT_PAGE);
		mav.addObject("paymentForm", paymentVM);
		mav.addObject("creditCardTypes", CreditCardType.getCreditCardTypes());
	}

}
