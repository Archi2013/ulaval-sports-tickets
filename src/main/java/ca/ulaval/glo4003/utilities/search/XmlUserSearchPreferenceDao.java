package ca.ulaval.glo4003.utilities.search;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.directory.NoSuchAttributeException;
import javax.xml.xpath.XPathExpressionException;

import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import ca.ulaval.glo4003.exceptions.UserDoesntHaveSavedSearchPreference;
import ca.ulaval.glo4003.utilities.persistence.SimpleNode;
import ca.ulaval.glo4003.utilities.persistence.XmlDatabase;

@Component
public class XmlUserSearchPreferenceDao implements UserSearchPreferenceDao {

	private static final String DEFAULT_FILE = "resources/DemoUserData.xml";

	private static final String USERS_XPATH = "/base/users";
	private static final String USER_XPATH = USERS_XPATH + "/user";
	private static final String USER_XPATH_ID = USER_XPATH+ "[username=\"%s\"]";
	


	private XmlDatabase database;
	

	public XmlUserSearchPreferenceDao() {
		database = XmlDatabase.getInstance(DEFAULT_FILE);
	}

	public XmlUserSearchPreferenceDao(String filename) {
		database = XmlDatabase.getUniqueInstance(filename);
	}

	@Override
	public UserSearchPreferenceDto get(String username) throws UserDoesntHaveSavedSearchPreference {
		String xPath = String.format(USER_XPATH_ID, username);
		try {
			SimpleNode node = database.extractNode(xPath+"/userPreferences");	
			return convertNodeToUserPreferences(node);
		} catch (XPathExpressionException | NoSuchAttributeException e) {
			throw new UserDoesntHaveSavedSearchPreference();
		}
	}

	private UserSearchPreferenceDto convertNodeToUserPreferences(SimpleNode node) throws NoSuchAttributeException {
		Gson gson = new Gson();
		
		String displayedPeriod = node.getNodeValue("displayedPeriod");
		Boolean localGameOnly = Boolean.valueOf(node.getNodeValue("localGameOnly"));
		List<String> listTicket = gson.fromJson(node.getNodeValue("listTicket"), new TypeToken<List<String>>(){}.getType());
		List<String> sportsName = gson.fromJson(node.getNodeValue("sportsName"), new TypeToken<List<String>>(){}.getType());

		return new UserSearchPreferenceDto(sportsName, displayedPeriod, localGameOnly, listTicket);
	}

	@Override
	public void save(String username, UserSearchPreferenceDto userPreferences) throws UserSearchPreferenceDoesntExistException  {
		SimpleNode simpleNode = convertUserPreferencesToNode(userPreferences);
		String xPath = String.format(USER_XPATH_ID, username);		

		try {
			if (isUserPreferencesAlreadySaved(xPath)) {
				database.remove(xPath + "/userPreferences");
			}
			database.addNode(xPath, simpleNode);
		} catch (XPathExpressionException e) {
			throw new UserSearchPreferenceDoesntExistException();
		}	
	}

	private SimpleNode convertUserPreferencesToNode(UserSearchPreferenceDto userPreferences) {
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
