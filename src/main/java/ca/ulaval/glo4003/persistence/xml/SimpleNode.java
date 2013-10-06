package ca.ulaval.glo4003.persistence.xml;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class SimpleNode {
	
	private String name;
	private Map<String, String> subNodes = new HashMap<>();
	private Map<String, String> attributeValues = new HashMap<>();
	
	public SimpleNode(Node parent) {
		if (parent == null) {
			return;
		}
		this.name = parent.getNodeName();
		NamedNodeMap attributes = parent.getAttributes();
		for (int i = 0 ; i < attributes.getLength() ; i++) {
			Node attribute = attributes.item(i);
			attributeValues.put(attribute.getNodeName(), attribute.getTextContent());
		}
		NodeList children = parent.getChildNodes();
		for (int i = 0 ; i < children.getLength() ; i++) {
			Node child = children.item(i);
			subNodes.put(child.getNodeName(), child.getTextContent());
		}
	}
	
	public SimpleNode(String name, Map<String, String> subNodes, Map<String, String> attributeValues) {
		this.name = name;
		this.subNodes = subNodes;
		this.attributeValues = attributeValues;
	}
	
	public SimpleNode(String name, Map<String, String> subNodes) {
	    this(name, subNodes, new HashMap<String, String>());
    }

	public boolean hasNode(String... nodeNames) {
		for(String nodeName : nodeNames) {
			if (!subNodes.containsKey(nodeName) && !attributeValues.containsKey(nodeName)) {
				return false;
			}
		}
		return true;
	}
	
	public String getNodeValue(String nodeName) {
		if (subNodes.containsKey(nodeName)) {
			return subNodes.get(nodeName);
		}
		if (attributeValues.containsKey(nodeName)) {
			return attributeValues.get(nodeName);
		}
		throw new RuntimeException();
	}

	public Node toNode(Document document) {
		Node node = document.createElement(name);
		appendAttributes(document, node);
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

	private void appendAttributes(Document document, Node node) {
		for (String attribute : attributeValues.keySet()) {
			String value = attributeValues.get(attribute);
			
			Attr attr = document.createAttribute(attribute);
			attr.setValue(value);
			
			node.appendChild(attr);
		}
	}
}
