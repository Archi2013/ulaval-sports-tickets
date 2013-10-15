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

	private static final String GAMES_XPATH = "/base/games";
	private static final String GAME_XPATH = GAMES_XPATH + "/game";
	private static final String GAME_XPATH_ID = GAME_XPATH + "[id=\"%d\"]";
	private static final String GAME_XPATH_SPORT_NAME = GAME_XPATH + "[sportName=\"%s\"]";

	@Inject
	private XmlDatabase database;

	public XmlGameDao() {
		database = XmlDatabase.getInstance();
	}

	XmlGameDao(String filename) {
		database = XmlDatabase.getUniqueInstance(filename);
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
	public GameDto get(int id) throws GameDoesntExistException {
		String xPath = String.format(GAME_XPATH_ID, id);
		try {
			SimpleNode node = database.extractNode(xPath);
			return convertNodeToGame(node);
		} catch (XPathExpressionException | NoSuchAttributeException e) {
			throw new XmlIntegrityException(e);
		}
	}

	@Override
	public void add(GameDto game) throws GameAlreadyExistException {
		if (isIdExist(game.getId())) {
			throw new GameAlreadyExistException();
		}
		SimpleNode simpleNode = convertGameToNode(game);
		try {
			database.addNode(GAMES_XPATH, simpleNode);
		} catch (XPathExpressionException e) {
			throw new XmlIntegrityException(e);
		}
	}

	private boolean isIdExist(long id) {
		String xPath = String.format(GAME_XPATH_ID, id);
		return database.exist(xPath);
	}

	private SimpleNode convertGameToNode(GameDto game) {
		Map<String, String> nodes = new HashMap<>();
		nodes.put("id", Long.toString(game.getId()));
		nodes.put("oponents", game.getOpponents());
		nodes.put("date", game.getGameDate().toString("yyyyMMdd"));
		SimpleNode simpleNode = new SimpleNode("game", nodes);
		return simpleNode;
	}

	private GameDto convertNodeToGame(SimpleNode node) throws NoSuchAttributeException, GameDoesntExistException {
		if (node.hasNode("id", "oponents", "date", "sportName")) {
			long id = Long.parseLong(node.getNodeValue("id"));
			String opponents = node.getNodeValue("oponents");
			DateTimeFormatter format = DateTimeFormat.forPattern("yyyyMMdd");
			DateTime gameDate = DateTime.parse(node.getNodeValue("date"), format);
			String sportName = node.getNodeValue("sportName");
			return new GameDto(id, opponents, gameDate, sportName);
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
	public void saveChanges(GameDto game) throws GameDoesntExistException {
		// TODO Auto-generated method stub

	}

}
