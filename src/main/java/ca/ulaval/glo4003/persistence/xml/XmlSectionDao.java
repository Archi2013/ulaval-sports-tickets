package ca.ulaval.glo4003.persistence.xml;

import java.util.ArrayList;
import java.util.List;

import javax.naming.directory.NoSuchAttributeException;
import javax.xml.xpath.XPathExpressionException;

import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.domain.dtos.SectionDto;
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
	public SectionDto get(int gameId, String sectionName) throws SectionDoesntExistException {
		String xPath = "/base/tickets/ticket[gameID=\"" + gameId + "\"][section=\"" + sectionName + "\"]";

		try {
			int numberOfTickets = database.countNode(xPath);
			if (numberOfTickets == 0) {
				throw new SectionDoesntExistException();
			}
			String price = database.extractPath(xPath + "/price");
			String admissionType = database.extractPath(xPath + "/type");

			return new SectionDto(admissionType, sectionName, numberOfTickets, Double.parseDouble(price));
		} catch (XPathExpressionException e) {
			throw new XmlIntegrityException(e);
		}
	}

	@Override
	public List<SectionDto> getAll(int gameId) throws GameDoesntExistException {
		String xPath = "/base/games-sections/game-section[@gameID=\"" + gameId + "\"]/sections/section";

		try {
			List<SimpleNode> nodes = database.extractNodeSet(xPath);
			return convertNodesToSectionDtos(gameId, nodes);
		} catch (XPathExpressionException | SectionDoesntExistException | NoSuchAttributeException e) {
			throw new XmlIntegrityException(e);
		}
	}

	private List<SectionDto> convertNodesToSectionDtos(int gameId, List<SimpleNode> nodes)
			throws SectionDoesntExistException, NoSuchAttributeException {
		List<SectionDto> sections = new ArrayList<>();
		for (SimpleNode node : nodes) {
			SectionDto section = get(gameId, node.getNodeValue("name"));
			sections.add(section);
		}
		return sections;
	}

}