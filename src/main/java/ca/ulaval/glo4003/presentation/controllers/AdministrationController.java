package ca.ulaval.glo4003.presentation.controllers;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ca.ulaval.glo4003.domain.services.CommandGameService;
import ca.ulaval.glo4003.domain.services.CommandTicketService;
import ca.ulaval.glo4003.domain.services.SportService;
import ca.ulaval.glo4003.domain.users.User;
import ca.ulaval.glo4003.domain.utilities.Constants;
import ca.ulaval.glo4003.domain.utilities.Constants.TicketKind;
import ca.ulaval.glo4003.domain.utilities.DateParser;
import ca.ulaval.glo4003.domain.utilities.NoSportForUrlException;
import ca.ulaval.glo4003.domain.utilities.SportUrlMapper;
import ca.ulaval.glo4003.domain.utilities.YearMonthDayHourMinuteDateParser;
import ca.ulaval.glo4003.persistence.daos.GameAlreadyExistException;
import ca.ulaval.glo4003.persistence.daos.GameDoesntExistException;
import ca.ulaval.glo4003.persistence.daos.SportDoesntExistException;
import ca.ulaval.glo4003.persistence.daos.TicketAlreadyExistsException;
import ca.ulaval.glo4003.persistence.daos.TicketDoesntExistException;
import ca.ulaval.glo4003.presentation.viewmodels.GameToAddViewModel;
import ca.ulaval.glo4003.presentation.viewmodels.GeneralTicketsToAddViewModel;
import ca.ulaval.glo4003.presentation.viewmodels.SeatedTicketsToAddViewModel;
import ca.ulaval.glo4003.presentation.viewmodels.SelectSportViewModel;

@Controller
@RequestMapping(value = "/admin", method = RequestMethod.GET)
public class AdministrationController {

	@Inject
	private Constants constants;

	@Inject
	private CommandGameService gameService;

	@Inject
	SportService sportService;

	@Inject
	CommandTicketService ticketService;

	@Inject
	private DateParser dateParser;

	@Inject
	private SportUrlMapper sportUrlMapper;

	@Inject
	private YearMonthDayHourMinuteDateParser otherParser;

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
					otherParser.parseDate(gameToAddVM.getDate()));
		} catch (SportDoesntExistException | GameDoesntExistException | GameAlreadyExistException
				| NoSportForUrlException | TicketAlreadyExistsException | TicketDoesntExistException e) {
			e.printStackTrace();
			return new ModelAndView("admin/game-added-data-error");
		}

		return mav;
	}

	@RequestMapping(value = "/billets/choisir-sport", method = RequestMethod.GET)
	public ModelAndView tickets() {
		ModelAndView mav = new ModelAndView("admin/addTickets-chooseSport", "command", new SelectSportViewModel());

		manageUserConnection(mav);

		mav.addObject("sportsVM", sportService.getSports());
		mav.addObject("ticketKinds", constants.getTicketKinds());

		return mav;
	}

	@RequestMapping(value = "/billets", method = RequestMethod.POST)
	public ModelAndView addTickets_selectSport(@ModelAttribute("SpringWeb") SelectSportViewModel selectSportVM,
			Model model) throws SportDoesntExistException, GameDoesntExistException {

		ModelAndView mav;

		if (selectSportVM.getTypeBillet() == TicketKind.GENERAL_ADMISSION) {
			mav = new ModelAndView("admin/addTickets-General", "command", new GeneralTicketsToAddViewModel());
		} else {
			mav = new ModelAndView("admin/addTickets-Seated", "command", new SeatedTicketsToAddViewModel());
		}

		manageUserConnection(mav);

		mav.addObject("gamesVM", sportService.getGamesForSport(selectSportVM.getSport()));
		mav.addObject("sportName", selectSportVM.getSport());

		return mav;
	}

	@RequestMapping(value = "/ajout-billets-general", method = RequestMethod.POST)
	public ModelAndView addTickets_general(@ModelAttribute("SpringWeb") GeneralTicketsToAddViewModel viewModel,
			Model model) throws SportDoesntExistException, GameDoesntExistException, NoSportForUrlException {
		ModelAndView mav;
		try {
			ticketService.addGeneralTickets(sportUrlMapper.getSportName(viewModel.getSportName()),
					dateParser.parseDate(viewModel.getGameDate()), viewModel.getNumberOfTickets(),
					Double.parseDouble(viewModel.getPrice()));

		} catch (GameAlreadyExistException | TicketAlreadyExistsException | TicketDoesntExistException e) {
			mav = new ModelAndView("/admin/tickets-added-date-error");
		}
		mav = new ModelAndView("/admin/tickets-added");

		manageUserConnection(mav);

		return mav;
	}

	@RequestMapping(value = "/ajout-billets-seated", method = RequestMethod.POST)
	public ModelAndView addTickets_seated(@ModelAttribute("SpringWeb") SeatedTicketsToAddViewModel ticketsToAddVM,
			Model model) throws SportDoesntExistException, GameDoesntExistException, GameAlreadyExistException,
			TicketAlreadyExistsException, TicketDoesntExistException, NoSportForUrlException {

		ModelAndView mav;
		try {
			ticketService.addSeatedTicket(sportUrlMapper.getSportName(ticketsToAddVM.getSportName()),
					dateParser.parseDate(ticketsToAddVM.getGameDate()), ticketsToAddVM.getSection(),
					ticketsToAddVM.getSeat(), Double.parseDouble(ticketsToAddVM.getPrice()));
		} catch (GameAlreadyExistException | TicketAlreadyExistsException | TicketDoesntExistException
				| NoSportForUrlException e) {
			return new ModelAndView("/admin/tickets-added-date-error");
		}
		mav = new ModelAndView("/admin/tickets-added");

		manageUserConnection(mav);

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
