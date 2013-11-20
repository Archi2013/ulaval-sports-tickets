package ca.ulaval.glo4003.persistence.xml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.directory.NoSuchAttributeException;
import javax.xml.xpath.XPathExpressionException;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.domain.dtos.GeneralTicketDto;
import ca.ulaval.glo4003.domain.dtos.SeatedTicketDto;
import ca.ulaval.glo4003.domain.dtos.TicketDto;
import ca.ulaval.glo4003.persistence.daos.GameDoesntExistException;
import ca.ulaval.glo4003.persistence.daos.SectionDoesntExistException;
import ca.ulaval.glo4003.persistence.daos.TicketAlreadyExistException;
import ca.ulaval.glo4003.persistence.daos.TicketDao;
import ca.ulaval.glo4003.persistence.daos.TicketDoesntExistException;

@Component
public class XmlTicketDao implements TicketDao {

	public static final String DATE_PATTERN = "yyyy/MM/dd HH:mm z";
	private static final String TICKETS_XPATH = "/base/tickets";
	private final static String TICKET_XPATH = TICKETS_XPATH + "/ticket";
	private final static String TICKET_XPATH_ID = TICKET_XPATH + "[id=\"%s\"]";
	private final static String TICKET_XPATH_UNIQUE_ID = TICKET_XPATH_ID + "[sportName=\"%s\"][gameDate=\"%s\"]";
	private final static String TICKET_XPATH_GAME_ID = TICKET_XPATH + "[sportName=\"%s\"][gameDate=\"%s\"]";
	private final static String TICKET_AVAILABLE_XPATH_GAME_ID = TICKET_XPATH_GAME_ID + "[available='true']";
	private final static String TICKET_XPATH_SECTION = TICKET_XPATH_GAME_ID + "[section=\"%s\"]";

	private XmlDatabase database;

	public XmlTicketDao() {
		database = XmlDatabase.getInstance();
	}

	XmlTicketDao(String filename) {
		database = XmlDatabase.getUniqueInstance(filename);
	}

	@Override
	public TicketDto get(int ticketId) throws TicketDoesntExistException {
		String xPath = String.format(TICKET_XPATH_ID, ticketId);
		return convertNodeToTicket(xPath);
	}

	private TicketDto convertNodeToTicket(String xPath) throws TicketDoesntExistException {
		try {
			SimpleNode node = database.extractNode(xPath);
			return convertNodeToTicket(node);
		} catch (XPathExpressionException | NoSuchAttributeException e) {
			throw new XmlIntegrityException(e);
		}
	}

	@Override
	public List<TicketDto> getAvailableTicketsForGame(String sportName, DateTime gameDate) throws GameDoesntExistException {
		String xPath = String.format(TICKET_AVAILABLE_XPATH_GAME_ID, sportName, toString(gameDate));
		return convertNodesToTickets(xPath);
	}

	@Override
	public List<TicketDto> getAllTicketsForGame(String sportName, DateTime gameDate) throws GameDoesntExistException {
		String xPath = String.format(TICKET_XPATH_GAME_ID, sportName, gameDate);
		return convertNodesToTickets(xPath);

	}

	private List<TicketDto> convertNodesToTickets(String xPath) throws GameDoesntExistException {
		try {
			List<SimpleNode> nodes = database.extractNodeSet(xPath);
			if (nodes.isEmpty()) {
				throw new GameDoesntExistException();
			}
			return convertNodesToTickets(nodes);
		} catch (NoSuchAttributeException | TicketDoesntExistException | XPathExpressionException e) {
			throw new XmlIntegrityException(e);
		}
	}

	@Override
	public void add(TicketDto ticket) throws TicketAlreadyExistException, GameDoesntExistException {
		if (isIdExist(ticket)) {
			throw new TicketAlreadyExistException();
		}
		SimpleNode simpleNode = convertTicketToNode(ticket);
		try {
			database.addNode(TICKETS_XPATH, simpleNode);
		} catch (XPathExpressionException cause) {
			throw new XmlIntegrityException(cause);
		}
	}

	@Override
	public List<TicketDto> getTicketsForSection(String sportName, DateTime gameDate, String sectionName)
			throws SectionDoesntExistException {
		String xPath = String.format(TICKET_XPATH_SECTION, sportName, toString(gameDate), sectionName);

		try {
			List<SimpleNode> nodes = database.extractNodeSet(xPath);
			if (nodes.isEmpty()) {
				throw new SectionDoesntExistException();
			}
			return convertNodesToTickets(nodes);
		} catch (NoSuchAttributeException | TicketDoesntExistException | XPathExpressionException e) {
			throw new XmlIntegrityException(e);
		}
	}

	private boolean isIdExist(TicketDto ticket) {
		String xPath = String.format(TICKET_XPATH_UNIQUE_ID, ticket.ticketId, ticket.sportName, toString(ticket.gameDate));
		return database.exist(xPath);
	}

	private SimpleNode convertTicketToNode(TicketDto ticket) {
		Map<String, String> nodes = new HashMap<>();
		nodes.put("id", Long.toString(ticket.ticketId));
		nodes.put("price", Double.toString(ticket.price));
		nodes.put("available", Boolean.toString(ticket.available));
		nodes.put("sportName", ticket.sportName);
		nodes.put("gameDate", toString(ticket.gameDate));
		if (ticket.section != null && ticket.seat != null) {
			nodes.put("section", ticket.section);
			nodes.put("seat", ticket.seat);
		}
		return new SimpleNode("ticket", nodes);
	}

	private TicketDto convertNodeToTicket(SimpleNode parent) throws NoSuchAttributeException, TicketDoesntExistException {
		if (parent.hasNode("id", "sportName", "price")) {
			long ticketId = Long.parseLong(parent.getNodeValue("id"));
			Double price = Double.parseDouble(parent.getNodeValue("price"));
			boolean available = Boolean.parseBoolean(parent.getNodeValue("available"));
			String sportName = parent.getNodeValue("sportName");
			DateTime gameDate = parseDate(parent.getNodeValue("gameDate"));
			if (parent.hasNode("section", "seat")) {
				String section = parent.getNodeValue("section");
				String seat = parent.getNodeValue("seat");
				return new SeatedTicketDto(ticketId, sportName, gameDate, section, seat, price, available);
			} else {
				return new GeneralTicketDto(ticketId, sportName, gameDate, price, available);
			}
		}
		throw new TicketDoesntExistException();
	}

	private List<TicketDto> convertNodesToTickets(List<SimpleNode> nodes) throws NoSuchAttributeException,
			TicketDoesntExistException {
		List<TicketDto> tickets = new ArrayList<>();
		for (SimpleNode node : nodes) {
			tickets.add(convertNodeToTicket(node));
		}
		return tickets;
	}

	@Override
	public TicketDto get(String sportName, DateTime date, int ticketID) throws TicketDoesntExistException {
		String xPath = String.format(TICKET_XPATH_UNIQUE_ID, ticketID, sportName, toString(date));
		return convertNodeToTicket(xPath);
	}

	@Override
	public TicketDto get(String sport, DateTime date, String seat) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TicketDto> getTicketsForGame(String sportName, DateTime gameDate) throws GameDoesntExistException {
		return getAllTicketsForGame(sportName, gameDate);
	}

	@Override
	public void update(TicketDto ticket) throws TicketDoesntExistException {
		String xPath = String.format(TICKET_XPATH_UNIQUE_ID, ticket.ticketId, ticket.sportName, toString(ticket.gameDate));
		if (!database.exist(xPath)) {
			throw new TicketDoesntExistException();
		}
		try {
			database.remove(xPath);
			add(ticket);
		} catch (XPathExpressionException | TicketAlreadyExistException | GameDoesntExistException e) {
			throw new XmlIntegrityException(e);
		}
	}

	private DateTime parseDate(String dateString) {
		DateTimeFormatter format = DateTimeFormat.forPattern(DATE_PATTERN);
		return DateTime.parse(dateString, format);
	}

	private String toString(DateTime date) {
		return date.toString(DATE_PATTERN);
	}

	@Override
	public void commit() {
		database.commit();
	}
}
