package ca.ulaval.glo4003.domain.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4003.domain.dtos.GameDto;
import ca.ulaval.glo4003.domain.dtos.SectionDto;
import ca.ulaval.glo4003.domain.utilities.NoTicketTypeForUrlException;
import ca.ulaval.glo4003.domain.utilities.TicketTypeUrlMapper;
import ca.ulaval.glo4003.persistence.daos.GameDao;
import ca.ulaval.glo4003.persistence.daos.GameDoesntExistException;
import ca.ulaval.glo4003.persistence.daos.SectionDao;
import ca.ulaval.glo4003.persistence.daos.SectionDoesntExistException;
import ca.ulaval.glo4003.presentation.viewmodels.ChooseTicketsViewModel;
import ca.ulaval.glo4003.presentation.viewmodels.SectionViewModel;
import ca.ulaval.glo4003.presentation.viewmodels.factories.ChooseTicketsViewModelFactory;
import ca.ulaval.glo4003.presentation.viewmodels.factories.SectionViewModelFactory;

@Ignore
@RunWith(MockitoJUnitRunner.class)
public class SectionServiceTest {

	private static final String SECTION_URL = "SECTION_URL";
	private static final String SECTION_NAME = "BLEUS";
	private static final String SPORT_NAME = "Football";
	private static final DateTime GAME_DATE = DateTime.now();

	@Mock
	private TicketTypeUrlMapper ticketTypeUrlMapperMock;

	@Mock
	private GameDao gameDaoMock;

	@Mock
	private SectionDao sectionDaoMock;

	@Mock
	private SectionViewModelFactory sectionFactoryMock;
	
	@Mock
	private ChooseTicketsViewModelFactory chooseTicketsViewModelFactoryMock;

	@InjectMocks
	private SectionService service;

	@Before
	public void setUp() throws NoTicketTypeForUrlException {
		when(ticketTypeUrlMapperMock.getTicketType(SECTION_URL)).thenReturn(SECTION_NAME);
	}

	@Test
	public void getSection_should_get_ticket_type_from_section_url() throws SectionDoesntExistException,
			NoTicketTypeForUrlException {
		service.getSection(SPORT_NAME, GAME_DATE, SECTION_URL);

		verify(ticketTypeUrlMapperMock).getTicketType(SECTION_URL);
	}

	@SuppressWarnings("unchecked")
	@Test(expected = SectionDoesntExistException.class)
	public void getSection_should_throw_section_doesnt_exist_exception_if_section_doesnt_exist()
			throws SectionDoesntExistException, NoTicketTypeForUrlException {
		when(ticketTypeUrlMapperMock.getTicketType(SECTION_URL)).thenThrow(NoTicketTypeForUrlException.class);

		service.getSection(SPORT_NAME, GAME_DATE, SECTION_URL);
	}

	@Test
	public void getSection_should_get_section_from_dao() throws SectionDoesntExistException {
		service.getSection(SPORT_NAME, GAME_DATE, SECTION_URL);

		verify(sectionDaoMock).get(SPORT_NAME, GAME_DATE, SECTION_NAME);
	}

	@Test
	public void getSection_should_get_game_from_dao() throws SectionDoesntExistException, GameDoesntExistException {
		service.getSection(SPORT_NAME, GAME_DATE, SECTION_URL);

		verify(gameDaoMock).get(SPORT_NAME, GAME_DATE);
	}

	@SuppressWarnings("unchecked")
	@Test(expected = SectionDoesntExistException.class)
	public void getSection_should_throw_section_doesnt_exist_exception_if_game_doesnt_exist() throws GameDoesntExistException,
			SectionDoesntExistException {
		when(gameDaoMock.get(SPORT_NAME, GAME_DATE)).thenThrow(GameDoesntExistException.class);

		service.getSection(SPORT_NAME, GAME_DATE, SECTION_URL);
	}

	@Test
	public void getSection_should_convert_dto_to_view_model() throws SectionDoesntExistException, GameDoesntExistException {
		SectionDto dto = mock(SectionDto.class);
		when(sectionDaoMock.get(SPORT_NAME, GAME_DATE, SECTION_NAME)).thenReturn(dto);
		GameDto gameDto = mock(GameDto.class);
		when(gameDaoMock.get(SPORT_NAME, GAME_DATE)).thenReturn(gameDto);

		service.getSection(SPORT_NAME, GAME_DATE, SECTION_URL);

		verify(sectionFactoryMock).createViewModel(dto, gameDto);
	}

	@Test
	public void getSection_should_return_view_model() throws SectionDoesntExistException, GameDoesntExistException {
		SectionDto dto = mock(SectionDto.class);
		when(sectionDaoMock.get(SPORT_NAME, GAME_DATE, SECTION_NAME)).thenReturn(dto);
		GameDto gameDto = mock(GameDto.class);
		when(gameDaoMock.get(SPORT_NAME, GAME_DATE)).thenReturn(gameDto);
		SectionViewModel expectedViewModel = mock(SectionViewModel.class);
		when(sectionFactoryMock.createViewModel(dto, gameDto)).thenReturn(expectedViewModel);

		SectionViewModel viewModel = service.getSection(SPORT_NAME, GAME_DATE, SECTION_URL);

		assertEquals(expectedViewModel, viewModel);
	}
	
	@Test
	public void getChooseTicketsViewModel_should_get_ticket_type_from_section_url() throws SectionDoesntExistException,
			NoTicketTypeForUrlException {
		service.getChooseTicketsViewModel(SPORT_NAME, GAME_DATE, SECTION_URL);

		verify(ticketTypeUrlMapperMock).getTicketType(SECTION_URL);
	}
	
	@Test
	public void getChooseTicketsViewModel_should_get_section_from_dao() throws SectionDoesntExistException {
		service.getChooseTicketsViewModel(SPORT_NAME, GAME_DATE, SECTION_URL);

		verify(sectionDaoMock).get(SPORT_NAME, GAME_DATE, SECTION_NAME);
	}

	@Test
	public void getChooseTicketsViewModel_should_get_game_from_dao() throws SectionDoesntExistException, GameDoesntExistException {
		service.getChooseTicketsViewModel(SPORT_NAME, GAME_DATE, SECTION_URL);

		verify(gameDaoMock).get(SPORT_NAME, GAME_DATE);
	}
	
	@Test
	public void getChooseTicketsViewModel_should_obtain_a_chooseTicketsViewModel() throws SectionDoesntExistException, GameDoesntExistException {
		SectionDto sectionDto = mock(SectionDto.class);
		when(sectionDaoMock.get(SPORT_NAME, GAME_DATE, SECTION_NAME)).thenReturn(sectionDto);
		GameDto gameDto = mock(GameDto.class);
		when(gameDaoMock.get(SPORT_NAME, GAME_DATE)).thenReturn(gameDto);

		service.getChooseTicketsViewModel(SPORT_NAME, GAME_DATE, SECTION_URL);

		verify(chooseTicketsViewModelFactoryMock).createViewModel(gameDto, sectionDto);
	}

	@Test
	public void getChooseTicketsViewModel_should_return_a_chooseTicketsViewModel() throws SectionDoesntExistException, GameDoesntExistException {
		SectionDto sectionDto = mock(SectionDto.class);
		when(sectionDaoMock.get(SPORT_NAME, GAME_DATE, SECTION_NAME)).thenReturn(sectionDto);
		GameDto gameDto = mock(GameDto.class);
		when(gameDaoMock.get(SPORT_NAME, GAME_DATE)).thenReturn(gameDto);
		ChooseTicketsViewModel expectedViewModel = mock(ChooseTicketsViewModel.class);
		when(chooseTicketsViewModelFactoryMock.createViewModel(gameDto, sectionDto)).thenReturn(expectedViewModel);

		ChooseTicketsViewModel viewModel = service.getChooseTicketsViewModel(SPORT_NAME, GAME_DATE, SECTION_URL);

		assertEquals(expectedViewModel, viewModel);
	}
}
