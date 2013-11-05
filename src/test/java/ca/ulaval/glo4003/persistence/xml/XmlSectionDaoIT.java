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
		sectionDao = new XmlSectionDao("/BasicData.xml");
	}
	
	@Test
	public void testGetSection() throws Exception {
		SectionDto actual = sectionDao.get(1, "Front Row");
		
		List<String> seats = new ArrayList();
		
		SectionDto expected = new SectionDto("VIP", "Front Row", 2, 35.00f, seats);
		assertSection(expected, actual);
	}
	
	@Test
	public void testGetAllSections() throws Exception {
		List<SectionDto> sections = sectionDao.getAll(1);
		
		List<String> seats = new ArrayList();
		
		SectionDto expected0 = new SectionDto("VIP", "Front Row", 2, 35.00f, seats);
		SectionDto expected1 = new SectionDto("Générale", "Générale", 2, 15.50f, seats);
		SectionDto expected2 = new SectionDto("VIP", "Rouges", 5, 20.00f, seats);
		
		Assert.assertEquals(3, sections.size());
		
		assertSection(expected0, sections.get(0));
		assertSection(expected1, sections.get(1));
		assertSection(expected2, sections.get(2));
	}
	
	@Test(expected=SectionDoesntExistException.class)
	public void testGetInvalidGameSectionShouldThrow() throws Exception {
		sectionDao.get(-1, "Générale");
	}
	
	@Test(expected=SectionDoesntExistException.class)
	public void testGetInvalidSectionShouldThrow() throws Exception {
		sectionDao.get(1, "Indigo");
	}

	private void assertSection(SectionDto expected, SectionDto actual) {
		Assert.assertEquals(expected.getPrice(), actual.getPrice(), 0.001f);
		Assert.assertEquals(expected.getAdmissionType(), actual.getAdmissionType());
		Assert.assertEquals(expected.getSectionName(), actual.getSectionName());
		Assert.assertEquals(expected.getNumberOfTickets(), actual.getNumberOfTickets());
		//Assert.assertEquals(expected.getSeats(), actual.getSeats());
	}

}
