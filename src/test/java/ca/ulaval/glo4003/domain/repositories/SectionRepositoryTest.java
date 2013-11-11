package ca.ulaval.glo4003.domain.repositories;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4003.domain.dtos.SectionDto;
import ca.ulaval.glo4003.domain.factories.ISectionFactory;
import ca.ulaval.glo4003.domain.pojos.Section;
import ca.ulaval.glo4003.persistence.daos.SectionDao;
import ca.ulaval.glo4003.persistence.daos.SectionDoesntExistException;
import ca.ulaval.glo4003.persistence.daos.SportDoesntExistException;

@RunWith(MockitoJUnitRunner.class)
public class SectionRepositoryTest {

	private static final String SECTION_NAME = "Indigo";

	private static final long GAME_ID = 265L;

	@Mock
	private SectionDao sectionDao;
	
	@Mock
	private ISectionFactory sectionFactory;
	
	@InjectMocks
	SectionRepository sectionRepository;

	@Before
	public void setUp() throws SportDoesntExistException {
	}
	
	@Test
	public void given_a_gameId_and_a_sectionName_get_should_use_factory() throws SectionDoesntExistException {
		SectionDto sectionDto = mock(SectionDto.class);
		when(sectionDao.get(GAME_ID, SECTION_NAME)).thenReturn(sectionDto);
		
		sectionRepository.get(GAME_ID, SECTION_NAME);
		
		verify(sectionFactory).createSection(sectionDto);
	}
	
	@Test
	public void given_a_gameId_and_a_sectionName_get_should_use_return_a_Section_from_factory() throws SectionDoesntExistException {
		SectionDto sectionDto = mock(SectionDto.class);
		Section section = mock(Section.class);
		when(sectionDao.get(GAME_ID, SECTION_NAME)).thenReturn(sectionDto);
		when(sectionFactory.createSection(sectionDto)).thenReturn(section);
		
		Section actual = sectionRepository.get(GAME_ID, SECTION_NAME);
		
		assertSame(section, actual);
	}
}
