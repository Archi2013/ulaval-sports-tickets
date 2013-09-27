package ca.ulaval.glo4003.xml;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import ca.ulaval.glo4003.dao.GameDoesntExistException;
import ca.ulaval.glo4003.dao.TicketDao;
import ca.ulaval.glo4003.dao.TicketDoesntExistException;
import ca.ulaval.glo4003.dto.TicketDto;


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
			Node node = database.extractNode(xPath);
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
			NodeList nodes = database.extractNodeSet(xPath);
			for (int i = 0; i < nodes.getLength(); i++) {
				Node node = nodes.item(i);
				Node attribute = node.getAttributes().item(0);
				if ("ticketID".equals(attribute.getNodeName())) {
					ids.add(Integer.parseInt(attribute.getTextContent()));
				}
			}
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ids;
	}

	private TicketDto createFromNode(Node parent) {
		Node idNode = parent.getChildNodes().item(1);
		Node priceNode = parent.getChildNodes().item(3);
		Node typeNode = parent.getChildNodes().item(5);
		Node sectionNode = parent.getChildNodes().item(7);

		if ("id".equals(idNode.getNodeName()) && "price".equals(priceNode.getNodeName())
		        && "type".equals(typeNode.getNodeName()) && "section".equals(sectionNode.getNodeName())) {
			int ticketId = Integer.parseInt(idNode.getTextContent());
			Double price = Double.parseDouble(priceNode.getTextContent());
			String admissionType = typeNode.getTextContent();
			String section = sectionNode.getTextContent();
			return new TicketDto(null, ticketId, price, admissionType, section);
		}
		return null;
	}

}
