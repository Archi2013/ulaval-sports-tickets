package ca.ulaval.glo4003.persistence.xml;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class SimpleNode {
	
	private Map<String, String> map = new HashMap<>();
	
	public SimpleNode(Node parent) {
		if (parent == null) {
			return;
		}
		NamedNodeMap attributes = parent.getAttributes();
		for (int i = 0 ; i < attributes.getLength() ; i++) {
			Node attribute = attributes.item(i);
			map.put(attribute.getNodeName(), attribute.getTextContent());
		}
		NodeList children = parent.getChildNodes();
		for (int i = 0 ; i < children.getLength() ; i++) {
			Node child = children.item(i);
			map.put(child.getNodeName(), child.getTextContent());
		}
	}
	
	public boolean hasNode(String... nodeNames) {
		for(String nodeName : nodeNames) {
			if (!map.containsKey(nodeName)) {
				return false;
			}
		}
		return true;
	}
	
	public String getNodeValue(String nodeName) {
		if (hasNode(nodeName)) {
			return map.get(nodeName);
		}
		throw new RuntimeException();
	}
}
