package ca.ulaval.glo4003.persistence.xml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.naming.directory.NoSuchAttributeException;
import javax.xml.xpath.XPathExpressionException;

import ca.ulaval.glo4003.domain.dtos.TicketDto;
import ca.ulaval.glo4003.persistence.daos.GameDoesntExistException;
import ca.ulaval.glo4003.persistence.daos.TicketDao;
import ca.ulaval.glo4003.persistence.daos.TicketDoesntExistException;


public class XmlTicketDao implements TicketDao {
	
	@Inject
	private XmlDatabase database = XmlDatabase.getInstance();

	private String basePath = "/base/tickets/ticket";
	private String mappingPath = "/base/games-tickets/game-tickets";
	
	@Override
    public List<TicketDto> getTicketsForGame(int gameID) throws GameDoesntExistException {
		List<Integer> ids = getIdForGame(gameID);
		try {
			return getTicketsFromIds(ids);
        } catch (TicketDoesntExistException e) {
            throw new XmlIntegrityException(e);
        }
    }

	private List<TicketDto> getTicketsFromIds(List<Integer> ids) throws TicketDoesntExistException {
		List<TicketDto> tickets = new ArrayList<>();
		for (Integer id : ids) {
			tickets.add(getTicket(id));
		}
		return tickets;
	}

	@Override
    public TicketDto getTicket(int ticketId) throws TicketDoesntExistException {
		String xPath = basePath + "[id=\"" + ticketId + "\"]";
		try {
			SimpleNode node = database.extractNode(xPath);
			return createFromNode(node);
		} catch (XPathExpressionException | NoSuchAttributeException e) {
			throw new XmlIntegrityException(e);
		}
    }
	
	public void add(TicketDto ticket) {
		Map<String, String> nodes = new HashMap<>();
		nodes.put("id", Integer.toString(ticket.getTicketId()));
		nodes.put("gameID", Long.toString(ticket.getGameId()));
		nodes.put("price", Double.toString(ticket.getPrice()));
		nodes.put("section", ticket.getSection());
		nodes.put("type", ticket.getAdmissionType());
		
		SimpleNode simpleNode = new SimpleNode("ticket", nodes);
		try {
	        database.addNode("/base/tickets", simpleNode);
        } catch (XPathExpressionException cause) {
	        throw new RuntimeException(cause);
        }
	}

	List<Integer> getIdForGame(int gameID) throws GameDoesntExistException {
		String xPath = mappingPath + "[@gameID=\"" + gameID + "\"]/tickets/ticket";
		try {
			List<SimpleNode> nodes = database.extractNodeSet(xPath);
			
			return NodesConverter.toIntegerList(nodes, "ticketID");
		} catch (XPathExpressionException e) {
			throw new XmlIntegrityException(e);
		} catch (NoSuchAttributeException e) {
			throw new GameDoesntExistException();
		}
	}

	private TicketDto createFromNode(SimpleNode parent) throws NoSuchAttributeException, TicketDoesntExistException {
		if (parent.hasNode("id", "gameID", "price", "type", "section")) {
			int ticketId = Integer.parseInt(parent.getNodeValue("id"));
			long gameId = Long.parseLong(parent.getNodeValue("gameID"));
			Double price = Double.parseDouble(parent.getNodeValue("price"));
			String admissionType = parent.getNodeValue("type");
			String section =parent.getNodeValue("section");
			return new TicketDto(gameId, ticketId, price, admissionType, section);
		}
		throw new TicketDoesntExistException();
	}

}
