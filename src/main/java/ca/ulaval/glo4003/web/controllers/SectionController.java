package ca.ulaval.glo4003.web.controllers;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ca.ulaval.glo4003.domain.services.SectionService;
import ca.ulaval.glo4003.persistence.daos.SectionDoesntExistException;
import ca.ulaval.glo4003.web.viewmodels.SectionViewModel;

@Controller
@RequestMapping(value = "/sport/{sportNameUrl}/match/{gameId}", method = RequestMethod.GET)
public class SectionController {
	private static final Logger logger = LoggerFactory.getLogger(SectionController.class);

	@Inject
	private SectionService sectionService;

	@RequestMapping(value = "/billets/{ticketType}", method = RequestMethod.GET)
	public String getSectionForGame(@PathVariable int gameId, @PathVariable String ticketType, Model model) {
		try {
			logger.info("Getting ticket section : " + ticketType);

			SectionViewModel section = sectionService.getSection(gameId, ticketType);
			model.addAttribute("section", section);
			return "section/details";
		} catch (SectionDoesntExistException e) {
			return "error/404";
		}
	}
}
