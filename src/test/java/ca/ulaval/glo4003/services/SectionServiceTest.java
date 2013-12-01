package ca.ulaval.glo4003.services;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4003.domain.game.GameDao;
import ca.ulaval.glo4003.domain.game.GameDto;
import ca.ulaval.glo4003.domain.sections.SectionDao;
import ca.ulaval.glo4003.domain.sections.SectionDto;
import ca.ulaval.glo4003.domain.sports.SportUrlMapper;
import ca.ulaval.glo4003.domain.tickets.TicketTypeUrlMapper;
import ca.ulaval.glo4003.exceptions.GameDoesntExistException;
import ca.ulaval.glo4003.exceptions.NoTicketTypeForUrlException;
import ca.ulaval.glo4003.exceptions.SectionDoesntExistException;
import ca.ulaval.glo4003.presentation.viewmodels.ChosenGeneralTicketsViewModel;
import ca.ulaval.glo4003.presentation.viewmodels.ChosenTicketsViewModel;
import ca.ulaval.glo4003.presentation.viewmodels.ChosenWithSeatTicketsViewModel;
import ca.ulaval.glo4003.presentation.viewmodels.SectionViewModel;
import ca.ulaval.glo4003.presentation.viewmodels.SectionsViewModel;
import ca.ulaval.glo4003.presentation.viewmodels.factories.ChosenTicketsViewModelFactory;
import ca.ulaval.glo4003.presentation.viewmodels.factories.SectionViewModelFactory;
import ca.ulaval.glo4003.presentation.viewmodels.factories.SectionsViewModelFactory;

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
	private ChosenTicketsViewModelFactory chosenTicketsViewModelFactoryMock;

	@Mock
	private SportUrlMapper sportUrlMapper;

	@Mock
	private SectionsViewModelFactory gameViewModelFactory;

	private GameDto gameDto;
	private List<SectionDto> sectionDtos;

	@InjectMocks
	private SectionService service;

	@Before
	public void setUp() throws NoTicketTypeForUrlException, GameDoesntExistException {
		gameDto = mock(GameDto.class);
		when(gameDaoMock.get(SPORT_NAME, GAME_DATE)).thenReturn(gameDto);

		sectionDtos = newArrayList();
		when(sectionDaoMock.getAll(SPORT_NAME, GAME_DATE)).thenReturn(sectionDtos);

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
	public void getSection_should_throw_section_doesnt_exist_exception_if_game_doesnt_exist()
			throws GameDoesntExistException, SectionDoesntExistException {
		when(gameDaoMock.get(SPORT_NAME, GAME_DATE)).thenThrow(GameDoesntExistException.class);

		service.getSection(SPORT_NAME, GAME_DATE, SECTION_URL);
	}

	@Test
	public void getSection_should_convert_dto_to_view_model() throws SectionDoesntExistException,
			GameDoesntExistException {
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
	public void getChosenGeneralTicketsViewModel_should_get_ticket_type_from_section_url()
			throws SectionDoesntExistException, NoTicketTypeForUrlException {
		service.getChosenGeneralTicketsViewModel(SPORT_NAME, GAME_DATE, SECTION_URL);

		verify(ticketTypeUrlMapperMock).getTicketType(SECTION_URL);
	}

	@Test
	public void getChosenGeneralTicketsViewModel_should_get_section_from_dao() throws SectionDoesntExistException {
		service.getChosenGeneralTicketsViewModel(SPORT_NAME, GAME_DATE, SECTION_URL);

		verify(sectionDaoMock).get(SPORT_NAME, GAME_DATE, SECTION_NAME);
	}

	@Test
	public void getChosenGeneralTicketsViewModel_should_get_game_from_dao() throws SectionDoesntExistException,
			GameDoesntExistException {
		service.getChosenGeneralTicketsViewModel(SPORT_NAME, GAME_DATE, SECTION_URL);

		verify(gameDaoMock).get(SPORT_NAME, GAME_DATE);
	}

	@Test
	public void getChosenGeneralTicketsViewModel_should_obtain_a_ChosenGeneralTicketsViewModel()
			throws SectionDoesntExistException, GameDoesntExistException {
		SectionDto sectionDto = mock(SectionDto.class);
		when(sectionDaoMock.get(SPORT_NAME, GAME_DATE, SECTION_NAME)).thenReturn(sectionDto);
		GameDto gameDto = mock(GameDto.class);
		when(gameDaoMock.get(SPORT_NAME, GAME_DATE)).thenReturn(gameDto);

		service.getChosenGeneralTicketsViewModel(SPORT_NAME, GAME_DATE, SECTION_URL);

		verify(chosenTicketsViewModelFactoryMock).createViewModelForGeneralTickets(gameDto, sectionDto);
	}

	@Test
	public void getChosenGeneralTicketsViewModel_should_return_a_ChosenGeneralTicketsViewModel()
			throws SectionDoesntExistException, GameDoesntExistException {
		SectionDto sectionDto = mock(SectionDto.class);
		when(sectionDaoMock.get(SPORT_NAME, GAME_DATE, SECTION_NAME)).thenReturn(sectionDto);
		GameDto gameDto = mock(GameDto.class);
		when(gameDaoMock.get(SPORT_NAME, GAME_DATE)).thenReturn(gameDto);
		ChosenGeneralTicketsViewModel expectedViewModel = mock(ChosenGeneralTicketsViewModel.class);
		when(chosenTicketsViewModelFactoryMock.createViewModelForGeneralTickets(gameDto, sectionDto)).thenReturn(
				expectedViewModel);

		ChosenTicketsViewModel viewModel = service.getChosenGeneralTicketsViewModel(SPORT_NAME, GAME_DATE, SECTION_URL);

		assertEquals(expectedViewModel, viewModel);
	}

	@Test
	public void getChosenWithSeatTicketsViewModel_should_get_ticket_type_from_section_url()
			throws SectionDoesntExistException, NoTicketTypeForUrlException {
		service.getChosenWithSeatTicketsViewModel(SPORT_NAME, GAME_DATE, SECTION_URL);

		verify(ticketTypeUrlMapperMock).getTicketType(SECTION_URL);
	}

	@Test
	public void getChosenWithSeatTicketsViewModel_should_get_section_from_dao() throws SectionDoesntExistException {
		service.getChosenWithSeatTicketsViewModel(SPORT_NAME, GAME_DATE, SECTION_URL);

		verify(sectionDaoMock).get(SPORT_NAME, GAME_DATE, SECTION_NAME);
	}

	@Test
	public void getChosenWithSeatTicketsViewModel_should_get_game_from_dao() throws SectionDoesntExistException,
			GameDoesntExistException {
		service.getChosenWithSeatTicketsViewModel(SPORT_NAME, GAME_DATE, SECTION_URL);

		verify(gameDaoMock).get(SPORT_NAME, GAME_DATE);
	}

	@Test
	public void getChosenWithSeatTicketsViewModel_should_obtain_a_ChosenWithSeatTicketsViewModel()
			throws SectionDoesntExistException, GameDoesntExistException {
		SectionDto sectionDto = mock(SectionDto.class);
		when(sectionDaoMock.get(SPORT_NAME, GAME_DATE, SECTION_NAME)).thenReturn(sectionDto);
		GameDto gameDto = mock(GameDto.class);
		when(gameDaoMock.get(SPORT_NAME, GAME_DATE)).thenReturn(gameDto);

		service.getChosenWithSeatTicketsViewModel(SPORT_NAME, GAME_DATE, SECTION_URL);

		verify(chosenTicketsViewModelFactoryMock).createViewModelForWithSeatTickets(gameDto, sectionDto);
	}

	@Test
	public void getChosenWithSeatTicketsViewModel_should_return_a_ChosenWithSeatTicketsViewModel()
			throws SectionDoesntExistException, GameDoesntExistException {
		SectionDto sectionDto = mock(SectionDto.class);
		when(sectionDaoMock.get(SPORT_NAME, GAME_DATE, SECTION_NAME)).thenReturn(sectionDto);
		GameDto gameDto = mock(GameDto.class);
		when(gameDaoMock.get(SPORT_NAME, GAME_DATE)).thenReturn(gameDto);
		ChosenWithSeatTicketsViewModel expectedViewModel = mock(ChosenWithSeatTicketsViewModel.class);
		when(chosenTicketsViewModelFactoryMock.createViewModelForWithSeatTickets(gameDto, sectionDto)).thenReturn(
				expectedViewModel);

		ChosenTicketsViewModel viewModel = service
				.getChosenWithSeatTicketsViewModel(SPORT_NAME, GAME_DATE, SECTION_URL);

		assertEquals(expectedViewModel, viewModel);
	}

	@Test
	public void getSectionsForGame_should_get_game_from_dao() throws Exception {
		service.getAvailableSectionsForGame(SPORT_NAME, GAME_DATE);

		verify(gameDaoMock).get(SPORT_NAME, GAME_DATE);
	}

	@Test
	public void getSectionsForGame_should_get_sections_for_game_from_dao() throws Exception {
		service.getAvailableSectionsForGame(SPORT_NAME, GAME_DATE);

		verify(sectionDaoMock).getAllAvailable(SPORT_NAME, GAME_DATE);
	}

	@Test
	public void getSectionsForGame_should_create_view_model_from_game() throws Exception {

		service.getAvailableSectionsForGame(SPORT_NAME, GAME_DATE);

		verify(gameViewModelFactory).createViewModel(gameDto, sectionDtos);
	}

	@Test
	public void getSectionsForGame_should_return_view_model() throws Exception {
		SectionsViewModel expectedViewModel = mock(SectionsViewModel.class);
		when(gameViewModelFactory.createViewModel(gameDto, sectionDtos)).thenReturn(expectedViewModel);

		SectionsViewModel gameViewModel = service.getAvailableSectionsForGame(SPORT_NAME, GAME_DATE);

		assertEquals(expectedViewModel, gameViewModel);
	}
}
