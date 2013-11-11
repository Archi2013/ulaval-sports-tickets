package ca.ulaval.glo4003.persistence.xml;

import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.domain.dtos.GameDto;
import ca.ulaval.glo4003.persistence.daos.GameAlreadyExistException;
import ca.ulaval.glo4003.persistence.daos.GameDoesntExistException;

public class XmlGameDaoIT {

	private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormat.forPattern(XmlGameDao.DATE_PATTERN);
	private static final DateTime SOME_DATE = DateTime.parse("2013/12/12 18:30 EST", DATE_TIME_FORMAT);

	private XmlGameDao gameDao;

	@Before
	public void setUp() throws Exception {
		gameDao = new XmlGameDao("resources/GameData.xml");
		gameDao.add(new GameDto(null, "Dinos de Calgary", SOME_DATE, "Football", "Stade TELUS-UL"));
		gameDao.add(new GameDto(null, "Redmen de McGill", SOME_DATE, "Football", "Stade TELUS-UL"));
		gameDao.add(new GameDto(null, "McGill", SOME_DATE, "Soccer-Masculin", "Montréal"));
		gameDao.add(new GameDto(null, "Ottawa", SOME_DATE, "Rugby-Féminin", "Stade TELUS-UL"));
	}

	@Test
	public void testGetGamesForSport() throws Exception {
		List<GameDto> ids = gameDao.getGamesForSport("Football");
		Assert.assertEquals(2, ids.size());

		GameDto expected0 = new GameDto(1, "Dinos de Calgary", SOME_DATE, "Football", "Stade TELUS-UL");
		GameDto expected1 = new GameDto(2, "Redmen de McGill", SOME_DATE, "Football", "Stade TELUS-UL");

		assertGame(expected0, ids.get(0));
		assertGame(expected1, ids.get(1));
	}

	@Test
	public void testAddDto() throws Exception {
		GameDto toAdd = new GameDto(5L, "McGill", SOME_DATE, "Soccer-Masculin", "Montréal");

		gameDao.add(toAdd);

		GameDto actual = gameDao.get(5L);
		GameDto expected = toAdd;

		assertGame(expected, actual);
	}

	@Test(expected = GameAlreadyExistException.class)
	public void testAddExistingShouldThrow() throws Exception {
		GameDto toAdd = new GameDto(1, "Carabins", SOME_DATE, "Hockey-Masculin", "");

		gameDao.add(toAdd);
	}

	@Test(expected = GameDoesntExistException.class)
	public void testGetInvalidGameShouldThrow() throws Exception {
		gameDao.get(-1L);
	}

	private void assertGame(GameDto expected, GameDto actual) {
		Assert.assertEquals(expected.getId(), actual.getId());
		Assert.assertEquals(expected.getOpponents(), actual.getOpponents());
		Assert.assertEquals(expected.getGameDate(), actual.getGameDate());
	}
}
