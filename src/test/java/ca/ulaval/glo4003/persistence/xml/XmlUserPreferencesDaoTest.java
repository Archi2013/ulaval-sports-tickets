package ca.ulaval.glo4003.persistence.xml;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.domain.users.User;
import ca.ulaval.glo4003.exceptions.UserDoesntHaveSavedSearchPreference;
import ca.ulaval.glo4003.utilities.search.UserSearchPreferenceDto;
import ca.ulaval.glo4003.utilities.search.UserSearchPreferenceDoesntExistException;
import ca.ulaval.glo4003.utilities.search.XmlUserSearchPreferenceDao;

public class XmlUserPreferencesDaoTest {

	private XmlUserSearchPreferenceDao xmlUserPreferencesDao;
	
	private User currentUser;
	
	private static final String USERNAME = "mo";

	@Before
	public void setUp() throws Exception {
		
		xmlUserPreferencesDao = new XmlUserSearchPreferenceDao();
		currentUser = new User(USERNAME, "test");
		String displayedPeriod = "ONE_WEEK";
		Boolean localGameOnly = Boolean.TRUE;
		List<String> listTicket = new ArrayList<String>();
		List<String> sportsName = new ArrayList<String>();
		sportsName.add("Football");
		
		xmlUserPreferencesDao.save(USERNAME,new UserSearchPreferenceDto(sportsName, displayedPeriod, localGameOnly, listTicket));
	}
	
	@Test
	public void savingUserPreferencesShouldAddItToXml() throws UserDoesntHaveSavedSearchPreference, UserSearchPreferenceDoesntExistException{
		
		String displayedPeriod = "ALL";
		Boolean localGameOnly = Boolean.FALSE;
		List<String> listTicket = new ArrayList<String>();
		List<String> sportsName = new ArrayList<String>();
		
		xmlUserPreferencesDao.save(USERNAME ,new UserSearchPreferenceDto(sportsName, displayedPeriod, localGameOnly, listTicket));
		UserSearchPreferenceDto ticketSPDto = xmlUserPreferencesDao.get(currentUser.getUsername());
		System.out.println(ticketSPDto.isLocalGameOnly());
		Assert.assertEquals(false,ticketSPDto.isLocalGameOnly());
		Assert.assertEquals("ALL",ticketSPDto.getDisplayedPeriod());
		
		
	
	}
		
		
	
	
	
}
