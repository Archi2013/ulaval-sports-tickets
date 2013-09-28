package ca.ulaval.glo4003.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ca.ulaval.glo4003.dao.GameDao;
import ca.ulaval.glo4003.dao.SportDao;
import ca.ulaval.glo4003.dao.SportDoesntExistException;
import ca.ulaval.glo4003.datafilter.DataFilter;
import ca.ulaval.glo4003.dto.GameDto;
import ca.ulaval.glo4003.dto.SportDto;
import ca.ulaval.glo4003.utility.SportDoesntExistInPropertieFileException;
import ca.ulaval.glo4003.utility.SportUrlMapper;

@Controller
@RequestMapping(value = "/sport", method = RequestMethod.GET)
public class SportController {

	private static final Logger logger = LoggerFactory.getLogger(SportController.class);

	@Inject
	private SportDao dao;

	@Inject
	private GameDao gameDao;

	@Inject
	private DataFilter<GameDto> filter;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String getSports(Model model) {
		logger.info("Getting all sports");

		List<SportDto> sports = dao.getAll();
		
		Map<SportDto, String> sportUrls = new HashMap<>();
		for(SportDto sport : sports) {
			try {
				sportUrls.put(sport, SportUrlMapper.getSportUrl(sport.getName()));
			} catch (RuntimeException | SportDoesntExistInPropertieFileException e) {
				e.printStackTrace();
				logger.info("==> Impossible to get url of sport: " + sport.getName());
			}
		}
		
		model.addAttribute("sportUrls", sportUrls);
		
		return "sport/list";
	}

	@RequestMapping(value = "/{sportUrl}/matchs", method = RequestMethod.GET)
	public String getSportGames(@PathVariable String sportUrl, Model model) {
		try {
			String sportName = SportUrlMapper.getSportName(sportUrl);
			logger.info("Getting games for sport: " + sportName);
			model.addAttribute("sportName", sportName);

			List<GameDto> games = gameDao.getGamesForSport(sportName);
			filter.applyFilterOnList(games);
			if (games.isEmpty()) {
				return "sport/no-games";
			} else {
				model.addAttribute("games", games);
				return "sport/games";
			}
		} catch (SportDoesntExistException | RuntimeException | SportDoesntExistInPropertieFileException e) {
			logger.info("==> Impossible to get games for sport: " + sportUrl);
			return "error/404";
		}
	}
}
