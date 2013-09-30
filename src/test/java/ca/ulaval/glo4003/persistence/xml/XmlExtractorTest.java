package ca.ulaval.glo4003.persistence.xml;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.xml.xpath.XPathExpressionException;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.persistence.xml.XmlExtractor;

public class XmlExtractorTest {

	private static final String INVALID_XPATH = "][]<<><>A##{}";
	private XmlExtractor extractor;

	@Before
	public void setUp() throws Exception {
		String xml = "<Magasin><Items><Item><Nom>Chemise</Nom><Prix>9,99</Prix></Item>" + 
				"<Item><Nom>Pantalon</Nom><Prix>12,99</Prix></Item></Items></Magasin>";
		InputStream stream = new ByteArrayInputStream(xml.getBytes("UTF-8"));
		extractor = new XmlExtractor(stream);
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
