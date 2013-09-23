package ca.ulaval.glo4003.controllers;

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

import ca.ulaval.glo4003.data_access.TicketDao;
import ca.ulaval.glo4003.dtos.TicketDto;

@RunWith(MockitoJUnitRunner.class)
public class TicketControllerTest {

	public static final int UN_ID = 123;

	@Mock
	private TicketDto ticketDto;
	
	@Mock
	private TicketDao ticketDao;

	@Mock
	private Model model;
	
	@InjectMocks
	private TicketController ticketController;

	@Before
	public void setUp() {
	}

	@Test
	public void getTicket_add_ticket_specified_to_model() {
		when(ticketDao.getTicket(UN_ID)).thenReturn(ticketDto);

		ticketController.getTicket(UN_ID, model);

		verify(model).addAttribute("ticket", ticketDto);
	}
}
