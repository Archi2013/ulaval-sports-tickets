package ca.ulaval.glo4003.utilities.persistence;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.xpath.XPathExpressionException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class XmlDatabaseTest {
	private static final String FILENAME = "resources/XmlDatabaseTest.xml";
	private static final String OTHER_FILENAME = "resources/XmlDatabaseTest2.xml";
	private static final String ITEM_XPATH = "/Magasin/Items/Item";
	private static final String ITEMS_XPATH = "/Magasin/Items";
	private static final String CHEMISE_XPATH = "/Magasin/Items/Item[Nom=\"Chemise\"]";
	private static final String CHAPEAU_XPATH = "/Magasin/Items/Item[Nom=\"Chapeau\"]";
	private static final String PANTALON_XPATH = "/Magasin/Items/Item[Nom=\"Pantalon\"]";
	private static final String CHEMISE_PRICE_XPATH = "/Magasin/Items/Item[Nom=\"Chemise\"]/Prix";
	private static final String PANTALON_PRICE_XPATH = "/Magasin/Items/Item[Nom=\"Pantalon\"]/Prix";
	private static final String INVALID_XPATH = "][]<<><>A##{}";
	private XmlDatabase database;

	@Before
	public void setUp() throws Exception {
		database = XmlDatabase.getUniqueInstance(FILENAME);
		database.addNode(ITEMS_XPATH, initSimpleNode("1", "Chemise", "9,99"));
		database.addNode(ITEMS_XPATH, initSimpleNode("2", "Chapeau", "4,99"));
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
		database.extractPath(INVALID_XPATH);
	}
	
	@Test
	public void testExtractPriceOfItem() throws Exception {
		String result = database.extractPath(CHEMISE_PRICE_XPATH);
		String expected = "9,99";
		assertEquals(expected, result);
	}

	@Test
	public void testExtractNode() throws Exception {
		SimpleNode result = database.extractNode(CHEMISE_XPATH);
		String expected = "9,99";
		assertEquals(expected, result.getNodeValue("Prix"));
	}

	@Test
	public void testExtractNodeSet() throws Exception {
		List<SimpleNode> result = database.extractNodeSet(CHEMISE_XPATH);
		String expected = "9,99";
		assertEquals(1, result.size());
		assertEquals(expected, result.get(0).getNodeValue("Prix"));
	}

	@Test
	public void testCreateNode() throws Exception {
		database.addNode(ITEMS_XPATH, initSimpleNode("3", "Pantalon", "19.99"));

		String result = database.extractPath(PANTALON_PRICE_XPATH);
		String expected = "19.99";

		assertEquals(expected, result);
	}
	
	@Test
	public void testCreateNodeMissingParent() throws Exception {
		database.addNode("/Magasin/Departement/Items", initSimpleNode("3", "Pantalon", "19.99"));

		String result = database.extractPath("/Magasin/Departement/Items/Item[Nom=\"Pantalon\"]/Prix");
		String expected = "19.99";

		assertEquals(expected, result);
	}

	@Test
	public void testCountNodeWithFilter() throws Exception {
		int result = database.countNode(CHEMISE_XPATH);
		int expected = 1;
		assertEquals(expected, result);
	}

	@Test
	public void testCountNode() throws Exception {
		int result = database.countNode(ITEM_XPATH);
		int expected = 2;
		assertEquals(expected, result);
	}

	@Test
	public void testNodeExistWithExistingEntry() throws Exception {
		boolean result = database.exist(CHEMISE_XPATH);
		Assert.assertTrue(result);
	}

	@Test
	public void testNodeExistWithMissingEntry() throws Exception {
		boolean result = database.exist(PANTALON_XPATH);
		Assert.assertFalse(result);
	}
	
	@Test
	public void testNodeExistWithInvalidPath() throws Exception {
		boolean result = database.exist(INVALID_XPATH);
		Assert.assertFalse(result);
	}

	@Test
	public void testMaxValue() throws Exception {
		long actual = database.getMaxValue(ITEM_XPATH, "id");
		long expected = 2L;

		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testDistinct() throws Exception {
		Set<String> actual = database.distinct(ITEM_XPATH, "Nom");
		
		Assert.assertEquals(2, actual.size());
		Assert.assertTrue(actual.contains("Chemise"));
		Assert.assertTrue(actual.contains("Chapeau"));
		Assert.assertFalse(actual.contains("Pantalon"));
	}
	
	@Test
	public void testRemoveNode() throws Exception  {
		database.remove(CHEMISE_XPATH);
		
		SimpleNode result = database.extractNode(CHEMISE_XPATH);
		Assert.assertTrue(result.isNull());
	}
	
	@Test
	public void testCommit() throws Exception  {
		database.commit();
		database = XmlDatabase.getUniqueInstance(FILENAME);
		
		boolean result = database.exist(CHEMISE_XPATH);
		Assert.assertTrue(result);
	}
	
	@Test
	public void testGetInstance() throws Exception {
		XmlDatabase instance = XmlDatabase.getInstance(FILENAME);
		XmlDatabase sameInstance = XmlDatabase.getInstance(FILENAME);
		XmlDatabase otherInstance = XmlDatabase.getInstance(OTHER_FILENAME);
		
		instance.addNode(ITEMS_XPATH, initSimpleNode("1", "Chemise", "9,99"));
		otherInstance.addNode(ITEMS_XPATH, initSimpleNode("2", "Chapeau", "4,99"));
		
		boolean result = instance.exist(CHEMISE_XPATH);
		Assert.assertTrue(result);
		result = sameInstance.exist(CHEMISE_XPATH);
		Assert.assertTrue(result);
		result = otherInstance.exist(CHEMISE_XPATH);
		Assert.assertFalse(result);
		result = instance.exist(CHAPEAU_XPATH);
		Assert.assertFalse(result);
		result = sameInstance.exist(CHAPEAU_XPATH);
		Assert.assertFalse(result);
		result = otherInstance.exist(CHAPEAU_XPATH);
		Assert.assertTrue(result);
	}

	private SimpleNode initSimpleNode(String id, String nom, String prix) {
		Map<String, String> map = new HashMap<>();
		map.put("id", id);
		map.put("Nom", nom);
		map.put("Prix", prix);
		return new SimpleNode("Item", map);
	}
}
