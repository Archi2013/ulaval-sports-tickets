package ca.ulaval.glo4003.persistence.xml;

import java.io.InputStream;
import java.util.List;

import javax.xml.xpath.XPathExpressionException;

public class XmlDatabase {

	private static final String DEFAULT_FILE = "/BasicData.xml";
	private String filename;
	private XmlExtractor extractor;
	private static XmlDatabase instance;

	/*
	 * public XmlDatabase() { this(DEFAULT_FILE); }
	 */

	private XmlDatabase(String filename) {
		this.filename = filename;
		try {
			InputStream input = this.getClass().getResourceAsStream(filename);
			extractor = new XmlExtractor(input);
		} catch (Exception e) {
			throw new XmlIntegrityException(e);
		}
	}

	public static XmlDatabase getInstance() {
		return getInstance("/BasicData.xml");
	}

	static XmlDatabase getUniqueInstance(String filename) {
		return new XmlDatabase(filename);
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
		return extractor.extractNode(xPath);
	}

	public List<SimpleNode> extractNodeSet(String xPath) throws XPathExpressionException {
		return extractor.extractNodeSet(xPath);
	}

	public void addNode(String xPath, SimpleNode simpleNode) throws XPathExpressionException {
		extractor.createNode(xPath, simpleNode);
	}

	public void commit() {
		try {
			extractor.commit(filename);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public int countNode(String xPath) throws XPathExpressionException {
		return extractor.count(xPath);
	}

	public boolean exist(String xPath) {
		return extractor.isNodeExist(xPath);
	}
}
