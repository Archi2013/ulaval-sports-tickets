package ca.ulaval.glo4003.persistence.xml;

import org.junit.Assert;
import org.junit.Before;

import ca.ulaval.glo4003.domain.dtos.SectionDto;

public class XmlSectionDaoIT {

	private XmlSectionDao sectionDao;

	@Before
	public void setUp() throws Exception {
	}


	private void assertSection(SectionDto expected, SectionDto actual) {
		Assert.assertEquals(expected.getPrice(), actual.getPrice(), 0.001f);
		Assert.assertEquals(expected.getSectionName(), actual.getSectionName());
		Assert.assertEquals(expected.getNumberOfTickets(), actual.getNumberOfTickets());
		Assert.assertEquals(expected.getSeats().size(), actual.getSeats().size());
	}

}
