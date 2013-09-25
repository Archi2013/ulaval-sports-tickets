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

import ca.ulaval.glo4003.data_access.GameDoesntExistException;
import ca.ulaval.glo4003.data_access.TicketDao;
import ca.ulaval.glo4003.dtos.TicketDto;

@Controller
@RequestMapping(value = "/match", method = RequestMethod.GET)
public class GameController {
	private static final Logger logger = LoggerFactory.getLogger(GameController.class);

	@Inject
	private TicketDao dao;

	@RequestMapping(value = "/{gameId}/billets", method = RequestMethod.GET)
	public String getTicketsForGame(@PathVariable int gameId, Model model) {
		try {
			logger.info("Getting all tickets for game : " + gameId);

			List<TicketDto> tickets = dao.getTicketsForGame(gameId);
			model.addAttribute("GameId", gameId);
			model.addAttribute("tickets", tickets);

			return "game/tickets";
		} catch (GameDoesntExistException e) {
			logger.info("==> Impossible to get all tickets for game : " + gameId);
			return "redirect:/";
		}
	}
}
