package ca.ulaval.glo4003.web.controllers;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ca.ulaval.glo4003.domain.services.PaymentService;
import ca.ulaval.glo4003.domain.services.SearchService;
import ca.ulaval.glo4003.domain.utilities.Calculator;
import ca.ulaval.glo4003.domain.utilities.Constants;
import ca.ulaval.glo4003.domain.utilities.User;
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
	public ModelAndView home(@ModelAttribute("chooseTicketsForm") ChooseTicketsViewModel chooseTicketsVM) {
		ModelAndView mav = new ModelAndView("payment/home");
		
		mav.addObject("currency", Constants.CURRENCY);
		
		Boolean connectedUser = currentUser.isLogged();
		
		if (connectedUser) {
			logger.info("Payment : Home : usagé connecté");
		} else {
			logger.info("Payment : Home : usagé non connecté");
			return new ModelAndView("payment/not-connected-user");
		}
		
		mav.addObject("chooseTicketsForm", chooseTicketsVM);
		
		try {
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
	public ModelAndView modeOfPayment(@ModelAttribute("chooseTicketsForm") ChooseTicketsViewModel chooseTicketsVM) {
		ModelAndView mav = new ModelAndView("payment/mode-of-payment");
		
		mav.addObject("currency", Constants.CURRENCY);
		
		Boolean connectedUser = currentUser.isLogged();
		
		if (connectedUser) {
			logger.info("Payment : Mode of payment : usagé connecté");
		} else {
			logger.info("Payment : Mode of payment : usagé non connecté");
			return new ModelAndView("payment/not-connected-user");
		}
		
		PaymentViewModel paymentVM = new PaymentViewModel();
		
		mav.addObject("paymentForm", paymentVM);
		mav.addObject("creditCardTypes", paymentService.getCreditCardTypes());
		mav.addObject("cumulativePrice", paymentService.getCumulativePriceFR());
		
		return mav;
	}
	
	@RequestMapping(value = "validation-achat", method = RequestMethod.POST)
	public ModelAndView validate(@ModelAttribute("paymentForm") PaymentViewModel paymentVM) {
		ModelAndView mav = new ModelAndView("payment/valid");
		
		mav.addObject("currency", Constants.CURRENCY);
		
		Boolean connectedUser = currentUser.isLogged();
		
		if (connectedUser) {
			logger.info("Payment : Mode of payment : usagé connecté");
		} else {
			logger.info("Payment : Mode of payment : usagé non connecté");
			return new ModelAndView("payment/not-connected-user");
		}
		
		mav.addObject("cumulativePrice", paymentService.getCumulativePriceFR());
		
		System.out.println("Type " + paymentVM.getCreditCardType());
		System.out.println("CN " + paymentVM.getCreditCardNumber());
		System.out.println("SC " + paymentVM.getSecurityCode());
		System.out.println("Exp M " + paymentVM.getExpirationMonth());
		System.out.println("Exp Y " + paymentVM.getExpirationYear());
		System.out.println("Name " + paymentVM.getCreditCardUserName());
		
		return mav;
	}
}
