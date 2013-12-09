package ca.ulaval.glo4003.domain.tickets;

import java.io.File;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.exceptions.SectionDoesntExistException;
import ca.ulaval.glo4003.exceptions.TicketAlreadyExistsException;
import ca.ulaval.glo4003.exceptions.TicketDoesntExistException;
import ca.ulaval.glo4003.tickets.dto.GeneralTicketDto;
import ca.ulaval.glo4003.tickets.dto.SeatedTicketDto;
import ca.ulaval.glo4003.tickets.dto.TicketDto;

public class XmlTicketDaoIT {

	private static final String FILENAME = "resources/XmlTicketDaoIT.xml";
	
	private static final String AN_OTHER_SPORT_NAME = "Soccer masculin";
	private static final String A_SPORT_NAME = "Football";
	private static final String DATE_PATTERN = "yyyy/MM/dd HH:mm z";
	private static final DateTimeFormatter format = DateTimeFormat.forPattern(DATE_PATTERN);
	private static final DateTime A_GAME_DATE = format.parseDateTime("2014/08/24 13:00 EDT");
	private static final DateTime AN_OTHER_GAME_DATE = format.parseDateTime("2014/08/31 19:00 EDT");
	
	private static final boolean AVAILABLE = true;
	private static final boolean NOT_AVAILABLE = false;
	private XmlTicketDao ticketDao;
	private AtomicLong atomicLong;

	@Before
	public void setUp() throws Exception {
		ticketDao = new XmlTicketDao(FILENAME);
		
		atomicLong = new AtomicLong();
		createMultipleTicket(A_SPORT_NAME, A_GAME_DATE, 15f, "Générale", 4);
		createMultipleTicket(AN_OTHER_SPORT_NAME, AN_OTHER_GAME_DATE, 15f, "Générale", 4);
		createMultipleTicket(AN_OTHER_SPORT_NAME, AN_OTHER_GAME_DATE, 22f, "Section 100", 2);
	}
	
	@After
	public void teardown() throws Exception {
		File file = new File(FILENAME);
		if (file.exists()) {
			file.delete();
		}
	}

	private void createMultipleTicket(String sportName, DateTime gameDate, float price, String section, int number) throws Exception {
		for (int i = 0; i < number; i++) {
			ticketDao.add(createTicket(sportName, gameDate, price, section, true));
		}
	}

	private TicketDto createTicket(String sportName, DateTime gameDate, float price, String section, boolean available) {
		if ("Générale".equals(section)) {
			return new GeneralTicketDto(atomicLong.incrementAndGet(), sportName, gameDate, price, available);
		}
		long ticketId = atomicLong.incrementAndGet();
		char letter = (char) ((int) 'A' + ticketId / 10);
		long number = ticketId % 10;
		String code = letter + "-" + number;

		return new SeatedTicketDto(ticketId, sportName, gameDate, section, code, price, available);
	}

	@Test
	public void testGetTicket() throws Exception {
		TicketDto actual = ticketDao.get(1);

		TicketDto expected = new GeneralTicketDto(1L, A_SPORT_NAME, A_GAME_DATE, 15.00f, AVAILABLE);
		assertTicket(expected, actual);
	}

	@Test(expected = TicketDoesntExistException.class)
	public void testGetInvalidGameSectionShouldThrow() throws Exception {
		ticketDao.get(-1);
	}

	@Test
	public void testGetAllAvailable() throws Exception {
		List<TicketDto> tickets = ticketDao.getAllAvailable(AN_OTHER_SPORT_NAME, AN_OTHER_GAME_DATE);

		TicketDto expected0 = new GeneralTicketDto(5L, AN_OTHER_SPORT_NAME, AN_OTHER_GAME_DATE, 15.00f, AVAILABLE);
		TicketDto expected1 = new GeneralTicketDto(6L, AN_OTHER_SPORT_NAME, AN_OTHER_GAME_DATE, 15.00f, AVAILABLE);
		TicketDto expected2 = new GeneralTicketDto(7L, AN_OTHER_SPORT_NAME, AN_OTHER_GAME_DATE, 15.00f, AVAILABLE);
		TicketDto expected3 = new GeneralTicketDto(8L, AN_OTHER_SPORT_NAME, AN_OTHER_GAME_DATE, 15.00f, AVAILABLE);
		TicketDto expected4 = new SeatedTicketDto(9L, AN_OTHER_SPORT_NAME, AN_OTHER_GAME_DATE, "Section 100", "A-9", 22.00f, AVAILABLE);
		TicketDto expected5 = new SeatedTicketDto(10L, AN_OTHER_SPORT_NAME, AN_OTHER_GAME_DATE, "Section 100", "B-0", 22.00f, AVAILABLE);

		Assert.assertEquals(6, tickets.size());

		assertTicket(expected0, tickets.get(0));
		assertTicket(expected1, tickets.get(1));
		assertTicket(expected2, tickets.get(2));
		assertTicket(expected3, tickets.get(3));
		assertTicket(expected4, tickets.get(4));
		assertTicket(expected5, tickets.get(5));
	}

	@Test
	public void testGetTicketsForInvalidGameReturnNone() throws Exception {
		List<TicketDto> tickets = ticketDao.getAllAvailable("INVALIDE", DateTime.now());
		Assert.assertTrue(tickets.isEmpty());
	}

	@Test
	public void testGetTicketsForSection() throws Exception {
		List<TicketDto> tickets = ticketDao.getAllInSection(AN_OTHER_SPORT_NAME, AN_OTHER_GAME_DATE, "Section 100");

		TicketDto expected0 = new SeatedTicketDto(9L, AN_OTHER_SPORT_NAME, AN_OTHER_GAME_DATE, "Section 100", "A-9", 22.00f, AVAILABLE);
		TicketDto expected1 = new SeatedTicketDto(10L, AN_OTHER_SPORT_NAME, AN_OTHER_GAME_DATE, "Section 100", "B-0", 22.00f, AVAILABLE);

		Assert.assertEquals(2, tickets.size());

		assertTicket(expected0, tickets.get(0));
		assertTicket(expected1, tickets.get(1));
	}

	@Test(expected = SectionDoesntExistException.class)
	public void testGetTicketsForInvalidSectionShouldThrow() throws Exception {
		ticketDao.getAllInSection("", DateTime.now(), "Général");
	}

	@Test
	public void testAddDto() throws Exception {
		TicketDto toAdd = new GeneralTicketDto(1000L, A_SPORT_NAME, A_GAME_DATE, 20.00f, AVAILABLE);

		ticketDao.add(toAdd);

		TicketDto actual = ticketDao.get(1000);
		TicketDto expected = toAdd;

		assertTicket(expected, actual);
	}

	@Test(expected = TicketAlreadyExistsException.class)
	public void testAddExistingShouldThrow() throws Exception {
		TicketDto toAdd = new SeatedTicketDto(3L, A_SPORT_NAME, A_GAME_DATE, "Front Row", "C-01", 35.00f, AVAILABLE);

		ticketDao.add(toAdd);
	}
	
	@Test
	public void testGetTicketsForGameAndSeat() throws Exception {
		TicketDto actual = ticketDao.get(AN_OTHER_SPORT_NAME, AN_OTHER_GAME_DATE, "Section 100", "A-9");
		TicketDto expected = new SeatedTicketDto(9L, AN_OTHER_SPORT_NAME, AN_OTHER_GAME_DATE, "Section 100", "A-9", 22.00f, AVAILABLE);

		assertTicket(expected, actual);
	}
	
	@Test
	public void testGetAll() throws Exception {
		List<TicketDto> tickets = ticketDao.getAll(AN_OTHER_SPORT_NAME, AN_OTHER_GAME_DATE);

		TicketDto expected0 = new GeneralTicketDto(5L, AN_OTHER_SPORT_NAME, AN_OTHER_GAME_DATE, 15.00f, AVAILABLE);
		TicketDto expected1 = new GeneralTicketDto(6L, AN_OTHER_SPORT_NAME, AN_OTHER_GAME_DATE, 15.00f, AVAILABLE);
		TicketDto expected2 = new GeneralTicketDto(7L, AN_OTHER_SPORT_NAME, AN_OTHER_GAME_DATE, 15.00f, AVAILABLE);
		TicketDto expected3 = new GeneralTicketDto(8L, AN_OTHER_SPORT_NAME, AN_OTHER_GAME_DATE, 15.00f, AVAILABLE);
		TicketDto expected4 = new SeatedTicketDto(9L, AN_OTHER_SPORT_NAME, AN_OTHER_GAME_DATE, "Section 100", "A-9", 22.00f, AVAILABLE);
		TicketDto expected5 = new SeatedTicketDto(10L, AN_OTHER_SPORT_NAME, AN_OTHER_GAME_DATE, "Section 100", "B-0", 22.00f, AVAILABLE);

		Assert.assertEquals(6, tickets.size());

		assertTicket(expected0, tickets.get(0));
		assertTicket(expected1, tickets.get(1));
		assertTicket(expected2, tickets.get(2));
		assertTicket(expected3, tickets.get(3));
		assertTicket(expected4, tickets.get(4));
		assertTicket(expected5, tickets.get(5));
	}
	
	@Test
	public void testUpdate() throws Exception {
		TicketDto updatedTicket = new GeneralTicketDto(5L, AN_OTHER_SPORT_NAME, AN_OTHER_GAME_DATE, 15.00f, NOT_AVAILABLE);
		
		ticketDao.update(updatedTicket);
		
		TicketDto actual = ticketDao.get(5);
		TicketDto expected = updatedTicket;
		
		assertTicket(expected, actual);
	}
	
	@Test(expected=TicketDoesntExistException.class)
	public void testUpdateInvalidTicket() throws Exception {
		TicketDto updatedTicket = new GeneralTicketDto(-1L, AN_OTHER_SPORT_NAME, AN_OTHER_GAME_DATE, 15.00f, NOT_AVAILABLE);
		
		ticketDao.update(updatedTicket);
	}
	
	@Test
	public void testCommit() throws Exception {
		ticketDao.commit();
		ticketDao = new XmlTicketDao(FILENAME);
		
		List<TicketDto> tickets = ticketDao.getAllInSection(AN_OTHER_SPORT_NAME, AN_OTHER_GAME_DATE, "Section 100");

		TicketDto expected0 = new SeatedTicketDto(9L, AN_OTHER_SPORT_NAME, AN_OTHER_GAME_DATE, "Section 100", "A-9", 22.00f, AVAILABLE);
		TicketDto expected1 = new SeatedTicketDto(10L, AN_OTHER_SPORT_NAME, AN_OTHER_GAME_DATE, "Section 100", "B-0", 22.00f, AVAILABLE);

		Assert.assertEquals(2, tickets.size());

		assertTicket(expected0, tickets.get(0));
		assertTicket(expected1, tickets.get(1));
	}

	private void assertTicket(TicketDto expected, TicketDto actual) {
		Assert.assertEquals(expected.ticketId, actual.ticketId);
		Assert.assertEquals(expected.price, actual.price, 0.01f);
		Assert.assertEquals(expected.section, actual.section);
		Assert.assertEquals(expected.seat, actual.seat);
	}
}
