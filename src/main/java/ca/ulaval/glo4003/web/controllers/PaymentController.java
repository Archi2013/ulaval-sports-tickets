package ca.ulaval.glo4003.web.controllers;

import javax.inject.Inject;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import ca.ulaval.glo4003.domain.utilities.payment.InvalidCardException;
import ca.ulaval.glo4003.domain.utilities.user.User;
import ca.ulaval.glo4003.persistence.daos.GameDoesntExistException;
import ca.ulaval.glo4003.persistence.daos.SectionDoesntExistException;
import ca.ulaval.glo4003.web.viewmodels.ChooseTicketsViewModel;
import ca.ulaval.glo4003.web.viewmodels.PaymentViewModel;

@Controller
@RequestMapping(value = "/paiement", method = RequestMethod.GET)
public class PaymentController {
	private static final String TRAFFICKED_PAGE = "payment/trafficked";

	private static final String HOME_PAGE = "payment/home";

	private static final String MODE_OF_PAYMENT_PAGE = "payment/mode-of-payment";

	private static final String VALIDATION_SUCCES_PAGE = "payment/succes";

	private static final String NO_TICKETS_PAGE = "payment/no-tickets";

	private static final String NOT_CONNECTED_USER_PAGE = "payment/not-connected-user";

	private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);
	
	@Inject
	SearchService searchService;
	
	@Inject
	PaymentService paymentService;
	
	@Autowired
	private User currentUser;

	@RequestMapping(value = "", method = RequestMethod.POST)
	public ModelAndView home(@ModelAttribute("chooseTicketsForm") @Valid ChooseTicketsViewModel chooseTicketsVM,
			BindingResult result) {		
		Boolean connectedUser = currentUser.isLogged();
		
		ModelAndView mav = new ModelAndView(HOME_PAGE);
		
		addConnectedUserToModelAndView(connectedUser, mav);
		
		addLogOfUserConnection(connectedUser);
		
		if (!connectedUser) {
			mav.setViewName(NOT_CONNECTED_USER_PAGE);
			return mav;
		}
		
		if(result.hasErrors()) {
			mav.setViewName(TRAFFICKED_PAGE);
            return mav;
        }
		
		mav.addObject("currency", Constants.CURRENCY);
		
		try {
			if (!paymentService.isValidChooseTicketsViewModel(chooseTicketsVM)) {
				String errorMessage = "Une erreur s'est produite lors de la vérification des éléments choisis. "
						+ "Veuillez réessayer en recommençant votre sélection de billets. <a href=\"/\">Accueil</a>";
				mav.addObject("errorMessage", errorMessage);
				return mav;
			}
			mav.addObject("payableItems", paymentService.getPayableItemsViewModel(chooseTicketsVM));
			paymentService.saveToCart(chooseTicketsVM);
		} catch (GameDoesntExistException | SectionDoesntExistException e) {
			String errorMessage = "Une erreur s'est produite lors du passage à la phase de paiement. "
					+ "Veuillez réessayer en recommençant votre sélection de billets. <a href=\"/\">Accueil</a>";
			mav.addObject("errorMessage", errorMessage);
		}	
		
		return mav;
	}
	
	@RequestMapping(value = "mode-de-paiement", method = RequestMethod.GET)
	public ModelAndView modeOfPayment() {
		ModelAndView mav = new ModelAndView(MODE_OF_PAYMENT_PAGE);
		
		Boolean connectedUser = currentUser.isLogged();
		
		addConnectedUserToModelAndView(connectedUser, mav);
		
		addLogOfUserConnection(connectedUser);
		
		if (!connectedUser) {
			mav.setViewName(NOT_CONNECTED_USER_PAGE);
			return mav;
		}
		
		mav.addObject("currency", Constants.CURRENCY);
		
		mav.addObject("paymentForm", new PaymentViewModel());
		mav.addObject("creditCardTypes", paymentService.getCreditCardTypes());
		try {
			mav.addObject("cumulativePrice", paymentService.getCumulativePriceFR());
		} catch (NoTicketsInCartException e) {
			mav.setViewName(NO_TICKETS_PAGE);
		}
		
		return mav;
	}
	
	@RequestMapping(value = "validation-achat", method = RequestMethod.POST)
	public ModelAndView validate(@ModelAttribute("paymentForm") @Valid PaymentViewModel paymentVM,
			BindingResult result) {
		ModelAndView mav = new ModelAndView(VALIDATION_SUCCES_PAGE);
		
		Boolean connectedUser = currentUser.isLogged();
		
		addConnectedUserToModelAndView(connectedUser, mav);
		
		addLogOfUserConnection(connectedUser);
		
		if (!connectedUser) {
			mav.setViewName(NOT_CONNECTED_USER_PAGE);
			return mav;
		}
		
		mav.addObject("currency", Constants.CURRENCY);
		
		try {
			mav.addObject("cumulativePrice", paymentService.getCumulativePriceFR());
		} catch (NoTicketsInCartException e) {
			mav.setViewName(NO_TICKETS_PAGE);
			return mav;
		}
		
		if(result.hasErrors()) {
			modifyModelAndViewToRetryModeOfPayment(paymentVM, mav);
            return mav;
        }
		
		try {
			paymentService.payAmount(paymentVM);
		} catch (InvalidCardException e) {
			modifyModelAndViewToRetryModeOfPayment(paymentVM, mav);
			String errorMessage = "Une erreur s'est produite lors de la vérification de votre carte de crédit. "
					+ "Veuillez réessayer en modifiants les informations fournies.";
			mav.addObject("errorMessage", errorMessage);
            return mav;
		}
		
		paymentService.emptyCart();
		
		return mav;
	}
	
	private void addLogOfUserConnection(Boolean connectedUser) {
		if (connectedUser) {
			logger.info("usagé connecté");
		} else {
			logger.info("usagé non connecté");
		}
	}

	private void addConnectedUserToModelAndView(Boolean connectedUser,
			ModelAndView mav) {
		if (connectedUser) {
			mav.addObject("connectedUser", true);
		} else {
			mav.addObject("connectedUser", false);
		}
	}
	
	private void modifyModelAndViewToRetryModeOfPayment(PaymentViewModel paymentVM,
			ModelAndView mav) {
		mav.setViewName(MODE_OF_PAYMENT_PAGE);
		mav.addObject("paymentForm", paymentVM);
		mav.addObject("creditCardTypes", paymentService.getCreditCardTypes());
	}
}
