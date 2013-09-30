package ca.ulaval.glo4003.persistence.xml;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlDatabase {
	private XmlExtractor extractor;
	private static XmlDatabase instance;

	private XmlDatabase(String filename) {
		try {
			InputStream input = this.getClass().getResourceAsStream(filename);
			extractor = new XmlExtractor(input);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static XmlDatabase getInstance() {
		return getInstance("/BasicData.xml");
	}

	public static XmlDatabase getInstance(String filename) {
		if (instance == null) {
			instance = new XmlDatabase(filename);
		}
		return instance;
	}

	public String extractPath(String xPath) throws XPathExpressionException {
		return extractor.extractPath(xPath);
	}

	public SimpleNode extractNode(String xPath) throws XPathExpressionException {
		Node node = extractor.extractNode(xPath);
		return new SimpleNode(node);
	}

	public List<SimpleNode> extractNodeSet(String xPath) throws XPathExpressionException {
		List<SimpleNode> simpleNodes = new ArrayList<>();
		NodeList nodes = extractor.extractNodeSet(xPath);
		for (int i = 0; i < nodes.getLength(); i++) {
			Node node = nodes.item(i);
			simpleNodes.add(new SimpleNode(node));
		}
		return simpleNodes;
	}
}
