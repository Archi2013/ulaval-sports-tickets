package ca.ulaval.glo4003.presentation.controllers;

import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

/**
 * Handles requests for the application home page.
 */
@Controller
@SessionAttributes({ "currentUser" })
public class HomeController {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView home(Locale locale) {
		return new ModelAndView("home");
	}
}
