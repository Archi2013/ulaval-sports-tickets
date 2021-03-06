package ca.ulaval.glo4003.utilities.search;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.domain.users.UserAlreadyExistException;
import ca.ulaval.glo4003.domain.users.UserDto;
import ca.ulaval.glo4003.domain.users.XmlUserDao;
import ca.ulaval.glo4003.utilities.search.XmlUserSearchPreferenceDao;
import ca.ulaval.glo4003.utilities.search.dto.UserSearchPreferenceDto;

public class XmlUserSearchPreferenceDaoTest {

	private static final String AN_OTHER_DISPLAY_PERIOD = "ALL";
	private static final ArrayList<String> EMPTY_LIST = new ArrayList<String>();
	private static final String A_DISPLAY_PERIOD = "ONE_WEEK";
	private static final String A_USER_PASSWORD = "test";
	private static final String A_USER_NAME = "mo";

	private static final String FILENAME = "resources/XmlUserPreferencesDaoTest.xml";
	
	private XmlUserSearchPreferenceDao xmlUserSearchPreferenceDao;

	@Before
	public void setUp() throws Exception {
		setUpUser();

		xmlUserSearchPreferenceDao = new XmlUserSearchPreferenceDao(FILENAME);

		List<String> sportsName = new ArrayList<>();
		sportsName.add("Football");

		xmlUserSearchPreferenceDao.save(A_USER_NAME, new UserSearchPreferenceDto(sportsName, A_DISPLAY_PERIOD, Boolean.TRUE, EMPTY_LIST));
	}

	private void setUpUser() throws UserAlreadyExistException {
		XmlUserDao userDao = new XmlUserDao(FILENAME);
		userDao.add(new UserDto(A_USER_NAME, A_USER_PASSWORD));
		userDao.commit();
	}
	
	@After
	public void teardown() throws Exception {
		File file = new File(FILENAME);
		if (file.exists()) {
			file.delete();
		}
	}

	@Test
	public void savingUserPreferencesShouldAddItToXml() throws Exception {

		xmlUserSearchPreferenceDao.save(A_USER_NAME, new UserSearchPreferenceDto(EMPTY_LIST, AN_OTHER_DISPLAY_PERIOD, Boolean.FALSE, EMPTY_LIST));
		UserSearchPreferenceDto ticketSPDto = xmlUserSearchPreferenceDao.get(A_USER_NAME);
		Assert.assertEquals(false, ticketSPDto.isLocalGameOnly());
		Assert.assertEquals(AN_OTHER_DISPLAY_PERIOD, ticketSPDto.getDisplayedPeriod());
	}
	
	@Test
	public void testCommit() throws Exception {
		xmlUserSearchPreferenceDao.commit();
		xmlUserSearchPreferenceDao = new XmlUserSearchPreferenceDao(FILENAME);

		UserSearchPreferenceDto ticketSPDto = xmlUserSearchPreferenceDao.get(A_USER_NAME);
		Assert.assertEquals(Boolean.TRUE, ticketSPDto.isLocalGameOnly());
		Assert.assertEquals(A_DISPLAY_PERIOD, ticketSPDto.getDisplayedPeriod());
	}
}
