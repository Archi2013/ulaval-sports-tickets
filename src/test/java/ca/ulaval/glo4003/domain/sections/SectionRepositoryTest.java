package ca.ulaval.glo4003.domain.sections;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4003.exceptions.SectionDoesntExistException;

@RunWith(MockitoJUnitRunner.class)
public class SectionRepositoryTest {

	static final String A_SPORT = "Sport";
	static final DateTime A_DATE = new DateTime(100);
	static final String A_SECTION = "Section";

	SectionDto dtoAllTickets = new SectionDto(A_SECTION, 0, 0);
	SectionDto dtoAvailableTickets = new SectionDto(A_SECTION, 0, 0);

	@Mock
	Section sectionAllTickets;
	@Mock
	Section sectionAvailableTickets;

	@Mock
	SectionDao dao;

	@Mock
	SectionFactory factory;

	@InjectMocks
	SectionRepository repository;

	@Before
	public void setUp() throws SectionDoesntExistException {
		when(dao.get(A_SPORT, A_DATE, A_SECTION)).thenReturn(dtoAllTickets);
		when(dao.getAvailable(A_SPORT, A_DATE, A_SECTION)).thenReturn(dtoAvailableTickets);
		when(factory.createSection(dtoAllTickets)).thenReturn(sectionAllTickets);
		when(factory.createSection(dtoAvailableTickets)).thenReturn(sectionAvailableTickets);
	}

	@Test
	public void get_asks_dao_for_data_then_use_factory_to_instantiate() throws SectionDoesntExistException {
		Section section = repository.get(A_SPORT, A_DATE, A_SECTION);

		assertSame(sectionAllTickets, section);
		verify(dao).get(A_SPORT, A_DATE, A_SECTION);
		verify(factory).createSection(dtoAllTickets);
	}

	@Test
	public void getAvailable_asks_dao_for_data_then_use_factory_to_instantiate() throws SectionDoesntExistException {
		Section section = repository.getAvailable(A_SPORT, A_DATE, A_SECTION);

		assertSame(sectionAvailableTickets, section);
		verify(dao).getAvailable(A_SPORT, A_DATE, A_SECTION);
		verify(factory).createSection(dtoAvailableTickets);
	}
}
