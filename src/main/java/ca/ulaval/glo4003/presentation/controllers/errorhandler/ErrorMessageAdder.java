package ca.ulaval.glo4003.presentation.controllers.errorhandler;

import javax.inject.Inject;

import org.springframework.context.MessageSource;
import org.springframework.web.servlet.ModelAndView;

public class ErrorMessageAdder {

	@Inject
	private MessageSource messageSource;

	public void prepareErrorPage(ModelAndView mav, String messageCode, String errorPage) {
		mav.setViewName(errorPage);
		addErrorMessageToModel(mav, messageCode);
	}

	public void addErrorMessageToModel(ModelAndView mav, String messageCode) {
		String errorMessage = this.messageSource.getMessage(messageCode, new Object[] {}, null);
		mav.addObject("errorMessage", errorMessage);
	}

}