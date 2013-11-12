package ca.ulaval.glo4003.persistence.xml;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.domain.dtos.SectionDto;
import ca.ulaval.glo4003.persistence.daos.SectionDoesntExistException;

public class XmlSectionDaoIT {

	private XmlSectionDao sectionDao;

	@Before
	public void setUp() throws Exception {
		sectionDao = new XmlSectionDao("resources/SectionData.xml");
	}

	@Test
	public void testGetSection() throws Exception {
		SectionDto actual = sectionDao.get(2L, "Section 100");

		List<String> seats = new ArrayList<>();
		seats.add("A-1");
		seats.add("B-2");

		SectionDto expected = new SectionDto("Section 100", 2, 22.00f, seats);
		assertSection(expected, actual);
	}

	@Test
	public void testGetAllSections() throws Exception {
		List<SectionDto> sections = sectionDao.getAll(2L);

		List<String> seats = new ArrayList<>();
		seats.add("A-1");
		seats.add("B-2");

		SectionDto expected0 = new SectionDto(4, 15.00f);
		SectionDto expected1 = new SectionDto("Section 100", 2, 22.00f, seats);

		Assert.assertEquals(2, sections.size());

		assertSection(expected0, sections.get(0));
		assertSection(expected1, sections.get(1));
	}

	@Test(expected = SectionDoesntExistException.class)
	public void testGetInvalidGameSectionShouldThrow() throws Exception {
		sectionDao.get(-1L, "Générale");
	}

	@Test(expected = SectionDoesntExistException.class)
	public void testGetInvalidSectionShouldThrow() throws Exception {
		sectionDao.get(2L, "Indigo");
	}

	private void assertSection(SectionDto expected, SectionDto actual) {
		Assert.assertEquals(expected.getPrice(), actual.getPrice(), 0.001f);
		Assert.assertEquals(expected.getSectionName(), actual.getSectionName());
		Assert.assertEquals(expected.getNumberOfTickets(), actual.getNumberOfTickets());
		Assert.assertEquals(expected.getSeats().size(), actual.getSeats().size());
	}

}
