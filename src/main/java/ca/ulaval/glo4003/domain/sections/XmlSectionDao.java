package ca.ulaval.glo4003.domain.sections;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.naming.directory.NoSuchAttributeException;
import javax.xml.xpath.XPathExpressionException;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.exceptions.GameDoesntExistException;
import ca.ulaval.glo4003.exceptions.SectionDoesntExistException;
import ca.ulaval.glo4003.utilities.persistence.SimpleNode;
import ca.ulaval.glo4003.utilities.persistence.XmlDatabase;
import ca.ulaval.glo4003.utilities.persistence.XmlIntegrityException;

@Component
public class XmlSectionDao implements SectionDao {
	public static final String DATE_PATTERN = "yyyy/MM/dd HH:mm z";
	private static final String SECTIONS_XPATH = "/base/tickets";
	private final static String SECTION_XPATH = SECTIONS_XPATH + "/ticket";

	private final static String SECTION_FOR_GAME_XPATH_SPORT = SECTION_XPATH + "[sportName=\"%s\"][gameDate=\"%s\"]";
	private final static String SECTION_XPATH_SPORT = SECTION_FOR_GAME_XPATH_SPORT + "[section=\"%s\"]";
	private final static String SECTION_XPATH_AVAILABLE_SPORT = SECTION_XPATH_SPORT + "[available=\"%s\"]";

	private XmlDatabase database;

	public XmlSectionDao() {
		database = XmlDatabase.getInstance();
	}

	public XmlSectionDao(String filename) {
		database = XmlDatabase.getUniqueInstance(filename);
	}

	@Override
	public SectionDto get(String sportName, DateTime gameDate, String sectionName) throws SectionDoesntExistException {
		String xPath = String.format(SECTION_XPATH_SPORT, sportName, toString(gameDate), sectionName);
		return getWithClause(sectionName, xPath);
	}

	@Override
	public SectionDto getAvailable(String sportName, DateTime gameDate, String sectionName)
			throws SectionDoesntExistException {
		String xPath = String.format(SECTION_XPATH_AVAILABLE_SPORT, sportName, toString(gameDate), sectionName, true);
		return getWithClause(sectionName, xPath);
	}

	@Override
	public List<SectionDto> getAll(String sportName, DateTime gameDate) throws GameDoesntExistException {
		try {
			return convertToSectionDtos(sportName, gameDate, getAllSections());
		} catch (NoSuchAttributeException | SectionDoesntExistException e) {
			throw new XmlIntegrityException(e);
		}
	}

	@Override
	public List<SectionDto> getAllAvailable(String sportName, DateTime gameDate) throws GameDoesntExistException {
		try {
			return convertToAvailableSectionDtos(sportName, gameDate, getAllSections());
		} catch (NoSuchAttributeException | SectionDoesntExistException e) {
			throw new XmlIntegrityException(e);
		}
	}

	private List<SectionDto> convertToSectionDtos(String sportName, DateTime gameDate, Set<String> sectionNames)
			throws SectionDoesntExistException, NoSuchAttributeException {
		List<SectionDto> sections = new ArrayList<>();
		for (String sectionName : sectionNames) {
			SectionDto section = get(sportName, gameDate, sectionName);
			sections.add(section);
		}
		return sections;
	}

	private List<SectionDto> convertToAvailableSectionDtos(String sportName, DateTime gameDate, Set<String> sectionNames)
			throws SectionDoesntExistException, NoSuchAttributeException {
		List<SectionDto> sections = new ArrayList<>();
		for (String sectionName : sectionNames) {
			SectionDto section = getAvailable(sportName, gameDate, sectionName);
			sections.add(section);
		}
		return sections;
	}

	private SectionDto getWithClause(String sectionName, String xPathClause) throws SectionDoesntExistException {
		try {
			int numberOfTickets = database.countNode(xPathClause);
			if (numberOfTickets == 0) {
				throw new SectionDoesntExistException();
			}
			String price = database.extractPath(xPathClause + "/price");
			List<SimpleNode> nodes = database.extractNodeSet(xPathClause);

			Set<String> seats = new HashSet<>();
			for (SimpleNode node : nodes) {
				if (node.hasNode("seat")) {
					seats.add(node.getNodeValue("seat"));
				}
			}

			return new SectionDto(sectionName, numberOfTickets, Double.parseDouble(price), seats);
		} catch (XPathExpressionException | NoSuchAttributeException e) {
			throw new XmlIntegrityException(e);
		}
	}

	@Override
	public Set<String> getAllSections() {
		try {
	        return database.distinct(SECTION_XPATH, "section");
        } catch (XPathExpressionException e) {
	        throw new XmlIntegrityException();
        }
	}

	private String toString(DateTime date) {
		if (date == null) {
			return "";
		}
		return date.toString(DATE_PATTERN);
	}
}
