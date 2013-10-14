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
import ca.ulaval.glo4003.web.viewmodels.SportsViewModel;


@Controller

@RequestMapping(value = "/session", method = RequestMethod.GET)
public class SessionController {
	
	@Inject
	private UserService userService;

	@Autowired
	public User currentUser;
	
	private static final Logger logger = LoggerFactory.getLogger(SessionController.class);
	
	@RequestMapping(value = "/signin", method = RequestMethod.GET)
	public String signIn(Model model) {
		logger.info("Sign In");
		
		model.addAttribute("user", currentUser);
		if (currentUser.isLogged()) {
			return "session/logged";
		} else {
			return "session/signin";
		}	
		
	}
	
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signUp(Model model) {
		logger.info("Sign Up");
		
		model.addAttribute("user", currentUser);
		if (currentUser.isLogged()) {
			return "session/logged";
		} else {
			return "session/signup";
		}
	}
	
	@RequestMapping(value = "/auth", method = RequestMethod.POST)
	public String submitSignIn(@RequestParam String usernameParam,@RequestParam String passwordParam,Model model){
		logger.info("Authentification");
		
		try {
			UserDto user = userService.signIn(usernameParam,passwordParam);
			model.addAttribute("user", currentUser); 
	        return "session/success";
		} catch (UserDoesntExistException | UsernameAndPasswordDoesntMatchException e) {
			logger.info("==> Impossible to Sign In : " + usernameParam);
			return "session/retry";
		}
	
	}
	
	@RequestMapping(value="/save",method = RequestMethod.POST)    
    public String registerUser(@RequestParam String usernameParam,@RequestParam String passwordParam,Model model) { 
		
		userService.signUp(usernameParam, passwordParam);
		model.addAttribute("user", currentUser); 
        return "session/success";  
        
    } 
	
	@RequestMapping(value="/logout",method = RequestMethod.GET)    
    public String logoutUser(Model model) { 
		userService.logOutCurrentUser(); 
        return "session/logout";  
    } 
	
	@RequestMapping(value="/current",method = RequestMethod.GET)
	public String showCurrentUser(Model model){
		
		model.addAttribute("user", currentUser); 
		if (currentUser.isLogged()) {
			return "session/success";
		} else {
			return "session/logout";
		}
	}

}
