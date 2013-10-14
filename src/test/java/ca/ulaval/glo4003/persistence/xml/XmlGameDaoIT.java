package ca.ulaval.glo4003.persistence.xml;

import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.domain.dtos.GameDto;
import ca.ulaval.glo4003.persistence.daos.GameDoesntExistException;

public class XmlGameDaoIT {

	private XmlGameDao gameDao;
	
	@Before
	public void setUp() throws Exception {
		gameDao = new XmlGameDao("/BasicData.xml");
	}

	@Test
	public void testGetIdForSport() throws Exception {
		String sportName = "Hockey-Masculin";
		List<Integer> ids = gameDao.getIdForSport(sportName);
		Assert.assertEquals(2, ids.size());
		Assert.assertEquals(new Integer(1), ids.get(0));
		Assert.assertEquals(new Integer(2), ids.get(1));
	}

	@Test
	public void testGetGamesForSport() throws Exception {
		String sportName = "Hockey-Masculin";
		List<GameDto> ids = gameDao.getGamesForSport(sportName);
		Assert.assertEquals(2, ids.size());

		DateTimeFormatter format = DateTimeFormat.forPattern("yyyyMMdd");
		GameDto expected0 = new GameDto(1, "Carabins", DateTime.parse("20131212", format));
		GameDto expected1 = new GameDto(2, "Redmen", DateTime.parse("20131212", format));

		assertGame(expected0, ids.get(0));
		assertGame(expected1, ids.get(1));
	}

	@Test
	public void testAddDto() throws Exception {
		DateTimeFormatter format = DateTimeFormat.forPattern("yyyyMMdd");
		GameDto toAdd = new GameDto(4, "Vert et Or", DateTime.parse("20131201", format));

		gameDao.add(toAdd);

		GameDto actual = gameDao.get(4);
		GameDto expected = toAdd;

		assertGame(expected, actual);
	}
	
	@Test(expected=GameDoesntExistException.class)
	public void testGetInvalidGameShouldThrow() throws Exception {
		gameDao.get(-1);
	}

	private void assertGame(GameDto expected, GameDto actual) {
		Assert.assertEquals(expected.getId(), actual.getId());
		Assert.assertEquals(expected.getOpponents(), actual.getOpponents());
		Assert.assertEquals(expected.getGameDate(), actual.getGameDate());
	}
}
