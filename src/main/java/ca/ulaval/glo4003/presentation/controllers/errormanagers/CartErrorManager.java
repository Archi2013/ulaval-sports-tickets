package ca.ulaval.glo4003.presentation.controllers.errormanagers;

import javax.inject.Inject;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import ca.ulaval.glo4003.services.InvalidTicketsException;
import ca.ulaval.glo4003.services.TicketsNotFoundException;

@Component
public class CartErrorManager {

	private static final String ERROR_PAGE = "cart/error-page";
	
	private static final String ERROR_MESSAGE_INVALID_TICKETS = "error-message.cart.invalid-tickets";

	private static final String ERROR_MESSAGE_NOT_FOUND_TICKETS = "error-message.cart.not-found-tickets";
	
	private static final String ERROR_MESSAGE_UNKNOWN_ERROR = "error-message.cart.unknown-error";

	private static final String ERROR_MESSAGE_NOT_CONNECTED_USER = "error-message.cart.not-connected-user";
	
	private static final String ERROR_MESSAGE_TRAFFICKED_PAGE = "error-message.payment.trafficked-page";
	
	@Inject
	private MessageSource messageSource;
	
	public void prepareErrorPage(ModelAndView mav, Exception e) {
		if (e instanceof InvalidTicketsException) {
			prepareErrorPage(mav, ERROR_MESSAGE_INVALID_TICKETS);
		} else if (e instanceof TicketsNotFoundException) {
			prepareErrorPage(mav, ERROR_MESSAGE_NOT_FOUND_TICKETS);
		} else {
			prepareErrorPage(mav, ERROR_MESSAGE_UNKNOWN_ERROR);
		}
	}
	
	public void prepareErrorPageToShowNotConnectedUserMessage(ModelAndView mav) {
		prepareErrorPage(mav, ERROR_MESSAGE_NOT_CONNECTED_USER);
	}
	
	public void prepareErrorPageToShowTraffickedPageMessage(ModelAndView mav) {
		prepareErrorPage(mav, ERROR_MESSAGE_TRAFFICKED_PAGE);
	}
	
	private void prepareErrorPage(ModelAndView mav, String message) {
		mav.setViewName(ERROR_PAGE);
		addErrorMessageToModel(mav, message);
	}

	private void addErrorMessageToModel(ModelAndView mav, String message) {
		String errorMessage = this.messageSource.getMessage(message, new Object[] {}, null);
		mav.addObject("errorMessage", errorMessage);
	}
}
