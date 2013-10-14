package ca.ulaval.glo4003.persistence.xml;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.domain.dtos.SportDto;

public class XmlSportDaoIT {
	private XmlSportDao sportDao;
	
	@Before
	public void setUp() throws Exception {
		sportDao = new XmlSportDao("/BasicData.xml");
	}
	
	@Test
	public void testGet() throws Exception {
		String sportName = "Hockey-Masculin";
		SportDto sport = sportDao.get(sportName);
		Assert.assertEquals(sportName, sport.getName());
	}
	
	@Test
	public void testGetAll() throws Exception {
		List<SportDto> sports = sportDao.getAll();
		Assert.assertEquals(3, sports.size());
		Assert.assertEquals("Hockey-Masculin", sports.get(0).getName());
		Assert.assertEquals("Baseball-Masculin", sports.get(1).getName());
		Assert.assertEquals("Volleyball-Feminin", sports.get(2).getName());
	}
	
	@Test
	public void testAddDto() throws Exception {
		SportDto toAdd = new SportDto("Football américain");
		
		sportDao.add(toAdd);
		
		List<SportDto> sports = sportDao.getAll();
		Assert.assertEquals(4, sports.size());
		Assert.assertEquals("Football américain", sports.get(3).getName());
	}
}
