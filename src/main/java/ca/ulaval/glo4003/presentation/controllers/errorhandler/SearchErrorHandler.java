package ca.ulaval.glo4003.presentation.controllers.errorhandler;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import ca.ulaval.glo4003.services.exceptions.NoTicketsInCartException;

@Component
public class SearchErrorHandler extends ErrorMessageAdder {
	
	private static final String ERROR_MESSAGE_USER_PREFERENCES_NOT_SAVED = "error-message.search.user-preferences-not-saved";
	
	public void addErrorMessageUserPreferencesNotSaved(ModelAndView mav) {
		addErrorMessageToModel(mav, ERROR_MESSAGE_USER_PREFERENCES_NOT_SAVED);
	}
}
