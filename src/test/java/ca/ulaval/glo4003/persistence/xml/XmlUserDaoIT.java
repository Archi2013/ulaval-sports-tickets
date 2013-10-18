package ca.ulaval.glo4003.persistence.xml;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.domain.dtos.UserDto;
import ca.ulaval.glo4003.domain.utilities.UserAlreadyExistException;
import ca.ulaval.glo4003.domain.utilities.UserDoesntExistException;

public class XmlUserDaoIT {
	private XmlUserDao userDao;
	
	@Before
	public void setUp() throws Exception {
		userDao = new XmlUserDao("/BasicData.xml");
	}
	
	@Test
	public void testGet() throws Exception {
		String userName = "Robert Trebob";
		UserDto user = userDao.get(userName);
		Assert.assertEquals(userName, user.getUsername());
	}
	
	@Test
	public void testGetAll() throws Exception {
		List<UserDto> users = userDao.getAll();
		
		UserDto expected0 = new UserDto("Robert Trebob", "try5yrth");
		UserDto expected1 = new UserDto("Bobby Ybbob", "435gdfg");
		
		Assert.assertEquals(2, users.size());
		assertUser(expected0, users.get(0));
		assertUser(expected1, users.get(1));
	}
	
	@Test(expected=UserDoesntExistException.class)
	public void testGetInvalidUserShouldThrow() throws Exception {
		userDao.get("Jimmy Ymmij");
	}
	
	@Test
	public void testAddDto() throws Exception {
		UserDto toAdd = new UserDto("Herry Yrreh", "jhkvcbd");
		
		userDao.add(toAdd);
		
		List<UserDto> users = userDao.getAll();
		Assert.assertEquals(3, users.size());
		assertUser(toAdd, users.get(2));
	}
	
	@Test(expected=UserAlreadyExistException.class)
	public void testAddExistingShouldThrow() throws Exception {
		UserDto toAdd = new UserDto("Robert Trebob", "try5yrth");
		
		userDao.add(toAdd);
	}
	
	private void assertUser(UserDto expected, UserDto actual) {
		Assert.assertEquals(expected.getUsername(), actual.getUsername());
		Assert.assertEquals(expected.getPassword(), actual.getPassword());
	}
}
