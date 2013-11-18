package ca.ulaval.glo4003.presentation.controllers;

import javax.inject.Inject;

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

	@Inject
	private SportService service;

	@Autowired
	private User currentUser;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView getSports() {
		ModelAndView mav = new ModelAndView("sport/list");

		Boolean connectedUser = currentUser.isLogged();

		if (connectedUser) {
			mav.addObject("connectedUser", true);
		} else {
			mav.addObject("connectedUser", false);
		}

		SportsViewModel sports = service.getSports();
		mav.addObject("sports", sports);
		return mav;
	}

	@RequestMapping(value = "/{sportUrl}/matchs", method = RequestMethod.GET)
	public ModelAndView getSportGames(@PathVariable String sportUrl) {

		ModelAndView mav = new ModelAndView("sport/games");

		Boolean connectedUser = currentUser.isLogged();

		if (connectedUser) {
			mav.addObject("connectedUser", true);
		} else {
			mav.addObject("connectedUser", false);
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
			e.printStackTrace();
			mav.setViewName("error/404");
			return mav;
		}
	}
}
