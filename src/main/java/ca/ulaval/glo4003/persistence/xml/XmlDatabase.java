package ca.ulaval.glo4003.persistence.xml;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import javax.xml.xpath.XPathExpressionException;

public class XmlDatabase {

	private static final String DEFAULT_FILE = "resources/DemoData.xml";
	private File file;
	private XmlExtractor extractor;
	private static XmlDatabase instance;

	private XmlDatabase(String filename) {
		this.file = new File(filename);
		try {
			extractor = new XmlExtractor(file.exists() ? new FileInputStream(file) : null);
		} catch (Exception e) {
			throw new XmlIntegrityException(e);
		}
	}

	public static XmlDatabase getInstance() {
		return getInstance(DEFAULT_FILE);
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
			extractor.commit(file);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public int countNode(String xPath) throws XPathExpressionException {
		return extractor.count(xPath);
	}

	public int getMaxValue(String baseXPath, String idName) throws XPathExpressionException {
		return extractor.max(baseXPath, idName);
	}

	public boolean exist(String xPath) {
		return extractor.isNodeExist(xPath);
	}

	public void remove(String xPath) throws XPathExpressionException {
		extractor.removeParentNode(xPath);
	}
}
