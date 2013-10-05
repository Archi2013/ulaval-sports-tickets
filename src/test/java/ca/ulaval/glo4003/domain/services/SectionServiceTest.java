package ca.ulaval.glo4003.domain.services;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4003.domain.dtos.SectionDto;
import ca.ulaval.glo4003.domain.utilities.SectionDoesntExistInPropertiesFileException;
import ca.ulaval.glo4003.domain.utilities.SectionUrlMapper;
import ca.ulaval.glo4003.domain.utilities.TicketType;
import ca.ulaval.glo4003.persistence.daos.SectionDao;
import ca.ulaval.glo4003.persistence.daos.SectionDoesntExistException;
import ca.ulaval.glo4003.web.converters.SectionConverter;
import ca.ulaval.glo4003.web.viewmodels.SectionViewModel;

@RunWith(MockitoJUnitRunner.class)
public class SectionServiceTest {

	private static final String SECTION_URL = "SECTION_URL";
	private static final String ADMISSION = "GENERAL";
	private static final String SECTION_NAME = "BLEUS";

	private static final int GAME_ID = 12;

	@Mock
	private SectionUrlMapper sectionUrlMapperMock;

	@Mock
	private SectionDao sectionDaoMock;

	@Mock
	private SectionConverter sectionConverterMock;

	@InjectMocks
	private SectionService service;

	@Before
	public void setup() throws SectionDoesntExistInPropertiesFileException {
		TicketType ticketType = new TicketType(ADMISSION, SECTION_NAME);
		when(sectionUrlMapperMock.getTicketType(SECTION_URL)).thenReturn(ticketType);
	}

	@Test
	public void getSection_should_get_ticket_type_from_section_url() throws SectionDoesntExistException,
			SectionDoesntExistInPropertiesFileException {
		service.getSection(GAME_ID, SECTION_URL);

		verify(sectionUrlMapperMock).getTicketType(SECTION_URL);
	}

	@SuppressWarnings("unchecked")
	@Test(expected = SectionDoesntExistException.class)
	public void getSection_should_throw_section_doesnt_exist_exception_if_section_doesnt_exist_in_properties_file()
			throws SectionDoesntExistInPropertiesFileException, SectionDoesntExistException {
		when(sectionUrlMapperMock.getTicketType(SECTION_URL)).thenThrow(SectionDoesntExistInPropertiesFileException.class);

		service.getSection(GAME_ID, SECTION_URL);
	}

	@Test
	public void getSection_should_get_section_from_dao() throws SectionDoesntExistException {
		service.getSection(GAME_ID, SECTION_URL);

		verify(sectionDaoMock).get(GAME_ID, ADMISSION, SECTION_NAME);
	}

	@Test
	public void getSection_should_convert_dto_to_view_model() throws SectionDoesntExistException {
		SectionDto dto = mock(SectionDto.class);
		when(sectionDaoMock.get(GAME_ID, ADMISSION, SECTION_NAME)).thenReturn(dto);

		service.getSection(GAME_ID, SECTION_URL);

		verify(sectionConverterMock).convert(dto);
	}

	@Test
	public void getSection_should_return_view_model() throws SectionDoesntExistException {
		SectionDto dto = mock(SectionDto.class);
		when(sectionDaoMock.get(GAME_ID, ADMISSION, SECTION_NAME)).thenReturn(dto);
		SectionViewModel expectedViewModel = mock(SectionViewModel.class);
		when(sectionConverterMock.convert(dto)).thenReturn(expectedViewModel);

		SectionViewModel viewModel = service.getSection(GAME_ID, SECTION_URL);

		assertEquals(expectedViewModel, viewModel);
	}
}
