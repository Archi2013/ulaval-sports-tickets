package ca.ulaval.glo4003.xml;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.xml.xpath.XPathExpressionException;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import ca.ulaval.glo4003.data_access.GameDao;
import ca.ulaval.glo4003.data_access.SportDoesntExistException;
import ca.ulaval.glo4003.dtos.GameDto;

public class XmlGameDao implements GameDao {

	@Inject
	private XmlDatabase database = XmlDatabase.getInstance();

	private String basePath = "/base/games/game";
	private String mappingPath = "/base/sport-games/sport-game";

	@Override
	public List<GameDto> getGamesForSport(String sportName) throws SportDoesntExistException {
		List<GameDto> games = new ArrayList<>();
		for (String id : getIdForSport(sportName)) {
			games.add(get(id));
		}
		return games;
	}

	List<String> getIdForSport(String sportName) {
		List<String> ids = new ArrayList<>();
		String xPath = mappingPath + "[@name=\"" + sportName + "\"]/games/game";
		try {
			NodeList nodes = database.extractNodeSet(xPath);
			for (int i = 0; i < nodes.getLength(); i++) {
				Node node = nodes.item(i);
				Node attribute = node.getAttributes().item(0);
				if ("id".equals(attribute.getNodeName())) {
					ids.add(attribute.getTextContent());
				}
			}
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ids;
	}

	private GameDto get(String id) {
		String xPath = basePath + "[id=\"" + id + "\"]";
		try {
			Node node = database.extractNode(xPath);
			return createFromNode(node);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		return null;
	}

	private GameDto createFromNode(Node parent) {
		Node idNode = parent.getChildNodes().item(1);
		Node oponentsNode = parent.getChildNodes().item(3);
		Node dateNode = parent.getChildNodes().item(5);

		if ("id".equals(idNode.getNodeName()) && "oponents".equals(oponentsNode.getNodeName())
		        && "date".equals(dateNode.getNodeName())) {
			long id = Long.parseLong(idNode.getTextContent());
			String opponents = oponentsNode.getTextContent();
			DateTimeFormatter format = DateTimeFormat.forPattern("yyyyMMdd");
			DateTime gameDate = DateTime.parse(dateNode.getTextContent(), format);
			return new GameDto(id, opponents, gameDate);
		}
		return null;
	}

}
