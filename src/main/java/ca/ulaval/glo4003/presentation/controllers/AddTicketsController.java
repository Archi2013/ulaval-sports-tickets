package ca.ulaval.glo4003.presentation.controllers;

import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import ca.ulaval.glo4003.domain.services.AdministrationViewService;
import ca.ulaval.glo4003.domain.services.CommandTicketService;
import ca.ulaval.glo4003.domain.services.SportViewService;
import ca.ulaval.glo4003.domain.users.User;
import ca.ulaval.glo4003.domain.utilities.Constants;
import ca.ulaval.glo4003.domain.utilities.Constants.TicketKind;
import ca.ulaval.glo4003.domain.utilities.NoSportForUrlException;
import ca.ulaval.glo4003.domain.utilities.SportUrlMapper;
import ca.ulaval.glo4003.persistence.daos.GameAlreadyExistException;
import ca.ulaval.glo4003.persistence.daos.GameDoesntExistException;
import ca.ulaval.glo4003.persistence.daos.SportDoesntExistException;
import ca.ulaval.glo4003.persistence.daos.TicketAlreadyExistsException;
import ca.ulaval.glo4003.persistence.daos.TicketDoesntExistException;
import ca.ulaval.glo4003.presentation.viewmodels.GameSelectionViewModel;
import ca.ulaval.glo4003.presentation.viewmodels.GeneralTicketsToAddViewModel;
import ca.ulaval.glo4003.presentation.viewmodels.SeatedTicketsToAddViewModel;
import ca.ulaval.glo4003.presentation.viewmodels.SelectSportViewModel;

@Controller
@SessionAttributes({ "currentUser" })
@RequestMapping(value = "/admin", method = RequestMethod.GET)
public class AddTicketsController {

	@Inject
	private Constants constants;

	@Inject
	SportViewService sportService;

	@Inject
	CommandTicketService ticketService;

	@Inject
	private SportUrlMapper sportUrlMapper;

	@Inject
	private AdministrationViewService viewService;

	@Autowired
	private User currentUser;

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

		List<GameSelectionViewModel> viewModels = viewService.getGameSelectionForSport(selectSportVM.getSport());
		System.out.println("Affichage");
		for (GameSelectionViewModel viewModel : viewModels) {
			System.out.println("Date du match:" + viewModel.getGameDate());
		}

		if (selectSportVM.getTypeBillet() == TicketKind.GENERAL_ADMISSION) {
			mav = new ModelAndView("admin/addTickets-General", "command", new GeneralTicketsToAddViewModel());
		} else {
			mav = new ModelAndView("admin/addTickets-Seated", "command", new SeatedTicketsToAddViewModel());
		}

		manageUserConnection(mav);

		mav.addObject("gameSelectionVM", viewService.getGameSelectionForSport(selectSportVM.getSport()));
		mav.addObject("sportName", selectSportVM.getSport());

		return mav;
	}

	@RequestMapping(value = "/ajout-billets-general", method = RequestMethod.POST)
	public ModelAndView addTickets_general(@ModelAttribute("SpringWeb") GeneralTicketsToAddViewModel viewModel,
			Model model) throws SportDoesntExistException, GameDoesntExistException, NoSportForUrlException {
		ModelAndView mav;
		System.out.println("Date Retournee par la vue: " + viewModel.getGameDate().toString());
		try {
			ticketService.addGeneralTickets(sportUrlMapper.getSportName(viewModel.getSportName()), viewModel
					.getGameDate().getDateTime(), viewModel.getNumberOfTickets(), Double.parseDouble(viewModel
					.getPrice()));

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
			ticketService.addSeatedTicket(sportUrlMapper.getSportName(ticketsToAddVM.getSportName()), ticketsToAddVM
					.getGameDate().getDateTime(), ticketsToAddVM.getSection(), ticketsToAddVM.getSeat(), Double
					.parseDouble(ticketsToAddVM.getPrice()));
		} catch (GameAlreadyExistException | TicketAlreadyExistsException | TicketDoesntExistException
				| NoSportForUrlException e) {
			return new ModelAndView("/admin/tickets-added-date-error");
		}
		mav = new ModelAndView("/admin/tickets-added");

		manageUserConnection(mav);

		return mav;
	}

	private void manageUserConnection(ModelAndView mav) {
		Boolean connectedUser = currentUser.isLogged();

		addConnectedUserToModelAndView(mav, connectedUser);
	}

	private void addConnectedUserToModelAndView(ModelAndView mav, Boolean connectedUser) {
		if (connectedUser) {
			mav.addObject("connectedUser", true);
		} else {
			mav.addObject("connectedUser", false);
		}
	}

}
