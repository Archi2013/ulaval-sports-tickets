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

import ca.ulaval.glo4003.domain.services.PaymentService;
import ca.ulaval.glo4003.domain.users.User;
import ca.ulaval.glo4003.presentation.viewmodels.ChooseTicketsViewModel;

@Controller
@SessionAttributes({ "currentUser" })
@RequestMapping(value = "/panier", method = RequestMethod.GET)
public class CartController {
	
	private static final String ERROR_MESSAGE_NOT_CONNECTED_USER = "error-message.payment.not-connected-user";
	
	private static final String ERROR_PAGE = "cart/error-page";

	private static final String ADD_TICKETS_PAGE = "cart/add-tickets";
	
	@Inject
	PaymentService paymentService;

	@Autowired
	private User currentUser;

	@Inject
	private MessageSource messageSource;

	@RequestMapping(value = "ajout-billets", method = RequestMethod.POST)
	public ModelAndView addToCart(@ModelAttribute("chooseTicketsForm") @Valid ChooseTicketsViewModel chooseTicketsVM,
			BindingResult result) {
		Boolean connectedUser = currentUser.isLogged();

		ModelAndView mav = new ModelAndView(ADD_TICKETS_PAGE);

		addConnectedUserToModelAndView(connectedUser, mav);

		if (!connectedUser) {
			prepareErrorPageToShowNotConnectedUserMessage(mav);
			return mav;
		}
		
		paymentService.prepareCartViewAndCart(mav, result, chooseTicketsVM);

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
