package ca.ulaval.glo4003.domain.utilities;

import java.util.List;

public interface DisplayedPeriodMapper {
	public String getName(Integer numberOfDay) throws DisplayedPeriodDoesntExistInPropertiesFileException;

	public Integer getNumberOfDays(String name) throws DisplayedPeriodDoesntExistInPropertiesFileException;
	
	public List<String> getAllNames();
	
	public List<Integer> getAllNumberOfDays();
}
