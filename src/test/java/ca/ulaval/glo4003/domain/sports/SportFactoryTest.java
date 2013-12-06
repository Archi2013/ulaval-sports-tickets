package ca.ulaval.glo4003.domain.sports;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4003.domain.game.Game;

@RunWith(MockitoJUnitRunner.class)
public class SportFactoryTest {
	private static final String A_SPORT = "Sport";
	private List<Game> gameList = new ArrayList<>();

	@InjectMocks
	private SportFactory factory;

	@Test
	public void with_a_sportName_and_a_game_list_factory_instantiate_a_persistableSport() {
		Sport sport = factory.instantiateSport(A_SPORT, gameList);

		Assert.assertSame(PersistableSport.class, sport.getClass());
	}
}
