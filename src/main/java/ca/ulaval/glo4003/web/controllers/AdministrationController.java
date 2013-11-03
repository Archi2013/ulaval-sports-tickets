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
import ca.ulaval.glo4003.domain.services.SportService;
import ca.ulaval.glo4003.domain.utilities.DateParser;
import ca.ulaval.glo4003.domain.utilities.NoSportForUrlException;
import ca.ulaval.glo4003.persistence.daos.GameAlreadyExistException;
import ca.ulaval.glo4003.persistence.daos.GameDoesntExistException;
import ca.ulaval.glo4003.persistence.daos.SportDoesntExistException;
import ca.ulaval.glo4003.web.viewmodels.GameToAddViewModel;
import ca.ulaval.glo4003.web.viewmodels.GeneralTicketsToAddViewModel;
import ca.ulaval.glo4003.web.viewmodels.SeatedTicketsToAddViewModel;
import ca.ulaval.glo4003.web.viewmodels.SelectSportViewModel;

@Controller
@RequestMapping(value = "/admin", method = RequestMethod.GET)
public class AdministrationController {
	private static final Logger logger = LoggerFactory.getLogger(AdministrationController.class);

	@Inject
	private CommandGameService gameService;

	@Inject
	SportService sportService;

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

		ModelAndView mav = new ModelAndView("admin/game", "command", new GameToAddViewModel());

		mav.addObject("sportsVM", sportService.getSports());

		return mav;
	}

	@RequestMapping(value = "/ajout-match", method = RequestMethod.POST)
	public String addGame(@ModelAttribute("SpringWeb") GameToAddViewModel gameToAddVM, Model model) {
		logger.info("Adminisatration : Add a new game for a sport : " + gameToAddVM.getSport());

		model.addAttribute("game", gameToAddVM);

		try {
			gameService.createNewGame(gameToAddVM.getSport(), gameToAddVM.getOpponents(),
					dateParser.parseDate(gameToAddVM.getDate()));
		} catch (SportDoesntExistException | GameDoesntExistException | GameAlreadyExistException
				| NoSportForUrlException e) {
			e.printStackTrace();
			return "admin/game-added-data-error";
		}

		return "admin/game-added";
	}

	@RequestMapping(value = "/billets/choisir-sport", method = RequestMethod.GET)
	public ModelAndView tickets() {
		logger.info("Adminisatration : Page to add new tickets for a sport");

		ModelAndView mav = new ModelAndView("admin/addTickets-chooseSport", "command", new SelectSportViewModel());

		mav.addObject("sportsVM", sportService.getSports());

		return mav;
	}

	@RequestMapping(value = "/billets", method = RequestMethod.POST)
	public ModelAndView addTickets_selectSport(@ModelAttribute("SpringWeb") SelectSportViewModel selectSportVM,
			Model model) throws SportDoesntExistException, GameDoesntExistException {
		logger.info("Adminisatration : Add new tickets for a sport : " + selectSportVM.getSport());
		logger.info("Ticket is of type : " + selectSportVM.getTypeBillet());

		ModelAndView mav;
		if (selectSportVM.getTypeBillet().equals("General")) {
			mav = new ModelAndView("admin/addTickets-General", "command", new GeneralTicketsToAddViewModel());
		} else {
			mav = new ModelAndView("admin/addTickets-Seated", "command", new SeatedTicketsToAddViewModel());
		}

		mav.addObject("gamesVM", sportService.getGamesForSport(selectSportVM.getSport()));

		return mav;
	}

	@RequestMapping(value = "/ajout-billets-general", method = RequestMethod.POST)
	public String addTickets_general(@ModelAttribute("SpringWeb") GeneralTicketsToAddViewModel ticketsToAddVM,
			Model model) throws SportDoesntExistException, GameDoesntExistException {
		logger.info("Adminisatration :Adding " + ticketsToAddVM.getNumberOfTickets() + "new general tickets to game"
				+ ticketsToAddVM.getGameDate());

		return "/admin/tickets-added";
	}
}
