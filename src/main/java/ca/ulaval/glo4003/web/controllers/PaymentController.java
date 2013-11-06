package ca.ulaval.glo4003.web.controllers;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ca.ulaval.glo4003.domain.services.PaymentService;
import ca.ulaval.glo4003.domain.services.SearchService;
import ca.ulaval.glo4003.domain.utilities.Constants;
import ca.ulaval.glo4003.persistence.daos.GameDoesntExistException;
import ca.ulaval.glo4003.persistence.daos.SectionDoesntExistException;
import ca.ulaval.glo4003.web.viewmodels.ChooseTicketsViewModel;

@Controller
@RequestMapping(value = "/paiement", method = RequestMethod.GET)
public class PaymentController {
	private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);
	
	@Inject
	SearchService searchService;
	
	@Inject
	PaymentService paymentService;

	@RequestMapping(value = "", method = RequestMethod.POST)
	public ModelAndView home(@ModelAttribute("chooseTicketsForm") ChooseTicketsViewModel chooseTicketsVM) {
		ModelAndView mav = new ModelAndView("payment/home");
		
		mav.addObject("currency", Constants.CURRENCY);
		
		boolean connectedUser = true; // mettre la bonne valeur suivant la situation
		
		if (connectedUser) {
			logger.info("Payment : Home : usagé connecté");
		} else {
			logger.info("Payment : Home : usagé non connecté");
			return new ModelAndView("payment/not-connected-user");
		}
		
		mav.addObject("chooseTicketsForm", chooseTicketsVM);
		
		try {
			mav.addObject("payment", paymentService.getPaymentViewModel(chooseTicketsVM));
		} catch (GameDoesntExistException | SectionDoesntExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return mav;
	}
}
