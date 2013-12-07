package ca.ulaval.glo4003.utilities.time;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

@Component
public class UrlDateTimeFactory {

	public UrlDateTime create(DateTime gameDate) {
		return new UrlDateTime(gameDate);
	}

	public UrlDateTime create(String dateString) {
		return new UrlDateTime(dateString);
	}

}
