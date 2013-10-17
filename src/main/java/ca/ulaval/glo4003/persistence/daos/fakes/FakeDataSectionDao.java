package ca.ulaval.glo4003.persistence.daos.fakes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import ca.ulaval.glo4003.domain.dtos.GameDto;
import ca.ulaval.glo4003.domain.dtos.SectionDto;
import ca.ulaval.glo4003.domain.dtos.TicketDto;
import ca.ulaval.glo4003.persistence.daos.GameDoesntExistException;
import ca.ulaval.glo4003.persistence.daos.SectionDao;
import ca.ulaval.glo4003.persistence.daos.SectionDoesntExistException;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Maps;

//@Repository
public class FakeDataSectionDao implements SectionDao {

	@Inject
	private FakeDatabase database;

	@Override
	public List<SectionDto> getAll(int gameId) throws GameDoesntExistException {
		Map<String, SectionDto> sections = createSections(gameId);
		return new ArrayList<SectionDto>(sections.values());
	}

	@Override
	public SectionDto get(int gameId, String sectionName) throws SectionDoesntExistException {
		try {
			Map<String, SectionDto> sections = createSections(gameId);
			return sections.get(sectionName);
		} catch (GameDoesntExistException e) {
			throw new SectionDoesntExistException();
		}
	}

	private Map<String, SectionDto> createSections(int gameId) throws GameDoesntExistException {
		GameDto game = database.getGame(gameId);
		List<TicketDto> tickets = game.getTickets();
		Map<String, SectionDto> sections = convertListTicketDtoToListOfSections(tickets);
		return sections;
	}

	private Map<String, SectionDto> convertListTicketDtoToListOfSections(List<TicketDto> ticketDtos) {
		ArrayListMultimap<String, TicketDto> map = ArrayListMultimap.create();
		for (TicketDto ticket : ticketDtos) {
			String section = ticket.section;
			map.put(section, ticket);
		}
		Map<String, SectionDto> sections = Maps.newHashMap();
		for (String key : map.keySet()) {
			String admissionType = key.split(",")[0];
			String sectionName = key.split(",")[1];
			int numberOfTickets = map.get(key).size();
			double price = map.get(key).get(0).getPrice();
			SectionDto section = new SectionDto(admissionType, sectionName, numberOfTickets, price);
			sections.put(key, section);
		}
		return sections;
	}

}
