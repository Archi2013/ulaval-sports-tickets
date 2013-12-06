package ca.ulaval.glo4003.presentation.controllers;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import ca.ulaval.glo4003.domain.sports.SportUrlMapper;
import ca.ulaval.glo4003.exceptions.GameDoesntExistException;
import ca.ulaval.glo4003.exceptions.NoSportForUrlException;
import ca.ulaval.glo4003.exceptions.SportDoesntExistException;
import ca.ulaval.glo4003.presentation.viewmodels.GamesViewModel;
import ca.ulaval.glo4003.services.QueryGameService;

@Controller
@SessionAttributes({ "currentUser" })
@RequestMapping(value = "/sport/{sportUrl}", method = RequestMethod.GET)
public class GameController {

	private static final String ERROR_404_PAGE = "error/404";

	@Inject
	private QueryGameService gameService;

	@Inject
	private SportUrlMapper sportUrlMapper;

	@RequestMapping(value = "/matchs", method = RequestMethod.GET)
	public ModelAndView getGamesForSport(@PathVariable String sportUrl) {

		ModelAndView mav = new ModelAndView("game/list");

		try {
			String sportName = sportUrlMapper.getSportName(sportUrl);
			GamesViewModel games = gameService.getGamesForSport(sportName);
			mav.addObject("games", games);

			if (games.hasGames()) {
				return mav;
			} else {
				mav.setViewName("game/no-games");
				return mav;
			}
		} catch (RuntimeException | NoSportForUrlException | SportDoesntExistException | GameDoesntExistException e) {
			mav.setViewName(ERROR_404_PAGE);
			return mav;
		}
	}
}
