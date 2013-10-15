package ca.ulaval.glo4003.domain.repositories;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4003.domain.dtos.SportDto;
import ca.ulaval.glo4003.domain.factories.SportFactory;
import ca.ulaval.glo4003.domain.pojos.persistable.PersistableGame;
import ca.ulaval.glo4003.domain.pojos.persistable.PersistableSport;
import ca.ulaval.glo4003.persistence.daos.SportDao;

@RunWith(MockitoJUnitRunner.class)
public class SportRepositoryTest {
	private static final String PARAMETER_SPORT = "Sport";
	private static final String DTO_SPORT = "Another Sport";

	private SportDto sportDto;

	@Mock
	private PersistableSport sport;

	@Mock
	private SportDao sportDao;

	@Mock
	private List<PersistableGame> gameList;

	@Mock
	private IGameRepository gameRepository;

	@Mock
	private SportFactory sportFactory;

	@InjectMocks
	private SportRepository repository;

	@Before()
	public void setUp() throws Exception {
		sportDto = new SportDto(DTO_SPORT);
		when(sportDao.get(PARAMETER_SPORT)).thenReturn(sportDto);
		when(sportFactory.instantiateSport(DTO_SPORT, gameList)).thenReturn(sport);
		when(gameRepository.getGamesScheduledForSport(PARAMETER_SPORT)).thenReturn(gameList);
	}

	@Test
	public void getSportByName_gets_sport_data_from_dao() throws Exception {
		repository.getSportByName(PARAMETER_SPORT);

		verify(sportDao).get(PARAMETER_SPORT);
	}

	@Test
	public void getSportByName_gets_game_scheduled_in_sport_from_GameRepository() throws Exception {
		repository.getSportByName(PARAMETER_SPORT);

		verify(gameRepository).getGamesScheduledForSport(PARAMETER_SPORT);
	}

	@Test
	public void getSportByName_instantiate_sport_using_factory() throws Exception {
		repository.getSportByName(PARAMETER_SPORT);

		verify(sportFactory).instantiateSport(DTO_SPORT, gameList);
	}

	@Test
	public void getSportByName_returns_sport_instantiated_by_factory() throws Exception {
		PersistableSport sportReturned = repository.getSportByName(PARAMETER_SPORT);

		Assert.assertSame(sport, sportReturned);
	}
}
