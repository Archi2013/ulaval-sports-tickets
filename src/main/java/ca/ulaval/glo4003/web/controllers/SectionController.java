package ca.ulaval.glo4003.web.controllers;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ca.ulaval.glo4003.domain.services.SectionService;
import ca.ulaval.glo4003.domain.utilities.Constants;
import ca.ulaval.glo4003.domain.utilities.user.User;
import ca.ulaval.glo4003.persistence.daos.SectionDoesntExistException;
import ca.ulaval.glo4003.web.viewmodels.ChooseTicketsViewModel;
import ca.ulaval.glo4003.web.viewmodels.SectionViewModel;

@Controller
@RequestMapping(value = "/sport/{sportNameUrl}/match/{gameId}", method = RequestMethod.GET)
public class SectionController {
	private static final Logger logger = LoggerFactory.getLogger(SectionController.class);

	@Inject
	private SectionService sectionService;
	
	@Autowired
	private User currentUser;

	@RequestMapping(value = "/billets/{ticketType}", method = RequestMethod.GET)
	public ModelAndView getSectionForGame(@PathVariable Long gameId, @PathVariable String ticketType) {
		try {
			logger.info("Getting ticket section : " + ticketType);
			
			ModelAndView mav = new ModelAndView("section/details");
			mav.addObject("currency", Constants.CURRENCY);
			
			Boolean connectedUser = currentUser.isLogged();
			
			if (connectedUser) {
				logger.info("usagé connecté");
				mav.addObject("connectedUser", connectedUser);
			} else {
				logger.info("usagé non connecté");
				mav.addObject("connectedUser", connectedUser);
			}
			
			SectionViewModel section = sectionService.getSection(gameId, ticketType);
			
			mav.addObject("section", section);
			
			ChooseTicketsViewModel chooseTicketsVM = sectionService.getChooseTicketsViewModel(gameId, ticketType);
			
			mav.addObject("chooseTicketsForm", chooseTicketsVM);
			
			return mav;
		} catch (SectionDoesntExistException e) {
			return new ModelAndView("error/404");
		}
	}
}
