package ca.ulaval.glo4003.domain.sports;

import java.io.File;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.domain.sports.SportDto;
import ca.ulaval.glo4003.domain.sports.XmlSportDao;
import ca.ulaval.glo4003.exceptions.SportAlreadyExistException;
import ca.ulaval.glo4003.exceptions.SportDoesntExistException;

public class XmlSportDaoIT {
	private static final String FILENAME = "resources/XmlSportDaoIT.xml";
	private static final String SPORT_NAME_4 = "Football";
	private static final String SPORT_NAME_3 = "Rugby-Féminin";
	private static final String SPORT_NAME_2 = "Soccer-Féminin";
	private static final String SPORT_NAME_1 = "Soccer-Masculin";
	private static final SportDto SPORT_1 = new SportDto(SPORT_NAME_1);
	private static final SportDto SPORT_2 = new SportDto(SPORT_NAME_2);
	private static final SportDto SPORT_3 = new SportDto(SPORT_NAME_3);
	private static final SportDto SPORT_4 = new SportDto(SPORT_NAME_4);
	private XmlSportDao sportDao;

	@Before
	public void setUp() throws Exception {
		sportDao = new XmlSportDao(FILENAME);

		sportDao.add(SPORT_1);
		sportDao.add(SPORT_2);
		sportDao.add(SPORT_3);
		sportDao.add(SPORT_4);
	}
	
	@After
	public void teardown() throws Exception {
		File file = new File(FILENAME);
		if (file.exists()) {
			file.delete();
		}
	}

	@Test
	public void testGet() throws Exception {
		String sportName = SPORT_NAME_1;
		SportDto sport = sportDao.get(sportName);
		Assert.assertEquals(sportName, sport.getName());
	}

	@Test
	public void testGetAll() throws Exception {
		List<SportDto> sports = sportDao.getAll();
		
		Assert.assertEquals(4, sports.size());
		Assert.assertTrue(sports.contains(SPORT_1));
		Assert.assertTrue(sports.contains(SPORT_2));
		Assert.assertTrue(sports.contains(SPORT_3));
		Assert.assertTrue(sports.contains(SPORT_4));
	}
	
	@Test
	public void testGetAllSportName() throws Exception {
		List<String> sports = sportDao.getAllSportNames();
		
		Assert.assertEquals(4, sports.size());
		Assert.assertTrue(sports.contains(SPORT_NAME_1));
		Assert.assertTrue(sports.contains(SPORT_NAME_2));
		Assert.assertTrue(sports.contains(SPORT_NAME_3));
		Assert.assertTrue(sports.contains(SPORT_NAME_4));
	}

	@Test(expected = SportDoesntExistException.class)
	public void testGetInvalidSportShouldThrow() throws Exception {
		sportDao.get("Natation");
	}

	@Test
	public void testAddDto() throws Exception {
		SportDto toAdd = new SportDto("Football américain");

		sportDao.add(toAdd);

		List<SportDto> sports = sportDao.getAll();
		Assert.assertEquals(5, sports.size());
		Assert.assertEquals("Football américain", sports.get(4).getName());
	}

	@Test(expected = SportAlreadyExistException.class)
	public void testAddExistingShouldThrow() throws Exception {
		SportDto toAdd = SPORT_1;

		sportDao.add(toAdd);
	}
	
	@Test
	public void testCommit() throws Exception {
		sportDao.commit();
		sportDao = new XmlSportDao(FILENAME);
		
		List<SportDto> sports = sportDao.getAll();
		
		Assert.assertEquals(4, sports.size());
		Assert.assertTrue(sports.contains(SPORT_1));
		Assert.assertTrue(sports.contains(SPORT_2));
		Assert.assertTrue(sports.contains(SPORT_3));
		Assert.assertTrue(sports.contains(SPORT_4));
	}
}
