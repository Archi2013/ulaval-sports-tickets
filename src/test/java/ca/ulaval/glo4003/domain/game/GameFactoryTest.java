package ca.ulaval.glo4003.domain.game;

import java.util.List;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4003.domain.tickets.Ticket;
import ca.ulaval.glo4003.utilities.persistence.Persistable;

@RunWith(MockitoJUnitRunner.class)
public class GameFactoryTest {
	private static final String AN_OPPONENT = "Opponents";
	private static final String A_LOCATION = "Location";
	private static final String A_SPORT = "Sport";
	private static final DateTime A_DATE = new DateTime(100);

	private GameDto data;

	private List<Ticket> tickets;

	@InjectMocks
	private GameFactory factory;

	@Before
	public void setUp() {
		data = new GameDto(AN_OPPONENT, A_DATE, A_SPORT, A_LOCATION);
	}

	@Test
	public void with_opponents_and_location_factory_instantiate_a_new_game() {
		Game game = factory.instantiateGame(AN_OPPONENT, A_LOCATION);

		Assert.assertSame(PersistableGame.class, game.getClass());
	}

	@Test
	public void with_complete_data_and_ticket_list_factory_reinstantiate_an_existing_game() {
		Game game = factory.instantiateGame(data, tickets);

		Assert.assertSame(PersistableGame.class, game.getClass());
	}
}
