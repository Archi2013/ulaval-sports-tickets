package ca.ulaval.glo4003.domain.game;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.exceptions.GameAlreadyExistException;
import ca.ulaval.glo4003.exceptions.GameDoesntExistException;
import ca.ulaval.glo4003.utilities.search.UserSearchPreferenceDto;

public class XmlGameDaoIT {

	private static final String FILENAME = "resources/XmlGameDaoIT.xml";
	private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormat.forPattern(XmlGameDao.DATE_PATTERN);
	private static final DateTime SOME_DATE = DateTime.parse("2013/12/12 18:30 EST", DATE_TIME_FORMAT);
	private static final GameDto GAME_4 = new GameDto("Ottawa", SOME_DATE, "Rugby-Féminin", "Stade TELUS-UL");
	private static final GameDto GAME_3 = new GameDto("McGill", SOME_DATE, "Soccer-Masculin", "Montréal");
	private static final GameDto GAME_1 = new GameDto("Dinos de Calgary", SOME_DATE, "Football", "Stade TELUS-UL");
	private static final DateTime OTHER_DATE = DateTime.parse("2013/11/11 18:30 EST", DATE_TIME_FORMAT);
	private static final GameDto GAME_2 = new GameDto("Redmen de McGill", OTHER_DATE, "Football", "Stade TELUS-UL");

	private XmlGameDao gameDao;

	@Before
	public void setUp() throws Exception {
		gameDao = new XmlGameDao(FILENAME);
		gameDao.add(GAME_1);
		gameDao.add(GAME_2);
		gameDao.add(GAME_3);
		gameDao.add(GAME_4);
	}
	
	@After
	public void teardown() throws Exception {
		File file = new File(FILENAME);
		if (file.exists()) {
			file.delete();
		}
	}

	@Test
	public void testGetGamesForSport() throws Exception {
		List<GameDto> ids = gameDao.getGamesForSport("Football");
		Assert.assertEquals(2, ids.size());

		GameDto expected0 = GAME_1;
		GameDto expected1 = GAME_2;

		assertGame(expected0, ids.get(0));
		assertGame(expected1, ids.get(1));
	}
	
	@Test
	public void testGetGame() throws Exception {
		GameDto game = gameDao.get("Football", SOME_DATE);
		GameDto expected = GAME_1;
		assertGame(expected, game);
	}
	
	@Test
	public void testCommit() throws Exception {
		gameDao.commit();
		gameDao = new XmlGameDao(FILENAME);
		
		GameDto game = gameDao.get("Football", SOME_DATE);
		GameDto expected = GAME_1;
		assertGame(expected, game);
	}
	
	@Test
	public void testUpdate() throws Exception {
		GameDto updatedGame = new GameDto("Redmen de McGill", SOME_DATE, "Football", "Montréal");
		gameDao.update(updatedGame);
		
		GameDto actual = gameDao.get("Football", SOME_DATE);
		GameDto expected = updatedGame;
		assertGame(expected, actual);
	}
	
	@Test(expected=GameDoesntExistException.class)
	public void testUpdateNonExisting() throws Exception {
		GameDto updatedGame = new GameDto("McGill", OTHER_DATE, "Soccer-Masculin", "Montréal");
		gameDao.update(updatedGame);
	}

	@Test
	public void testAddDto() throws Exception {
		GameDto toAdd = new GameDto("McGill", OTHER_DATE, "Soccer-Masculin", "Montréal");
		gameDao.add(toAdd);

		GameDto actual = gameDao.get("Soccer-Masculin", OTHER_DATE);
		GameDto expected = toAdd;

		assertGame(expected, actual);
	}

	@Test(expected = GameAlreadyExistException.class)
	public void testAddExistingShouldThrow() throws Exception {
		GameDto toAdd = GAME_2;

		gameDao.add(toAdd);
	}

	@Test(expected = GameDoesntExistException.class)
	public void testGetInvalidGameShouldThrow() throws Exception {
		gameDao.get("BaseketBall", DateTime.now());
	}
	
	@Test
	public void testBuildOrClause() throws Exception {
		List<String> sportNames = new ArrayList<>();
		sportNames.add("Baseball");
		sportNames.add("Football");
		
		String actual = gameDao.buildOrClause("sportName", sportNames);
		String expected = "[sportName=\"Baseball\" or sportName=\"Football\"]";
		
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testGetFromUserSearchPreferenceLocalOnlyNone() throws Exception {
		List<String> selectedSports = new ArrayList<>();
		selectedSports.add(GAME_3.getSportName());
		UserSearchPreferenceDto userSearchPreference = new UserSearchPreferenceDto(selectedSports, null, true, null);
		
		List<GameDto> actuals = gameDao.getFromUserSearchPreference(userSearchPreference);
		Assert.assertTrue(actuals.isEmpty());
	}
	
	@Test
	public void testGetFromUserSearchPreferenceLocalOnly() throws Exception {
		List<String> selectedSports = new ArrayList<>();
		selectedSports.add(GAME_4.getSportName());
		UserSearchPreferenceDto userSearchPreference = new UserSearchPreferenceDto(selectedSports, null, true, null);
		
		List<GameDto> actuals = gameDao.getFromUserSearchPreference(userSearchPreference);
		GameDto expected = GAME_4;
		Assert.assertEquals(1, actuals.size());
		assertGame(expected, actuals.get(0));
	}
	
	@Test
	public void testGetFromUserSearchPreference() throws Exception {
		List<String> selectedSports = new ArrayList<>();
		selectedSports.add(GAME_3.getSportName());
		UserSearchPreferenceDto userSearchPreference = new UserSearchPreferenceDto(selectedSports, null, false, null);
		
		List<GameDto> actuals = gameDao.getFromUserSearchPreference(userSearchPreference);
		GameDto expected = GAME_3;
		Assert.assertEquals(1, actuals.size());
		assertGame(expected, actuals.get(0));
	}

	private void assertGame(GameDto expected, GameDto actual) {
		Assert.assertEquals(expected.getOpponents(), actual.getOpponents());
		Assert.assertEquals(expected.getGameDate(), actual.getGameDate());
	}
}
