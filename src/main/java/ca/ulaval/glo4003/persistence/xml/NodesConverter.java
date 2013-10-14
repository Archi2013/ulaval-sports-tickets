package ca.ulaval.glo4003.persistence.xml;

import java.util.ArrayList;
import java.util.List;

import javax.naming.directory.NoSuchAttributeException;

public class NodesConverter {

	public static Integer toInteger(SimpleNode node, String key) throws NoSuchAttributeException {
		return Integer.parseInt(node.getNodeValue(key));
	}

	public static List<Integer> toIntegerList(List<SimpleNode> nodes, String key) throws NoSuchAttributeException {
		List<Integer> ids = new ArrayList<>();
		for (SimpleNode node : nodes) {
			ids.add(toInteger(node, key));
		}
		return ids;
	}
}
