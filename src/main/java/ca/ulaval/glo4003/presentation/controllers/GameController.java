package ca.ulaval.glo4003.presentation.controllers;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import ca.ulaval.glo4003.domain.services.QueryGameService;
import ca.ulaval.glo4003.domain.users.User;
import ca.ulaval.glo4003.domain.utilities.Constants;
import ca.ulaval.glo4003.persistence.daos.GameDoesntExistException;
import ca.ulaval.glo4003.persistence.xml.XmlIntegrityException;
import ca.ulaval.glo4003.presentation.viewmodels.SectionsViewModel;

@Controller
@SessionAttributes({ "currentUser" })
@RequestMapping(value = "/sport/{sportNameUrl}/match", method = RequestMethod.GET)
public class GameController {

	@Autowired
	private User currentUser;

	@Inject
	private QueryGameService gameService;

	@RequestMapping(value = "/{gameId}/billets", method = RequestMethod.GET)
	public ModelAndView getTicketsForGame(@PathVariable Long gameId, @PathVariable String sportNameUrl) {
		try {
			ModelAndView mav = new ModelAndView("game/sections");

			manageUserConnection(mav);

			mav.addObject("currency", Constants.CURRENCY);

			SectionsViewModel sectionsViewModel = gameService.getAvailableSectionsForGame(gameId);
			mav.addObject("gameSections", sectionsViewModel);
			return mav;

		} catch (GameDoesntExistException e) {
			return new ModelAndView("error/404");
		} catch (XmlIntegrityException e) {
			return new ModelAndView("game/no-ticket");
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
