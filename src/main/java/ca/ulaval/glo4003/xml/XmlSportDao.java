package ca.ulaval.glo4003.xml;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import ca.ulaval.glo4003.data_access.SportDao;
import ca.ulaval.glo4003.dtos.SportDto;

public class XmlSportDao implements SportDao {
	
	@Inject
	private XmlDatabase database = XmlDatabase.getInstance();
	
	private String basePath = "/base/sports/sport";

	@Override
	public List<SportDto> getAll() {
		try {
			NodeList nodes = database.extractNodeSet(basePath);
	        
	        List<SportDto> sports = new ArrayList<>();
	        for (int i = 0 ; i < nodes.getLength() ; i++) {
	        	Node node = nodes.item(i);
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
	        Node node = database.extractNode(xPath);
	        return createFromNode(node);
        } catch (XPathExpressionException e) {
	        e.printStackTrace();
        }
		return null;
	}
	
	private SportDto createFromNode(Node parent) {
		Node nameNode = parent.getChildNodes().item(1);
		if ("name".equals(nameNode.getNodeName()))
				return new SportDto(nameNode.getTextContent());
		return null;
	}

}
