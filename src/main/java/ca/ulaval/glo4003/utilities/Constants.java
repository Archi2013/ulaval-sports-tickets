package ca.ulaval.glo4003.utilities;

import java.util.List;

import javax.inject.Inject;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import ca.ulaval.glo4003.domain.sports.SportDao;

@Component
public class Constants {

	public static final String LONG_DATE_TIME_FORMAT_FR = "d MMMM yyyy à HH'h'mm z";
	public static final String CURRENCY = "CDN$";

	@Inject
	private SportDao sportDao;

	public List<String> getSportList() {
		return sportDao.getAllSportNames();
	}

	public String toLongDateTimeFormatFR(DateTime dateTime) {
		return dateTime.toString(LONG_DATE_TIME_FORMAT_FR);
	}
}
