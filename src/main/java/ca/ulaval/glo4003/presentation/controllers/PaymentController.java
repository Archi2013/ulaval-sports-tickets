package ca.ulaval.glo4003.presentation.controllers;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ca.ulaval.glo4003.domain.services.NoTicketsInCartException;
import ca.ulaval.glo4003.domain.services.PaymentService;
import ca.ulaval.glo4003.domain.services.SearchService;
import ca.ulaval.glo4003.domain.utilities.Constants;
import ca.ulaval.glo4003.domain.utilities.payment.InvalidCreditCardException;
import ca.ulaval.glo4003.domain.utilities.user.User;
import ca.ulaval.glo4003.persistence.daos.GameDoesntExistException;
import ca.ulaval.glo4003.persistence.daos.SectionDoesntExistException;
import ca.ulaval.glo4003.presentation.viewmodels.ChooseTicketsViewModel;
import ca.ulaval.glo4003.presentation.viewmodels.PaymentViewModel;

@Controller
@RequestMapping(value = "/paiement", method = RequestMethod.GET)
public class PaymentController {
	private static final String ERROR_MESSAGE_INVALID_CREDIT_CARD = "error-message.payment.invalid-credit-card";

	private static final String ERROR_MESSAGE_NO_TICKETS = "error-message.payment.no-tickets";

	private static final String ERROR_MESSAGE_NOT_FOUND_TICKET = "error-message.payment.not-found-ticket";

	private static final String ERROR_MESSAGE_INVALID_CHOOSE_TICKETS_VIEW_MODEL = "error-message.payment.invalid-choose-tickets-view-model";

	private static final String ERROR_MESSAGE_TRAFFICKED_PAGE = "error-message.payment.trafficked-page";

	private static final String ERROR_MESSAGE_NOT_CONNECTED_USER = "error-message.payment.not-connected-user";

	private static final String ERROR_PAGE = "payment/error-page";

	private static final String HOME_PAGE = "payment/home";

	private static final String MODE_OF_PAYMENT_PAGE = "payment/mode-of-payment";

	private static final String VALIDATION_SUCCES_PAGE = "payment/succes";

	@Inject
	SearchService searchService;

	@Inject
	PaymentService paymentService;

	@Autowired
	private User currentUser;

	@Inject
	private MessageSource messageSource;

	@RequestMapping(value = "", method = RequestMethod.POST)
	public ModelAndView home(@ModelAttribute("chooseTicketsForm") @Valid ChooseTicketsViewModel chooseTicketsVM,
			BindingResult result) {
		Boolean connectedUser = currentUser.isLogged();

		ModelAndView mav = new ModelAndView(HOME_PAGE);

		addConnectedUserToModelAndView(connectedUser, mav);

		if (!connectedUser) {
			modifyModelAndViewToShowNotConnectedUserPage(mav);
			return mav;
		}

		if (result.hasErrors()) {
			mav.setViewName(ERROR_PAGE);
			String errorMessage = this.messageSource.getMessage(ERROR_MESSAGE_TRAFFICKED_PAGE, new Object[] {}, null);
			mav.addObject("errorMessage", errorMessage);
			return mav;
		}

		mav.addObject("currency", Constants.CURRENCY);

		try {
			if (!paymentService.isValidChooseTicketsViewModel(chooseTicketsVM)) {
				String errorMessage = this.messageSource.getMessage(ERROR_MESSAGE_INVALID_CHOOSE_TICKETS_VIEW_MODEL,
						new Object[] {}, null);
				mav.addObject("errorMessage", errorMessage);
				return mav;
			}
			mav.addObject("payableItems", paymentService.getPayableItemsViewModel(chooseTicketsVM));
			paymentService.saveToCart(chooseTicketsVM);
		} catch (GameDoesntExistException | SectionDoesntExistException e) {
			String errorMessage = this.messageSource.getMessage(ERROR_MESSAGE_NOT_FOUND_TICKET, new Object[] {}, null);
			mav.addObject("errorMessage", errorMessage);
		}

		return mav;
	}

	@RequestMapping(value = "mode-de-paiement", method = RequestMethod.GET)
	public ModelAndView modeOfPayment() {
		ModelAndView mav = new ModelAndView(MODE_OF_PAYMENT_PAGE);

		Boolean connectedUser = currentUser.isLogged();

		addConnectedUserToModelAndView(connectedUser, mav);

		if (!connectedUser) {
			modifyModelAndViewToShowNotConnectedUserPage(mav);
			return mav;
		}

		mav.addObject("currency", Constants.CURRENCY);

		mav.addObject("paymentForm", new PaymentViewModel());
		mav.addObject("creditCardTypes", paymentService.getCreditCardTypes());
		try {
			mav.addObject("cumulativePrice", paymentService.getCumulativePriceFR());
		} catch (NoTicketsInCartException e) {
			mav.setViewName(ERROR_PAGE);
			String errorMessage = this.messageSource.getMessage(ERROR_MESSAGE_NO_TICKETS, new Object[] {}, null);
			mav.addObject("errorMessage", errorMessage);
		}

		return mav;
	}

	@RequestMapping(value = "validation-achat", method = RequestMethod.POST)
	public ModelAndView validate(@ModelAttribute("paymentForm") @Valid PaymentViewModel paymentVM, BindingResult result) {
		
		ModelAndView mav = new ModelAndView(VALIDATION_SUCCES_PAGE);

		Boolean userIsconnected = currentUser.isLogged();

		addConnectedUserToModelAndView(userIsconnected, mav);

		if (!userIsconnected) {
			modifyModelAndViewToShowNotConnectedUserPage(mav);
			return mav;
		}

		mav.addObject("currency", Constants.CURRENCY);

		try {
			mav.addObject("cumulativePrice", paymentService.getCumulativePriceFR());
		} catch (NoTicketsInCartException e) {
			mav.setViewName(ERROR_PAGE);
			String errorMessage = this.messageSource.getMessage(ERROR_MESSAGE_NO_TICKETS, new Object[] {}, null);
			mav.addObject("errorMessage", errorMessage);
			return mav;
		}

		if (result.hasErrors()) {
			modifyModelAndViewToRetryModeOfPayment(paymentVM, mav);
			return mav;
		}

		try {
			paymentService.buyTicketsInCart(paymentVM);
		} catch (InvalidCreditCardException e) {
			modifyModelAndViewToRetryModeOfPayment(paymentVM, mav);
			String errorMessage = this.messageSource.getMessage(ERROR_MESSAGE_INVALID_CREDIT_CARD, new Object[] {}, null);
			mav.addObject("errorMessage", errorMessage);
			return mav;
		} catch (NoTicketsInCartException e) {
			mav.setViewName(ERROR_PAGE);
			String errorMessage = this.messageSource.getMessage(ERROR_MESSAGE_NO_TICKETS, new Object[] {}, null);
			mav.addObject("errorMessage", errorMessage);
		}

		paymentService.emptyCart();
		return mav;
	}

	private void modifyModelAndViewToShowNotConnectedUserPage(ModelAndView mav) {
		mav.setViewName(ERROR_PAGE);
		String errorMessage = this.messageSource.getMessage(ERROR_MESSAGE_NOT_CONNECTED_USER, new Object[] {}, null);
		mav.addObject("errorMessage", errorMessage);
	}

	private void addConnectedUserToModelAndView(Boolean connectedUser, ModelAndView mav) {
		if (connectedUser) {
			mav.addObject("connectedUser", true);
		} else {
			mav.addObject("connectedUser", false);
		}
	}

	private void modifyModelAndViewToRetryModeOfPayment(PaymentViewModel paymentVM, ModelAndView mav) {
		mav.setViewName(MODE_OF_PAYMENT_PAGE);
		mav.addObject("paymentForm", paymentVM);
		mav.addObject("creditCardTypes", paymentService.getCreditCardTypes());
	}
}
