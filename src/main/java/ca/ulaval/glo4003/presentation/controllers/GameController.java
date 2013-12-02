package ca.ulaval.glo4003.presentation.controllers;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import ca.ulaval.glo4003.domain.users.User;
import ca.ulaval.glo4003.exceptions.GameDoesntExistException;
import ca.ulaval.glo4003.exceptions.SportDoesntExistException;
import ca.ulaval.glo4003.presentation.viewmodels.GamesViewModel;
import ca.ulaval.glo4003.services.QueryGameService;

@Controller
@SessionAttributes({ "currentUser" })
@RequestMapping(value = "/sport/", method = RequestMethod.GET)
public class GameController {

	@Autowired
	private User currentUser;

	@Inject
	private QueryGameService gameService;

	@RequestMapping(value = "/{sportUrl}/matchs", method = RequestMethod.GET)
	public ModelAndView getGamesForSport(@PathVariable String sportUrl) {

		ModelAndView mav = new ModelAndView("sport/games");

		Boolean connectedUser = currentUser.isLogged();

		if (connectedUser) {
			mav.addObject("connectedUser", true);
		} else {
			mav.addObject("connectedUser", false);
		}

		try {
			GamesViewModel games = gameService.getGamesForSport(sportUrl);
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
