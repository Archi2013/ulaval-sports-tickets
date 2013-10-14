package ca.ulaval.glo4003.session.beta;

import java.util.Locale;

import javax.inject.Inject;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import ca.ulaval.glo4003.domain.services.QueryGameService;


@Controller

public class SessionController {
	
	@Inject
	private UserService userService;

	@Autowired
	public User currentUser;
	
	private static final Logger logger = LoggerFactory.getLogger(SessionController.class);

	@RequestMapping(value = "/session/signup", method = RequestMethod.GET)
	public String signUp(Model model) {
		logger.info("Sign Up");
	
		return "session/signup";
	}
	
	@RequestMapping(value = "/session/signin", method = RequestMethod.GET)
	public String signIn(Model model){
		logger.info("Sign In");
		
		return "session/signin";
	}
	
	@RequestMapping(value="session/save",method = RequestMethod.POST)    
    public String registerUser(@RequestParam String usernameParam,@RequestParam String passwordParam,Model model) { 
		
		
		userService.signIn(usernameParam,passwordParam);
		model.addAttribute("user", currentUser); 
        return "session/success";  
    } 
	
	@RequestMapping(value="/session/current",method = RequestMethod.GET)
	public String showCurrentUser(Model model){
		
		model.addAttribute("user", currentUser); 
		return "session/success";
	}

}
