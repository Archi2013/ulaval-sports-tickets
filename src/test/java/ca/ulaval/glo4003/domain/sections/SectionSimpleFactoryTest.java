package ca.ulaval.glo4003.domain.sections;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SectionSimpleFactoryTest {

	private static final double A_FEW_GREATER_PRICE = 15.96;
	private static final double A_FEW_LOWER_PRICE = 15.94;
	private static final double PRICE = 15.95;
	private static final int NUMBER_OF_TICKETS = 15;
	private static final String SECTION_NAME = "Azure";
	
	@InjectMocks
	private SectionSimpleFactory sectionSimpleFactory;
	
	private Set<String> seats;
	
	@Before
	public void setUp() throws Exception {
		seats = new HashSet<String>();
	}

	@Test
	public void createSection_create_a_Section_with_same_attributes() {
		SectionDto sectionDto = new SectionDto(SECTION_NAME, NUMBER_OF_TICKETS, PRICE, seats);
		Section section = sectionSimpleFactory.createSection(sectionDto);
		
		assertSame(SECTION_NAME, section.getSectionName());
		assertEquals(NUMBER_OF_TICKETS, section.getNumberOfTickets());
		assertTrue(A_FEW_LOWER_PRICE < section.getPrice());
		assertTrue(A_FEW_GREATER_PRICE > section.getPrice());
		assertSame(seats, section.getSeats());
	}
}
