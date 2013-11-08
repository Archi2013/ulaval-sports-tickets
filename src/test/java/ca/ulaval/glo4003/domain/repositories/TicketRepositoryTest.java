package ca.ulaval.glo4003.domain.repositories;

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
import ca.ulaval.glo4003.domain.tickets.Ticket;
import ca.ulaval.glo4003.persistence.daos.TicketDao;

@RunWith(MockitoJUnitRunner.class)
public class TicketRepositoryTest {

	private static final String A_SPORT = "Sport";
	private static final DateTime A_DATE = new DateTime(100);
	private static final int A_TICKET_NUMBER = 145;
	private static final String AN_EXISTING_SEAT = "existingSeat";
	private static final String ANOTHER_EXISTING_SEAT = "existingseat2";
	private static final String AN_EXISTING_SECTION = "existingSection";
	private static final String ANOTHER_EXISTING_SECTION = "existingSection2";
	private static final String A_NEW_SEAT = "Seat";
	private static final String A_NEW_SECTION = "Section";

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
	public void setUp() {
		datas = new ArrayList<>();
		datas.add(firstTicketData);
		datas.add(secondTicketData);
		when(ticketFactory.instantiateTicket()).thenReturn(ticketGeneratedWithNoParameter);
		when(ticketFactory.instantiateTicket(A_NEW_SEAT, A_NEW_SECTION)).thenReturn(ticketGeneratedWithParameter);
		when(ticketDao.get(A_SPORT, A_DATE, A_TICKET_NUMBER)).thenReturn(firstTicketData);
		when(ticketDao.getTicketsForGame(A_SPORT, A_DATE)).thenReturn(datas);
		when(firstTicketData.getSection()).thenReturn(AN_EXISTING_SECTION);
		when(firstTicketData.getSeat()).thenReturn(AN_EXISTING_SEAT);
		when(secondTicketData.getSection()).thenReturn(ANOTHER_EXISTING_SECTION);
		when(secondTicketData.getSeat()).thenReturn(ANOTHER_EXISTING_SEAT);
		when(ticketFactory.instantiateTicket(AN_EXISTING_SEAT, AN_EXISTING_SECTION)).thenReturn(ticketWithDataFromDao);
		when(ticketFactory.instantiateTicket(ANOTHER_EXISTING_SEAT, ANOTHER_EXISTING_SECTION)).thenReturn(
				anotherTicketWithDataFromDao);
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
}
