package ca.ulaval.glo4003.presentation.controllers.errorhandler;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import ca.ulaval.glo4003.services.exceptions.NoTicketsInCartException;

@Component
public class PaymentErrorHandler extends ErrorMessageAdder {

	private static final String ERROR_PAGE = "payment/error-page";
	
	private static final String ERROR_MESSAGE_NO_TICKETS = "error-message.payment.no-tickets";
	
	private static final String ERROR_MESSAGE_NOT_CONNECTED_USER = "error-message.payment.not-connected-user";
	
	private static final String ERROR_MESSAGE_UNKNOWN_ERROR = "error-message.payment.unknown-error";
	
	private static final String ERROR_MESSAGE_INVALID_CREDIT_CARD = "error-message.payment.invalid-credit-card";
	
	public void prepareErrorPage(ModelAndView mav, Exception e) {
		if (e instanceof NoTicketsInCartException) {
			prepareErrorPageForPayment(mav, ERROR_MESSAGE_NO_TICKETS);
		} else {
			prepareErrorPageForPayment(mav, ERROR_MESSAGE_UNKNOWN_ERROR);
		}
	}
	
	public void addErrorMessageInvalidCreditCardToModel(ModelAndView mav) {
		addErrorMessageToModel(mav, ERROR_MESSAGE_INVALID_CREDIT_CARD);
		
	}
	
	public void prepareErrorPageToShowNotConnectedUserMessage(ModelAndView mav) {
		prepareErrorPageForPayment(mav, ERROR_MESSAGE_NOT_CONNECTED_USER);
	}
	
	private void prepareErrorPageForPayment(ModelAndView mav, String messageCode) {
		prepareErrorPage(mav, messageCode, ERROR_PAGE);
	}
}
