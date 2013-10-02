package ca.ulaval.glo4003.web.controllers;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ca.ulaval.glo4003.persistence.dao.TicketDao;
import ca.ulaval.glo4003.persistence.dao.TicketDoesntExistException;
import ca.ulaval.glo4003.web.converter.TicketConverter;

@Controller
@RequestMapping(value = "/sport/{sportNameUrl}/match/{matchId}/billet", method = RequestMethod.GET)
public class TicketController {
	private static final Logger logger = LoggerFactory.getLogger(TicketController.class);
	
	@Inject
	private TicketConverter ticketConverter;

	@Inject
	private TicketDao dao;

	@RequestMapping(value = "/{ticketId}", method = RequestMethod.GET)
	public String getTicket(@PathVariable int ticketId, Model model) {
		try {
			logger.info("Getting ticket : " + ticketId);
			model.addAttribute("ticket", ticketConverter.convert(dao.getTicket(ticketId)));

			return "ticket/detail";
		} catch (TicketDoesntExistException e) {
			logger.info("==> Impossible to get ticket : " + ticketId);
			return "error/404";
		}
	}
}
