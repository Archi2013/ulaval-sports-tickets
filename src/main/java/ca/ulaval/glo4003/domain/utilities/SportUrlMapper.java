package ca.ulaval.glo4003.domain.utilities;

public interface SportUrlMapper {
	public String getUrl(String sportName);

	public String getSportName(String sportUrl) throws NoSportForUrlException;
}
