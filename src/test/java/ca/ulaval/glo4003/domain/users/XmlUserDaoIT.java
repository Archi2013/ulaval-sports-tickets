package ca.ulaval.glo4003.domain.users;

import java.io.File;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.domain.users.UserAlreadyExistException;
import ca.ulaval.glo4003.domain.users.UserDoesntExistException;
import ca.ulaval.glo4003.domain.users.UserDto;
import ca.ulaval.glo4003.domain.users.XmlUserDao;

public class XmlUserDaoIT {
	
	private static final String FILENAME = "resources/XmlUserDaoIT.xml";
	
	private static final String INVALID_USER_NAME = "Robert";
	private static final String A_USER_NAME = "Robert Trebob";
	private XmlUserDao userDao;

	@Before
	public void setUp() throws Exception {
		userDao = new XmlUserDao(FILENAME);
		userDao.add(new UserDto(A_USER_NAME, "try5yrth"));
		userDao.add(new UserDto("Bobby Ybbob", "435gdfg"));
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
		UserDto user = userDao.get(A_USER_NAME);
		Assert.assertEquals(A_USER_NAME, user.getUsername());
	}

	@Test
	public void testGetAll() throws Exception {
		List<UserDto> users = userDao.getAll();

		UserDto expected0 = new UserDto(A_USER_NAME, "try5yrth");
		UserDto expected1 = new UserDto("Bobby Ybbob", "435gdfg");

		Assert.assertEquals(2, users.size());
		assertUser(expected0, users.get(0));
		assertUser(expected1, users.get(1));
	}

	@Test(expected = UserDoesntExistException.class)
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

	@Test(expected = UserAlreadyExistException.class)
	public void testAddExistingShouldThrow() throws Exception {
		UserDto toAdd = new UserDto(A_USER_NAME, "try5yrth");

		userDao.add(toAdd);
	}
	
	@Test
	public void testDoesUserExist() throws Exception {
		boolean userExist = userDao.doesUserExist(A_USER_NAME);
		
		Assert.assertTrue(userExist);
	}
	
	@Test
	public void testDoesUserDoesNotExist() throws Exception {
		boolean userExist = userDao.doesUserExist(INVALID_USER_NAME);
		
		Assert.assertFalse(userExist);
	}
	
	@Test
	public void testCommit() throws Exception {
		userDao.commit();
		userDao = new XmlUserDao(FILENAME);
		
		UserDto user = userDao.get(A_USER_NAME);
		Assert.assertEquals(A_USER_NAME, user.getUsername());
	}

	private void assertUser(UserDto expected, UserDto actual) {
		Assert.assertEquals(expected.getUsername(), actual.getUsername());
		Assert.assertEquals(expected.getPassword(), actual.getPassword());
	}
}
