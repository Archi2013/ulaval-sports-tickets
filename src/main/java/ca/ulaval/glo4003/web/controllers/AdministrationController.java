package ca.ulaval.glo4003.web.controllers;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ca.ulaval.glo4003.domain.services.CommandGameService;
import ca.ulaval.glo4003.domain.utilities.DateParser;
import ca.ulaval.glo4003.domain.utilities.SportDoesntExistInPropertiesFileException;
import ca.ulaval.glo4003.persistence.daos.GameAlreadyExistException;
import ca.ulaval.glo4003.persistence.daos.GameDoesntExistException;
import ca.ulaval.glo4003.persistence.daos.SportDoesntExistException;
import ca.ulaval.glo4003.web.viewmodels.GameToAddViewModel;

@Controller
@RequestMapping(value = "/admin", method = RequestMethod.GET)
public class AdministrationController {
	private static final Logger logger = LoggerFactory.getLogger(AdministrationController.class);

	@Inject
	private CommandGameService gameService;

	@Inject
	private DateParser dateParser;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String home() {
		logger.info("Adminisatration : Home");
		return "admin/home";
	}

	@RequestMapping(value = "/match", method = RequestMethod.GET)
	public ModelAndView game() {
		logger.info("Adminisatration : Page to add a new game for a sport");
		return new ModelAndView("admin/game", "command", new GameToAddViewModel());
	}

	@RequestMapping(value = "/ajout-match", method = RequestMethod.POST)
	public String addGame(@ModelAttribute("SpringWeb") GameToAddViewModel gameToAddVM, Model model) {
		logger.info("Adminisatration : Add a new game for a sport : " + gameToAddVM.getSport());

		model.addAttribute("game", gameToAddVM);

		try {
			gameService.createNewGame(gameToAddVM.getSport(), gameToAddVM.getOpponents(),
					dateParser.parseDate(gameToAddVM.getDate()));
		} catch (SportDoesntExistException | GameDoesntExistException | GameAlreadyExistException
				| SportDoesntExistInPropertiesFileException e) {
			e.printStackTrace();
			return "admin/game-added-data-error";
		}

		return "admin/game-added";
	}
}
