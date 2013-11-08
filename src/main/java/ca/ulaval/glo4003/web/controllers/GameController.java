package ca.ulaval.glo4003.web.controllers;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ca.ulaval.glo4003.domain.services.QueryGameService;
import ca.ulaval.glo4003.domain.utilities.Constants;
import ca.ulaval.glo4003.domain.utilities.user.User;
import ca.ulaval.glo4003.persistence.daos.GameDoesntExistException;
import ca.ulaval.glo4003.web.viewmodels.SectionsViewModel;

@Controller
@RequestMapping(value = "/sport/{sportNameUrl}/match", method = RequestMethod.GET)
public class GameController {
	private static final Logger logger = LoggerFactory.getLogger(GameController.class);

	@Autowired
	private User currentUser;
	
	@Inject
	private QueryGameService gameService;

	@RequestMapping(value = "/{gameId}/billets", method = RequestMethod.GET)
	public ModelAndView getTicketsForGame(@PathVariable Long gameId, @PathVariable String sportNameUrl) {
		try {
			logger.info("Getting all tickets for game : " + gameId);
			
			ModelAndView mav = new ModelAndView("game/sections");
			
			Boolean connectedUser = currentUser.isLogged();
			
			if (connectedUser) {
				mav.addObject("connectedUser", true);
				logger.info("usagé connecté");
			} else {
				mav.addObject("connectedUser", false);
				logger.info("usagé non connecté");
			}
			
			mav.addObject("currency", Constants.CURRENCY);

			SectionsViewModel sectionsViewModel = gameService.getSectionsForGame(gameId);
			mav.addObject("gameSections", sectionsViewModel);
			return mav;

		} catch (GameDoesntExistException e) {
			logger.info("==> Impossible to get all tickets for game : " + gameId);
			return new ModelAndView("error/404");
		}
	}
}
