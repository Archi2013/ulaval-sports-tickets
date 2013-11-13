package ca.ulaval.glo4003.persistence.xml;

import java.util.List;

import javax.naming.directory.NoSuchAttributeException;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.glo4003.domain.dtos.TicketDto;
import ca.ulaval.glo4003.persistence.daos.GameDoesntExistException;

@RunWith(MockitoJUnitRunner.class)
public class XmlTicketDaoTest {

	private XmlDatabase database;

	private XmlTicketDao ticketDao;

	@Before
	public void setUp() {
		ticketDao = new XmlTicketDao("resources/DemoData.xml");
	}

	@Test
	public void can_find_correct_game_id_given_sportName_and_gameDate() throws NumberFormatException,
			NoSuchAttributeException, GameDoesntExistException {
		Long gameId = ticketDao.getGameID("Football", new DateTime(2014, 8, 24, 13, 0));

		Assert.assertEquals(1, gameId.intValue());
	}

	@Ignore
	@Test
	public void searching_game_tickets_with_sportName_dans_gameDate_gives_same_result_as_with_gameID()
			throws GameDoesntExistException {
		List<TicketDto> ticketsBySportName = ticketDao.getTicketsForGame("football", new DateTime(2014, 8, 24, 13, 0));
		List<TicketDto> ticketsByGameId = ticketDao.getAvailableTicketsForGame(new Long(1));

		Assert.assertEquals(ticketsBySportName, ticketsByGameId);
	}

}
