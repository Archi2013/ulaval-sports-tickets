package ca.ulaval.glo4003.xml;

import java.io.InputStream;

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

	public Node extractNode(String xPath) throws XPathExpressionException {
		return extractor.extractNode(xPath);
    }

	public NodeList extractNodeSet(String xPath) throws XPathExpressionException {
		return extractor.extractNodeSet(xPath);
    }
}
