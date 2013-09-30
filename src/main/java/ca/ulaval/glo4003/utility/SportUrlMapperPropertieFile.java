package ca.ulaval.glo4003.utility;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

import org.springframework.stereotype.Component;

@Component
public class SportUrlMapperPropertieFile implements SportUrlMapper {
	private String propertiesFileName = "sport-url.properties";
	private Properties properties = new Properties();
	
	@Override
	public String getSportUrl(String sportName) throws RuntimeException, SportDoesntExistInPropertieFileException {
		if ( !properties.isEmpty() ) {
			return getSportUrlFromProperties(sportName);
		} else {
			try(InputStream input = getClass().getResourceAsStream("/" + propertiesFileName);) {
				properties.load(input);
				return getSportUrlFromProperties(sportName);
			} catch (NullPointerException | IOException e) {
				e.printStackTrace();
				throw new RuntimeException();
			}
		}
	}

	private String getSportUrlFromProperties(String sportName) throws SportDoesntExistInPropertieFileException {
		Set<Object> keySet = properties.keySet();
		for(Object key : keySet) {
			if (properties.getProperty(key.toString()).equals(sportName)) {
				return key.toString();
			}
		}
		throw new SportDoesntExistInPropertieFileException();
	}
	
	@Override
	public String getSportName(String sportUrl) throws RuntimeException, SportDoesntExistInPropertieFileException {
		if ( !properties.isEmpty() ) {
			return getSportNameFromProperties(sportUrl);
		} else {
			try(InputStream input = getClass().getResourceAsStream("/" + propertiesFileName);) {
				properties.load(input);
				return getSportNameFromProperties(sportUrl);
			} catch (NullPointerException | IOException e) {
				e.printStackTrace();
				throw new RuntimeException();
			}
		}
	}

	private String getSportNameFromProperties(String sportUrl)
			throws SportDoesntExistInPropertieFileException {
		String sportName = properties.getProperty(sportUrl);
		if (sportName != null) {
			return sportName;
		}
		throw new SportDoesntExistInPropertieFileException();
	}

	public void setPropertiesFileName(String propertiesFileName) {
		this.propertiesFileName = propertiesFileName;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}
}
