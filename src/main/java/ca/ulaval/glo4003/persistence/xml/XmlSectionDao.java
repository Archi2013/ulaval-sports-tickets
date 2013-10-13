package ca.ulaval.glo4003.persistence.xml;

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
    public SectionDto get(int gameId, String admissionType, String sectionName) throws SectionDoesntExistException {
	    String xPath = "/base/games/game[\"" + gameId + "\"]/section[\"" + sectionName + "\"]";
		
		int numberOfTickets = 0;
		try {
			numberOfTickets = database.countNode(xPath);
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new SectionDto(admissionType, sectionName, numberOfTickets, 0.00f);
    }

	@Override
    public List<SectionDto> getAll(int gameId) throws GameDoesntExistException {
	    // TODO Auto-generated method stub
	    return null;
    }

}
