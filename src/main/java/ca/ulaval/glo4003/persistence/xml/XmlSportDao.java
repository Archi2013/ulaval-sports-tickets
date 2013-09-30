package ca.ulaval.glo4003.persistence.xml;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.xml.xpath.XPathExpressionException;

import ca.ulaval.glo4003.dao.SportDao;
import ca.ulaval.glo4003.dto.SportDto;

public class XmlSportDao implements SportDao {
	
	@Inject
	private XmlDatabase database = XmlDatabase.getInstance();
	
	private String basePath = "/base/sports/sport";

	@Override
	public List<SportDto> getAll() {
		try {
			List<SimpleNode> nodes = database.extractNodeSet(basePath);
	        
	        List<SportDto> sports = new ArrayList<>();
	        for (SimpleNode node : nodes) {
	        	sports.add(createFromNode(node));
	        }
	        return sports;
        } catch (XPathExpressionException e) {
	        e.printStackTrace();
        }
		return null;
	}

	@Override
	public SportDto get(String sportName) {
		try {
			String xPath = basePath + "[name=\"" + sportName + "\"]";
			SimpleNode node = database.extractNode(xPath);
	        return createFromNode(node);
        } catch (XPathExpressionException e) {
	        e.printStackTrace();
        }
		return null;
	}
	
	private SportDto createFromNode(SimpleNode parent) {
		if (parent.hasNode("name"))
			return new SportDto(parent.getNodeValue("name"));
		return null;
	}

}
