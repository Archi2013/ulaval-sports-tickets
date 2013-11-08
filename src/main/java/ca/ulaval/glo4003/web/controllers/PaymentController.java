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

import ca.ulaval.glo4003.domain.services.PaymentService;
import ca.ulaval.glo4003.domain.services.SearchService;
import ca.ulaval.glo4003.domain.utilities.Calculator;
import ca.ulaval.glo4003.domain.utilities.Constants;
import ca.ulaval.glo4003.domain.utilities.User;
import ca.ulaval.glo4003.domain.utilities.payment.InvalidCardException;
import ca.ulaval.glo4003.persistence.daos.GameDoesntExistException;
import ca.ulaval.glo4003.persistence.daos.SectionDoesntExistException;
import ca.ulaval.glo4003.web.viewmodels.ChooseTicketsViewModel;
import ca.ulaval.glo4003.web.viewmodels.PaymentViewModel;

@Controller
@RequestMapping(value = "/paiement", method = RequestMethod.GET)
public class PaymentController {
	private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);
	
	@Inject
	SearchService searchService;
	
	@Inject
	PaymentService paymentService;
	
	@Autowired
	private User currentUser;
	
	@Inject
	Calculator calculator;

	@RequestMapping(value = "", method = RequestMethod.POST)
	public ModelAndView home(@ModelAttribute("chooseTicketsForm") @Valid ChooseTicketsViewModel chooseTicketsVM,
			BindingResult result) {		
		Boolean connectedUser = currentUser.isLogged();
		
		ModelAndView mav = new ModelAndView("payment/home");
		
		if (connectedUser) {
			mav.addObject("connectedUser", true);
			logger.info("usagé connecté");
		} else {
			mav.addObject("connectedUser", false);
			mav.setViewName("payment/not-connected-user");
			logger.info("usagé non connecté");
			return mav;
		}
		
		if(result.hasErrors()) {
			mav.setViewName("payment/trafficked-page");
            return mav;
        }
		
		mav.addObject("currency", Constants.CURRENCY);
		
		try {
			if (!paymentService.isValidPayableItemsViewModel(chooseTicketsVM)) {
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
		ModelAndView mav = new ModelAndView("payment/mode-of-payment");
		
		Boolean connectedUser = currentUser.isLogged();
		
		if (connectedUser) {
			mav.addObject("connectedUser", true);
			logger.info("usagé connecté");
		} else {
			mav.addObject("connectedUser", false);
			mav.setViewName("payment/not-connected-user");
			logger.info("usagé non connecté");
			return mav;
		}
		
		mav.addObject("currency", Constants.CURRENCY);
		
		mav.addObject("paymentForm", new PaymentViewModel());
		mav.addObject("creditCardTypes", paymentService.getCreditCardTypes());
		mav.addObject("cumulativePrice", paymentService.getCumulativePriceFR());
		
		return mav;
	}
	
	@RequestMapping(value = "validation-achat", method = RequestMethod.POST)
	public ModelAndView validate(@ModelAttribute("paymentForm") @Valid PaymentViewModel paymentVM,
			BindingResult result) {
		ModelAndView mav = new ModelAndView("payment/valid");
		
		Boolean connectedUser = currentUser.isLogged();
		
		if (connectedUser) {
			mav.addObject("connectedUser", true);
			logger.info("usagé connecté");
		} else {
			mav.addObject("connectedUser", false);
			mav.setViewName("payment/not-connected-user");
			logger.info("usagé non connecté");
			return mav;
		}
		
		if(result.hasErrors()) {
			modifyToRetryModeOfPayment(paymentVM, mav);
            return mav;
        }
		
		try {
			paymentService.payAmount(paymentVM);
		} catch (InvalidCardException e) {
			modifyToRetryModeOfPayment(paymentVM, mav);
			String errorMessage = "Une erreur s'est produite lors de la vérification de votre carte de crédit. "
					+ "Veuillez réessayer en modifiants les informations fournies.";
			mav.addObject("errorMessage", errorMessage);
            return mav;
		}
		
		mav.addObject("currency", Constants.CURRENCY);
		
		mav.addObject("cumulativePrice", paymentService.getCumulativePriceFR());
		
		paymentService.emptyCart();
		
		return mav;
	}

	private void modifyToRetryModeOfPayment(PaymentViewModel paymentVM,
			ModelAndView mav) {
		mav.setViewName("payment/mode-of-payment");
		mav.addObject("paymentForm", paymentVM);
		mav.addObject("creditCardTypes", paymentService.getCreditCardTypes());
		mav.addObject("cumulativePrice", paymentService.getCumulativePriceFR());
	}
}
