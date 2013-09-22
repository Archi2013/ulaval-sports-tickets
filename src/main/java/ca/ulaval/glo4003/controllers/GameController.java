package ca.ulaval.glo4003.controllers;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ca.ulaval.glo4003.data_access.TicketDao;
import ca.ulaval.glo4003.dtos.TicketDto;

@Controller
@RequestMapping(value = "/Game", method = RequestMethod.GET)
public class GameController {
	private static final Logger logger = LoggerFactory.getLogger(SportController.class);

	@Inject
	private TicketDao dao;

	@RequestMapping(value = "/{gameId}/tickets", method = RequestMethod.GET)
	public String getTicketsForGame(@PathVariable int gameId, Model model) {
		List<TicketDto> tickets = dao.getTicketsForGame(gameId);
		model.addAttribute("GameId", gameId);
		model.addAttribute("tickets", tickets);

		return "games/tickets";
	}
}
