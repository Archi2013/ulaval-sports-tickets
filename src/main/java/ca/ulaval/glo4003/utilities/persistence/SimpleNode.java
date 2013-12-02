package ca.ulaval.glo4003.utilities.persistence;

import java.util.HashMap;
import java.util.Map;

import javax.naming.directory.NoSuchAttributeException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class SimpleNode {
	
	private String name;
	private Map<String, String> subNodes = new HashMap<>();
	
	public SimpleNode(Node parent) {
		if (parent == null) {
			return;
		}
		this.name = parent.getNodeName();
		NodeList children = parent.getChildNodes();
		for (int i = 0 ; i < children.getLength() ; i++) {
			Node child = children.item(i);
			subNodes.put(child.getNodeName(), child.getTextContent());
		}
	}
	
	public SimpleNode(String name, Map<String, String> subNodes) {
		this.name = name;
		this.subNodes = subNodes;
	}
	
	public boolean hasNode(String... nodeNames) {
		for(String nodeName : nodeNames) {
			if (!subNodes.containsKey(nodeName)) {
				return false;
			}
		}
		return true;
	}
	
	public String getNodeValue(String nodeName) throws NoSuchAttributeException {
		if (subNodes.containsKey(nodeName)) {
			return subNodes.get(nodeName);
		}
		throw new NoSuchAttributeException();
	}
	
	public void setNodeValue(String nodeName, String nodeValue) {
		if (subNodes.containsKey(nodeName)) {
			subNodes.put(nodeName, nodeValue);
		}
	}

	public Node toNode(Document document) {
		Node node = document.createElement(name);
		appendSubNodes(document, node);
		return node;
	}

	private void appendSubNodes(Document document, Node node) {
		for (String attribute : subNodes.keySet()) {
			String value = subNodes.get(attribute);
			
			Node sub = document.createElement(attribute);
			sub.setTextContent(value);
			
			node.appendChild(sub);
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof SimpleNode) {
			SimpleNode s = (SimpleNode)obj;
			if (s.name == null && this.name == null) {
				return true;
			}
		}
		return super.equals(obj);
	}
}
