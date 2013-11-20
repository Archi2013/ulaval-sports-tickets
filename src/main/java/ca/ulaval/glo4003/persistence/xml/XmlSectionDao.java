package ca.ulaval.glo4003.persistence.xml;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.naming.directory.NoSuchAttributeException;
import javax.xml.xpath.XPathExpressionException;

import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.domain.dtos.SectionDto;
import ca.ulaval.glo4003.persistence.daos.GameDoesntExistException;
import ca.ulaval.glo4003.persistence.daos.SectionDao;
import ca.ulaval.glo4003.persistence.daos.SectionDoesntExistException;

@Component
public class XmlSectionDao implements SectionDao {

	private static final String GENERAL_KEYWORD = "Générale";
	private static final String SECTIONS_XPATH = "/base/tickets";
	private final static String SECTION_XPATH = SECTIONS_XPATH + "/ticket";

	private final static String SECTION_XPATH_SPORT = SECTION_XPATH + "[sportName=\"%s\"][gameDate=\"%s\"][section=\"%s\"]";
	private final static String SECTION_XPATH_AVAILABLE_SPORT = SECTION_XPATH
			+ "[sportName=\"%s\"][gameDate=\"%s\"][section=\"%s\"][available=\"%s\"]";

	private XmlDatabase database;
	private Map<Long, Set<String>> sectionCache;

	public XmlSectionDao() {
		database = XmlDatabase.getInstance();
	}

	public XmlSectionDao(String filename) {
		database = XmlDatabase.getUniqueInstance(filename);
	}

	@Override
	public SectionDto get(String sportName, String gameDate, String sectionName) throws SectionDoesntExistException {
		String xPath = String.format(SECTION_XPATH_SPORT, sportName, gameDate, sectionName);
		return getWithClause(sectionName, xPath);
	}

	@Override
	public SectionDto getAvailable(String sportName, String gameDate, String sectionName) throws SectionDoesntExistException {
		String xPath = String.format(SECTION_XPATH_AVAILABLE_SPORT, sportName, gameDate, sectionName, true);
		return getWithClause(sectionName, xPath);
	}

	@Override
	public List<SectionDto> getAll(String sportName, String gameDate) throws GameDoesntExistException {
		try {
			return convertToSectionDtos(sportName, gameDate, getAllSections());
		} catch (NoSuchAttributeException | SectionDoesntExistException e) {
			throw new XmlIntegrityException(e);
		}
	}

	@Override
	public List<SectionDto> getAllAvailable(String sportName, String gameDate) throws GameDoesntExistException {
		try {
			return convertToAvailableSectionDtos(sportName, gameDate, getAllSections());
		} catch (NoSuchAttributeException | SectionDoesntExistException e) {
			throw new XmlIntegrityException(e);
		}
	}

	private List<SectionDto> convertToSectionDtos(String sportName, String gameDate, Set<String> sectionNames)
			throws SectionDoesntExistException, NoSuchAttributeException {
		List<SectionDto> sections = new ArrayList<>();
		for (String sectionName : sectionNames) {
			SectionDto section = get(sportName, gameDate, sectionName);
			sections.add(section);
		}
		return sections;
	}

	private List<SectionDto> convertToAvailableSectionDtos(String sportName, String gameDate, Set<String> sectionNames)
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

			List<String> seats = new ArrayList<>();
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

	private Set<String> getSections(Long gameId) throws SectionDoesntExistException {
		if (sectionCache == null) {
			initCache();
		}
		if (sectionCache.containsKey(gameId)) {
			return sectionCache.get(gameId);
		}
		throw new SectionDoesntExistException();
	}

	private synchronized void initCache() {
		sectionCache = Collections.synchronizedMap(new HashMap<Long, Set<String>>());

		String xPath = String.format("/base/tickets/ticket");
		try {
			List<SimpleNode> nodes = database.extractNodeSet(xPath);
			for (SimpleNode node : nodes) {
				putSectionInCache(node);
			}
		} catch (XPathExpressionException | NoSuchAttributeException e) {
			throw new XmlIntegrityException(e);
		}
	}

	private void putSectionInCache(SimpleNode node) throws NoSuchAttributeException {
		Long gameId = Long.parseLong(node.getNodeValue("gameID"));
		if (!sectionCache.containsKey(gameId)) {
			sectionCache.put(gameId, new LinkedHashSet<String>());
		}
		Set<String> sections = sectionCache.get(gameId);
		sections.add(node.hasNode("section") ? node.getNodeValue("section") : GENERAL_KEYWORD);
	}

	@Override
	public Set<String> getAllSections() {
		if (sectionCache == null) {
			initCache();
		}
		Set<String> sections = new HashSet<>();
		for (Set<String> section : sectionCache.values()) {
			sections.addAll(section);
		}
		return sections;
	}

	@Deprecated
	private List<SectionDto> convertToSectionDtos(Long gameId, Set<String> sectionNames) throws SectionDoesntExistException,
			NoSuchAttributeException {
		List<SectionDto> sections = new ArrayList<>();
		for (String sectionName : sectionNames) {
			SectionDto section = get(gameId, sectionName);
			sections.add(section);
		}
		return sections;
	}

	@Deprecated
	private List<SectionDto> convertToAvailableSectionDtos(Long gameId, Set<String> sectionNames)
			throws SectionDoesntExistException, NoSuchAttributeException {
		List<SectionDto> sections = new ArrayList<>();
		for (String sectionName : sectionNames) {
			SectionDto section = getAvailable(gameId, sectionName);
			sections.add(section);
		}
		return sections;
	}

	@Override
	@Deprecated
	public SectionDto get(Long gameId, String sectionName) throws SectionDoesntExistException {
		String sectionClause = GENERAL_KEYWORD.equals(sectionName) ? "[not(section)]" : "[section=\"" + sectionName + "\"]";
		String xPath = "/base/tickets/ticket[gameID=\"" + gameId + "\"]" + sectionClause;

		return getWithClause(sectionName, xPath);
	}

	@Override
	@Deprecated
	public SectionDto getAvailable(Long gameId, String sectionName) throws SectionDoesntExistException {
		String sectionClause = GENERAL_KEYWORD.equals(sectionName) ? "[not(section)]" : "[section=\"" + sectionName + "\"]";
		String xPath = "/base/tickets/ticket[gameID=\"" + gameId + "\"]" + sectionClause + "[available='true']";

		return getWithClause(sectionName, xPath);
	}

	@Override
	@Deprecated
	public List<SectionDto> getAll(Long gameId) throws GameDoesntExistException {
		try {
			Set<String> sections = getSections(gameId);
			return convertToSectionDtos(gameId, sections);
		} catch (SectionDoesntExistException | NoSuchAttributeException e) {
			throw new XmlIntegrityException(e);
		}
	}

	@Override
	@Deprecated
	public List<SectionDto> getAllAvailable(Long gameId) throws GameDoesntExistException {
		try {
			Set<String> sections = getSections(gameId);
			return convertToAvailableSectionDtos(gameId, sections);
		} catch (SectionDoesntExistException | NoSuchAttributeException e) {
			throw new XmlIntegrityException(e);
		}
	}

}
