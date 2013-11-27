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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import ca.ulaval.glo4003.domain.users.User;
import ca.ulaval.glo4003.presentation.viewmodels.PaymentViewModel;
import ca.ulaval.glo4003.services.PaymentService;

@Controller
@SessionAttributes({ "currentUser" })
@RequestMapping(value = "/paiement", method = RequestMethod.GET)
public class PaymentController {
	
	private static final String ERROR_MESSAGE_NOT_CONNECTED_USER = "error-message.payment.not-connected-user";

	private static final String ERROR_PAGE = "payment/error-page";

	private static final String MODE_OF_PAYMENT_PAGE = "payment/mode-of-payment";

	private static final String VALIDATION_SUCCES_PAGE = "payment/succes";

	@Inject
	PaymentService paymentService;

	@Autowired
	private User currentUser;

	@Inject
	private MessageSource messageSource;

	@RequestMapping(value = "mode-de-paiement", method = RequestMethod.GET)
	public ModelAndView modeOfPayment() {
		ModelAndView mav = new ModelAndView(MODE_OF_PAYMENT_PAGE);

		Boolean connectedUser = currentUser.isLogged();

		addConnectedUserToModelAndView(connectedUser, mav);

		if (!connectedUser) {
			prepareErrorPageToShowNotConnectedUserMessage(mav);
			return mav;
		}
		
		paymentService.prepareModeOfPaymentView(mav);

		return mav;
	}

	@RequestMapping(value = "validation-achat", method = RequestMethod.POST)
	public ModelAndView validate(@ModelAttribute("paymentForm") @Valid PaymentViewModel paymentVM, BindingResult result) {
		
		ModelAndView mav = new ModelAndView(VALIDATION_SUCCES_PAGE);

		Boolean connectedUser = currentUser.isLogged();

		addConnectedUserToModelAndView(connectedUser, mav);

		if (!connectedUser) {
			prepareErrorPageToShowNotConnectedUserMessage(mav);
			return mav;
		}

		paymentService.prepareValidationViewAndCart(mav, result, paymentVM);
		
		return mav;
	}

	private void prepareErrorPageToShowNotConnectedUserMessage(ModelAndView mav) {
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
}
