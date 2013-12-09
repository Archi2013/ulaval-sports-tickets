package ca.ulaval.glo4003.utilities.urlmapper;

import ca.ulaval.glo4003.exceptions.NoSportForUrlException;

public interface SportUrlMapper {
	public String getUrl(String sportName);

	public String getSportName(String sportUrl) throws NoSportForUrlException;
}
