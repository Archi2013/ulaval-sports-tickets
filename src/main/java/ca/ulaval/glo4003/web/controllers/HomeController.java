package ca.ulaval.glo4003.web.controllers;

import java.util.Locale;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ca.ulaval.glo4003.domain.utilities.User;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
 
	@Autowired
	public User currentUser;
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		DateTime now = DateTime.now();
		model.addAttribute("serverTime", now.toString(DateTimeFormat.longDateTime().withLocale(locale)));
		model.addAttribute("user", currentUser);
		return "home";
	}

}
