package ca.ulaval.glo4003.persistence.xml;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.xml.xpath.XPathExpressionException;

import ca.ulaval.glo4003.dto.TicketDto;
import ca.ulaval.glo4003.persistence.dao.GameDoesntExistException;
import ca.ulaval.glo4003.persistence.dao.TicketDao;
import ca.ulaval.glo4003.persistence.dao.TicketDoesntExistException;


public class XmlTicketDao implements TicketDao {
	
	@Inject
	private XmlDatabase database = XmlDatabase.getInstance();

	private String basePath = "/base/tickets/ticket";
	private String mappingPath = "/base/games-tickets/game-tickets";
	
	@Override
    public List<TicketDto> getTicketsForGame(int gameID) throws GameDoesntExistException {
		List<TicketDto> tickets = new ArrayList<>();
		for (Integer id : getIdForGame(gameID)) {
			try {
	            tickets.add(getTicket(id));
            } catch (TicketDoesntExistException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
            }
		}
		return tickets;
    }

	@Override
    public TicketDto getTicket(int ticketId) throws TicketDoesntExistException {
		String xPath = basePath + "[id=\"" + ticketId + "\"]";
		try {
			SimpleNode node = database.extractNode(xPath);
			return createFromNode(node);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		return null;
    }

	List<Integer> getIdForGame(int gameID) {
		List<Integer> ids = new ArrayList<>();
		String xPath = mappingPath + "[@gameID=\"" + gameID + "\"]/tickets/ticket";
		try {
			List<SimpleNode> nodes = database.extractNodeSet(xPath);
			for (SimpleNode node : nodes) {
				if (node.hasNode("ticketID")) {
					int id = Integer.parseInt(node.getNodeValue("ticketID"));
					ids.add(id);
				}
			}
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ids;
	}

	private TicketDto createFromNode(SimpleNode parent) {
		if (parent.hasNode("id", "price", "type", "section")) {
			int ticketId = Integer.parseInt(parent.getNodeValue("id"));
			Double price = Double.parseDouble(parent.getNodeValue("price"));
			String admissionType = parent.getNodeValue("type");
			String section =parent.getNodeValue("section");
			return new TicketDto(null, ticketId, price, admissionType, section);
		}
		return null;
	}

}
