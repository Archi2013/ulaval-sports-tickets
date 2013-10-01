package ca.ulaval.glo4003.web.controller;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ca.ulaval.glo4003.dto.GameDto;
import ca.ulaval.glo4003.persistence.dao.GameDao;
import ca.ulaval.glo4003.persistence.dao.GameDoesntExistException;
import ca.ulaval.glo4003.web.converter.GameConverter;
import ca.ulaval.glo4003.web.viewmodel.GameViewModel;

@Controller
@RequestMapping(value = "/sport/{sportNameUrl}/match", method = RequestMethod.GET)
public class GameController {
	private static final Logger logger = LoggerFactory.getLogger(GameController.class);

	@Inject
	private GameConverter gameConverter;

	@Inject
	private GameDao dao;

	@RequestMapping(value = "/{gameId}/billets", method = RequestMethod.GET)
	public String getTicketsForGame(@PathVariable int gameId, @PathVariable String sportNameUrl, Model model) {
		try {
			logger.info("Getting all tickets for game : " + gameId);

			GameDto game = dao.get(gameId);
			GameViewModel gameViewModel = gameConverter.convert(game);
			model.addAttribute("game", gameViewModel);

			return "game/sections";
		} catch (GameDoesntExistException e) {
			logger.info("==> Impossible to get all tickets for game : " + gameId);
			return "error/404";
		}
	}
}
