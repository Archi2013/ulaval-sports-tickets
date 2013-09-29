package ca.ulaval.glo4003.persistance.xml;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import javax.xml.xpath.XPathExpressionException;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Node;

import static org.junit.Assert.assertFalse;

public class SimpleNodeTest {
	
	private SimpleNode emptyNode = new SimpleNode(null);
	private SimpleNode someNode;
	
	@Before
	public void setup() throws Exception {
		Node node = createBasicNode();
		someNode = new SimpleNode(node);
	}

	@Test
	public void testEmptyHasNoNode() throws Exception {
		boolean actual = emptyNode.hasNode("test");
		assertFalse(actual);
	}
	
	@Test
	public void testHasNodeWithInvalideName() throws Exception {
		boolean actual = someNode.hasNode("test");
		assertFalse(actual);
	}
	
	@Test
	public void testHasNodeWithValideName() throws Exception {
		boolean actual = someNode.hasNode("name");
		assertFalse(actual);
	}
	
	private Node createBasicNode() throws UnsupportedEncodingException, Exception, XPathExpressionException {
	    String xml = "<base><name>somename</name></base>";
		InputStream stream = new ByteArrayInputStream(xml.getBytes("UTF-8"));
		XmlExtractor extractor = new XmlExtractor(stream);
		String xPath = "/base";
		return extractor.extractNode(xPath);
    }
}
