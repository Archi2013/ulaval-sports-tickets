package ca.ulaval.glo4003.persistence.xml;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.xml.xpath.XPathExpressionException;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import ca.ulaval.glo4003.dao.GameDao;
import ca.ulaval.glo4003.dao.GameDoesntExistException;
import ca.ulaval.glo4003.dao.SportDoesntExistException;
import ca.ulaval.glo4003.dto.GameDto;

public class XmlGameDao implements GameDao {

	@Inject
	private XmlDatabase database = XmlDatabase.getInstance();

	private String basePath = "/base/games/game";
	private String mappingPath = "/base/sport-games/sport-game";

	@Override
	public List<GameDto> getGamesForSport(String sportName) throws SportDoesntExistException {
		List<GameDto> games = new ArrayList<>();
		for (long id : getIdForSport(sportName)) {
			try {
				GameDto game = get(id);
				games.add(game);
			} catch (GameDoesntExistException cause) {
				throw new RuntimeException("'Database contains invalide game ID", cause);
			}
		}
		return games;
	}
	
	@Override
	public GameDto get(long id) throws GameDoesntExistException {
		String xPath = basePath + "[id=\"" + id + "\"]";
		try {
			SimpleNode node = database.extractNode(xPath);
			return createFromNode(node);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		return null;
	}

	List<Long> getIdForSport(String sportName) {
		List<Long> ids = new ArrayList<>();
		String xPath = mappingPath + "[@name=\"" + sportName + "\"]/games/game";
		try {
			List<SimpleNode> nodes = database.extractNodeSet(xPath);
			for (SimpleNode node : nodes) {
				if (node.hasNode("id")) {
					long id = Long.parseLong(node.getNodeValue("id"));
					ids.add(id);
				}
			}
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ids;
	}

	private GameDto createFromNode(SimpleNode parent) {
		if (parent.hasNode("id", "oponents", "date")) {
			long id = Long.parseLong(parent.getNodeValue("id"));
			String opponents = parent.getNodeValue("oponents");
			DateTimeFormatter format = DateTimeFormat.forPattern("yyyyMMdd");
			DateTime gameDate = DateTime.parse(parent.getNodeValue("date"), format);
			return new GameDto(id, opponents, gameDate);
		}
		return null;
	}

}
