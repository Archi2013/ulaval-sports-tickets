package ca.ulaval.glo4003.domain.utilities;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

public abstract class PropertiesFileMapper {
	protected Properties properties = new Properties();
	protected String propertiesFileName;

	protected void loadProperties() {
		try (InputStream input = getClass().getResourceAsStream("/" + propertiesFileName);) {
			properties.load(input);
		} catch (NullPointerException | IOException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	protected String getValue(String key) throws KeyValueDoesntExistException {
		if (properties.isEmpty()) {
			loadProperties();
		}
		String sportName = properties.getProperty(key);
		if (sportName != null) {
			return sportName;
		}
		throw new KeyValueDoesntExistException();
	}

	protected String getKey(String value) throws KeyValueDoesntExistException {
		if (properties.isEmpty()) {
			loadProperties();
		}
		Set<Object> keySet = properties.keySet();
		for (Object key : keySet) {
			if (properties.getProperty(key.toString()).equals(value)) {
				return key.toString();
			}
		}
		throw new KeyValueDoesntExistException();
	}
	
	protected List<String> getAllValues() {
		if (properties.isEmpty()) {
			loadProperties();
		}
		List<String> list = new ArrayList<>();
		for (Object key : properties.keySet()) {
			list.add(properties.getProperty(key.toString()));
		}
		return list;
	}
	
	protected List<String> getAllKeys() {
		if (properties.isEmpty()) {
			loadProperties();
		}
		List<String> list = new ArrayList<>();
		for (Object key : properties.keySet()) {
			list.add(key.toString());
		}
		return list;
	}
}
