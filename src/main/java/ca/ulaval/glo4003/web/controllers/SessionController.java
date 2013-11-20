package ca.ulaval.glo4003.web.controllers;

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
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import ca.ulaval.glo4003.domain.services.UserService;
import ca.ulaval.glo4003.domain.utilities.user.User;
import ca.ulaval.glo4003.domain.utilities.user.UserAlreadyExistException;
import ca.ulaval.glo4003.domain.utilities.user.UserDoesntExistException;
import ca.ulaval.glo4003.domain.utilities.user.UsernameAndPasswordDoesntMatchException;
import ca.ulaval.glo4003.web.viewmodels.UserViewModel;

@Controller
@RequestMapping(value = "/session", method = RequestMethod.GET)
@SessionAttributes({ "currentUser" })
public class SessionController {

	@Inject
	private UserService userService;

	@Autowired
	private User currentUser;

	private static final Logger logger = LoggerFactory.getLogger(SessionController.class);

	@RequestMapping(value = "/signin", method = RequestMethod.GET)
	public ModelAndView signIn() {
		logger.info("Sign In");

		ModelAndView mav = new ModelAndView("session/logged");

		mav.addObject("user", currentUser);

		Boolean connectedUser = currentUser.isLogged();

		if (connectedUser) {
			mav.addObject("connectedUser", true);
			logger.info("usagé connecté");
		} else {
			mav.addObject("connectedUser", false);
			logger.info("usagé non connecté");
			mav.setViewName("session/signin");
		}
		return mav;
	}

	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public ModelAndView signUp() {
		logger.info("Sign Up");

		ModelAndView mav = new ModelAndView("session/logged");

		mav.addObject("user", currentUser);

		Boolean connectedUser = currentUser.isLogged();

		if (connectedUser) {
			mav.addObject("connectedUser", true);
			logger.info("usagé connecté");
		} else {
			mav.addObject("connectedUser", false);
			logger.info("usagé non connecté");
			mav.setViewName("session/signup");
		}
		return mav;
	}

	@RequestMapping(value = "/auth", method = RequestMethod.POST)
	public ModelAndView submitSignIn(Model model, @RequestParam String usernameParam, @RequestParam String passwordParam) {
		logger.info("Authentification");

		ModelAndView mav = new ModelAndView("session/success");

		try {
			UserViewModel userViewModel = userService.signIn(usernameParam, passwordParam);
			mav.addObject("user", userViewModel);
			mav.addObject("connectedUser", true);
			model.addAttribute("currentUser", currentUser);
			return mav;
		} catch (UserDoesntExistException | UsernameAndPasswordDoesntMatchException e) {
			logger.info("==> Impossible to Sign In : " + usernameParam);
			mav.addObject("connectedUser", false);
			mav.setViewName("session/retry");
			return mav;
		}
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView registerUser(Model model, @RequestParam String usernameParam, @RequestParam String passwordParam) {
		ModelAndView mav = new ModelAndView("session/signin");

		Boolean connectedUser = currentUser.isLogged();

		if (connectedUser) {
			mav.addObject("connectedUser", true);
			logger.info("usagé connecté");
		} else {
			mav.addObject("connectedUser", false);
			logger.info("usagé non connecté");
		}

		try {
			userService.signUp(usernameParam, passwordParam);
			return submitSignIn(model, usernameParam, passwordParam);
		} catch (UserAlreadyExistException e) {
			mav.setViewName("session/exist");
			return mav;
		}
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ModelAndView logoutUser(SessionStatus sessionStatus) {
		userService.logOutCurrentUser();
		sessionStatus.setComplete();

		ModelAndView mav = new ModelAndView("session/logout");

		Boolean connectedUser = currentUser.isLogged();

		if (connectedUser) {
			mav.addObject("connectedUser", true);
			logger.info("usagé connecté");
		} else {
			mav.addObject("connectedUser", false);
			logger.info("usagé non connecté");
		}

		return mav;
	}

	@RequestMapping(value = "/current", method = RequestMethod.GET)
	public ModelAndView showCurrentUser() {
		ModelAndView mav = new ModelAndView("session/success");

		Boolean connectedUser = currentUser.isLogged();

		if (connectedUser) {
			mav.addObject("connectedUser", true);
			logger.info("usagé connecté");
		} else {
			mav.addObject("connectedUser", false);
			logger.info("usagé non connecté");
			mav.setViewName("session/logout");
		}
		mav.addObject("user", currentUser);
		return mav;
	}
}
