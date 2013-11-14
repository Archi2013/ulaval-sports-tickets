package ca.ulaval.glo4003.persistence.xml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import javax.naming.directory.NoSuchAttributeException;
import javax.xml.xpath.XPathExpressionException;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.domain.dtos.GameDto;
import ca.ulaval.glo4003.persistence.daos.GameAlreadyExistException;
import ca.ulaval.glo4003.persistence.daos.GameDao;
import ca.ulaval.glo4003.persistence.daos.GameDoesntExistException;
import ca.ulaval.glo4003.persistence.daos.SportDoesntExistException;

@Component
public class XmlGameDao implements GameDao {

	public static final String DATE_PATTERN = "yyyy/MM/dd HH:mm z";
	private static final String GAMES_XPATH = "/base/games";
	private static final String GAME_XPATH = GAMES_XPATH + "/game";
	private static final String GAME_XPATH_ID = GAME_XPATH + "[id=\"%d\"]";
	private static final String GAME_XPATH_SPORT_NAME = GAME_XPATH + "[sportName=\"%s\"]";
	private static final String GAME_XPATH_SPORT_NAME_GAMEDATE = GAME_XPATH_SPORT_NAME + "[date=\"%s\"]";

	private static AtomicLong nextId;

	private XmlDatabase database;

	public XmlGameDao() {
		database = XmlDatabase.getInstance();
	}

	XmlGameDao(String filename) {
		database = XmlDatabase.getUniqueInstance(filename);
		nextId = null;
	}

	@Override
	public List<GameDto> getGamesForSport(String sportName) throws SportDoesntExistException {
		String xPath = String.format(GAME_XPATH_SPORT_NAME, sportName);

		try {
			List<SimpleNode> nodes = database.extractNodeSet(xPath);
			return convertNodesToGames(nodes);
		} catch (NoSuchAttributeException | XPathExpressionException | GameDoesntExistException e) {
			throw new XmlIntegrityException(e);
		}
	}

	@Override
	public GameDto get(Long id) throws GameDoesntExistException {
		String xPath = String.format(GAME_XPATH_ID, id);
		return makeGameWithPath(xPath);
	}

	private GameDto makeGameWithPath(String xPath) throws GameDoesntExistException {
		try {
			SimpleNode node = database.extractNode(xPath);
			return convertNodeToGame(node);
		} catch (XPathExpressionException | NoSuchAttributeException e) {
			throw new XmlIntegrityException(e);
		}
	}

	@Override
	public void add(GameDto game) throws GameAlreadyExistException {
		if (game.getId() != null && isIdExist(game.getId())) {
			throw new GameAlreadyExistException();
		}
		try {
			SimpleNode simpleNode = convertGameToNode(game);
			database.addNode(GAMES_XPATH, simpleNode);
		} catch (XPathExpressionException e) {
			throw new XmlIntegrityException(e);
		}
	}

	synchronized private long getNextId() throws XPathExpressionException {
		if (nextId == null) {
			long next = (long) database.getMaxValue(GAME_XPATH, "id");
			nextId = new AtomicLong(next);
		}
		return nextId.incrementAndGet();
	}

	private boolean isIdExist(long id) {
		String xPath = String.format(GAME_XPATH_ID, id);
		return database.exist(xPath);
	}

	private SimpleNode convertGameToNode(GameDto game) throws XPathExpressionException {
		Map<String, String> nodes = new HashMap<>();
		nodes.put("id", Long.toString(getNextId()));
		nodes.put("oponents", game.getOpponents());
		nodes.put("date", game.getGameDate().toString(DATE_PATTERN));
		nodes.put("sportName", game.getSportName());
		nodes.put("location", game.getLocation());
		SimpleNode simpleNode = new SimpleNode("game", nodes);
		return simpleNode;
	}

	private GameDto convertNodeToGame(SimpleNode node) throws NoSuchAttributeException, GameDoesntExistException {
		if (node.hasNode("id", "oponents", "date", "sportName", "location")) {
			long id = Long.parseLong(node.getNodeValue("id"));
			String opponents = node.getNodeValue("oponents");
			DateTimeFormatter format = DateTimeFormat.forPattern(DATE_PATTERN);
			DateTime gameDate = DateTime.parse(node.getNodeValue("date"), format);
			String sportName = node.getNodeValue("sportName");
			String location = node.getNodeValue("location");
			return new GameDto(id, opponents, gameDate, sportName, location);
		}
		throw new GameDoesntExistException();
	}

	private List<GameDto> convertNodesToGames(List<SimpleNode> nodes) throws NoSuchAttributeException,
			GameDoesntExistException {
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
		return get(new Long(1));
	}

	@Override
	public void update(GameDto dto) throws GameDoesntExistException {
		String xPath = String.format(GAME_XPATH_ID, dto.getId());
		if (!database.exist(xPath)) {
			throw new GameDoesntExistException();
		}
		try {
			database.remove(xPath);
			replace(dto);
		} catch (XPathExpressionException e) {
			throw new XmlIntegrityException(e);
		}
	}

	private void replace(GameDto dto) {
		try {
			SimpleNode simpleNode = convertGameToNodeWithCurrentID(dto);
			database.addNode(GAMES_XPATH, simpleNode);
		} catch (XPathExpressionException e) {
			throw new XmlIntegrityException(e);
		}

	}

	private SimpleNode convertGameToNodeWithCurrentID(GameDto dto) {
		Map<String, String> nodes = new HashMap<>();
		nodes.put("id", Long.toString(dto.getId()));
		nodes.put("oponents", dto.getOpponents());
		nodes.put("date", dto.getGameDate().toString(DATE_PATTERN));
		nodes.put("sportName", dto.getSportName());
		nodes.put("location", dto.getLocation());
		SimpleNode simpleNode = new SimpleNode("game", nodes);
		return simpleNode;
	}
}
