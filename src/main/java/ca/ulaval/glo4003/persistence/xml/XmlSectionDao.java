package ca.ulaval.glo4003.persistence.xml;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.naming.directory.NoSuchAttributeException;
import javax.xml.xpath.XPathExpressionException;

import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.domain.dtos.SectionDto;
import ca.ulaval.glo4003.domain.utilities.TicketType;
import ca.ulaval.glo4003.persistence.daos.GameDoesntExistException;
import ca.ulaval.glo4003.persistence.daos.SectionDao;
import ca.ulaval.glo4003.persistence.daos.SectionDoesntExistException;

@Component
public class XmlSectionDao implements SectionDao {

	// @Inject
	private XmlDatabase database;

	public XmlSectionDao() {
		database = XmlDatabase.getInstance();
	}

	public XmlSectionDao(String filename) {
		database = XmlDatabase.getUniqueInstance(filename);
	}

	@Override
	public SectionDto get(Long gameId, String sectionName) throws SectionDoesntExistException {
		String xPath = "/base/tickets/ticket[gameID=\"" + gameId + "\"][section=\"" + sectionName + "\"]";

		try {
			int numberOfTickets = database.countNode(xPath);
			if (numberOfTickets == 0) {
				throw new SectionDoesntExistException();
			}
			String price = database.extractPath(xPath + "/price");
			String admissionType = database.extractPath(xPath + "/type");
			
			// TODO à remplacer par des vrais sièges
			List<String> seats = new ArrayList<>();
			seats.add("2A");
			seats.add("375");
			seats.add("X1");
			seats.add("L87A");

			return new SectionDto(admissionType, sectionName, numberOfTickets, Double.parseDouble(price), seats);
		} catch (XPathExpressionException e) {
			throw new XmlIntegrityException(e);
		}
	}

	@Override
	public List<SectionDto> getAll(Long gameId) throws GameDoesntExistException {
		String xPath = "/base/games-sections/game-section[@gameID=\"" + gameId + "\"]/sections/section";

		try {
			List<SimpleNode> nodes = database.extractNodeSet(xPath);
			return convertNodesToSectionDtos(gameId, nodes);
		} catch (XPathExpressionException | SectionDoesntExistException | NoSuchAttributeException e) {
			throw new XmlIntegrityException(e);
		}
	}

	@Override
	public Set<TicketType> getAllTicketTypes() {
		Set<TicketType> ticketTypes = new HashSet<>();
		ticketTypes.add(new TicketType("VIP", "Loge Nord-Est"));
		ticketTypes.add(new TicketType("VIP", "Loge Sud-Est"));
		ticketTypes.add(new TicketType("VIP", "Front Row"));
		ticketTypes.add(new TicketType("VIP", "Rouges"));
		ticketTypes.add(new TicketType("VIP", "Indigo"));
		ticketTypes.add(new TicketType("VIP", "Poupres"));
		ticketTypes.add(new TicketType("VIP", "Bordeaux"));
		ticketTypes.add(new TicketType("VIP", "Loge A"));
		ticketTypes.add(new TicketType("VIP", "Loge B"));
		ticketTypes.add(new TicketType("VIP", "Loge C"));
		ticketTypes.add(new TicketType("VIP", "Loge E"));
		ticketTypes.add(new TicketType("Générale", "Générale"));
		ticketTypes.add(new TicketType("Générale", "Cyan"));
		ticketTypes.add(new TicketType("Générale", "Indigo"));
		return ticketTypes;
	}
	
	private List<SectionDto> convertNodesToSectionDtos(Long gameId, List<SimpleNode> nodes)
			throws SectionDoesntExistException, NoSuchAttributeException {
		List<SectionDto> sections = new ArrayList<>();
		for (SimpleNode node : nodes) {
			SectionDto section = get(gameId, node.getNodeValue("name"));
			sections.add(section);
		}
		return sections;
	}
}
