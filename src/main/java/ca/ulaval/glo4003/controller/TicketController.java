package ca.ulaval.glo4003.controller;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ca.ulaval.glo4003.dao.TicketDao;
import ca.ulaval.glo4003.dao.TicketDoesntExistException;
import ca.ulaval.glo4003.dto.TicketDto;

@Controller
@RequestMapping(value = "/sport/{sportName}/match/{matchId}/billet", method = RequestMethod.GET)
public class TicketController {
	private static final Logger logger = LoggerFactory.getLogger(TicketController.class);

	@Inject
	private TicketDao dao;

	@RequestMapping(value = "/{ticketId}", method = RequestMethod.GET)
	public String getTicket(@PathVariable int ticketId, Model model) {
		try {
			logger.info("Getting ticket : " + ticketId);

			TicketDto ticket = dao.getTicket(ticketId);
			model.addAttribute("ticketId", ticketId);
			model.addAttribute("ticket", ticket);

			return "ticket/detail";
		} catch (TicketDoesntExistException e) {
			logger.info("==> Impossible to get ticket : " + ticketId);
			return "error/404";
		}
	}
}
