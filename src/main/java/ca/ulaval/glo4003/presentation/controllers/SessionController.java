package ca.ulaval.glo4003.presentation.controllers;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import ca.ulaval.glo4003.domain.users.User;
import ca.ulaval.glo4003.domain.users.UserAlreadyExistException;
import ca.ulaval.glo4003.domain.users.UserDoesntExistException;
import ca.ulaval.glo4003.domain.users.UsernameAndPasswordDoesntMatchException;
import ca.ulaval.glo4003.presentation.viewmodels.UserViewModel;
import ca.ulaval.glo4003.services.UserService;


@Controller
@RequestMapping(value = "/usager", method = RequestMethod.GET)
@SessionAttributes({ "currentUser" })
public class SessionController {
	
	@Inject
	private UserService userService;

	@Autowired
	private User currentUser;
	
	@RequestMapping(value = "/connexion", method = RequestMethod.GET)
	public ModelAndView signIn() {
		
		ModelAndView mav = new ModelAndView("session/logged");
		
		if (!currentUser.isLogged()) {
			mav.setViewName("session/signin");
		}
		return mav;
	}
	
	@RequestMapping(value = "/inscription", method = RequestMethod.GET)
	public ModelAndView signUp() {
		ModelAndView mav = new ModelAndView("session/logged");
		
		if (!currentUser.isLogged()) {
			mav.setViewName("session/signup");
		}
		return mav;
	}
	
	@RequestMapping(value = "/authentification", method = RequestMethod.POST)
	public ModelAndView submitSignIn(Model model, @RequestParam String usernameParam, @RequestParam String passwordParam){
		ModelAndView mav = new ModelAndView("session/success");
		
		try {
			UserViewModel userViewModel = userService.signIn(usernameParam, passwordParam);
			mav.addObject("user", userViewModel); 
			model.addAttribute("currentUser", currentUser);
	        return mav;
		} catch (UserDoesntExistException | UsernameAndPasswordDoesntMatchException e) {
			mav.setViewName("session/retry");
			return mav;
		}
	}
	
	@RequestMapping(value="/enregistrer",method = RequestMethod.POST)    
    public ModelAndView registerUser(Model model, @RequestParam String usernameParam, @RequestParam String passwordParam) { 
		ModelAndView mav = new ModelAndView("session/signin");
		
		try {
			userService.signUp(usernameParam, passwordParam);
	        return submitSignIn(model, usernameParam, passwordParam);
		} catch (UserAlreadyExistException e) {
			mav.setViewName("session/exist");
			return mav;
		} 
    } 
	
	@RequestMapping(value="/deconnexion",method = RequestMethod.GET)    
    public ModelAndView logoutUser(SessionStatus sessionStatus) {
		userService.logOutCurrentUser(); 
		sessionStatus.setComplete();
		
		ModelAndView mav = new ModelAndView("session/logout");

        return mav;  
    } 
	
	@RequestMapping(value="/courrant",method = RequestMethod.GET)
	public ModelAndView showCurrentUser(){
		ModelAndView mav = new ModelAndView("session/success");

		if (!currentUser.isLogged()) {
			mav.setViewName("session/logout");
		}
		return mav;
	}
}
