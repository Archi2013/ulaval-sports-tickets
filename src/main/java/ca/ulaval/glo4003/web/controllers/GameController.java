package ca.ulaval.glo4003.web.controllers;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ca.ulaval.glo4003.domain.services.QueryGameService;
import ca.ulaval.glo4003.domain.utilities.Constants;
import ca.ulaval.glo4003.persistence.daos.GameDoesntExistException;
import ca.ulaval.glo4003.web.viewmodels.SectionsViewModel;

@Controller
@RequestMapping(value = "/sport/{sportNameUrl}/match", method = RequestMethod.GET)
public class GameController {
	private static final Logger logger = LoggerFactory.getLogger(GameController.class);

	@Inject
	private QueryGameService gameService;

	@RequestMapping(value = "/{gameId}/billets", method = RequestMethod.GET)
	public String getTicketsForGame(@PathVariable Long gameId, @PathVariable String sportNameUrl, Model model) {
		try {
			logger.info("Getting all tickets for game : " + gameId);
			model.addAttribute("currency", Constants.CURRENCY);

			SectionsViewModel sectionsViewModel = gameService.getSectionsForGame(gameId);
			model.addAttribute("gameSections", sectionsViewModel);
			return "game/sections";

		} catch (GameDoesntExistException e) {
			logger.info("==> Impossible to get all tickets for game : " + gameId);
			return "error/404";
		}
	}
}
