package ca.ulaval.glo4003.utilities.persistence;

import java.io.File;
import java.io.FileInputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.xpath.XPathExpressionException;

public class XmlDatabase {

	private static final String DEFAULT_FILE = "resources/DemoData.xml";
	private File file;
	private XmlExtractor extractor;
	private static Map<String, XmlDatabase> instances;

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

	public static XmlDatabase getUniqueInstance(String filename) {
		return new XmlDatabase(filename);
	}

	public synchronized static XmlDatabase getInstance(String filename) {
		if (instances == null) {
			instances = Collections.synchronizedMap(new HashMap<String, XmlDatabase>());
		}
		if (!instances.containsKey(filename)) {
			instances.put(filename, new XmlDatabase(filename));
		}
		return instances.get(filename);
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

	public long getMaxValue(String baseXPath, String idName) throws XPathExpressionException {
		return extractor.max(baseXPath, idName);
	}

	public boolean exist(String xPath) {
		return extractor.isNodeExist(xPath);
	}

	public void remove(String xPath) throws XPathExpressionException {
		extractor.removeParentNode(xPath);
	}
}
