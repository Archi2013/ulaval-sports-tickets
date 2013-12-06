package ca.ulaval.glo4003.persistence.xml;

import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import ca.ulaval.glo4003.domain.sections.SectionDto;
import ca.ulaval.glo4003.domain.sections.XmlSectionDao;

@Ignore
public class XmlSectionDaoIT {

	private XmlSectionDao sectionDao;

	@Before
	public void setUp() throws Exception {
		sectionDao = new XmlSectionDao();
	}
	
	@Test
	public void testGetAllSection() throws Exception {
		Set<String> sections = sectionDao.getAllSections();
		
		Assert.assertEquals(3, sections.size());
		Assert.assertTrue(sections.contains("Générale"));
		Assert.assertTrue(sections.contains("Section 100"));
		Assert.assertTrue(sections.contains("Section 200"));
	}


	private void assertSection(SectionDto expected, SectionDto actual) {
		Assert.assertEquals(expected.getPrice(), actual.getPrice(), 0.001f);
		Assert.assertEquals(expected.getSectionName(), actual.getSectionName());
		Assert.assertEquals(expected.getNumberOfTickets(), actual.getNumberOfTickets());
		Assert.assertEquals(expected.getSeats().size(), actual.getSeats().size());
	}

}
