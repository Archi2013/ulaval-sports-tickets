package ca.ulaval.glo4003.domain.repositories;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4003.domain.tickets.Ticket;
import ca.ulaval.glo4003.domain.tickets.TicketDao;
import ca.ulaval.glo4003.domain.tickets.TicketDto;
import ca.ulaval.glo4003.domain.tickets.TicketFactory;
import ca.ulaval.glo4003.domain.tickets.TicketRepository;
import ca.ulaval.glo4003.exceptions.GameDoesntExistException;
import ca.ulaval.glo4003.exceptions.TicketDoesntExistException;

@RunWith(MockitoJUnitRunner.class)
public class TicketRepositoryTest {

	private static final boolean AVAILABLE = true;
	private static final String A_SPORT = "Sport";
	private static final DateTime A_DATE = new DateTime(100);
	private static final int A_TICKET_NUMBER = 145;
	private static final String A_NEW_SEAT = "Seat";
	private static final String A_NEW_SECTION = "Section";
	private static final double A_PRICE = 12;

	@Mock
	private TicketDto firstTicketData;

	@Mock
	private TicketDto secondTicketData;

	@Mock
	private Ticket ticketGeneratedWithNoParameter;

	@Mock
	private Ticket ticketGeneratedWithParameter;

	@Mock
	private Ticket ticketWithDataFromDao;

	@Mock
	private Ticket anotherTicketWithDataFromDao;

	@Mock
	private TicketFactory ticketFactory;

	@Mock
	private TicketDao ticketDao;

	@InjectMocks
	private TicketRepository repository;

	@Before
	public void setUp() throws GameDoesntExistException, TicketDoesntExistException {

		when(ticketDao.get(A_SPORT, A_DATE, A_TICKET_NUMBER)).thenReturn(firstTicketData);

		when(ticketFactory.createTicket(firstTicketData)).thenReturn(ticketWithDataFromDao);
		when(ticketFactory.createTicket(secondTicketData)).thenReturn(anotherTicketWithDataFromDao);

		when(ticketGeneratedWithNoParameter.saveDataInDTO()).thenReturn(firstTicketData);
		when(ticketGeneratedWithParameter.saveDataInDTO()).thenReturn(secondTicketData);
		when(ticketWithDataFromDao.saveDataInDTO()).thenReturn(firstTicketData);
		when(anotherTicketWithDataFromDao.saveDataInDTO()).thenReturn(secondTicketData);
	}

	@Test
	public void createGeneralTicket_returns_ticket_created_by_factory() {
		when(ticketFactory.createGeneralTicket(A_PRICE, AVAILABLE)).thenReturn(ticketGeneratedWithNoParameter);

		Ticket ticketReturned = repository.createGeneralTicket(A_PRICE, AVAILABLE);

		assertSame(ticketGeneratedWithNoParameter, ticketReturned);
	}

	@Test
	public void createGenerealTicket_adds_new_ticket_to_new_ticket_list() {
		when(ticketFactory.createGeneralTicket(A_PRICE, AVAILABLE)).thenReturn(ticketGeneratedWithNoParameter);

		Ticket ticketReturned = repository.createGeneralTicket(A_PRICE, AVAILABLE);

		assertTrue(repository.getNewTickets().contains(ticketReturned));
		assertEquals(1, repository.getNewTickets().size());
	}

	@Test
	public void createSeatedTicket_returns_ticket_created_by_factory() {
		when(ticketFactory.createSeatedTicket(A_NEW_SECTION, A_NEW_SEAT, A_PRICE, AVAILABLE)).thenReturn(
				ticketGeneratedWithParameter);

		Ticket ticketReturned = repository.createSeatedTicket(A_NEW_SEAT, A_NEW_SECTION, A_PRICE, AVAILABLE);

		assertSame(ticketGeneratedWithParameter, ticketReturned);
	}

	@Test
	public void createSeatedTicket_adds_new_ticket_to_new_ticket_list() {
		when(ticketFactory.createSeatedTicket(A_NEW_SECTION, A_NEW_SEAT, A_PRICE, AVAILABLE)).thenReturn(
				ticketGeneratedWithParameter);

		Ticket ticketReturned = repository.createSeatedTicket(A_NEW_SEAT, A_NEW_SECTION, A_PRICE, AVAILABLE);

		assertTrue(repository.getNewTickets().contains(ticketReturned));
		assertEquals(1, repository.getNewTickets().size());
	}

	@Test
	public void getAll_returns_tickets_built_in_factory_with_data_from_dao() throws GameDoesntExistException {
		List<TicketDto> datas = new ArrayList<>();
		datas.add(firstTicketData);
		datas.add(secondTicketData);
		when(ticketDao.getTicketsForGame(A_SPORT, A_DATE)).thenReturn(datas);

		List<Ticket> ticketsReturned = repository.getAll(A_SPORT, A_DATE);

		assertSame(ticketWithDataFromDao, ticketsReturned.get(0));
		assertSame(anotherTicketWithDataFromDao, ticketsReturned.get(1));
	}

	@Test
	public void getAll_add_tickets_to_ticket_list() throws GameDoesntExistException {
		List<TicketDto> datas = new ArrayList<>();
		datas.add(firstTicketData);
		datas.add(secondTicketData);
		when(ticketDao.getTicketsForGame(A_SPORT, A_DATE)).thenReturn(datas);

		repository.getAll(A_SPORT, A_DATE);

		assertTrue(repository.getTicketsInDao().contains(ticketWithDataFromDao));
		assertTrue(repository.getTicketsInDao().contains(anotherTicketWithDataFromDao));
		assertEquals(2, repository.getTicketsInDao().size());
	}

	@Test
	public void commit_adds_the_new_tickets_to_the_dao() throws Exception {
		when(ticketFactory.createGeneralTicket(A_PRICE, AVAILABLE)).thenReturn(ticketGeneratedWithNoParameter);
		when(ticketFactory.createSeatedTicket(A_NEW_SECTION, A_NEW_SEAT, A_PRICE, AVAILABLE)).thenReturn(
				ticketGeneratedWithParameter);

		repository.createGeneralTicket(A_PRICE, AVAILABLE);
		repository.createSeatedTicket(A_NEW_SEAT, A_NEW_SECTION, A_PRICE, AVAILABLE);
		repository.commit();

		verify(ticketDao).add(firstTicketData);
		verify(ticketDao).add(secondTicketData);
	}

	@Test
	public void after_two_commits_new_tickets_are_added_only_once() throws Exception {
		when(ticketFactory.createGeneralTicket(A_PRICE, AVAILABLE)).thenReturn(ticketGeneratedWithNoParameter);
		when(ticketFactory.createSeatedTicket(A_NEW_SECTION, A_NEW_SEAT, A_PRICE, AVAILABLE)).thenReturn(
				ticketGeneratedWithParameter);

		repository.createGeneralTicket(A_PRICE, AVAILABLE);
		repository.createSeatedTicket(A_NEW_SEAT, A_NEW_SECTION, A_PRICE, AVAILABLE);
		repository.commit();
		repository.commit();

		verify(ticketDao, times(1)).add(firstTicketData);
		verify(ticketDao, times(1)).add(secondTicketData);
	}

	@Test
	public void commit_save_changes_of_group_of_recovered_tickets_to_dao() throws Exception {
		List<TicketDto> datas = new ArrayList<>();
		datas.add(firstTicketData);
		datas.add(secondTicketData);
		when(ticketDao.getTicketsForGame(A_SPORT, A_DATE)).thenReturn(datas);

		repository.getAll(A_SPORT, A_DATE);
		repository.commit();

		verify(ticketDao).update(firstTicketData);
		verify(ticketDao).update(secondTicketData);
		verify(ticketDao, times(1)).commit();
	}

	@Test
	public void after_new_tickets_have_been_added_to_dao_other_commits_save_changes() throws Exception {
		when(ticketFactory.createGeneralTicket(A_PRICE, AVAILABLE)).thenReturn(ticketGeneratedWithNoParameter);
		when(ticketFactory.createSeatedTicket(A_NEW_SECTION, A_NEW_SEAT, A_PRICE, AVAILABLE)).thenReturn(
				ticketGeneratedWithParameter);

		repository.createGeneralTicket(A_PRICE, AVAILABLE);
		repository.createSeatedTicket(A_NEW_SEAT, A_NEW_SECTION, A_PRICE, AVAILABLE);

		repository.commit();
		repository.commit();

		verify(ticketDao, times(1)).update(firstTicketData);
		verify(ticketDao, times(1)).update(secondTicketData);
		verify(ticketDao, times(2)).commit();
	}

	@Test
	public void commit_notify_dao_of_transaction_end() throws Exception {
		repository.commit();

		verify(ticketDao, times(1)).commit();
	}
}
