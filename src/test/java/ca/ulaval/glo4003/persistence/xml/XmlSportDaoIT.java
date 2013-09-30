package ca.ulaval.glo4003.persistence.xml;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import ca.ulaval.glo4003.dto.SportDto;
import ca.ulaval.glo4003.persistence.xml.XmlSportDao;

public class XmlSportDaoIT {
	private XmlSportDao sportDao = new XmlSportDao();
	
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
}
