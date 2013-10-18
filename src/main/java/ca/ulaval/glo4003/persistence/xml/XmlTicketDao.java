package ca.ulaval.glo4003.persistence.xml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.directory.NoSuchAttributeException;
import javax.xml.xpath.XPathExpressionException;

import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.domain.dtos.TicketDto;
import ca.ulaval.glo4003.persistence.daos.GameDoesntExistException;
import ca.ulaval.glo4003.persistence.daos.SectionDoesntExistException;
import ca.ulaval.glo4003.persistence.daos.TicketAlreadyExistException;
import ca.ulaval.glo4003.persistence.daos.TicketDao;
import ca.ulaval.glo4003.persistence.daos.TicketDoesntExistException;

@Component
public class XmlTicketDao implements TicketDao {

	private static final String TICKETS_XPATH = "/base/tickets";
	private final static String TICKET_XPATH = TICKETS_XPATH + "/ticket";
	private final static String TICKET_XPATH_ID = TICKET_XPATH + "[id=\"%s\"]";
	private final static String TICKET_XPATH_GAME_ID = TICKET_XPATH + "[gameID=\"%s\"]";
	private final static String TICKET_XPATH_SECTION = TICKET_XPATH_GAME_ID + "[section=\"%s\"]";

	// @Inject
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
		try {
			SimpleNode node = database.extractNode(xPath);
			return convertNodeToTicket(node);
		} catch (XPathExpressionException | NoSuchAttributeException e) {
			throw new XmlIntegrityException(e);
		}
	}

	@Override
	public List<TicketDto> getTicketsForGame(int gameID) throws GameDoesntExistException {
		String xPath = String.format(TICKET_XPATH_GAME_ID, gameID);

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
	public void add(TicketDto ticket) throws TicketAlreadyExistException {
		if (isIdExist(ticket.getTicketId())) {
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
	public List<TicketDto> getTicketsForSection(int gameID, String sectionName) throws SectionDoesntExistException {
		String xPath = String.format(TICKET_XPATH_SECTION, gameID, sectionName);

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

	private boolean isIdExist(int ticketId) {
		String xPath = String.format(TICKET_XPATH_ID, ticketId);
		return database.exist(xPath);
	}

	private SimpleNode convertTicketToNode(TicketDto ticket) {
		Map<String, String> nodes = new HashMap<>();
		nodes.put("id", Integer.toString(ticket.getTicketId()));
		nodes.put("gameID", Long.toString(ticket.getGameId()));
		nodes.put("price", Double.toString(ticket.getPrice()));
		nodes.put("section", ticket.getSection());
		nodes.put("type", ticket.getAdmissionType());

		return new SimpleNode("ticket", nodes);
	}

	private TicketDto convertNodeToTicket(SimpleNode parent) throws NoSuchAttributeException,
			TicketDoesntExistException {
		if (parent.hasNode("id", "gameID", "price", "type", "section")) {
			int ticketId = Integer.parseInt(parent.getNodeValue("id"));
			long gameId = Long.parseLong(parent.getNodeValue("gameID"));
			Double price = Double.parseDouble(parent.getNodeValue("price"));
			String admissionType = parent.getNodeValue("type");
			String section = parent.getNodeValue("section");
			return new TicketDto(gameId, ticketId, price, admissionType, section);
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
}
