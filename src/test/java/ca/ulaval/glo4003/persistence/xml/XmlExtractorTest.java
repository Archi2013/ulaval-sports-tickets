package ca.ulaval.glo4003.persistence.xml;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.xpath.XPathExpressionException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4003.utilities.persistence.SimpleNode;
import ca.ulaval.glo4003.utilities.persistence.XmlExtractor;

@RunWith(MockitoJUnitRunner.class)
public class XmlExtractorTest {

	private static final String FILENAME = "resources/XmlExtractorTest.xml";
	private static final String ITEM_XPATH = "/Magasin/Items/Item";
	private static final String ITEMS_XPATH = "/Magasin/Items";
	private static final String CHEMISE_XPATH = "/Magasin/Items/Item[Nom=\"Chemise\"]";
	private static final String PANTALON_XPATH = "/Magasin/Items/Item[Nom=\"Pantalon\"]";
	private static final String CHEMISE_PRICE_XPATH = "/Magasin/Items/Item[Nom=\"Chemise\"]/Prix";
	private static final String PANTALON_PRICE_XPATH = "/Magasin/Items/Item[Nom=\"Pantalon\"]/Prix";
	private static final String INVALID_XPATH = "][]<<><>A##{}";
	private XmlExtractor extractor;

	@Before
	public void setUp() throws Exception {
		String xml = "<Magasin><Items><Item><id>1</id><Nom>Chemise</Nom><Prix>9,99</Prix></Item><Item><id>2</id><Nom>Chapeau</Nom><Prix>4,99</Prix></Item></Items></Magasin>";
		InputStream stream = new ByteArrayInputStream(xml.getBytes("UTF-8"));
		extractor = new XmlExtractor(stream);
	}
	
	@After
	public void teardown() throws Exception {
		File file = new File(FILENAME);
		if (file.exists()) {
			file.delete();
		}
	}

	@Test(expected = XPathExpressionException.class)
	public void testExtractFail() throws Exception {
		extractor.extractPath(INVALID_XPATH);
	}
	
	@Test(expected = Exception.class)
	public void testExceptionThrowOnInvalidXml() throws Exception {
		String xml = "<base></base2>";
		InputStream stream = new ByteArrayInputStream(xml.getBytes("UTF-8"));
		new XmlExtractor(stream);
	}

	@Test
	public void testExtractPriceOfItem() throws Exception {
		String result = extractor.extractPath(CHEMISE_PRICE_XPATH);
		String expected = "9,99";
		assertEquals(expected, result);
	}

	@Test
	public void testEmptyXmlShouldCreate() throws Exception {
		extractor = new XmlExtractor(null);
		assertEquals(1, extractor.count("/"));
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
		extractor.createNode(ITEMS_XPATH, initSimpleNode());

		String result = extractor.extractPath(PANTALON_PRICE_XPATH);
		String expected = "19.99";

		assertEquals(expected, result);
	}
	
	@Test
	public void testCreateNodeMissingParent() throws Exception {
		extractor.createNode("/Magasin/Departement/Items", initSimpleNode());

		String result = extractor.extractPath("/Magasin/Departement/Items/Item[Nom=\"Pantalon\"]/Prix");
		String expected = "19.99";

		assertEquals(expected, result);
	}

	@Test
	public void testCountNodeWithFilter() throws Exception {
		int result = extractor.count(CHEMISE_XPATH);
		int expected = 1;
		assertEquals(expected, result);
	}

	@Test
	public void testCountNode() throws Exception {
		int result = extractor.count(ITEM_XPATH);
		int expected = 2;
		assertEquals(expected, result);
	}

	@Test
	public void testNodeExistWithExistingEntry() throws Exception {
		boolean result = extractor.isNodeExist(CHEMISE_XPATH);
		Assert.assertTrue(result);
	}

	@Test
	public void testNodeExistWithMissingEntry() throws Exception {
		boolean result = extractor.isNodeExist(PANTALON_XPATH);
		Assert.assertFalse(result);
	}
	
	@Test
	public void testNodeExistWithInvalidPath() throws Exception {
		boolean result = extractor.isNodeExist(INVALID_XPATH);
		Assert.assertFalse(result);
	}

	@Test
	public void testMaxValue() throws Exception {
		long actual = extractor.max(ITEM_XPATH, "id");
		long expected = 2L;

		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testDistinct() throws Exception {
		Set<String> actual = extractor.distinct(ITEM_XPATH, "Nom");
		
		Assert.assertEquals(2, actual.size());
		Assert.assertTrue(actual.contains("Chemise"));
		Assert.assertTrue(actual.contains("Chapeau"));
		Assert.assertFalse(actual.contains("Pantalon"));
	}
	
	@Test
	public void testRemoveNode() throws Exception  {
		extractor.removeParentNode(CHEMISE_XPATH);
		
		SimpleNode result = extractor.extractNode(CHEMISE_XPATH);
		Assert.assertEquals(new SimpleNode(null), result);
	}
	
	@Test
	public void testCommit() throws Exception {
		File file = new File(FILENAME);
		extractor.commit(file);
		
		boolean result = extractor.isNodeExist(CHEMISE_XPATH);
		Assert.assertTrue(result);
		
		extractor = new XmlExtractor(new FileInputStream(file));
		
		result = extractor.isNodeExist(CHEMISE_XPATH);
		Assert.assertTrue(result);
	}

	private SimpleNode initSimpleNode() {
		Map<String, String> map = new HashMap<>();
		map.put("Nom", "Pantalon");
		map.put("Prix", "19.99");
		return new SimpleNode("Item", map);
	}
}
