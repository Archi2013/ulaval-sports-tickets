package ca.ulaval.glo4003.presentation.controllers;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ca.ulaval.glo4003.domain.utilities.user.User;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
 
	@Autowired
	private User currentUser;
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView home(Locale locale) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		ModelAndView mav = new ModelAndView("home");

		mav.addObject("user", currentUser);
		
		manageUserConnection(mav);
		
		return mav;
	}

	private void addConnectedUserToModelAndView(ModelAndView mav,
			Boolean connectedUser) {
		if (connectedUser) {
			mav.addObject("connectedUser", true);
		} else {
			mav.addObject("connectedUser", false);
		}
	}
	
	private void addLogOfUserConnection(Boolean connectedUser) {
		if (connectedUser) {
			logger.info("usagé connecté");
		} else {
			logger.info("usagé non connecté");
		}
	}
	
	private void manageUserConnection(ModelAndView mav) {
		Boolean connectedUser = currentUser.isLogged();

		addConnectedUserToModelAndView(mav, connectedUser);
		
		addLogOfUserConnection(connectedUser);
	}
}