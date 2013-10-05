package ca.ulaval.glo4003.domain.utilities;

public interface SportUrlMapper {
	public String getSportUrl(String sportName) throws RuntimeException, SportDoesntExistInPropertieFileException;
	public String getSportName(String sportUrl) throws RuntimeException, SportDoesntExistInPropertieFileException;
}
