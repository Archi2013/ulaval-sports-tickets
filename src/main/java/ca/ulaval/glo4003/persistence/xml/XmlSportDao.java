package ca.ulaval.glo4003.persistence.xml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.naming.directory.NoSuchAttributeException;
import javax.xml.xpath.XPathExpressionException;

import ca.ulaval.glo4003.domain.dtos.SportDto;
import ca.ulaval.glo4003.persistence.daos.SportDao;
import ca.ulaval.glo4003.persistence.daos.SportDoesntExistException;

public class XmlSportDao implements SportDao {
	
	@Inject
	private XmlDatabase database;
	private String basePath = "/base/sports/sport";
	
	public XmlSportDao() {
		database = XmlDatabase.getInstance();
	}
	
	public XmlSportDao(String filename) {
		database = XmlDatabase.getUniqueInstance(filename);
	}

	@Override
	public List<SportDto> getAll() {
		try {
			List<SimpleNode> nodes = database.extractNodeSet(basePath);
	        return convertNodesToDtos(nodes);
        } catch (XPathExpressionException | NoSuchAttributeException | SportDoesntExistException e) {
        	throw new XmlIntegrityException(e);
        }
	}

	private List<SportDto> convertNodesToDtos(List<SimpleNode> nodes) throws NoSuchAttributeException, SportDoesntExistException {
		List<SportDto> sports = new ArrayList<>();
		for (SimpleNode node : nodes) {
			sports.add(createFromNode(node));
		}
		return sports;
	}

	@Override
	public SportDto get(String sportName) throws SportDoesntExistException {
		try {
			String xPath = basePath + "[name=\"" + sportName + "\"]";
			SimpleNode node = database.extractNode(xPath);
	        return createFromNode(node);
        } catch (XPathExpressionException | NoSuchAttributeException e) {
	        throw new XmlIntegrityException(e);
        }
	}
	
	public void add(SportDto sport) {
		Map<String, String> nodes = new HashMap<>();
		nodes.put("name", sport.getName());
		SimpleNode simpleNode = new SimpleNode("sport", nodes);
		try {
	        database.addNode("/base/sports", simpleNode);
        } catch (XPathExpressionException e) {
        	throw new XmlIntegrityException(e);
        }
	}
	
	private SportDto createFromNode(SimpleNode parent) throws NoSuchAttributeException, SportDoesntExistException {
		if (parent.hasNode("name"))
			return new SportDto(parent.getNodeValue("name"));
		throw new SportDoesntExistException();
	}

}
