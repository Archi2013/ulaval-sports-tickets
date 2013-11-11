package ca.ulaval.glo4003.persistence.xml;

import static com.google.common.collect.Lists.transform;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.directory.NoSuchAttributeException;
import javax.xml.xpath.XPathExpressionException;

import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.domain.dtos.SportDto;
import ca.ulaval.glo4003.persistence.daos.SportAlreadyExistException;
import ca.ulaval.glo4003.persistence.daos.SportDao;
import ca.ulaval.glo4003.persistence.daos.SportDoesntExistException;

import com.google.common.base.Function;

@Component
public class XmlSportDao implements SportDao {

	private static final String SPORTS_XPATH = "/base/sports";
	private static final String SPORT_XPATH = SPORTS_XPATH + "/sport";
	private static final String SPORT_XPATH_ID = SPORT_XPATH + "[name=\"%s\"]";

	// @Inject
	private XmlDatabase database;

	public XmlSportDao() {
		database = XmlDatabase.getInstance();
	}

	XmlSportDao(String filename) {
		database = XmlDatabase.getUniqueInstance(filename);
	}

	@Override
	public List<SportDto> getAll() {
		try {
			List<SimpleNode> nodes = database.extractNodeSet(SPORT_XPATH);
			return convertNodesToSports(nodes);
		} catch (XPathExpressionException | NoSuchAttributeException | SportDoesntExistException e) {
			throw new XmlIntegrityException(e);
		}
	}

	@Override
	public List<String> getAllSportNames() {
		List<SportDto> sports = getAll();
		List<String> result = transform(sports, new Function<SportDto, String>() {
			@Override
			public String apply(SportDto dto) {
				return dto.getName();
			}
		});
		return result;
	}

	@Override
	public SportDto get(String sportName) throws SportDoesntExistException {
		try {
			String xPath = String.format(SPORT_XPATH_ID, sportName);
			SimpleNode node = database.extractNode(xPath);
			return convertNodeToSport(node);
		} catch (XPathExpressionException | NoSuchAttributeException e) {
			throw new XmlIntegrityException(e);
		}
	}

	@Override
	public void add(SportDto sport) throws SportAlreadyExistException {
		if (isIdExist(sport.getName())) {
			throw new SportAlreadyExistException();
		}
		SimpleNode simpleNode = convertSportToNode(sport);
		try {
			database.addNode(SPORTS_XPATH, simpleNode);
		} catch (XPathExpressionException e) {
			throw new XmlIntegrityException(e);
		}
	}

	private boolean isIdExist(String sportName) {
		String xPath = String.format(SPORT_XPATH_ID, sportName);
		return database.exist(xPath);
	}

	private List<SportDto> convertNodesToSports(List<SimpleNode> nodes) throws NoSuchAttributeException, SportDoesntExistException {
		List<SportDto> sports = new ArrayList<>();
		for (SimpleNode node : nodes) {
			sports.add(convertNodeToSport(node));
		}
		return sports;
	}

	private SimpleNode convertSportToNode(SportDto sport) {
		Map<String, String> nodes = new HashMap<>();
		nodes.put("name", sport.getName());
		SimpleNode simpleNode = new SimpleNode("sport", nodes);
		return simpleNode;
	}

	private SportDto convertNodeToSport(SimpleNode node) throws NoSuchAttributeException, SportDoesntExistException {
		if (node.hasNode("name"))
			return new SportDto(node.getNodeValue("name"));
		throw new SportDoesntExistException();
	}

	@Override
	public void commit() {
		database.commit();
	}
}
