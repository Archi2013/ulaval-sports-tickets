package ca.ulaval.glo4003.persistence.xml;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.domain.sports.SportDto;
import ca.ulaval.glo4003.domain.sports.XmlSportDao;
import ca.ulaval.glo4003.exceptions.SportAlreadyExistException;
import ca.ulaval.glo4003.exceptions.SportDoesntExistException;

public class XmlSportDaoIT {
	private XmlSportDao sportDao;

	@Before
	public void setUp() throws Exception {
		sportDao = new XmlSportDao("resources/SportData.xml");

		sportDao.add(new SportDto("Soccer-Masculin"));
		sportDao.add(new SportDto("Soccer-Féminin"));
		sportDao.add(new SportDto("Rugby-Féminin"));
		sportDao.add(new SportDto("Football"));
	}

	@Test
	public void testGet() throws Exception {
		String sportName = "Soccer-Masculin";
		SportDto sport = sportDao.get(sportName);
		Assert.assertEquals(sportName, sport.getName());
	}

	@Test
	public void testGetAll() throws Exception {
		List<SportDto> sports = sportDao.getAll();
		Assert.assertEquals(4, sports.size());
		Assert.assertEquals("Soccer-Masculin", sports.get(0).getName());
		Assert.assertEquals("Soccer-Féminin", sports.get(1).getName());
		Assert.assertEquals("Rugby-Féminin", sports.get(2).getName());
		Assert.assertEquals("Football", sports.get(3).getName());
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
		SportDto toAdd = new SportDto("Soccer-Masculin");

		sportDao.add(toAdd);
	}
}
