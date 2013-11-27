package ca.ulaval.glo4003.domain.tickets;

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

import ca.ulaval.glo4003.exceptions.GameDoesntExistException;
import ca.ulaval.glo4003.exceptions.SectionDoesntExistException;
import ca.ulaval.glo4003.exceptions.TicketAlreadyExistsException;
import ca.ulaval.glo4003.exceptions.TicketDoesntExistException;
import ca.ulaval.glo4003.utilities.persistence.SimpleNode;
import ca.ulaval.glo4003.utilities.persistence.XmlDatabase;
import ca.ulaval.glo4003.utilities.persistence.XmlIntegrityException;

@Component
public class XmlTicketDao implements TicketDao {

	public static final String DATE_PATTERN = "yyyy/MM/dd HH:mm z";
	private static final String TICKETS_XPATH = "/base/tickets";
	private final static String TICKET_XPATH = TICKETS_XPATH + "/ticket";

	private final static String TICKET_XPATH_ID = TICKET_XPATH + "[id=\"%s\"]";
	private final static String TICKET_XPATH_ID_AND_SPORT = TICKET_XPATH_ID + "[sportName=\"%s\"][gameDate=\"%s\"]";
	private final static String TICKET_XPATH_SPORT = TICKET_XPATH + "[sportName=\"%s\"][gameDate=\"%s\"]";
	private final static String TICKET_XPATH_SPORT_AND_SEAT = TICKET_XPATH_SPORT + "[section=\"%s\"][seat=\"%s\"]";
	private final static String TICKET_XPATH_AVAILABLE_AND_SPORT = TICKET_XPATH_SPORT + "[available='true']";
	private final static String TICKET_XPATH_SPORT_AND_SECTION = TICKET_XPATH_SPORT + "[section=\"%s\"]";

	private XmlDatabase database;

	public XmlTicketDao() {
		database = XmlDatabase.getInstance();
	}

	public XmlTicketDao(String filename) {
		database = XmlDatabase.getUniqueInstance(filename);
	}

	@Override
	public TicketDto get(int ticketId) throws TicketDoesntExistException {
		String xPath = String.format(TICKET_XPATH_ID, ticketId);
		return convertNodeToTicket(xPath);
	}

	@Override
	public TicketDto get(String sportName, DateTime gameDate, String section, String seat)
			throws TicketDoesntExistException {
		String xPath = String.format(TICKET_XPATH_SPORT_AND_SEAT, sportName, toString(gameDate), section, seat);
		return convertNodeToTicket(xPath);
	}

	@Override
	public List<TicketDto> getAllAvailable(String sportName, DateTime gameDate) throws GameDoesntExistException {
		String xPath = String.format(TICKET_XPATH_AVAILABLE_AND_SPORT, sportName, toString(gameDate));
		return convertNodesToTickets(xPath);
	}

	@Override
	public List<TicketDto> getAll(String sportName, DateTime gameDate) throws GameDoesntExistException {
		String xPath = String.format(TICKET_XPATH_SPORT, sportName, gameDate);
		return convertNodesToTickets(xPath);

	}

	@Override
	public List<TicketDto> getAllInSection(String sportName, DateTime gameDate, String sectionName)
			throws SectionDoesntExistException {
		String xPath = String.format(TICKET_XPATH_SPORT_AND_SECTION, sportName, toString(gameDate), sectionName);

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

	@Override
	public void add(TicketDto ticket) throws TicketAlreadyExistsException, GameDoesntExistException {
		if (doesIdExist(ticket)) {
			throw new TicketAlreadyExistsException();
		}
		SimpleNode simpleNode = convertTicketToNode(ticket);
		try {
			database.addNode(TICKETS_XPATH, simpleNode);
		} catch (XPathExpressionException cause) {
			throw new XmlIntegrityException(cause);
		}
	}

	@Override
	public void update(TicketDto ticket) throws TicketDoesntExistException {
		String xPath = String.format(TICKET_XPATH_ID_AND_SPORT, ticket.ticketId, ticket.sportName,
				toString(ticket.gameDate));
		if (!database.exist(xPath)) {
			throw new TicketDoesntExistException();
		}
		try {
			database.remove(xPath);
			add(ticket);
		} catch (XPathExpressionException | TicketAlreadyExistsException | GameDoesntExistException e) {
			throw new XmlIntegrityException(e);
		}
	}

	private boolean doesIdExist(TicketDto ticket) {
		String xPath = String.format(TICKET_XPATH_ID_AND_SPORT, ticket.ticketId, ticket.sportName,
				toString(ticket.gameDate));
		return database.exist(xPath);
	}

	private TicketDto convertNodeToTicket(String xPath) throws TicketDoesntExistException {
		try {
			SimpleNode node = database.extractNode(xPath);
			return convertNodeToTicket(node);
		} catch (XPathExpressionException | NoSuchAttributeException e) {
			throw new XmlIntegrityException(e);
		}
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

	private TicketDto convertNodeToTicket(SimpleNode parent) throws NoSuchAttributeException,
			TicketDoesntExistException {
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
	@Deprecated
	public TicketDto get(String sportName, DateTime date, int ticketID) throws TicketDoesntExistException {
		String xPath = String.format(TICKET_XPATH_ID_AND_SPORT, ticketID, sportName, toString(date));
		return convertNodeToTicket(xPath);
	}

	@Override
	@Deprecated
	public TicketDto get(String sport, DateTime date, String seat) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Deprecated
	public List<TicketDto> getTicketsForGame(String sportName, DateTime gameDate) throws GameDoesntExistException {
		return getAll(sportName, gameDate);
	}

	private DateTime parseDate(String dateString) {
		DateTimeFormatter format = DateTimeFormat.forPattern(DATE_PATTERN);
		return DateTime.parse(dateString, format);
	}

	private String toString(DateTime date) {
		if (date == null) {
			return "";
		}
		return date.toString(DATE_PATTERN);
	}

	@Override
	public void commit() {
		database.commit();
	}

}
