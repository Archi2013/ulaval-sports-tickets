package ca.ulaval.glo4003.domain.sections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.naming.directory.NoSuchAttributeException;
import javax.xml.xpath.XPathExpressionException;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.exceptions.GameDoesntExistException;
import ca.ulaval.glo4003.exceptions.SectionDoesntExistException;
import ca.ulaval.glo4003.sections.dto.SectionDto;
import ca.ulaval.glo4003.utilities.persistence.SimpleNode;
import ca.ulaval.glo4003.utilities.persistence.XmlDatabase;
import ca.ulaval.glo4003.utilities.persistence.XmlIntegrityException;

@Component
public class XmlSectionDao implements SectionDao {
	private static final String GENERAL_ADMISSION = "Générale";
	public static final String DATE_PATTERN = "yyyy/MM/dd HH:mm z";
	private static final String SECTIONS_XPATH = "/base/tickets";
	private final static String SECTION_XPATH = SECTIONS_XPATH + "/ticket";

	private final static String SECTION_FOR_GAME_XPATH_SPORT = SECTION_XPATH + "[sportName=\"%s\"][gameDate=\"%s\"]";
	private final static String SECTION_XPATH_SPORT = SECTION_FOR_GAME_XPATH_SPORT + "[section=\"%s\"]";
	private final static String SECTION_XPATH_AVAILABLE_SPORT = SECTION_XPATH_SPORT + "[available=\"%s\"]";
	
	private final static Set<String> SECTION_CACHE = Collections.synchronizedSet(new HashSet<String>());
	private static DateTime LAST_REFRESH = DateTime.now();

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
		return convertToSectionDtos(sportName, gameDate, getAllSections());
	}

	@Override
	public List<SectionDto> getAllAvailable(String sportName, DateTime gameDate) {
		return convertToAvailableSectionDtos(sportName, gameDate, getAllSections());
	}

	private List<SectionDto> convertToSectionDtos(String sportName, DateTime gameDate, Set<String> sectionNames) {
		List<SectionDto> sections = new ArrayList<>();
		for (String sectionName : sectionNames) {
			try {
				SectionDto section = get(sportName, gameDate, sectionName);
				sections.add(section);
			} catch (SectionDoesntExistException e) {
			}
		}
		return sections;
	}

	private List<SectionDto> convertToAvailableSectionDtos(String sportName, DateTime gameDate, Set<String> sectionNames) {
		List<SectionDto> sections = new ArrayList<>();
		for (String sectionName : sectionNames) {
			try {
				SectionDto section = getAvailable(sportName, gameDate, sectionName);
				sections.add(section);
			} catch (SectionDoesntExistException e) {
			}
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

			if (sectionName.equals(GENERAL_ADMISSION)) {
				return new SectionDto(sectionName, numberOfTickets, Double.parseDouble(price));
			} else {
				return new SectionDto(sectionName, numberOfTickets, Double.parseDouble(price), seats);
			}
		} catch (XPathExpressionException | NoSuchAttributeException e) {
			throw new XmlIntegrityException(e);
		}
	}

	@Override
	public Set<String> getAllSections() {
		if (SECTION_CACHE.isEmpty() || timeOutRefresh()) {
			LAST_REFRESH = DateTime.now();
			try {
				SECTION_CACHE.addAll(database.distinct(SECTION_XPATH, "section"));
	        } catch (XPathExpressionException e) {
		        throw new XmlIntegrityException();
	        }
		}
		return SECTION_CACHE;
	}

	private boolean timeOutRefresh() {
		return LAST_REFRESH.plusMinutes(5).isBefore(DateTime.now());
	}
	
	@Override
	public List<SectionDto> getAllSectionsForTicketKind(String sportName, DateTime gameDate, List<String> ticketKinds) {
		List<SectionDto> sections = new ArrayList<>();
		for(String sectionName : getSectionsFromTicketKinds(ticketKinds)) {
			try {
				SectionDto found = getAvailable(sportName, gameDate, sectionName);
				if (found.getNumberOfTickets() > 0) {
					sections.add(found);
				}
			} catch (SectionDoesntExistException e) {
				// Ignore empty sections
			}
		}
		return sections;
	}

	private String toString(DateTime date) {
		if (date == null) {
			return "";
		}
		return date.toString(DATE_PATTERN);
	}

	private List<String> getSectionsFromTicketKinds(List<String> ticketKinds) {
		List<String> sections = new ArrayList<>();
		if (ticketKinds.contains("WITH_SEAT")) {
			sections.addAll(getAllSections());
			sections.remove(GENERAL_ADMISSION);
		}
		if (ticketKinds.contains("GENERAL_ADMISSION")) {
			sections.add(GENERAL_ADMISSION);
		}
		return sections;
	}
}
