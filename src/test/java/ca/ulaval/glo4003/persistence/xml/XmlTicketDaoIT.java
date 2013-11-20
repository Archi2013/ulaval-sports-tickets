package ca.ulaval.glo4003.persistence.xml;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import ca.ulaval.glo4003.domain.dtos.GeneralTicketDto;
import ca.ulaval.glo4003.domain.dtos.SeatedTicketDto;
import ca.ulaval.glo4003.domain.dtos.TicketDto;
import ca.ulaval.glo4003.persistence.daos.GameDoesntExistException;
import ca.ulaval.glo4003.persistence.daos.SectionDoesntExistException;
import ca.ulaval.glo4003.persistence.daos.TicketAlreadyExistException;
import ca.ulaval.glo4003.persistence.daos.TicketDoesntExistException;

@Ignore
public class XmlTicketDaoIT {

	private static final boolean AVAILABLE = true;
	private XmlTicketDao ticketDao;
	private AtomicLong atomicLong;

	@Before
	public void setUp() throws Exception {
		ticketDao = new XmlTicketDao("resources/TicketData.xml");
		atomicLong = new AtomicLong();
		createMultipleTicket(1, 15f, "Générale", 4);
		createMultipleTicket(2, 15f, "Générale", 4);
		createMultipleTicket(2, 22f, "Section 100", 2);
	}

	private void createMultipleTicket(long gameId, float price, String section, int number) throws Exception {
		for (int i = 0; i < number; i++) {
			ticketDao.add(createTicket(gameId, price, section, true));
		}
	}

	// TODO Remove gameId...
	private TicketDto createTicket(long gameId, float price, String section, boolean available) {
		if ("Générale".equals(section)) {
			return new GeneralTicketDto(atomicLong.incrementAndGet(), null, null, price, available);
		}
		long ticketId = atomicLong.incrementAndGet();
		char letter = (char) ((int) 'A' + ticketId / 10);
		long number = ticketId % 10;
		String code = letter + "-" + number;
		// return new TicketDto(atomicLong.incrementAndGet(), null, null,
		// section, null, price, available);

		return new SeatedTicketDto(atomicLong.incrementAndGet(), null, null, section, code, price, available);
	}

	@Test
	public void testGetTicket() throws Exception {
		TicketDto actual = ticketDao.get(1);

		TicketDto expected = new SeatedTicketDto(1L, null, null, null, null, 15.00f, AVAILABLE);
		assertTicket(expected, actual);
	}

	@Test(expected = TicketDoesntExistException.class)
	public void testGetInvalidGameSectionShouldThrow() throws Exception {
		ticketDao.get(-1);
	}

	@Test
	// TODO REVIEW TESTS TICKETS. WAS USING GAMEIDS.
	public void testGetTicketsForGame() throws Exception {
		List<TicketDto> tickets = ticketDao.getAvailableTicketsForGame("", DateTime.now());

		TicketDto expected0 = new SeatedTicketDto(5L, null, null, null, null, 15.00f, AVAILABLE);
		TicketDto expected1 = new SeatedTicketDto(6L, null, null, null, null, 15.00f, AVAILABLE);
		TicketDto expected2 = new SeatedTicketDto(7L, null, null, null, null, 15.00f, AVAILABLE);
		TicketDto expected3 = new SeatedTicketDto(8L, null, null, null, null, 15.00f, AVAILABLE);
		TicketDto expected4 = new SeatedTicketDto(9L, null, null, "Section 100", "A-9", 22.00f, AVAILABLE);
		TicketDto expected5 = new SeatedTicketDto(10L, null, null, "Section 100", "B-0", 22.00f, AVAILABLE);

		Assert.assertEquals(6, tickets.size());

		assertTicket(expected0, tickets.get(0));
		assertTicket(expected1, tickets.get(1));
		assertTicket(expected2, tickets.get(2));
		assertTicket(expected3, tickets.get(3));
		assertTicket(expected4, tickets.get(4));
		assertTicket(expected5, tickets.get(5));
	}

	@Test(expected = GameDoesntExistException.class)
	public void testGetTicketsForInvalidGameShouldThrow() throws Exception {
		ticketDao.getAvailableTicketsForGame("INVALIDE", DateTime.now());
	}

	@Test
	public void testGetTicketsForSection() throws Exception {
		List<TicketDto> tickets = ticketDao.getTicketsForSection("", DateTime.now(), "Section 100");

		TicketDto expected0 = new SeatedTicketDto(9L, null, null, "Section 100", "A-9", 22.00f, AVAILABLE);
		TicketDto expected1 = new SeatedTicketDto(10L, null, null, "Section 100", "B-0", 22.00f, AVAILABLE);

		Assert.assertEquals(2, tickets.size());

		assertTicket(expected0, tickets.get(0));
		assertTicket(expected1, tickets.get(1));
	}

	@Test(expected = SectionDoesntExistException.class)
	public void testGetTicketsForInvalidSectionShouldThrow() throws Exception {
		ticketDao.getTicketsForSection("", DateTime.now(), "Général");
	}

	@Test
	// TODO REMOVE USING GAMEID
	public void testAddDto() throws Exception {
		TicketDto toAdd = new SeatedTicketDto(1000L, null, null, null, null, 20.00f, AVAILABLE);

		ticketDao.add(toAdd);

		TicketDto actual = ticketDao.get(1000);
		TicketDto expected = toAdd;

		assertTicket(expected, actual);
	}

	@Test(expected = TicketAlreadyExistException.class)
	public void testAddExistingShouldThrow() throws Exception {
		TicketDto toAdd = new SeatedTicketDto(3L, null, null, "Front Row", "C-01", 35.00f, AVAILABLE);

		ticketDao.add(toAdd);
	}

	private void assertTicket(TicketDto expected, TicketDto actual) {
		Assert.assertEquals(expected.ticketId, actual.ticketId);
		Assert.assertEquals(expected.price, actual.price, 0.01f);
		Assert.assertEquals(expected.section, actual.section);
		Assert.assertEquals(expected.seat, actual.seat);
	}
}
