package ca.ulaval.glo4003.persistence.xml;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.Before;
import org.junit.Test;

public class SimpleNodeTest {
	
	private SimpleNode emptyNode = new SimpleNode(null);
	private SimpleNode someNode;
	
	@Before
	public void setup() throws Exception {
		someNode = createBasicNode();
	}

	@Test
	public void testEmptyHasNoNode() throws Exception {
		boolean actual = emptyNode.hasNode("Nom");
		assertFalse(actual);
	}
	
	@Test
	public void testHasNodeWithInvalideName() throws Exception {
		boolean actual = someNode.hasNode("test");
		assertFalse(actual);
	}
	
	@Test
	public void testHasNodeWithValideName() throws Exception {
		boolean actual = someNode.hasNode("Nom");
		assertTrue(actual);
	}
	
	@Test
	public void testHasNodeWithValideAttribute() throws Exception {
		boolean actual = someNode.hasNode("Id");
		assertTrue(actual);
	}
	
	@Test
	public void testGetNodeValue() throws Exception {
		String actual = someNode.getNodeValue("Nom");
		String expected = "Chemise";
		assertEquals(expected, actual);
	}
	
	@Test(expected=RuntimeException.class)
	public void testGetNodeValueThrowException() throws Exception {
		someNode.getNodeValue("test");
	}
	
	private SimpleNode createBasicNode() throws Exception {
		String xml = "<Magasin><Items><Item Id=\"1\"><Nom>Chemise</Nom></Item></Items></Magasin>";
		InputStream stream = new ByteArrayInputStream(xml.getBytes("UTF-8"));
		XmlExtractor extractor = new XmlExtractor(stream);
		String xPath = "/Magasin/Items/Item[Nom=\"Chemise\"]";
		return extractor.extractNode(xPath);
    }
}
