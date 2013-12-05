package ca.ulaval.glo4003.domain.users;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.directory.NoSuchAttributeException;
import javax.xml.xpath.XPathExpressionException;

import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import ca.ulaval.glo4003.exceptions.UserDoesntHaveSavedPreferences;
import ca.ulaval.glo4003.utilities.persistence.SimpleNode;
import ca.ulaval.glo4003.utilities.persistence.XmlDatabase;
import ca.ulaval.glo4003.utilities.search.TicketSearchPreferenceDto;

@Component
public class XmlUserPreferencesDao implements UserPreferencesDao {

	private static final String DEFAULT_FILE = "resources/DemoUserData.xml";

	private static final String USERS_XPATH = "/base/users";
	private static final String USER_XPATH = USERS_XPATH + "/user";
	private static final String USER_XPATH_ID = USER_XPATH+ "[username=\"%s\"]";
	


	private XmlDatabase database;
	

	public XmlUserPreferencesDao() {
		database = XmlDatabase.getInstance(DEFAULT_FILE);
	}

	public XmlUserPreferencesDao(String filename) {
		database = XmlDatabase.getUniqueInstance(filename);
	}

	@Override
	public TicketSearchPreferenceDto get(String username) throws UserDoesntHaveSavedPreferences {
		String xPath = String.format(USER_XPATH_ID, username);
		try {
			SimpleNode node = database.extractNode(xPath+"/userPreferences");	
			return convertNodeToUserPreferences(node);
		} catch (XPathExpressionException | NoSuchAttributeException e) {
			throw new UserDoesntHaveSavedPreferences();
		}
	}

	private TicketSearchPreferenceDto convertNodeToUserPreferences(SimpleNode node) throws NoSuchAttributeException {
		Gson gson = new Gson();
		
		String displayedPeriod = node.getNodeValue("displayedPeriod");
		Boolean localGameOnly = Boolean.valueOf(node.getNodeValue("localGameOnly"));
		List<String> listTicket = gson.fromJson(node.getNodeValue("listTicket"), new TypeToken<List<String>>(){}.getType());
		List<String> sportsName = gson.fromJson(node.getNodeValue("sportsName"), new TypeToken<List<String>>(){}.getType());

		return new TicketSearchPreferenceDto(sportsName, displayedPeriod, localGameOnly, listTicket);
	}

	@Override
	public void save(User currentUser, TicketSearchPreferenceDto userPreferences) throws UserPreferencesDoesntExistException  {
		SimpleNode simpleNode = convertUserPreferencesToNode(userPreferences);
		String xPath = String.format(USER_XPATH_ID, currentUser.getUsername());		

		try {
			if (isUserPreferencesAlreadySaved(xPath)) {
				database.remove(xPath + "/userPreferences");
			}
			database.addNode(xPath, simpleNode);
		} catch (XPathExpressionException e) {
			throw new UserPreferencesDoesntExistException();
		}
		
		
	}

	private SimpleNode convertUserPreferencesToNode(TicketSearchPreferenceDto userPreferences) {
		Gson gson = new Gson();
		Map<String, String> nodes = new HashMap<>();
		
		nodes.put("displayedPeriod", userPreferences.getDisplayedPeriod());
		nodes.put("localGameOnly", userPreferences.isLocalGameOnly().toString());
		nodes.put("sportsName", gson.toJson(userPreferences.getSelectedSports()));
		nodes.put("listTicket", gson.toJson(userPreferences.getSelectedTicketKinds()));
		
		SimpleNode simpleNode = new SimpleNode("userPreferences", nodes);
		return simpleNode;

	}

	@Override
	public void commit() {
		database.commit();

	}

	private boolean isUserPreferencesAlreadySaved(String xPath) {
		return database.exist(xPath + "/userPreferences");
	}

}
