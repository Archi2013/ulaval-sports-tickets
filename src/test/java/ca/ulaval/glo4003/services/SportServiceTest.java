package ca.ulaval.glo4003.services;

import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4003.domain.sports.SportDao;
import ca.ulaval.glo4003.domain.sports.SportDto;
import ca.ulaval.glo4003.presentation.viewmodels.factories.SportsViewModelFactory;

@RunWith(MockitoJUnitRunner.class)
public class SportServiceTest {

	@Mock
	private SportDao sportDao;

	@Mock
	private SportsViewModelFactory sportsViewModelFactory;

	@InjectMocks
	private SportService sportService;

	private List<SportDto> sports;

	@Before
	public void setUp() throws Exception {
		when(sportDao.getAll()).thenReturn(sports);
	}

	@Test
	public void testGetSports() throws Exception {
		sportService.getSports();

		verify(sportsViewModelFactory, atLeastOnce()).createViewModel(anyListOf(SportDto.class));
	}
}