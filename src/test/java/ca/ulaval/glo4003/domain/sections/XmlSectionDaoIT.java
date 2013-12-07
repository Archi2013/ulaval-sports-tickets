package ca.ulaval.glo4003.domain.sections;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.domain.game.GameDto;
import ca.ulaval.glo4003.domain.game.XmlGameDao;
import ca.ulaval.glo4003.domain.sports.SportDto;
import ca.ulaval.glo4003.domain.sports.XmlSportDao;
import ca.ulaval.glo4003.domain.tickets.GeneralTicketDto;
import ca.ulaval.glo4003.domain.tickets.SeatedTicketDto;
import ca.ulaval.glo4003.domain.tickets.XmlTicketDao;
import ca.ulaval.glo4003.exceptions.SectionDoesntExistException;

public class XmlSectionDaoIT {

	private static final String INVALID_SECTION = "Section 300";
	private static final String A_SEAT = "A-0";
	private static final String SECTION_200 = "Section 200";
	private static final String SECTION_100 = "Section 100";
	private static final float A_PRICE = 15.00f;
	private static final String GENERAL_SECTION = "Générale";
	private static final String A_SPORT_NAME = "Football";
	
	private static final String FILENAME = "resources/XmlSectionDaoIT.xml";

	private XmlSportDao sportDao;
	private XmlGameDao gameDao;
	private XmlTicketDao ticketDao;
	private XmlSectionDao sectionDao;

	private static final String DATE_PATTERN = "yyyy/MM/dd HH:mm z";
	private static final DateTimeFormatter format = DateTimeFormat.forPattern(DATE_PATTERN);
	private static final DateTime A_GAME_DATE = format.parseDateTime("2014/08/24 13:00 EDT");

	@Before
	public void setUp() throws Exception {
		setUpSportGameAndTickets();

		sectionDao = new XmlSectionDao(FILENAME);
	}

	@After
	public void teardown() throws Exception {
		File file = new File(FILENAME);
		if (file.exists()) {
			file.delete();
		}
	}

	@Test
	public void testGetAllSection() throws Exception {
		Set<String> sections = sectionDao.getAllSections();

		Assert.assertEquals(3, sections.size());
		Assert.assertTrue(sections.contains(GENERAL_SECTION));
		Assert.assertTrue(sections.contains(SECTION_100));
		Assert.assertTrue(sections.contains(SECTION_200));
	}
	
	@Test
	public void testGet() throws Exception {
		Set<String> seats = new HashSet<>();
		seats.add(GENERAL_SECTION);
		
		SectionDto actual = sectionDao.get(A_SPORT_NAME, A_GAME_DATE, GENERAL_SECTION);
		SectionDto expected = new SectionDto(GENERAL_SECTION, 1, A_PRICE);
		
		assertSection(expected, actual);
	}
	
	@Test(expected=SectionDoesntExistException.class)
	public void testGetInvalidSection() throws Exception {
		sectionDao.get(A_SPORT_NAME, A_GAME_DATE, INVALID_SECTION);
	}
	
	@Test(expected=SectionDoesntExistException.class)
	public void testGetInvalidDate() throws Exception {
		sectionDao.get(A_SPORT_NAME, null, GENERAL_SECTION);
	}

	@Test
	public void testGetAvailable() throws Exception {
		Set<String> seats = new HashSet<>();
		seats.add(GENERAL_SECTION);
		
		SectionDto actual = sectionDao.getAvailable(A_SPORT_NAME, A_GAME_DATE, GENERAL_SECTION);
		SectionDto expected = new SectionDto(GENERAL_SECTION, 1, A_PRICE);
		
		assertSection(expected, actual);
	}

	@Test
	public void testGetAll() throws Exception {
		List<SectionDto> actuals = sectionDao.getAll(A_SPORT_NAME, A_GAME_DATE);
		
		Set<String> seats = new HashSet<>();
		seats.add(A_SEAT);
		
		SectionDto expected2 = new SectionDto(GENERAL_SECTION, 1, A_PRICE);
		SectionDto expected1 = new SectionDto(SECTION_100, 1, A_PRICE, seats);
		SectionDto expected0 = new SectionDto(SECTION_200, 1, A_PRICE, seats);
		
		assertSection(expected0, actuals.get(0));
		assertSection(expected1, actuals.get(1));
		assertSection(expected2, actuals.get(2));
	}

	@Test
	public void testGetAllAvailable() throws Exception {
		List<SectionDto> actuals = sectionDao.getAllAvailable(A_SPORT_NAME, A_GAME_DATE);

		Set<String> seats = new HashSet<>();
		seats.add(A_SEAT);
		
		SectionDto expected2 = new SectionDto(GENERAL_SECTION, 1, A_PRICE);
		SectionDto expected1 = new SectionDto(SECTION_100, 1, A_PRICE, seats);
		SectionDto expected0 = new SectionDto(SECTION_200, 1, A_PRICE, seats);
		
		assertSection(expected0, actuals.get(0));
		assertSection(expected1, actuals.get(1));
		assertSection(expected2, actuals.get(2));
	}

	private void assertSection(SectionDto expected, SectionDto actual) {
		Assert.assertEquals(expected.getPrice(), actual.getPrice(), 0.001f);
		Assert.assertEquals(expected.getSectionName(), actual.getSectionName());
		Assert.assertEquals(expected.getNumberOfTickets(), actual.getNumberOfTickets());
		Assert.assertEquals(expected.getSeats().size(), actual.getSeats().size());
	}

	private void setUpSportGameAndTickets() throws Exception {
		sportDao = new XmlSportDao(FILENAME);
		sportDao.add(new SportDto(A_SPORT_NAME));
		sportDao.commit();

		gameDao = new XmlGameDao(FILENAME);
		gameDao.add(new GameDto("Vert et or", A_GAME_DATE, A_SPORT_NAME, "Stade Telus"));
		gameDao.commit();

		ticketDao = new XmlTicketDao(FILENAME);
		ticketDao.add(new GeneralTicketDto(1L, A_SPORT_NAME, A_GAME_DATE, 15.00f, true));
		ticketDao.add(new SeatedTicketDto(2L, A_SPORT_NAME, A_GAME_DATE, SECTION_100, A_SEAT, 15.00f, true));
		ticketDao.add(new SeatedTicketDto(3L, A_SPORT_NAME, A_GAME_DATE, SECTION_200, A_SEAT, 15.00f, true));
		ticketDao.commit();
	}
}
