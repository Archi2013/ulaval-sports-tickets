package ca.ulaval.glo4003.persistence.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
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
		return (String) extract(xPath, XPathConstants.STRING);
	}

	public SimpleNode extractNode(String xPath) throws XPathExpressionException {
		Node node = (Node) extract(xPath, XPathConstants.NODE);
		return new SimpleNode(node);
	}

	public List<SimpleNode> extractNodeSet(String xPath) throws XPathExpressionException {

		List<SimpleNode> simpleNodes = new ArrayList<>();
		NodeList nodes = (NodeList) extract(xPath, XPathConstants.NODESET);
		for (int i = 0; i < nodes.getLength(); i++) {
			Node node = nodes.item(i);
			simpleNodes.add(new SimpleNode(node));
		}
		return simpleNodes;
	}

	private Object extract(String xPath, QName qName) throws XPathExpressionException {
		XPath xpath = xPathfactory.newXPath();
		XPathExpression expr = xpath.compile(xPath);
		return expr.evaluate(document, qName);
	}
	
	public int count(String xPath) throws XPathExpressionException {
		double result = (double)extract("count(" + xPath + ")", XPathConstants.NUMBER);
		return (int)result;
	}

	public void createNode(String xPath, SimpleNode simpleNode) throws XPathExpressionException {

		Node parent = (Node) extract(xPath, XPathConstants.NODE);
		Document dom = parent.getOwnerDocument();
		Node child = simpleNode.toNode(dom);

		parent.appendChild(child);
	}

	public void commit(String filename) throws TransformerException {
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		transformer.transform(new DOMSource(document), new StreamResult(filename));
	}

}
