package ca.ulaval.glo4003.persistence.xml;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.xpath.XPathExpressionException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class XmlExtractorTest {

	private static final String ITEMS_XPATH = "/Magasin/Items";
	private static final String CHEMISE_XPATH = "/Magasin/Items/Item[Nom=\"Chemise\"]";
	private static final String CHEMISE_PRICE_XPATH = "/Magasin/Items/Item[Nom=\"Chemise\"]/Prix";
	private static final String PANTALON_PRICE_XPATH = "/Magasin/Items/Item[Nom=\"Pantalon\"]/Prix";
	private static final String INVALID_XPATH = "][]<<><>A##{}";
	private XmlExtractor extractor;

	@Before
	public void setUp() throws Exception {
		String xml = "<Magasin><Items><Item><Nom>Chemise</Nom><Prix>9,99</Prix></Item></Items></Magasin>";
		InputStream stream = new ByteArrayInputStream(xml.getBytes("UTF-8"));
		extractor = new XmlExtractor(stream);
	}

	@Test(expected = XPathExpressionException.class)
	public void testExtractFail() throws Exception {
		extractor.extractPath(INVALID_XPATH);
	}

	@Test
	public void testExtractPriceOfItem() throws Exception {
		String result = extractor.extractPath(CHEMISE_PRICE_XPATH);
		String expected = "9,99";
		assertEquals(expected, result);
	}

	@Test(expected = Exception.class)
	public void testInvalidXmlShouldThrow() throws Exception {
		extractor = new XmlExtractor(null);
	}

	@Test
	public void testExtractNode() throws Exception {
		SimpleNode result = extractor.extractNode(CHEMISE_XPATH);
		String expected = "9,99";
		assertEquals(expected, result.getNodeValue("Prix"));
	}

	@Test
	public void testExtractNodeSet() throws Exception {
		List<SimpleNode> result = extractor.extractNodeSet(CHEMISE_XPATH);
		String expected = "9,99";
		assertEquals(1, result.size());
		assertEquals(expected, result.get(0).getNodeValue("Prix"));
	}

	@Test
	public void testCreateNode() throws Exception {
		initSimpleNode();
		
		extractor.createNode(ITEMS_XPATH, initSimpleNode());
		
		String result = extractor.extractPath(PANTALON_PRICE_XPATH);
		String expected = "19.99";
		
		assertEquals(expected, result);
	}
	
	private SimpleNode initSimpleNode() {
		Map<String, String> map = new HashMap<>();
		map.put("Nom", "Pantalon");
		map.put("Prix", "19.99");
		return new SimpleNode("Item", map, new HashMap<String, String>());
	}
}
