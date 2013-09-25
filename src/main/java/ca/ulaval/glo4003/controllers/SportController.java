package ca.ulaval.glo4003.controllers;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ca.ulaval.glo4003.data_access.GameDao;
import ca.ulaval.glo4003.data_access.SportDao;
import ca.ulaval.glo4003.data_access.SportDoesntExistException;
import ca.ulaval.glo4003.dtos.GameDto;
import ca.ulaval.glo4003.dtos.SportDto;

@Controller
@RequestMapping(value = "/sport", method = RequestMethod.GET)
public class SportController {

	private static final Logger logger = LoggerFactory.getLogger(SportController.class);

	@Inject
	private SportDao dao;

	@Inject
	private GameDao gameDao;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String getSports(Model model) {
		logger.info("Getting all sports");

		List<SportDto> sports = dao.getAll();
		model.addAttribute("sports", sports);

		return "sport/list";
	}

	@RequestMapping(value = "/{sportName}/matchs", method = RequestMethod.GET)
	public String getSportGames(@PathVariable String sportName, Model model) {
		logger.info("Getting games for sport: " + sportName);
		model.addAttribute("sportName", sportName);

		try {
			List<GameDto> games = gameDao.getGamesForSport(sportName);
			if (games.isEmpty()) {
				return "sport/no-games";
			} else {
				model.addAttribute("games", games);
				return "sport/games";
			}
		} catch (SportDoesntExistException e) {
			logger.info("Impossible to get games for sport: " + sportName);
			return "error/404";
		}
	}
}
