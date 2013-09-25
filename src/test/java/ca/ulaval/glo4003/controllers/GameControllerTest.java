package ca.ulaval.glo4003.controllers;

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

import ca.ulaval.glo4003.data_access.GameDoesntExistException;
import ca.ulaval.glo4003.data_access.TicketDao;
import ca.ulaval.glo4003.dtos.TicketDto;

@RunWith(MockitoJUnitRunner.class)
public class GameControllerTest {

	public static final int AN_ID = 123;

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

		gameController.getTicketsForGame(AN_ID, model);

		verify(model).addAttribute("tickets", tickets);
	}

	@Test
	public void getTicketsForGame_should_return_correct_view_path() {
		String path = gameController.getTicketsForGame(AN_ID, model);

		assertEquals("game/tickets", path);
	}

	@Test
	public void getTicketsForGame_should_return_home_path_when_ticket_dao_throws_game_doesnt_exist_exception() throws GameDoesntExistException {
		when(ticketDao.getTicketsForGame(AN_ID)).thenThrow(GameDoesntExistException.class);

		String path = gameController.getTicketsForGame(AN_ID, model);

		assertEquals("home", path);
	}
}
