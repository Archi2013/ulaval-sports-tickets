package ca.ulaval.glo4003.persistance.xml;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import ca.ulaval.glo4003.dto.TicketDto;
import ca.ulaval.glo4003.persistance.xml.XmlTicketDao;

public class XmlTicketDaoIT {
	
	private XmlTicketDao ticketDao = new XmlTicketDao();
	
	@Test
	public void testGetTicket() throws Exception {
		TicketDto actual = ticketDao.getTicket(1);
		
		TicketDto expected = new TicketDto(null, 1, 35.00f, "VIP", "Front Row");
		assertTicket(expected, actual);
	}
	
	@Test
	public void testGetTicketsForGame() throws Exception {
		List<TicketDto> tickets = ticketDao.getTicketsForGame(2);
		
		TicketDto expected0 = new TicketDto(null, 3, 35.00f, "VIP", "Front Row");
		TicketDto expected1 = new TicketDto(null, 4, 35.00f, "VIP", "Front Row");
		TicketDto expected2 = new TicketDto(null, 5, 35.00f, "VIP", "Front Row");
		TicketDto expected3 = new TicketDto(null, 13, 20.00f, "Général", "Rouges");
		TicketDto expected4 = new TicketDto(null, 14, 20.00f, "Général", "Rouges");
		TicketDto expected5 = new TicketDto(null, 15, 20.00f, "Général", "Rouges");
		
		assertTicket(expected0, tickets.get(0));
		assertTicket(expected1, tickets.get(1));
		assertTicket(expected2, tickets.get(2));
		assertTicket(expected3, tickets.get(3));
		assertTicket(expected4, tickets.get(4));
		assertTicket(expected5, tickets.get(5));
	}
	
	private void assertTicket(TicketDto expected, TicketDto actual) {
		Assert.assertEquals(expected.getTicketId(), actual.getTicketId());
		Assert.assertEquals(expected.getPrice(), actual.getPrice(), 0.01f);
		Assert.assertEquals(expected.getAdmissionType(), actual.getAdmissionType());
		Assert.assertEquals(expected.getSection(), actual.getSection());
	}
}
