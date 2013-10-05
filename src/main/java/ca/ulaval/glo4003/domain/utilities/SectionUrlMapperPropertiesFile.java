package ca.ulaval.glo4003.domain.utilities;

import org.springframework.stereotype.Component;

@Component
public class SectionUrlMapperPropertiesFile extends PropertiesFileMapper implements SectionUrlMapper {

	public SectionUrlMapperPropertiesFile() {
		this.propertiesFileName = "section-url.properties";
	}

	@Override
	public String getSectionUrl(String admissionType, String sectionName) throws SectionDoesntExistInPropertiesFileException {
		try {
			String value = createValueFromSectionInfo(admissionType, sectionName);
			return getKey(value);
		} catch (Exception e) {
			throw new SectionDoesntExistInPropertiesFileException();
		}
	}

	@Override
	public TicketType getTicketType(String sectionUrl) throws SectionDoesntExistInPropertiesFileException {
		try {
			String value = getValue(sectionUrl);
			return TicketType.fromString(value);
		} catch (Exception e) {
			throw new SectionDoesntExistInPropertiesFileException();
		}
	}

	private String createValueFromSectionInfo(String admissionType, String sectionName) {
		return admissionType + ":" + sectionName;
	}
}
