package ca.ulaval.glo4003.web.controllers;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ca.ulaval.glo4003.domain.services.SportService;
import ca.ulaval.glo4003.domain.utilities.SportDoesntExistInPropertiesFileException;
import ca.ulaval.glo4003.domain.utilities.SportUrlMapper;
import ca.ulaval.glo4003.persistence.daos.SportDoesntExistException;
import ca.ulaval.glo4003.web.viewmodels.GamesViewModel;
import ca.ulaval.glo4003.web.viewmodels.SportsViewModel;

@Controller
@RequestMapping(value = "/sport", method = RequestMethod.GET)
public class SportController {

	private static final Logger logger = LoggerFactory.getLogger(SportController.class);

	@Inject
	private SportUrlMapper sportUrlMapper;

	@Inject
	private SportService service;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String getSports(Model model) {
		logger.info("Getting all sports");

		SportsViewModel sports = service.getSports();
		model.addAttribute("sports", sports);
		return "sport/list";
	}

	@RequestMapping(value = "/{sportUrl}/matchs", method = RequestMethod.GET)
	public String getSportGames(@PathVariable String sportUrl, Model model) {
		try {
			String sportName = sportUrlMapper.getSportName(sportUrl);
			logger.info("Getting games for sport: " + sportName);

			GamesViewModel games = service.getGamesForSport(sportName);
			model.addAttribute("games", games);

			if (games.hasGames()) {
				return "sport/games";
			} else {
				return "sport/no-games";
			}
		} catch (RuntimeException | SportDoesntExistInPropertiesFileException | SportDoesntExistException e) {
			logger.info("==> Impossible to get games for sport: " + sportUrl);
			e.printStackTrace();
			return "error/404";
		}
	}
}
