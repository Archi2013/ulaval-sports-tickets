package ca.ulaval.glo4003.domain.game;

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

import ca.ulaval.glo4003.exceptions.GameAlreadyExistException;
import ca.ulaval.glo4003.exceptions.GameDoesntExistException;
import ca.ulaval.glo4003.exceptions.SportDoesntExistException;
import ca.ulaval.glo4003.utilities.persistence.SimpleNode;
import ca.ulaval.glo4003.utilities.persistence.XmlDatabase;
import ca.ulaval.glo4003.utilities.persistence.XmlIntegrityException;

@Component
public class XmlGameDao implements GameDao {

	public static final String DATE_PATTERN = "yyyy/MM/dd HH:mm z";
	private static final String GAMES_XPATH = "/base/games";
	private static final String GAME_XPATH = GAMES_XPATH + "/game";
	private static final String GAME_XPATH_SPORT_NAME = GAME_XPATH + "[sportName=\"%s\"]";
	private static final String GAME_XPATH_SPORT_NAME_GAMEDATE = GAME_XPATH_SPORT_NAME + "[date=\"%s\"]";

	private XmlDatabase database;

	public XmlGameDao() {
		database = XmlDatabase.getInstance();
	}

	public XmlGameDao(String filename) {
		database = XmlDatabase.getUniqueInstance(filename);
	}

	@Override
	public List<GameDto> getGamesForSport(String sportName) throws SportDoesntExistException {
		String xPath = String.format(GAME_XPATH_SPORT_NAME, sportName);

		try {
			List<SimpleNode> nodes = database.extractNodeSet(xPath);
			return convertNodesToGames(nodes);
		} catch (XPathExpressionException e) {
			throw new XmlIntegrityException(e);
		}
	}

	@Override
	public void add(GameDto game) throws GameAlreadyExistException {
		if (isGameExist(game.getSportName(), game.getGameDate())) {
			throw new GameAlreadyExistException();
		}
		try {
			SimpleNode simpleNode = convertGameToNode(game);
			database.addNode(GAMES_XPATH, simpleNode);
		} catch (XPathExpressionException e) {
			throw new XmlIntegrityException(e);
		}
	}

	private boolean isGameExist(String sportName, DateTime gameDate) {
		try {
			get(sportName, gameDate);
			return true;
		} catch (GameDoesntExistException e) {
			return false;
		}
	}

	private SimpleNode convertGameToNode(GameDto game) throws XPathExpressionException {
		Map<String, String> nodes = new HashMap<>();
		nodes.put("oponents", game.getOpponents());
		nodes.put("date", game.getGameDate().toString(DATE_PATTERN));
		nodes.put("sportName", game.getSportName());
		nodes.put("location", game.getLocation());
		return new SimpleNode("game", nodes);
	}

	private GameDto convertNodeToGame(SimpleNode node) {
		try {
			String opponents = node.getNodeValue("oponents");
			DateTimeFormatter format = DateTimeFormat.forPattern(DATE_PATTERN);
			DateTime gameDate = DateTime.parse(node.getNodeValue("date"), format);
			String sportName = node.getNodeValue("sportName");
			String location = node.getNodeValue("location");
			long nextTicketNumber = getNextTicketNumber(sportName, gameDate);
			return new GameDto(opponents, gameDate, sportName, location, nextTicketNumber);
		} catch (NoSuchAttributeException e) {
			throw new XmlIntegrityException();
		}
	}

	private long getNextTicketNumber(String sportName, DateTime gameDate) {
		String xPath = getGameXPathForSportNameAndGameDate(sportName, gameDate);
		try {
			long toReturn = database.getMaxValue(xPath, "id") + 1;
			System.out.println("XmlGameDao: Calcul reussi du nextTicketNumber: " + toReturn);
			return toReturn;
		} catch (XPathExpressionException e) {
			return 0;
		}
	}

	private List<GameDto> convertNodesToGames(List<SimpleNode> nodes) {
		List<GameDto> games = new ArrayList<>();
		for (SimpleNode node : nodes) {
			games.add(convertNodeToGame(node));
		}
		return games;
	}

	@Override
	public void commit() {
		database.commit();
	}

	@Override
	public GameDto get(String sportName, DateTime gameDate) throws GameDoesntExistException {
		String xPath = getGameXPathForSportNameAndGameDate(sportName, gameDate);
		SimpleNode node = getNodeFromXPath(xPath);
		return convertNodeToGame(node);
	}

	@Override
	public void update(GameDto dto) throws GameDoesntExistException {
		String xPath = getGameXPathForSportNameAndGameDate(dto.getSportName(), dto.getGameDate());
		SimpleNode node = getNodeFromXPath(xPath);
		updateNode(node, dto);
		updateDatabase(xPath, node);
	}

	private void updateDatabase(String oldElementXPath, SimpleNode updatedNode) {
		try {
			database.remove(oldElementXPath);
			database.addNode(GAMES_XPATH, updatedNode);
		} catch (XPathExpressionException e) {
			throw new XmlIntegrityException();
		}
	}

	private void updateNode(SimpleNode node, GameDto dto) {
		try {
			node.setNodeValue("oponents", dto.getOpponents());
			node.setNodeValue("location", dto.getLocation());
		} catch (NoSuchAttributeException e) {
			throw new XmlIntegrityException();
		}
	}
	
	private String getGameXPathForSportNameAndGameDate(String sportName, DateTime gameDate) {
		return String.format(GAME_XPATH_SPORT_NAME_GAMEDATE, sportName, gameDate.toString(DATE_PATTERN));
	}
	
	private SimpleNode getNodeFromXPath(String xPath) throws GameDoesntExistException {
		try {
			SimpleNode node = database.extractNode(xPath);
			if (node.isNull()) {
				throw new GameDoesntExistException();
			}
			return node;
		} catch (XPathExpressionException e) {
			throw new GameDoesntExistException();
		}
	}
}
