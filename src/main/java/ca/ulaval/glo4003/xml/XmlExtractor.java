package ca.ulaval.glo4003.xml;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XmlExtractor {

	private XPathFactory xPathfactory;
	private Document document;

	public XmlExtractor(InputStream xml) throws Exception {
		this.xPathfactory = XPathFactory.newInstance();
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			document = builder.parse(new InputSource(xml));
			document.getDocumentElement().normalize();
		} catch (ParserConfigurationException | SAXException | IOException exception) {
			String message = "Error while parsing XML";
			throw new Exception(message, exception);
		}
	}

	public String extractPath(String xPath) throws XPathExpressionException {
		return (String)extract(xPath, XPathConstants.STRING);
	}

	public Node extractNode(String xPath) throws XPathExpressionException {
		return (Node)extract(xPath, XPathConstants.NODE);
    }

	public NodeList extractNodeSet(String xPath) throws XPathExpressionException {
		return (NodeList)extract(xPath, XPathConstants.NODESET);
	}
	
	private Object extract(String xPath, QName qName) throws XPathExpressionException {
		XPath xpath = xPathfactory.newXPath();
		XPathExpression expr = xpath.compile(xPath);
		return expr.evaluate(document, qName);
	}
}
