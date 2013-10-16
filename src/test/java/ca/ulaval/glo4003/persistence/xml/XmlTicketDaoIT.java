package ca.ulaval.glo4003.persistence.xml;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.domain.dtos.TicketDto;
import ca.ulaval.glo4003.persistence.daos.GameDoesntExistException;
import ca.ulaval.glo4003.persistence.daos.SectionDoesntExistException;
import ca.ulaval.glo4003.persistence.daos.TicketAlreadyExistException;
import ca.ulaval.glo4003.persistence.daos.TicketDoesntExistException;

public class XmlTicketDaoIT {
	
	private XmlTicketDao ticketDao;
	
	@Before
	public void setUp() throws Exception {
		ticketDao = new XmlTicketDao("/BasicData.xml");
	}
	
	@Test
	public void testGetTicket() throws Exception {
		TicketDto actual = ticketDao.getTicket(1);
		
		TicketDto expected = new TicketDto(1, 1, 35.00f, "VIP", "Front Row");
		assertTicket(expected, actual);
	}
	
	@Test(expected=TicketDoesntExistException.class)
	public void testGetInvalidGameSectionShouldThrow() throws Exception {
		ticketDao.getTicket(-1);
	}
	
	@Test
	public void testGetTicketsForGame() throws Exception {
		List<TicketDto> tickets = ticketDao.getTicketsForGame(2);
		
		TicketDto expected0 = new TicketDto(2, 3, 35.00f, "VIP", "Front Row");
		TicketDto expected1 = new TicketDto(2, 4, 35.00f, "VIP", "Front Row");
		TicketDto expected2 = new TicketDto(2, 5, 35.00f, "VIP", "Front Row");
		TicketDto expected3 = new TicketDto(2, 13, 20.00f, "VIP", "Rouges");
		TicketDto expected4 = new TicketDto(2, 14, 20.00f, "VIP", "Rouges");
		TicketDto expected5 = new TicketDto(2, 15, 20.00f, "VIP", "Rouges");
		
		Assert.assertEquals(6, tickets.size());
		
		assertTicket(expected0, tickets.get(0));
		assertTicket(expected1, tickets.get(1));
		assertTicket(expected2, tickets.get(2));
		assertTicket(expected3, tickets.get(3));
		assertTicket(expected4, tickets.get(4));
		assertTicket(expected5, tickets.get(5));
	}
	
	@Test(expected=GameDoesntExistException.class)
	public void testGetTicketsForInvalidGameShouldThrow() throws Exception {
		ticketDao.getTicketsForGame(-1);
	}
	
	@Test
	public void testGetTicketsForSection() throws Exception {
		List<TicketDto> tickets = ticketDao.getTicketsForSection(2, "Front Row");
		
		TicketDto expected0 = new TicketDto(2, 3, 35.00f, "VIP", "Front Row");
		TicketDto expected1 = new TicketDto(2, 4, 35.00f, "VIP", "Front Row");
		TicketDto expected2 = new TicketDto(2, 5, 35.00f, "VIP", "Front Row");
		
		Assert.assertEquals(3, tickets.size());
		
		assertTicket(expected0, tickets.get(0));
		assertTicket(expected1, tickets.get(1));
		assertTicket(expected2, tickets.get(2));
	}
	
	@Test(expected=SectionDoesntExistException.class)
	public void testGetTicketsForInvalidSectionShouldThrow() throws Exception {
		ticketDao.getTicketsForSection(2, "Général");
	}
	
	@Test
	public void testAddDto() throws Exception {
		TicketDto toAdd = new TicketDto(1, 1000, 20.00f, "Général", "Rouges");
		
		ticketDao.add(toAdd);
		
		TicketDto actual = ticketDao.getTicket(1000);
		TicketDto expected = toAdd;
		
		assertTicket(expected, actual);
	}
	
	@Test(expected=TicketAlreadyExistException.class)
	public void testAddExistingShouldThrow() throws Exception {
		TicketDto toAdd = new TicketDto(2, 3, 35.00f, "VIP", "Front Row");
		
		ticketDao.add(toAdd);
	}
	
	private void assertTicket(TicketDto expected, TicketDto actual) {
		Assert.assertEquals(expected.getTicketId(), actual.getTicketId());
		Assert.assertEquals(expected.getGameId(), actual.getGameId());
		Assert.assertEquals(expected.getPrice(), actual.getPrice(), 0.01f);
		Assert.assertEquals(expected.getAdmissionType(), actual.getAdmissionType());
		Assert.assertEquals(expected.getSection(), actual.getSection());
	}
}
