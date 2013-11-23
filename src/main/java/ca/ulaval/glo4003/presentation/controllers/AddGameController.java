package ca.ulaval.glo4003.presentation.controllers;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ca.ulaval.glo4003.domain.services.CommandGameService;
import ca.ulaval.glo4003.domain.services.CommandTicketService;
import ca.ulaval.glo4003.domain.services.SportViewService;
import ca.ulaval.glo4003.domain.users.User;
import ca.ulaval.glo4003.domain.utilities.NoSportForUrlException;
import ca.ulaval.glo4003.persistence.daos.GameAlreadyExistException;
import ca.ulaval.glo4003.persistence.daos.GameDoesntExistException;
import ca.ulaval.glo4003.persistence.daos.SportDoesntExistException;
import ca.ulaval.glo4003.persistence.daos.TicketAlreadyExistsException;
import ca.ulaval.glo4003.persistence.daos.TicketDoesntExistException;
import ca.ulaval.glo4003.presentation.viewmodels.GameToAddViewModel;

@Controller
@RequestMapping(value = "/admin", method = RequestMethod.GET)
public class AddGameController {

	@Inject
	private CommandGameService gameService;

	@Inject
	SportViewService sportService;

	@Inject
	CommandTicketService ticketService;

	@Autowired
	private User currentUser;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView home() {
		ModelAndView mav = new ModelAndView("admin/home");

		manageUserConnection(mav);

		return mav;
	}

	@RequestMapping(value = "/match", method = RequestMethod.GET)
	public ModelAndView game() {
		ModelAndView mav = new ModelAndView("admin/game", "command", new GameToAddViewModel());

		manageUserConnection(mav);

		mav.addObject("sportsVM", sportService.getSports());

		return mav;
	}

	@RequestMapping(value = "/ajout-match", method = RequestMethod.POST)
	public ModelAndView addGame(@ModelAttribute("SpringWeb") GameToAddViewModel gameToAddVM) {
		ModelAndView mav = new ModelAndView("admin/game-added");

		manageUserConnection(mav);

		mav.addObject("game", gameToAddVM);

		try {
			gameService.createNewGame(gameToAddVM.getSport(), gameToAddVM.getOpponents(), gameToAddVM.getLocation(),
					gameToAddVM.getDate().getDateTime());
		} catch (SportDoesntExistException | GameDoesntExistException | GameAlreadyExistException
				| NoSportForUrlException | TicketAlreadyExistsException | TicketDoesntExistException e) {
			e.printStackTrace();
			return new ModelAndView("admin/game-added-data-error");
		}

		return mav;
	}

	private void addConnectedUserToModelAndView(ModelAndView mav, Boolean connectedUser) {
		if (connectedUser) {
			mav.addObject("connectedUser", true);
		} else {
			mav.addObject("connectedUser", false);
		}
	}

	private void manageUserConnection(ModelAndView mav) {
		Boolean connectedUser = currentUser.isLogged();

		addConnectedUserToModelAndView(mav, connectedUser);
	}
}
