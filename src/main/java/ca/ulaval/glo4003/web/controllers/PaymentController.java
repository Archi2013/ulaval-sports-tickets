package ca.ulaval.glo4003.web.controllers;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ca.ulaval.glo4003.domain.services.SearchService;
import ca.ulaval.glo4003.web.viewmodels.ChooseTicketsViewModel;

@Controller
@RequestMapping(value = "/paiement", method = RequestMethod.GET)
public class PaymentController {
	private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);
	
	@Inject
	SearchService searchService;

	@RequestMapping(value = "", method = RequestMethod.POST)
	public ModelAndView home(@ModelAttribute("chooseTicketsForm") ChooseTicketsViewModel chooseTicketsVM) {
		ModelAndView mav = new ModelAndView("payment/home");
		
		boolean connectedUser = true; // mettre la bonne valeur suivant la situation
		
		if (connectedUser) {
			mav.addObject("connectedUser", true);
			logger.info("Search : Home : usagé connecté");
		} else {
			mav.addObject("connectedUser", false);
			logger.info("Search : Home : usagé non connecté");
		}
		
		mav.addObject("x", chooseTicketsVM.getSport());
		
		return mav;
	}
}
