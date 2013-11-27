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
import ca.ulaval.glo4003.exceptions.SectionDoesntExistException;
import ca.ulaval.glo4003.presentation.viewmodels.ChooseTicketsViewModel;
import ca.ulaval.glo4003.presentation.viewmodels.SectionViewModel;
import ca.ulaval.glo4003.services.SectionService;
import ca.ulaval.glo4003.utilities.Constants;

@Controller
@SessionAttributes({ "currentUser" })
@RequestMapping(value = "/sport/{sportNameUrl}/match/{dateString}", method = RequestMethod.GET)
public class SectionController {

	@Inject
	private SectionService sectionService;

	@Autowired
	private User currentUser;

	@RequestMapping(value = "/billets/{ticketType}", method = RequestMethod.GET)
	public ModelAndView getSectionForGame(@PathVariable String dateString, @PathVariable String sportNameUrl, @PathVariable String ticketType) {
		try {
			DateTime gameDate = DateTime.parse(dateString, DateTimeFormat.forPattern("yyyyMMddHHmmz"));
			ModelAndView mav = new ModelAndView("section/details");
			mav.addObject("currency", Constants.CURRENCY);

			manageUserConnection(mav);

			SectionViewModel section = sectionService.getAvailableSection(sportNameUrl, gameDate, ticketType);

			mav.addObject("section", section);

			ChooseTicketsViewModel chooseTicketsVM = sectionService.getChooseTicketsViewModel(sportNameUrl, gameDate, ticketType);

			mav.addObject("chooseTicketsForm", chooseTicketsVM);

			return mav;
		} catch (SectionDoesntExistException e) {
			return new ModelAndView("error/404");
		}
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
