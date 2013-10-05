package ca.ulaval.glo4003.domain.utilities;

public interface SportUrlMapper {
	public String getSportUrl(String sportName) throws SportDoesntExistInPropertiesFileException;

	public String getSportName(String sportUrl) throws SportDoesntExistInPropertiesFileException;
}
