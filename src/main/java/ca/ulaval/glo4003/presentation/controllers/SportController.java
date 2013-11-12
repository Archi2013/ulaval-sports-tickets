package ca.ulaval.glo4003.presentation.controllers;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ca.ulaval.glo4003.domain.services.SportService;
import ca.ulaval.glo4003.domain.utilities.user.User;
import ca.ulaval.glo4003.persistence.daos.GameDoesntExistException;
import ca.ulaval.glo4003.persistence.daos.SportDoesntExistException;
import ca.ulaval.glo4003.presentation.viewmodels.GamesViewModel;
import ca.ulaval.glo4003.presentation.viewmodels.SportsViewModel;

@Controller
@RequestMapping(value = "/sport", method = RequestMethod.GET)
public class SportController {

	private static final Logger logger = LoggerFactory.getLogger(SportController.class);

	@Inject
	private SportService service;

	@Autowired
	private User currentUser;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView getSports() {
		logger.info("Getting all sports");

		ModelAndView mav = new ModelAndView("sport/list");

		Boolean connectedUser = currentUser.isLogged();

		if (connectedUser) {
			mav.addObject("connectedUser", true);
			logger.info("usagé connecté");
		} else {
			mav.addObject("connectedUser", false);
			logger.info("usagé non connecté");
		}

		SportsViewModel sports = service.getSports();
		mav.addObject("sports", sports);
		return mav;
	}

	@RequestMapping(value = "/{sportUrl}/matchs", method = RequestMethod.GET)
	public ModelAndView getSportGames(@PathVariable String sportUrl) {
		logger.info("Getting games for sport: " + sportUrl);

		ModelAndView mav = new ModelAndView("sport/games");

		Boolean connectedUser = currentUser.isLogged();

		if (connectedUser) {
			mav.addObject("connectedUser", true);
			logger.info("usagé connecté");
		} else {
			mav.addObject("connectedUser", false);
			logger.info("usagé non connecté");
		}

		try {
			GamesViewModel games = service.getGamesForSport(sportUrl);
			mav.addObject("games", games);

			if (games.hasGames()) {
				return mav;
			} else {
				mav.setViewName("sport/no-games");
				return mav;
			}
		} catch (RuntimeException | SportDoesntExistException | GameDoesntExistException e) {
			logger.info("==> Impossible to get games for sport: " + sportUrl);
			e.printStackTrace();
			mav.setViewName("error/404");
			return mav;
		}
	}
}
