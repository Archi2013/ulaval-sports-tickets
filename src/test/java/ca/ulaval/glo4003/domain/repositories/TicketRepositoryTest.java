package ca.ulaval.glo4003.domain.repositories;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4003.domain.dtos.TicketDto;
import ca.ulaval.glo4003.domain.factories.TicketFactory;
import ca.ulaval.glo4003.domain.tickets.Ticket;
import ca.ulaval.glo4003.persistence.daos.GameDoesntExistException;
import ca.ulaval.glo4003.persistence.daos.TicketDao;
import ca.ulaval.glo4003.persistence.daos.TicketDoesntExistException;

@RunWith(MockitoJUnitRunner.class)
public class TicketRepositoryTest {

	private static final boolean AVAILABLE = true;
	private static final String A_SPORT = "Sport";
	private static final DateTime A_DATE = new DateTime(100);
	private static final int A_TICKET_NUMBER = 145;
	private static final String A_NEW_SEAT = "Seat";
	private static final String A_NEW_SECTION = "Section";
	private static final double A_PRICE = 12;

	private List<TicketDto> datas;
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
		datas = new ArrayList<>();
		datas.add(firstTicketData);
		datas.add(secondTicketData);
		when(ticketFactory.instantiateTicket(A_PRICE)).thenReturn(ticketGeneratedWithNoParameter);
		when(ticketFactory.instantiateTicket(A_NEW_SECTION, A_NEW_SEAT, A_PRICE, AVAILABLE)).thenReturn(
				ticketGeneratedWithParameter);
		when(ticketDao.get(A_SPORT, A_DATE, A_TICKET_NUMBER)).thenReturn(firstTicketData);
		when(ticketDao.getTicketsForGame(A_SPORT, A_DATE)).thenReturn(datas);
		when(ticketFactory.instantiateTicket(firstTicketData)).thenReturn(ticketWithDataFromDao);
		when(ticketFactory.instantiateTicket(secondTicketData)).thenReturn(anotherTicketWithDataFromDao);
		when(ticketGeneratedWithNoParameter.saveDataInDTO()).thenReturn(firstTicketData);
		when(ticketGeneratedWithParameter.saveDataInDTO()).thenReturn(secondTicketData);
		when(ticketWithDataFromDao.saveDataInDTO()).thenReturn(firstTicketData);
		when(anotherTicketWithDataFromDao.saveDataInDTO()).thenReturn(secondTicketData);
	}

	@Test
	public void InstantiateTicket_returns_ticket_made_by_factory() {
		Ticket ticketReturned1 = repository.instantiateNewTicket(A_PRICE);
		Ticket ticketReturned2 = repository.instantiateNewTicket(A_NEW_SEAT, A_NEW_SECTION, A_PRICE, AVAILABLE);

		Assert.assertSame(ticketGeneratedWithNoParameter, ticketReturned1);
		Assert.assertSame(ticketGeneratedWithParameter, ticketReturned2);
	}

	@Test
	public void recoverTicket_returns_ticket_built_in_factory_with_data_from_dao() throws TicketDoesntExistException {
		Ticket ticketReturned = repository.getWithId(A_SPORT, A_DATE, A_TICKET_NUMBER);

		Assert.assertSame(ticketWithDataFromDao, ticketReturned);
	}

	@Test
	public void recoverAllTicketsForGame_returns_tickets_built_in_factory_with_data_from_dao() throws GameDoesntExistException {
		List<Ticket> ticketsReturned = repository.getAll(A_SPORT, A_DATE);

		Assert.assertSame(ticketWithDataFromDao, ticketsReturned.get(0));
		Assert.assertSame(anotherTicketWithDataFromDao, ticketsReturned.get(1));
	}

	@Test
	public void commit_adds_the_new_tickets_to_the_dao() throws Exception {
		repository.instantiateNewTicket(A_PRICE);
		repository.instantiateNewTicket(A_NEW_SEAT, A_NEW_SECTION, A_PRICE, AVAILABLE);
		repository.commit();

		verify(ticketDao).add(firstTicketData);
		verify(ticketDao).add(secondTicketData);
	}

	@Test
	public void after_two_commits_new_tickets_are_added_only_once() throws Exception {
		repository.instantiateNewTicket(A_PRICE);
		repository.instantiateNewTicket(A_NEW_SEAT, A_NEW_SECTION, A_PRICE, AVAILABLE);
		repository.commit();
		repository.commit();

		verify(ticketDao, times(1)).add(firstTicketData);
		verify(ticketDao, times(1)).add(secondTicketData);
	}

	@Test
	public void commit_save_changes_of_single_recovered_tickets_to_dao() throws Exception {
		repository.getWithId(A_SPORT, A_DATE, A_TICKET_NUMBER);
		repository.commit();

		verify(ticketDao).update(firstTicketData);
		verify(ticketDao, times(1)).commit();
	}

	@Test
	public void commit_save_changes_of_group_of_recovered_tickets_to_dao() throws Exception {
		repository.getAll(A_SPORT, A_DATE);
		repository.commit();

		verify(ticketDao).update(firstTicketData);
		verify(ticketDao).update(secondTicketData);
		verify(ticketDao, times(1)).commit();
	}

	@Test
	public void after_new_tickets_have_been_added_to_dao_other_commits_save_changes() throws Exception {
		repository.instantiateNewTicket(A_PRICE);
		repository.instantiateNewTicket(A_NEW_SEAT, A_NEW_SECTION, A_PRICE, AVAILABLE);

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
		;
	}
}
