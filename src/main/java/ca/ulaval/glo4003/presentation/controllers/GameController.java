package ca.ulaval.glo4003.presentation.controllers;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import ca.ulaval.glo4003.exceptions.GameDoesntExistException;
import ca.ulaval.glo4003.exceptions.NoSportForUrlException;
import ca.ulaval.glo4003.exceptions.SportDoesntExistException;
import ca.ulaval.glo4003.presentation.viewmodels.GamesViewModel;
import ca.ulaval.glo4003.services.GameViewService;
import ca.ulaval.glo4003.utilities.urlmapper.SportUrlMapper;

@Controller
@SessionAttributes({ "currentUser" })
@RequestMapping(value = "/sport/{sportUrl}", method = RequestMethod.GET)
public class GameController {

	private static final String GAME_LIST_PAGE = "game/list";

	private static final String NO_GAMES_PAGE = "game/no-games";

	private static final String ERROR_404_PAGE = "error/404";

	@Inject
	private GameViewService gameViewService;

	@Inject
	private SportUrlMapper sportUrlMapper;

	@RequestMapping(value = "/matchs", method = RequestMethod.GET)
	public ModelAndView getGamesForSport(@PathVariable String sportUrl) {

		ModelAndView mav = new ModelAndView(GAME_LIST_PAGE);

		try {
			GamesViewModel games = gameViewService.getGamesForSport(sportUrlMapper.getSportName(sportUrl));
			mav.addObject("games", games);

			if (games.hasGames()) {
				return mav;
			} else {
				mav.setViewName(NO_GAMES_PAGE);
				return mav;
			}
		} catch (RuntimeException | NoSportForUrlException | SportDoesntExistException | GameDoesntExistException e) {
			mav.setViewName(ERROR_404_PAGE);
			return mav;
		}
	}
}
