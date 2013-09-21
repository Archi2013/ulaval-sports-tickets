package ca.ulaval.glo4003.controllers;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ca.ulaval.glo4003.data_access.GameDao;
import ca.ulaval.glo4003.data_access.SportDao;
import ca.ulaval.glo4003.dtos.GameDto;
import ca.ulaval.glo4003.dtos.SportDto;

@Controller
@RequestMapping(value = "/sports", method = RequestMethod.GET)
public class SportController {

	private static final Logger logger = LoggerFactory.getLogger(SportController.class);

	@Inject
	private SportDao dao;

	@Inject
	private GameDao gameDao;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public @ResponseBody
	List<SportDto> getSports() {
		logger.info("Getting all sports");

		List<SportDto> sports = dao.getAll();
		return sports;
	}
	
	@RequestMapping(value = "/{sportName}", method = RequestMethod.GET)
	public @ResponseBody
	SportDto getSport(@PathVariable String sportName) {
		logger.info("Getting sport: "+ sportName);

		SportDto sport = dao.get(sportName);
		return sport;
	}

	@RequestMapping(value = "/{sportName}/games", method = RequestMethod.GET)
	public @ResponseBody List<GameDto> getSportGames(@PathVariable String sportName) {
		logger.info("Getting games for sport: "+ sportName);
		
		return gameDao.getGamesForSport(sportName);		
	}
}
