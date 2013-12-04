package ca.ulaval.glo4003.presentation.controllers.administration;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import ca.ulaval.glo4003.domain.users.User;
import ca.ulaval.glo4003.exceptions.GameAlreadyExistException;
import ca.ulaval.glo4003.exceptions.GameDoesntExistException;
import ca.ulaval.glo4003.exceptions.NoSportForUrlException;
import ca.ulaval.glo4003.exceptions.SportDoesntExistException;
import ca.ulaval.glo4003.exceptions.TicketAlreadyExistsException;
import ca.ulaval.glo4003.exceptions.TicketDoesntExistException;
import ca.ulaval.glo4003.presentation.viewmodels.GameToAddViewModel;
import ca.ulaval.glo4003.services.CommandGameService;
import ca.ulaval.glo4003.services.CommandTicketService;
import ca.ulaval.glo4003.services.SportViewService;

@Controller
@SessionAttributes({ "currentUser" })
@RequestMapping(value = "/admin", method = RequestMethod.GET)
public class AddGameController {

	@Inject
	private CommandGameService gameService;

	@Inject
	SportViewService sportService;

	@Inject
	CommandTicketService ticketService;

	@RequestMapping(value = "/match", method = RequestMethod.GET)
	public ModelAndView game(HttpSession session) {
		ModelAndView mav = new ModelAndView("admin/game", "command", new GameToAddViewModel());

		mav.addObject("sportsVM", sportService.getSports());

		return mav;
	}

	@RequestMapping(value = "/ajout-match", method = RequestMethod.POST)
	public ModelAndView addGame(HttpSession session, @ModelAttribute("SpringWeb") GameToAddViewModel gameToAddVM) {
		ModelAndView mav = new ModelAndView("admin/game-added");

		mav.addObject("game", gameToAddVM);

		try {
			gameService.createNewGame(gameToAddVM.getSport(), gameToAddVM.getOpponents(), gameToAddVM.getLocation(),
					gameToAddVM.getDate().getDateTime());
		} catch (SportDoesntExistException | GameDoesntExistException | GameAlreadyExistException
				| NoSportForUrlException | TicketAlreadyExistsException | TicketDoesntExistException e) {
			return new ModelAndView("admin/game-added-data-error");
		}

		return mav;
	}
}
