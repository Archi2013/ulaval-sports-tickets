package ca.ulaval.glo4003.persistence.xml;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.domain.users.User;
import ca.ulaval.glo4003.domain.users.UserPreferencesDoesntExistEcception;
import ca.ulaval.glo4003.domain.users.XmlUserPreferencesDao;
import ca.ulaval.glo4003.exceptions.UserDoesntHaveSavedPreferences;
import ca.ulaval.glo4003.utilities.search.TicketSearchPreferenceDto;

public class XmlUserPreferencesDaoTest {

	private XmlUserPreferencesDao xmlUserPreferencesDao;
	
	private User currentUser;
	
	@Before
	public void setUp() throws Exception {
		
		xmlUserPreferencesDao = new XmlUserPreferencesDao();
		
		currentUser = new User("mo", "test");
		String displayedPeriod = "ONE_WEEK";
		Boolean localGameOnly = Boolean.TRUE;
		List<String> listTicket = new ArrayList<String>();
		List<String> sportsName = new ArrayList<String>();
		sportsName.add("Football");
		
		xmlUserPreferencesDao.save(currentUser,new TicketSearchPreferenceDto(sportsName, displayedPeriod, localGameOnly, listTicket));
	}
	
	@Test
	public void savingUserPreferencesShouldAddItToXml() throws UserDoesntHaveSavedPreferences, UserPreferencesDoesntExistEcception{
		
		String displayedPeriod = "ALL";
		Boolean localGameOnly = Boolean.FALSE;
		List<String> listTicket = new ArrayList<String>();
		List<String> sportsName = new ArrayList<String>();
		
		xmlUserPreferencesDao.save(currentUser ,new TicketSearchPreferenceDto(sportsName, displayedPeriod, localGameOnly, listTicket));
		TicketSearchPreferenceDto ticketSPDto = xmlUserPreferencesDao.get(currentUser.getUsername());
		System.out.println(ticketSPDto.isLocalGameOnly());
		Assert.assertEquals(false,ticketSPDto.isLocalGameOnly());
		Assert.assertEquals("ALL",ticketSPDto.getDisplayedPeriod());
		
		
	
	}
		
		
	
	
	
}
