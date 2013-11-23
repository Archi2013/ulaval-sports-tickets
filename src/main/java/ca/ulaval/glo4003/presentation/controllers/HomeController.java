package ca.ulaval.glo4003.presentation.controllers;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import ca.ulaval.glo4003.domain.users.User;

/**
 * Handles requests for the application home page.
 */
@Controller
@SessionAttributes({ "currentUser" })
public class HomeController {
 
	@Autowired
	private User currentUser;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView home(Locale locale) {
		
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
	
	private void manageUserConnection(ModelAndView mav) {
		Boolean connectedUser = currentUser.isLogged();

		addConnectedUserToModelAndView(mav, connectedUser);
	}
}
