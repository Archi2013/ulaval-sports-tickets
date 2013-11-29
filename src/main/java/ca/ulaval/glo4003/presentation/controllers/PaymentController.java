package ca.ulaval.glo4003.presentation.controllers;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import ca.ulaval.glo4003.constants.CreditCardType;
import ca.ulaval.glo4003.domain.payment.Cart;
import ca.ulaval.glo4003.domain.payment.InvalidCreditCardException;
import ca.ulaval.glo4003.domain.users.User;
import ca.ulaval.glo4003.presentation.controllers.errormanagers.PaymentErrorManager;
import ca.ulaval.glo4003.presentation.viewmodels.PaymentViewModel;
import ca.ulaval.glo4003.services.NoTicketsInCartException;
import ca.ulaval.glo4003.services.PaymentService;
import ca.ulaval.glo4003.utilities.Calculator;
import ca.ulaval.glo4003.utilities.Constants;

@Controller
@SessionAttributes({ "currentUser" })
@RequestMapping(value = "/paiement", method = RequestMethod.GET)
public class PaymentController {

	private static final String MODE_OF_PAYMENT_PAGE = "payment/mode-of-payment";

	private static final String VALIDATION_SUCCES_PAGE = "payment/succes";

	@Inject
	PaymentService paymentService;
	
	@Autowired
	private Cart currentCart;
	
	@Inject
	private Calculator calculator;
	
	@Inject
	private PaymentErrorManager paymentErrorManager;

	@RequestMapping(value = "mode-de-paiement", method = RequestMethod.GET)
	public ModelAndView modeOfPayment(@ModelAttribute("currentUser") User currentUser) {
		ModelAndView mav = new ModelAndView(MODE_OF_PAYMENT_PAGE);
		
		if (!currentUser.isLogged()) {
			paymentErrorManager.prepareErrorPageToShowNotConnectedUserMessage(mav);
			return mav;
		}

		try {
			mav.addObject("cumulativePrice", getCumulativePriceFR());
			mav.addObject("currency", Constants.CURRENCY);
			mav.addObject("paymentForm", new PaymentViewModel());
			mav.addObject("creditCardTypes", CreditCardType.getCreditCardTypes());
		} catch (NoTicketsInCartException e) {
			paymentErrorManager.prepareErrorPage(mav, e);
		}
		
		return mav;
	}

	@RequestMapping(value = "validation-achat", method = RequestMethod.POST)
	public ModelAndView validate(@ModelAttribute("currentUser") User currentUser,
			@ModelAttribute("paymentForm") @Valid PaymentViewModel paymentVM,
			BindingResult result) {
		ModelAndView mav = new ModelAndView(VALIDATION_SUCCES_PAGE);

		if (!currentUser.isLogged()) {
			paymentErrorManager.prepareErrorPageToShowNotConnectedUserMessage(mav);
			return mav;
		}
		
		mav.addObject("currency", Constants.CURRENCY);

		if (result.hasErrors()) {
			return returnModelAndViewToRetryModeOfPayment(paymentVM, currentUser);
		}

		try {
			mav.addObject("cumulativePrice", getCumulativePriceFR());
			paymentService.buyTicketsInCart(paymentVM);
			paymentService.emptyCart();
		} catch (InvalidCreditCardException e) {
			ModelAndView mavToReturn = returnModelAndViewToRetryModeOfPayment(paymentVM, currentUser);
			paymentErrorManager.addErrorMessageInvalidCreditCardToModel(mavToReturn);
			return mavToReturn;
		} catch (NoTicketsInCartException e) {
			paymentErrorManager.prepareErrorPage(mav, e);
			paymentService.emptyCart();
		}
		
		return mav;
	}
	
	private String getCumulativePriceFR() throws NoTicketsInCartException {
		if (currentCart.containTickets()) {
			return calculator.toPriceFR(currentCart.getCumulativePrice());
		} else {
			throw new NoTicketsInCartException();
		}
	}
	
	private ModelAndView returnModelAndViewToRetryModeOfPayment(PaymentViewModel paymentVM, User currentUser) {
		ModelAndView mav = modeOfPayment(currentUser);
		mav.addObject("paymentForm", paymentVM);
		return mav;
	}
}
