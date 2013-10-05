package ca.ulaval.glo4003.repositories;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4003.domain.dtos.SportDto;
import ca.ulaval.glo4003.domain.factories.SportFactory;
import ca.ulaval.glo4003.domain.pojos.Sport;
import ca.ulaval.glo4003.domain.repositories.SportRepository;
import ca.ulaval.glo4003.persistence.dao.SportDao;

@RunWith(MockitoJUnitRunner.class)
public class SportRepositoryTest {
	private static final String PARAMETER_SPORT = "Sport";
	private static final String DTO_SPORT = "Another Sport";

	private SportDto sportDto;

	@Mock
	private Sport sport;

	@Mock
	private SportDao sportDao;

	@Mock
	private SportFactory sportFactory;

	@InjectMocks
	private SportRepository repository;

	@Before()
	public void setUp() {
		sportDto = new SportDto(DTO_SPORT);
		when(sportDao.get(PARAMETER_SPORT)).thenReturn(sportDto);
		when(sportFactory.instantiateSport(DTO_SPORT)).thenReturn(sport);
	}

	@Test
	public void getSportByName_gets_sport_data_from_dao() {
		repository.getSportByName(PARAMETER_SPORT);

		verify(sportDao).get(PARAMETER_SPORT);
	}

	@Test
	public void getSportByName_instantiate_sport_using_factory() {
		repository.getSportByName(PARAMETER_SPORT);

		verify(sportFactory).instantiateSport(DTO_SPORT);
	}

	@Test
	public void getSportByName_returns_sport_instantiated_by_factory() {
		Sport sportReturned = repository.getSportByName(PARAMETER_SPORT);

		Assert.assertSame(sport, sportReturned);
	}
}
