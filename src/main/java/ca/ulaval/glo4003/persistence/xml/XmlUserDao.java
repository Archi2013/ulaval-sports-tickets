package ca.ulaval.glo4003.persistence.xml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.directory.NoSuchAttributeException;
import javax.xml.xpath.XPathExpressionException;

import ca.ulaval.glo4003.domain.dtos.UserDto;
import ca.ulaval.glo4003.domain.utilities.UserAlreadyExistException;
import ca.ulaval.glo4003.domain.utilities.UserDoesntExistException;
import ca.ulaval.glo4003.persistence.daos.UserDao;

public class XmlUserDao implements UserDao {

	private static final String USERS_XPATH = "/base/users";
	private static final String USER_XPATH = USERS_XPATH + "/user";
	private static final String USER_XPATH_ID = USER_XPATH + "[username=\"%s\"]";

	private XmlDatabase database;

	public XmlUserDao() {
		database = XmlDatabase.getInstance();
	}

	XmlUserDao(String filename) {
		database = XmlDatabase.getUniqueInstance(filename);
	}

	@Override
	public List<UserDto> getAll() {
		try {
			List<SimpleNode> nodes = database.extractNodeSet(USER_XPATH);
			return convertNodesToUsers(nodes);
		} catch (XPathExpressionException | NoSuchAttributeException | UserDoesntExistException e) {
			throw new XmlIntegrityException(e);
		}
	}

	@Override
	public UserDto get(String name) throws UserDoesntExistException {
		String xPath = String.format(USER_XPATH_ID, name);
		try {
			SimpleNode node = database.extractNode(xPath);
			return convertNodeToUser(node);
		} catch (XPathExpressionException | NoSuchAttributeException e) {
			throw new XmlIntegrityException(e);
		}
	}

	@Override
	public void add(UserDto user) throws UserAlreadyExistException {
		if (isIdExist(user.getUsername())) {
			throw new UserAlreadyExistException();
		}
		SimpleNode simpleNode = convertUserToNode(user);
		try {
			database.addNode(USERS_XPATH, simpleNode);
		} catch (XPathExpressionException e) {
			throw new XmlIntegrityException(e);
		}
	}

	@Override
	public boolean doesUserExist(String name) {
		// TODO Auto-generated method stub
		return false;
	}

	private boolean isIdExist(String id) {
		String xPath = String.format(USER_XPATH_ID, id);
		return database.exist(xPath);
	}

	private SimpleNode convertUserToNode(UserDto user) {
		Map<String, String> nodes = new HashMap<>();
		nodes.put("username", user.getUsername());
		nodes.put("password", user.getPassword());
		SimpleNode simpleNode = new SimpleNode("user", nodes);
		return simpleNode;
	}

	private UserDto convertNodeToUser(SimpleNode node) throws UserDoesntExistException, NoSuchAttributeException {
		if (node.hasNode("username", "password")) {
			String username = node.getNodeValue("username");
			String password = node.getNodeValue("password");
			return new UserDto(username, password);
		}
		throw new UserDoesntExistException();
	}

	private List<UserDto> convertNodesToUsers(List<SimpleNode> nodes) throws NoSuchAttributeException, UserDoesntExistException {
		List<UserDto> users = new ArrayList<>();
		for (SimpleNode node : nodes) {
			users.add(convertNodeToUser(node));
		}
		return users;
	}

}
