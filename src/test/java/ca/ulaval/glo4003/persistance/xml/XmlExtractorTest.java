package ca.ulaval.glo4003.persistance.xml;

import static org.junit.Assert.assertEquals;

import java.io.InputStream;

import javax.xml.xpath.XPathExpressionException;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.persistance.xml.XmlExtractor;

public class XmlExtractorTest {

	private static final String INVALID_XPATH = "][]<<><>A##{}";
	private XmlExtractor extractor;

	@Before
	public void setUp() throws Exception {
		String fileName = "BasicXmlForUnitTesting.xml";
		InputStream xml = this.getClass().getResourceAsStream("/" + fileName);
		this.extractor = new XmlExtractor(xml);
	}

	@Test(expected = XPathExpressionException.class)
	public void testExtractFail() throws Exception {
		extractor.extractPath(INVALID_XPATH);
	}

	@Test
	public void testExtractPriceOfItem() throws Exception {
		String result = extractor.extractPath("/Magasin/Items/Item[Nom=\"Chemise\"]/Prix");
		String expected = "9,99";
		assertEquals(expected, result);
	}
}
