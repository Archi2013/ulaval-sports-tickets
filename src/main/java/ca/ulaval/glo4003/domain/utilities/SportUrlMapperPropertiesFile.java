package ca.ulaval.glo4003.domain.utilities;

import org.springframework.stereotype.Component;

@Component
public class SportUrlMapperPropertiesFile extends PropertiesFileMapper implements SportUrlMapper {

	public SportUrlMapperPropertiesFile() {
		this.propertiesFileName = "sport-url.properties";
	}

	@Override
	public String getSportUrl(String sportName) throws RuntimeException, SportDoesntExistInPropertiesFileException {
		try {
			return getKey(sportName);
		} catch (KeyValueDoesntExistException e) {
			throw new SportDoesntExistInPropertiesFileException();
		}
	}

	@Override
	public String getSportName(String sportUrl) throws RuntimeException, SportDoesntExistInPropertiesFileException {
		try {
			return getValue(sportUrl);
		} catch (KeyValueDoesntExistException e) {
			throw new SportDoesntExistInPropertiesFileException();
		}
	}
}
