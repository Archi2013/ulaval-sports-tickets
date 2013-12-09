package ca.ulaval.glo4003.services;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4003.domain.game.GameDao;
import ca.ulaval.glo4003.domain.sections.SectionDao;
import ca.ulaval.glo4003.exceptions.GameDoesntExistException;
import ca.ulaval.glo4003.exceptions.NoTicketTypeForUrlException;
import ca.ulaval.glo4003.exceptions.SectionDoesntExistException;
import ca.ulaval.glo4003.game.dto.GameDto;
import ca.ulaval.glo4003.presentation.viewmodels.factories.ChosenTicketsViewModelFactory;
import ca.ulaval.glo4003.presentation.viewmodels.factories.SectionViewModelFactory;
import ca.ulaval.glo4003.presentation.viewmodels.factories.SectionsViewModelFactory;
import ca.ulaval.glo4003.sections.dto.SectionDto;
import ca.ulaval.glo4003.utilities.urlmapper.TicketTypeUrlMapper;

@RunWith(MockitoJUnitRunner.class)
public class SectionServiceTest {
	@Mock
	private ChosenTicketsViewModelFactory chosenTicketsViewModelFactory;

	@Mock
	private GameDao gameDao;

	@Mock
	private SectionDao sectionDao;

	@Mock
	private SectionViewModelFactory sectionFactory;

	@Mock
	private TicketTypeUrlMapper ticketTypeUrlMapper;

	@Mock
	private SectionsViewModelFactory viewModelFactory;
	
	@InjectMocks
	private SectionService sectionService;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGetAvailableSectionsForGame() throws Exception {
		sectionService.getAvailableSectionsForGame(anyString(), any(DateTime.class));
		
		verify(viewModelFactory, atLeastOnce()).createViewModel(any(GameDto.class),	anyListOf(SectionDto.class));
	}

	@Test
	public void testGetAvailableSection() throws Exception {
		String aSport = "aSport";
		DateTime aDate = new DateTime();
		String aSectionName = anyString();
		
		sectionService.getAvailableSection(aSport, aDate, aSectionName);
		
		verify(sectionFactory, atLeastOnce()).createViewModel(any(SectionDto.class), any(GameDto.class));
	}
	
	@Test(expected = SectionDoesntExistException.class)
	public void testGetAvailableSection_withAGameThatDosentExist() throws Exception {
		String aSport = "aSport";
		DateTime aDate = new DateTime();
		String aSectionName = anyString();
		
		when(gameDao.get(anyString(), aDate)).thenThrow(new GameDoesntExistException());
		
		sectionService.getAvailableSection(aSport, aDate, aSectionName);
	}
	
	@Test(expected = SectionDoesntExistException.class)
	public void testGetAvailableSection_withNoTicketTypeForUrl() throws Exception {
		String aSport = "aSport";
		DateTime aDate = new DateTime();
		String aSectionName = "aSectionName";
		
		when(ticketTypeUrlMapper.getTicketType(aSectionName)).thenThrow(new NoTicketTypeForUrlException());
		
		sectionService.getAvailableSection(aSport, aDate, aSectionName);
	}


	@Test
	public void testGetChosenGeneralTicketsViewModel() throws Exception {
		String aSport = "aSport";
		DateTime aDate = new DateTime();
		String aSectionName = anyString();
		
		sectionService.getChosenGeneralTicketsViewModel(aSport, aDate, aSectionName);
		
		verify(chosenTicketsViewModelFactory, atLeastOnce()).createViewModelForGeneralTickets(any(GameDto.class), any(SectionDto.class));
	}
	
	@Test(expected = SectionDoesntExistException.class)
	public void testGetChosenGeneralTicketsViewModel_withGameThatDoesntExist() throws Exception {
		String aSport = "aSport";
		DateTime aDate = new DateTime();
		String aSectionName = anyString();
		
		when(gameDao.get(anyString(), aDate)).thenThrow(new GameDoesntExistException());
		
		sectionService.getChosenGeneralTicketsViewModel(aSport, aDate, aSectionName);
	}
	
	@Test(expected = SectionDoesntExistException.class)
	public void testGetChosenGeneralTicketsViewModel_withNoTicketTypeForUrl() throws Exception {
		String aSport = "aSport";
		DateTime aDate = new DateTime();
		String aSectionName = "aSectionName";

		when(ticketTypeUrlMapper.getTicketType(aSectionName)).thenThrow(new NoTicketTypeForUrlException());
		
		sectionService.getChosenGeneralTicketsViewModel(aSport, aDate, aSectionName);
	}

	@Test
	public void testGetChosenWithSeatTicketsViewModel() throws Exception {
		String aSport = "aSport";
		DateTime aDate = new DateTime();
		String aSectionName = anyString();
		
		sectionService.getChosenWithSeatTicketsViewModel(aSport, aDate, aSectionName);

		verify(chosenTicketsViewModelFactory, atLeastOnce()).createViewModelForWithSeatTickets(any(GameDto.class), any(SectionDto.class));
	}

	@Test(expected = SectionDoesntExistException.class)
	public void testGetChosenWithSeatTicketsViewModel_withGameThatDoesntExist() throws Exception {
		String aSport = "aSport";
		DateTime aDate = new DateTime();
		String aSectionName = anyString();
		
		when(gameDao.get(anyString(), aDate)).thenThrow(new GameDoesntExistException());
		
		sectionService.getChosenWithSeatTicketsViewModel(aSport, aDate, aSectionName);
	}
	
	@Test(expected = SectionDoesntExistException.class)
	public void testGetChosenWithSeatTicketsViewModel_withNoTicketTypeForUrlException() throws Exception {
		String aSport = "aSport";
		DateTime aDate = new DateTime();
		String aSectionName = anyString();
		
		when(ticketTypeUrlMapper.getTicketType(aSectionName)).thenThrow(new NoTicketTypeForUrlException());
		
		sectionService.getChosenWithSeatTicketsViewModel(aSport, aDate, aSectionName);
	}
	
}
