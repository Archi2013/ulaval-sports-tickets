package ca.ulaval.glo4003.presentation.controllers.errormanagers;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import ca.ulaval.glo4003.services.InvalidTicketsException;
import ca.ulaval.glo4003.services.TicketsNotFoundException;

@Component
public class CartErrorManager extends ErrorMessageAdder {

	private static final String ERROR_PAGE = "cart/error-page";
	
	private static final String ERROR_MESSAGE_INVALID_TICKETS = "error-message.cart.invalid-tickets";

	private static final String ERROR_MESSAGE_NOT_FOUND_TICKETS = "error-message.cart.not-found-tickets";
	
	private static final String ERROR_MESSAGE_UNKNOWN_ERROR = "error-message.cart.unknown-error";

	private static final String ERROR_MESSAGE_NOT_CONNECTED_USER = "error-message.cart.not-connected-user";
	
	private static final String ERROR_MESSAGE_TRAFFICKED_PAGE = "error-message.payment.trafficked-page";
	
	public void prepareErrorPage(ModelAndView mav, Exception e) {
		if (e instanceof InvalidTicketsException) {
			prepareErrorPageForCart(mav, ERROR_MESSAGE_INVALID_TICKETS);
		} else if (e instanceof TicketsNotFoundException) {
			prepareErrorPageForCart(mav, ERROR_MESSAGE_NOT_FOUND_TICKETS);
		} else {
			prepareErrorPageForCart(mav, ERROR_MESSAGE_UNKNOWN_ERROR);
		}
	}
	
	public void prepareErrorPageToShowNotConnectedUserMessage(ModelAndView mav) {
		prepareErrorPageForCart(mav, ERROR_MESSAGE_NOT_CONNECTED_USER);
	}
	
	public void prepareErrorPageToShowTraffickedPageMessage(ModelAndView mav) {
		prepareErrorPageForCart(mav, ERROR_MESSAGE_TRAFFICKED_PAGE);
	}
	
	private void prepareErrorPageForCart(ModelAndView mav, String messageCode) {
		prepareErrorPage(mav, messageCode, ERROR_PAGE);
	}
}
