package ca.ulaval.glo4003.domain.repositories;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4003.domain.sections.Section;
import ca.ulaval.glo4003.domain.sections.SectionDao;
import ca.ulaval.glo4003.domain.sections.SectionDto;
import ca.ulaval.glo4003.domain.sections.SectionFactory;
import ca.ulaval.glo4003.domain.sections.SectionRepository;
import ca.ulaval.glo4003.exceptions.SectionDoesntExistException;
import ca.ulaval.glo4003.exceptions.SportDoesntExistException;

@RunWith(MockitoJUnitRunner.class)
public class SectionByDaoRepositoryTest {

	private static final String SECTION_NAME = "Indigo";
	private static final String SPORT_NAME = "Football";
	private static final DateTime GAME_DATE = DateTime.now();

	@Mock
	private SectionDao sectionDao;
	
	@Mock
	private SectionFactory sectionFactory;
	
	@InjectMocks
	SectionRepository sectionRepository;

	@Before
	public void setUp() throws SportDoesntExistException {
	}
	
	@Test
	public void given_a_gameId_and_a_sectionName_get_should_use_factory() throws SectionDoesntExistException {
		SectionDto sectionDto = mock(SectionDto.class);
		when(sectionDao.get(SPORT_NAME, GAME_DATE, SECTION_NAME)).thenReturn(sectionDto);
		
		sectionRepository.get(SPORT_NAME, GAME_DATE, SECTION_NAME);
		
		verify(sectionFactory).createSection(sectionDto);
	}
	
	@Test
	public void given_a_gameId_and_a_sectionName_get_should_use_return_a_Section_from_factory() throws SectionDoesntExistException {
		SectionDto sectionDto = mock(SectionDto.class);
		Section section = mock(Section.class);
		when(sectionDao.get(SPORT_NAME, GAME_DATE, SECTION_NAME)).thenReturn(sectionDto);
		when(sectionFactory.createSection(sectionDto)).thenReturn(section);
		
		Section actual = sectionRepository.get(SPORT_NAME, GAME_DATE, SECTION_NAME);
		
		assertSame(section, actual);
	}
}
