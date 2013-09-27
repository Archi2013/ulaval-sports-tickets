package ca.ulaval.glo4003.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ui.Model;

import ca.ulaval.glo4003.controller.GameController;
import ca.ulaval.glo4003.dao.GameDoesntExistException;
import ca.ulaval.glo4003.dao.TicketDao;
import ca.ulaval.glo4003.dto.TicketDto;

@RunWith(MockitoJUnitRunner.class)
public class GameControllerTest {

	public static final int AN_ID = 123;
	public static final String A_SPORT_NAME = "SportName";

	@Mock
	private TicketDao ticketDao;

	@Mock
	private Model model;

	@InjectMocks
	private GameController gameController;

	@Before
	public void setUp() {
	}

	@Test
	public void getTicketsForGame_should_add_tickets_of_the_specified_game_to_model() throws GameDoesntExistException {
		List<TicketDto> tickets = new LinkedList<TicketDto>();
		when(ticketDao.getTicketsForGame(AN_ID)).thenReturn(tickets);

		gameController.getTicketsForGame(AN_ID, A_SPORT_NAME, model);

		verify(model).addAttribute("tickets", tickets);
	}

	@Test
	public void getTicketsForGame_should_return_correct_view_path() {
		String path = gameController.getTicketsForGame(AN_ID, A_SPORT_NAME, model);

		assertEquals("game/tickets", path);
	}

	@Test
	public void getTicketsForGame_should_redirect_to_home_path_when_game_id_doesnt_exist()
			throws GameDoesntExistException {
		when(ticketDao.getTicketsForGame(AN_ID)).thenThrow(GameDoesntExistException.class);

		String path = gameController.getTicketsForGame(AN_ID, A_SPORT_NAME, model);

		assertEquals("error/404", path);
	}
}
