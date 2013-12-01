package ca.ulaval.glo4003.presentation.controllers;

import javax.inject.Inject;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import ca.ulaval.glo4003.domain.users.User;
import ca.ulaval.glo4003.exceptions.GameDoesntExistException;
import ca.ulaval.glo4003.exceptions.NoSportForUrlException;
import ca.ulaval.glo4003.exceptions.SectionDoesntExistException;
import ca.ulaval.glo4003.presentation.viewmodels.ChosenGeneralTicketsViewModel;
import ca.ulaval.glo4003.presentation.viewmodels.ChosenWithSeatTicketsViewModel;
import ca.ulaval.glo4003.presentation.viewmodels.SectionViewModel;
import ca.ulaval.glo4003.presentation.viewmodels.SectionsViewModel;
import ca.ulaval.glo4003.services.SectionService;
import ca.ulaval.glo4003.utilities.Constants;
import ca.ulaval.glo4003.utilities.persistence.XmlIntegrityException;

@Controller
@SessionAttributes({ "currentUser" })
@RequestMapping(value = "/sport/{sportNameUrl}/match/{dateString}", method = RequestMethod.GET)
public class SectionController {

	@Autowired
	private User currentUser;

	@Inject
	private SectionService sectionService;

	@RequestMapping(value = "/billets/{ticketType}", method = RequestMethod.GET)
	public ModelAndView getSectionForGame(@PathVariable String dateString, @PathVariable String sportNameUrl,
			@PathVariable String ticketType) {
		try {
			DateTime gameDate = DateTime.parse(dateString, DateTimeFormat.forPattern("yyyyMMddHHmmz"));
			ModelAndView mav = new ModelAndView("section/details");
			mav.addObject("currency", Constants.CURRENCY);

			SectionViewModel section = sectionService.getAvailableSection(sportNameUrl, gameDate, ticketType);

			mav.addObject("section", section);

			if (section.getGeneralAdmission()) {
				ChosenGeneralTicketsViewModel chosenGeneralTicketsVM = sectionService.getChosenGeneralTicketsViewModel(
						sportNameUrl, gameDate, ticketType);
				mav.addObject("chosenGeneralTicketsForm", chosenGeneralTicketsVM);
			} else {
				ChosenWithSeatTicketsViewModel chosenWithSeatTicketsVM = sectionService
						.getChosenWithSeatTicketsViewModel(sportNameUrl, gameDate, ticketType);
				mav.addObject("chosenWithSeatTicketsForm", chosenWithSeatTicketsVM);
			}

			return mav;
		} catch (SectionDoesntExistException e) {
			return new ModelAndView("error/404");
		}
	}

	@RequestMapping(value = "/billets", method = RequestMethod.GET)
	public ModelAndView getTicketsForGame(@PathVariable String dateString, @PathVariable String sportNameUrl) {
		try {
			DateTime gameDate = DateTime.parse(dateString, DateTimeFormat.forPattern("yyyyMMddHHmmz"));
			ModelAndView mav = new ModelAndView("game/sections");

			manageUserConnection(mav);

			mav.addObject("currency", Constants.CURRENCY);

			SectionsViewModel sectionsViewModel = sectionService.getAvailableSectionsForGame(sportNameUrl, gameDate);
			mav.addObject("gameSections", sectionsViewModel);
			return mav;

		} catch (GameDoesntExistException | NoSportForUrlException e) {
			return new ModelAndView("error/404");
		} catch (XmlIntegrityException e) {
			return new ModelAndView("game/no-ticket");
		}
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
