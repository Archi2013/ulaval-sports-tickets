package ca.ulaval.glo4003.domain.repositories;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
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
import ca.ulaval.glo4003.domain.pojos.Game;
import ca.ulaval.glo4003.domain.pojos.Sport;
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

	private List<Game> gameList;

	@Mock
	private IGameRepository gameRepository;

	@Mock
	private SportFactory sportFactory;

	@InjectMocks
	private SportRepository repository;

	@Before()
	public void setUp() throws Exception {
		gameList = new ArrayList<>();
		sportDto = new SportDto(DTO_SPORT);
		when(sportDao.get(PARAMETER_SPORT)).thenReturn(sportDto);
		when(sportFactory.instantiateSport(DTO_SPORT, gameList)).thenReturn(sport);
		when(gameRepository.getGamesScheduledForSport(PARAMETER_SPORT)).thenReturn(gameList);
		when(sport.saveDataInDTO()).thenReturn(sportDto);
	}

	@Test
	public void getSportByName_instantiate_sport_using_factory() throws Exception {
		repository.getSportByName(PARAMETER_SPORT);

		verify(sportFactory).instantiateSport(DTO_SPORT, gameList);
	}

	@Test
	public void getSportByName_returns_sport_instantiated_by_factory() throws Exception {
		Sport sportReturned = repository.getSportByName(PARAMETER_SPORT);

		Assert.assertSame(sport, sportReturned);
	}

	@Test
	public void commit_tells_the_match_repo_to_commit_too() throws Exception {
		repository.commit();

		verify(gameRepository).commit();
	}

	@Test
	public void commit_sends_active_objects_to_dao_to_save_changes() throws Exception {
		repository.getSportByName(PARAMETER_SPORT);

		repository.commit();

		verify(sportDao).saveChanges(sportDto);
	}
}
