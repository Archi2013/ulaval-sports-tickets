package ca.ulaval.glo4003.persistence.xml;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.xml.xpath.XPathExpressionException;

import ca.ulaval.glo4003.domain.dtos.SectionDto;
import ca.ulaval.glo4003.persistence.daos.GameDoesntExistException;
import ca.ulaval.glo4003.persistence.daos.SectionDao;
import ca.ulaval.glo4003.persistence.daos.SectionDoesntExistException;

public class XmlSectionDao implements SectionDao {

	@Inject
	private XmlDatabase database = XmlDatabase.getInstance();
	
	@Override
    public SectionDto get(int gameId, String sectionName) throws SectionDoesntExistException {
	    String xPath = "/base/tickets/ticket[gameID=\"" + gameId + "\"][section=\"" + sectionName + "\"]";
		
		int numberOfTickets = 0;
		String price = "";
		String admissionType = "";
		try {
			numberOfTickets = database.countNode(xPath);
			price = database.extractPath(xPath + "/price");
			admissionType = database.extractPath(xPath + "/type");
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		
		return new SectionDto(admissionType, sectionName, numberOfTickets, Double.parseDouble(price));
    }

	@Override
    public List<SectionDto> getAll(int gameId) throws GameDoesntExistException {
		String xPath = "/base/games-sections/game-section[@gameID=\"" + gameId + "\"]/sections/section";
		
		List<SectionDto> sections = new ArrayList<>();
		try {
			List<SimpleNode> nodes = database.extractNodeSet(xPath);
			for(SimpleNode node : nodes) {
				if (node.hasNode("name")) {
					SectionDto section = get(gameId, node.getNodeValue("name"));
					sections.add(section);
				}
			}
		} catch (XPathExpressionException | SectionDoesntExistException e) {
			e.printStackTrace();
		}
		return sections;
    }

}
