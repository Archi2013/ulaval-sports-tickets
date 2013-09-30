package ca.ulaval.glo4003.web.controller;

import static com.google.common.collect.Lists.*;
import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ui.Model;

import ca.ulaval.glo4003.datafilter.DataFilter;
import ca.ulaval.glo4003.dto.GameDto;
import ca.ulaval.glo4003.dto.SportDto;
import ca.ulaval.glo4003.persistence.dao.SportDao;
import ca.ulaval.glo4003.persistence.dao.SportDoesntExistException;
import ca.ulaval.glo4003.utility.SportDoesntExistInPropertieFileException;
import ca.ulaval.glo4003.utility.SportUrlMapperPropertieFile;
import ca.ulaval.glo4003.web.converter.SportConverter;
import ca.ulaval.glo4003.web.converter.SportSimpleConverter;
import ca.ulaval.glo4003.web.viewmodel.GameSimpleViewModel;
import ca.ulaval.glo4003.web.viewmodel.SportSimpleViewModel;
import ca.ulaval.glo4003.web.viewmodel.SportViewModel;

@RunWith(MockitoJUnitRunner.class)
public class SportControllerTest {

	private static final String SPORT_NAME = "Basketball Féminin";
	private static final String SPORT_URL = "basketball-feminin";

	@Mock
	private SportDao sportDao;
	
	@Mock
	private Model model;

	@Mock
	private DataFilter<GameDto> dataFilter;

	@Mock
	private SportUrlMapperPropertieFile sportUrlMapper;
	
	@Mock
	private SportSimpleConverter sportSimpleConverter;
	
	@Mock
	private SportConverter sportConverter;
	
	@Mock
	private SportDto sportDto;
	
	@Mock
	private GameDto gameDto;
	
	@Mock
	List<GameDto> gameDtosEmpty;
	
	@InjectMocks
	private SportController controller;
	
	List<GameDto> gameDtos;
	List<GameDto> gameDtosNonEmpty;

	@Before
	public void setUp() throws SportDoesntExistException {
		gameDtos = newArrayList();
		
		gameDtosNonEmpty = newArrayList();
		gameDtosNonEmpty.add(gameDto);
	}

	@Test
	public void getSports_should_get_sports_from_dao() {
		controller.getSports(model);

		verify(sportDao).getAll();
	}
	
	@Test
	public void getSports_should_add_the_sports_to_model() {
		List<SportDto> sportDtos = newArrayList();
		when(sportDao.getAll()).thenReturn(sportDtos);
		List<SportSimpleViewModel> viewModels = addToConverter(sportDtos);
		
		controller.getSports(model);
		
		verify(model).addAttribute("sports", viewModels);
	}

	@Test
	public void getSportGames_should_use_filter_on_list_returned_by_dao() throws SportDoesntExistException, RuntimeException, SportDoesntExistInPropertieFileException {
		when(sportDao.get(SPORT_NAME)).thenReturn(sportDto);
		when(sportDto.getGames()).thenReturn(gameDtos);
		when(sportUrlMapper.getSportName(SPORT_URL)).thenReturn(SPORT_NAME);

		controller.getSportGames(SPORT_URL, model);

		verify(dataFilter).applyFilterOnList(gameDtos);
	}

	@Test
	public void getSportGames_should_add_sport_to_model_when_dao_return_a_non_empty_list()
			throws SportDoesntExistException, RuntimeException, SportDoesntExistInPropertieFileException {
		when(sportDao.get(SPORT_NAME)).thenReturn(sportDto);
		when(sportDto.getGames()).thenReturn(gameDtosNonEmpty);
		when(sportUrlMapper.getSportName(SPORT_URL)).thenReturn(SPORT_NAME);
		SportViewModel sportVM = addToConverter(sportDto);
		
		controller.getSportGames(SPORT_URL, model);

		verify(model).addAttribute("sport", sportVM);
	}

	@Test
	public void getSportGames_should_return_correct_path_when_dao_return_a_non_empty_list() throws RuntimeException, SportDoesntExistInPropertieFileException {
		when(sportDao.get(SPORT_NAME)).thenReturn(sportDto);
		when(sportDto.getGames()).thenReturn(gameDtosNonEmpty);
		when(sportUrlMapper.getSportName(SPORT_URL)).thenReturn(SPORT_NAME);
		
		String path = controller.getSportGames(SPORT_URL, model);

		assertEquals("sport/games", path);
	}

	@Test
	public void getSportGames_should_redirect_to_404_path_when_sport_doesnt_exist_in_propertie_file() throws SportDoesntExistException, RuntimeException, SportDoesntExistInPropertieFileException {
		when(sportUrlMapper.getSportName(SPORT_URL)).thenThrow(SportDoesntExistInPropertieFileException.class);

		String path = controller.getSportGames(SPORT_URL, model);

		assertEquals("error/404", path);
	}
	
	@Test
	public void getSportsGames_should_not_add_sport_games_to_model_when_dao_returns_empty_list() throws SportDoesntExistException, RuntimeException, SportDoesntExistInPropertieFileException {
		when(sportDao.get(SPORT_NAME)).thenReturn(sportDto);
		when(sportDto.getGames()).thenReturn(gameDtosEmpty);
		when(gameDtosEmpty.isEmpty()).thenReturn(true);
		when(sportUrlMapper.getSportName(SPORT_URL)).thenReturn(SPORT_NAME);

		controller.getSportGames(SPORT_URL, model);

		verify(model, never()).addAttribute(eq("sport"), any());
	}

	@Ignore
	@Test
	public void getSportsGames_should_return_no_games_path_when_sport_doesnt_have_any_game()
			throws SportDoesntExistException, RuntimeException, SportDoesntExistInPropertieFileException {
		when(sportUrlMapper.getSportName(SPORT_URL)).thenReturn(SPORT_NAME);
		when(sportDao.get(SPORT_NAME)).thenReturn(sportDto);
		when(sportDto.getGames()).thenReturn(gameDtosEmpty);
		when(gameDtosEmpty.isEmpty()).thenReturn(true);

		controller.getSportGames(SPORT_URL, model);
		
		String path = controller.getSportGames(SPORT_NAME, model);

		assertEquals("sport/no-games", path);
	}
	
	private List<SportSimpleViewModel> addToConverter(List<SportDto> sportDtos) {
		List<SportSimpleViewModel> viewModels = newArrayList();
		when(sportSimpleConverter.convert(sportDtos)).thenReturn(viewModels);
		return viewModels;
	}
	
	private SportViewModel addToConverter(SportDto sportDto) {
		List<GameSimpleViewModel> games = newArrayList();
		SportViewModel viewModel = new SportViewModel("Basketball", games);
		when(sportConverter.convert(sportDto)).thenReturn(viewModel);
		return viewModel;
	}

}
