package ca.ulaval.glo4003.persistence.xml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.naming.directory.NoSuchAttributeException;
import javax.xml.xpath.XPathExpressionException;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import ca.ulaval.glo4003.domain.dtos.GameDto;
import ca.ulaval.glo4003.persistence.daos.GameAlreadyExistException;
import ca.ulaval.glo4003.persistence.daos.GameDao;
import ca.ulaval.glo4003.persistence.daos.GameDoesntExistException;
import ca.ulaval.glo4003.persistence.daos.SportDoesntExistException;

public class XmlGameDao implements GameDao {

	@Inject
	private XmlDatabase database;
	private String basePath = "/base/games/game";
	private String mappingPath = "/base/sport-games/sport-game";
	
	public XmlGameDao() {
		database = XmlDatabase.getInstance();
	}
	
	public XmlGameDao(String filename) {
		database = XmlDatabase.getUniqueInstance(filename);
	}

	@Override
	public List<GameDto> getGamesForSport(String sportName) throws SportDoesntExistException {
		List<GameDto> games = new ArrayList<>();
		for (int id : getIdForSport(sportName)) {
			try {
				GameDto game = get(id);
				games.add(game);
			} catch (GameDoesntExistException cause) {
				throw new XmlIntegrityException("'Database contains invalide game ID", cause);
			}
		}
		return games;
	}

	@Override
	public GameDto get(int id) throws GameDoesntExistException {
		String xPath = basePath + "[id=\"" + id + "\"]";
		try {
			SimpleNode node = database.extractNode(xPath);
			return createFromNode(node);
		} catch (XPathExpressionException | NoSuchAttributeException e) {
			throw new XmlIntegrityException(e);
		}
	}

	@Override
	public void add(GameDto game) throws GameAlreadyExistException {
		Map<String, String> nodes = new HashMap<>();
		nodes.put("id", Long.toString(game.getId()));
		nodes.put("oponents", game.getOpponents());
		nodes.put("date", game.getGameDate().toString("yyyyMMdd"));
		SimpleNode simpleNode = new SimpleNode("game", nodes);
		try {
			database.addNode("/base/games", simpleNode);
		} catch (XPathExpressionException e) {
			throw new XmlIntegrityException(e);
		}
	}

	List<Integer> getIdForSport(String sportName) {
		String xPath = mappingPath + "[@name=\"" + sportName + "\"]/games/game";
		try {
			List<SimpleNode> nodes = database.extractNodeSet(xPath);
			return NodesConverter.toIntegerList(nodes, "id");
		} catch (XPathExpressionException | NoSuchAttributeException e) {
			throw new XmlIntegrityException(e);
		}
	}

	private GameDto createFromNode(SimpleNode parent) throws NoSuchAttributeException, GameDoesntExistException {
		if (parent.hasNode("id", "oponents", "date")) {
			long id = Long.parseLong(parent.getNodeValue("id"));
			String opponents = parent.getNodeValue("oponents");
			DateTimeFormatter format = DateTimeFormat.forPattern("yyyyMMdd");
			DateTime gameDate = DateTime.parse(parent.getNodeValue("date"), format);
			return new GameDto(id, opponents, gameDate);
		}
		throw new GameDoesntExistException();
	}

}
