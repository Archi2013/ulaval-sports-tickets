package ca.ulaval.glo4003.domain.repositories;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import ca.ulaval.glo4003.domain.tickets.PersistableTicket;
import ca.ulaval.glo4003.domain.tickets.Ticket;
import ca.ulaval.glo4003.persistence.daos.TicketAlreadyExistException;
import ca.ulaval.glo4003.persistence.daos.TicketDao;

@RunWith(MockitoJUnitRunner.class)
public class TicketRepositoryTest {

	private static final String A_SPORT = "Sport";
	private static final DateTime A_DATE = new DateTime(100);
	private static final int A_TICKET_NUMBER = 145;
	private static final String A_NEW_SEAT = "Seat";
	private static final String A_NEW_SECTION = "Section";

	private List<TicketDto> datas;
	@Mock
	private TicketDto firstTicketData;

	@Mock
	private TicketDto secondTicketData;

	@Mock
	private PersistableTicket ticketGeneratedWithNoParameter;

	@Mock
	private PersistableTicket ticketGeneratedWithParameter;

	@Mock
	private PersistableTicket ticketWithDataFromDao;

	@Mock
	private PersistableTicket anotherTicketWithDataFromDao;

	@Mock
	private TicketFactory ticketFactory;

	@Mock
	private TicketDao ticketDao;

	@InjectMocks
	private TicketRepository repository;

	@Before
	public void setUp() {
		datas = new ArrayList<>();
		datas.add(firstTicketData);
		datas.add(secondTicketData);
		when(ticketFactory.instantiateTicket()).thenReturn(ticketGeneratedWithNoParameter);
		when(ticketFactory.instantiateTicket(A_NEW_SEAT, A_NEW_SECTION)).thenReturn(ticketGeneratedWithParameter);
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
		Ticket ticketReturned1 = repository.instantiateNewTicket();
		Ticket ticketReturned2 = repository.instantiateNewTicket(A_NEW_SEAT, A_NEW_SECTION);

		Assert.assertSame(ticketGeneratedWithNoParameter, ticketReturned1);
		Assert.assertSame(ticketGeneratedWithParameter, ticketReturned2);
	}

	@Test
	public void recoverTicket_returns_ticket_built_in_factory_with_data_from_dao() {
		Ticket ticketReturned = repository.recoverTicket(A_SPORT, A_DATE, A_TICKET_NUMBER);

		Assert.assertSame(ticketWithDataFromDao, ticketReturned);
	}

	@Test
	public void recoverAllTicketsForGame_returns_tickets_built_in_factory_with_data_from_dao() {
		List<Ticket> ticketsReturned = repository.recoverAllTicketsForGame(A_SPORT, A_DATE);

		Assert.assertSame(ticketWithDataFromDao, ticketsReturned.get(0));
		Assert.assertSame(anotherTicketWithDataFromDao, ticketsReturned.get(1));
	}

	@Test
	public void commit_adds_the_new_tickets_to_the_dao() throws TicketAlreadyExistException {
		repository.instantiateNewTicket();
		repository.instantiateNewTicket(A_NEW_SEAT, A_NEW_SECTION);
		repository.commit();

		verify(ticketDao).add(firstTicketData);
		verify(ticketDao).add(secondTicketData);
	}

	@Test
	public void after_two_commits_new_tickets_are_added_only_once() throws TicketAlreadyExistException {
		repository.instantiateNewTicket();
		repository.instantiateNewTicket(A_NEW_SEAT, A_NEW_SECTION);
		repository.commit();
		repository.commit();

		verify(ticketDao, times(1)).add(firstTicketData);
		verify(ticketDao, times(1)).add(secondTicketData);
	}

	@Test
	public void commit_save_changes_of_single_recovered_tickets_to_dao() throws TicketAlreadyExistException {
		repository.recoverTicket(A_SPORT, A_DATE, A_TICKET_NUMBER);
		repository.commit();

		verify(ticketDao).saveChanges(firstTicketData);
	}

	@Test
	public void commit_save_changes_of_group_of_recovered_tickets_to_dao() throws TicketAlreadyExistException {
		repository.recoverAllTicketsForGame(A_SPORT, A_DATE);
		repository.commit();

		verify(ticketDao).saveChanges(firstTicketData);
		verify(ticketDao).saveChanges(secondTicketData);
	}

	@Test
	public void after_new_tickets_have_been_added_to_dao_other_commits_save_changes()
			throws TicketAlreadyExistException {
		repository.instantiateNewTicket();
		repository.instantiateNewTicket(A_NEW_SEAT, A_NEW_SECTION);

		repository.commit();
		repository.commit();

		verify(ticketDao, times(1)).saveChanges(firstTicketData);
		verify(ticketDao, times(1)).saveChanges(secondTicketData);
	}

	@Test
	public void commit_notify_dao_of_transaction_end() throws TicketAlreadyExistException {
		repository.commit();

		verify(ticketDao, times(1)).endTransaction();
	}
}
