package ca.ulaval.glo4003.xml;

import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Assert;
import org.junit.Test;

import ca.ulaval.glo4003.dto.GameDto;

public class XmlGameDaoTest {

	private XmlGameDao gameDao = new XmlGameDao();
	
	@Test
	public void testGetIdForSport() throws Exception {
		String sportName = "Hockey-Masculin";
		List<String> ids = gameDao.getIdForSport(sportName);
		Assert.assertEquals(2, ids.size());
		Assert.assertEquals("1", ids.get(0));
		Assert.assertEquals("2", ids.get(1));
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
	
	private void assertGame(GameDto expected, GameDto actual) {
		Assert.assertEquals(expected.getId(), actual.getId());
		Assert.assertEquals(expected.getOpponents(), actual.getOpponents());
		Assert.assertEquals(expected.getGameDate(), actual.getGameDate());
	}
}
