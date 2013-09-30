package ca.ulaval.glo4003.web.controller;

import static com.google.common.collect.Lists.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ui.Model;

import ca.ulaval.glo4003.dto.GameDto;
import ca.ulaval.glo4003.persistence.dao.GameDao;
import ca.ulaval.glo4003.persistence.dao.GameDoesntExistException;
import ca.ulaval.glo4003.web.converter.GameConverter;
import ca.ulaval.glo4003.web.viewmodel.GameViewModel;
import ca.ulaval.glo4003.web.viewmodel.TicketViewModel;

@RunWith(MockitoJUnitRunner.class)
public class GameControllerTest {

	public static final int AN_ID = 123;
	public static final String A_SPORT_NAME = "SportName";

	@Mock
	private GameDao gameDao;
	
	@Mock
	private GameDto gameDto;

	@Mock
	private Model model;
	
	@Mock
	private GameConverter gameConverter;

	@InjectMocks
	private GameController gameController;

	@Before
	public void setUp() {
	}

	@Test
	public void getTicketsForGame_should_add_the_specified_game_to_model() throws GameDoesntExistException {
		when(gameDao.get(AN_ID)).thenReturn(gameDto);
		GameViewModel gameVM = addToConverter(gameDto);
		
		gameController.getTicketsForGame(AN_ID, A_SPORT_NAME, model);

		verify(model).addAttribute("game", gameVM);
	}

	@Test
	public void getTicketsForGame_should_return_correct_view_path() {
		String path = gameController.getTicketsForGame(AN_ID, A_SPORT_NAME, model);

		assertEquals("game/tickets", path);
	}

	@Test
	public void getTicketsForGame_should_redirect_to_404_page_when_game_id_doesnt_exist()
			throws GameDoesntExistException {
		when(gameDao.get(AN_ID)).thenThrow(GameDoesntExistException.class);

		String path = gameController.getTicketsForGame(AN_ID, A_SPORT_NAME, model);

		assertEquals("error/404", path);
	}
	
	private GameViewModel addToConverter(GameDto gameDto) throws GameDoesntExistException {
		List<TicketViewModel> ticketDtos = newArrayList();
		GameViewModel viewModel = new GameViewModel(new Long(123), "Furets rouges", "14 septembre 2013", ticketDtos);
		when(gameConverter.convert(gameDto)).thenReturn(viewModel);
		return viewModel;
	}
}
