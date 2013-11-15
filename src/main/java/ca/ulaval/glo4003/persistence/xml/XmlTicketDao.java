package ca.ulaval.glo4003.persistence.xml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.directory.NoSuchAttributeException;
import javax.xml.xpath.XPathExpressionException;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.domain.dtos.TicketDto;
import ca.ulaval.glo4003.persistence.daos.GameDoesntExistException;
import ca.ulaval.glo4003.persistence.daos.SectionDoesntExistException;
import ca.ulaval.glo4003.persistence.daos.TicketAlreadyExistException;
import ca.ulaval.glo4003.persistence.daos.TicketDao;
import ca.ulaval.glo4003.persistence.daos.TicketDoesntExistException;

@Component
public class XmlTicketDao implements TicketDao {

	public static final String DATE_PATTERN = "yyyy/MM/dd HH:mm z";
	private static final String GAMES_XPATH_SPORTNAME_GAMEDATE = "/base/games/game[sportName=\"%s\"][date=\"%s\"]";
	private static final String TICKETS_XPATH = "/base/tickets";
	private final static String TICKET_XPATH = TICKETS_XPATH + "/ticket";
	private final static String TICKET_XPATH_ID = TICKET_XPATH + "[id=\"%s\"]";
	private final static String TICKET_XPATH_UNIQUE_ID = TICKET_XPATH_ID + "[gameID=\"%s\"]";
	private final static String TICKET_XPATH_GAME_ID = TICKET_XPATH + "[gameID=\"%s\"]";
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
	public List<TicketDto> getAvailableTicketsForGame(Long gameID) throws GameDoesntExistException {
		String xPath = String.format(TICKET_AVAILABLE_XPATH_GAME_ID, gameID);

		return convertNodesToTickets(xPath);
	}

	@Override
	public List<TicketDto> getAllTicketsForGame(Long gameID) throws GameDoesntExistException {
		String xPath = String.format(TICKET_XPATH_GAME_ID, gameID);
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
		System.out.println("SportName a la sauvegarde: " + ticket.sportName);
		System.out.println("Gamedate a la sauvegarde : " + ticket.gameDate);
		ticket.gameId = getGameID(ticket.sportName, ticket.gameDate);
		System.out.println("TicketId a la sauvegarde: " + ticket.ticketId);
		System.out.println("GameId a la sauvegarde  : " + ticket.gameId);
		if (isIdExist(ticket.getTicketId(), ticket.gameId)) {
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

	private boolean isIdExist(long ticketId, long gameId) {
		String xPath = String.format(TICKET_XPATH_UNIQUE_ID, ticketId, gameId);
		return database.exist(xPath);
	}

	private SimpleNode convertTicketToNode(TicketDto ticket) {
		Map<String, String> nodes = new HashMap<>();
		nodes.put("id", Long.toString(ticket.getTicketId()));
		nodes.put("gameID", Long.toString(ticket.getGameId()));
		nodes.put("price", Double.toString(ticket.getPrice()));
		nodes.put("available", Boolean.toString(ticket.isAvailable()));
		if (ticket.getSection() != null && ticket.getSeat() != null) {
			nodes.put("section", ticket.getSection());
			nodes.put("seat", ticket.getSeat());
		}
		return new SimpleNode("ticket", nodes);
	}

	private TicketDto convertNodeToTicket(SimpleNode parent) throws NoSuchAttributeException, TicketDoesntExistException {
		if (parent.hasNode("id", "gameID", "price")) {
			int ticketId = Integer.parseInt(parent.getNodeValue("id"));
			long gameId = Long.parseLong(parent.getNodeValue("gameID"));
			Double price = Double.parseDouble(parent.getNodeValue("price"));
			boolean available = Boolean.parseBoolean(parent.getNodeValue("available"));
			if (parent.hasNode("section", "seat")) {
				String section = parent.getNodeValue("section");
				String seat = parent.getNodeValue("seat");
				return new TicketDto(gameId, ticketId, price, section, seat, available);
			} else {
				return new TicketDto(gameId, ticketId, price, available);
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
		try {
			Long gameID = getGameID(sportName, date);
			String xPath = String.format(TICKET_XPATH_UNIQUE_ID, ticketID, gameID);
			return convertNodeToTicket(xPath);
		} catch (GameDoesntExistException e) {
			throw new TicketDoesntExistException();
		}
	}

	@Override
	public TicketDto get(String sport, DateTime date, String seat) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TicketDto> getTicketsForGame(String sportName, DateTime gameDate) throws GameDoesntExistException {
		Long gameID = getGameID(sportName, gameDate);
		System.out.println("GameID durant la lecture du dao: " + gameID);
		List<TicketDto> toReturn = getAllTicketsForGame(gameID);
		for (TicketDto data : toReturn) {
			data.sportName = sportName;
			data.gameDate = gameDate;
		}
		return toReturn;
	}

	@Override
	public void update(TicketDto dto) throws TicketDoesntExistException {
		try {
			dto.gameId = getGameID(dto.sportName, dto.gameDate);
		} catch (GameDoesntExistException e1) {
			throw new TicketDoesntExistException();
		}
		System.out.println("Le ticket id est: " + dto.ticketId);
		String xPath = String.format(TICKET_XPATH_UNIQUE_ID, dto.getTicketId(), dto.gameId);
		if (!database.exist(xPath)) {
			throw new TicketDoesntExistException();
		}
		try {
			database.remove(xPath);
			add(dto);
		} catch (XPathExpressionException | TicketAlreadyExistException | GameDoesntExistException e) {
			throw new XmlIntegrityException(e);
		}
	}

	@Override
	public void commit() {
		database.commit();
	}

	public Long getGameID(String sportName, DateTime gameDate) throws GameDoesntExistException {
		String xPath = String.format(GAMES_XPATH_SPORTNAME_GAMEDATE, sportName, gameDate.toString(DATE_PATTERN));
		SimpleNode node;
		try {
			node = database.extractNode(xPath);
			return Long.parseLong(node.getNodeValue("id"));
		} catch (XPathExpressionException | NumberFormatException | NoSuchAttributeException e) {
			throw new GameDoesntExistException();
		}
	}

}
