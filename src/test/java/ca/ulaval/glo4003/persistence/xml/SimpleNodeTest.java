package ca.ulaval.glo4003.persistence.xml;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

@RunWith(MockitoJUnitRunner.class)
public class SimpleNodeTest {
	
	private static final String NODE_TAG_VALUE = "Pantalon";
	private static final String NODE_TAG_NAME = "Nom";
	private static final String ATTRIBUTE_TAG_VALUE = "2";
	private static final String ATTRIBUTE_TAG_NAME = "Id";
	private static final String PARENT_TAG_NAME = "Item";
	private SimpleNode emptyNode = new SimpleNode(null);
	private SimpleNode someNode;
	private SimpleNode createdNode;
	
	@Mock
	private Document document;
	@Mock
	private Element parentElement;
	@Mock
	private Attr attribute;
	@Mock
	private Element childElement;
	
	@Before
	public void setup() throws Exception {
		someNode = createBasicNode();
		createdNode = createNodeFromMap();
	}

	@Test
	public void testEmptyHasNoNode() throws Exception {
		boolean actual = emptyNode.hasNode(NODE_TAG_NAME);
		assertFalse(actual);
	}
	
	@Test
	public void testHasNodeWithInvalideName() throws Exception {
		boolean actual = someNode.hasNode("test");
		assertFalse(actual);
	}
	
	@Test
	public void testHasNodeWithValideName() throws Exception {
		boolean actual = someNode.hasNode(NODE_TAG_NAME);
		assertTrue(actual);
	}
	
	@Test
	public void testHasNodeWithValideAttribute() throws Exception {
		boolean actual = someNode.hasNode(ATTRIBUTE_TAG_NAME);
		assertTrue(actual);
	}
	
	@Test
	public void testGetNodeValue() throws Exception {
		String actual = someNode.getNodeValue(NODE_TAG_NAME);
		String expected = "Chemise";
		assertEquals(expected, actual);
	}
	
	@Test
	public void testGetNodeAtribute() throws Exception {
		String actual = someNode.getNodeValue(ATTRIBUTE_TAG_NAME);
		String expected = "1";
		assertEquals(expected, actual);
	}
	
	@Test(expected=RuntimeException.class)
	public void testGetNodeValueThrowException() throws Exception {
		someNode.getNodeValue("test");
	}
	
	@Test
	public void testCreateNode() throws Exception {
		when(document.createElement(PARENT_TAG_NAME)).thenReturn(parentElement);
		when(document.createAttribute(ATTRIBUTE_TAG_NAME)).thenReturn(attribute);
		when(document.createElement(NODE_TAG_NAME)).thenReturn(childElement);
		
		createdNode.toNode(document);
		
		verify(document, times(1)).createElement(PARENT_TAG_NAME);
		verify(document, times(1)).createAttribute(ATTRIBUTE_TAG_NAME);
		verify(document, times(1)).createElement(NODE_TAG_NAME);
		
		verify(parentElement).appendChild(attribute);
		verify(parentElement).appendChild(childElement);
		verify(attribute).setValue(ATTRIBUTE_TAG_VALUE);
		verify(childElement).setTextContent(NODE_TAG_VALUE);
	}
	
	private SimpleNode createBasicNode() throws Exception {
		String xml = "<Magasin><Items><Item Id=\"1\"><Nom>Chemise</Nom></Item></Items></Magasin>";
		InputStream stream = new ByteArrayInputStream(xml.getBytes("UTF-8"));
		XmlExtractor extractor = new XmlExtractor(stream);
		String xPath = "/Magasin/Items/Item[Nom=\"Chemise\"]";
		return extractor.extractNode(xPath);
    }
	
	private SimpleNode createNodeFromMap() throws Exception {
		String name = PARENT_TAG_NAME;
		Map<String, String> attributes = new HashMap<>();
		attributes.put(ATTRIBUTE_TAG_NAME, ATTRIBUTE_TAG_VALUE);
		Map<String, String> subNodes = new HashMap<>();
		subNodes.put(NODE_TAG_NAME, NODE_TAG_VALUE);
		return new SimpleNode(name, subNodes, attributes);
	}
}
